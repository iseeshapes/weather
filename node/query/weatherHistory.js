var settings = {
  minutes : {
    updatePipeline : (pipeline) => {
      pipeline[1]['$project'].year = { $year: "$observationTimeUtc" };
      pipeline[1]['$project'].days = { $dayOfYear: "$observationTimeUtc" };
      pipeline[1]['$project'].hours = { $hour: "$observationTimeUtc" };
      pipeline[1]['$project'].minutes = { $minute: "$observationTimeUtc" };
      pipeline[2]['$group']['_id'] = {
        year: "$year",
        days: "$days",
        hours: "$hours",
        minutes: "$minutes"
      };
      pipeline[3]['$sort'] = { '_id.year': 1, '_id.days': 1, '_id.hours': 1, '_id.minutes': 1 };
    },
    setDate : (date, i) => {
        var minutes = date.getMinutes();
        date.setMilliseconds(0);
        date.setSeconds(0);
        date.setMinutes(minutes + i);
    }
  },
  minutes30 : {
    updatePipeline : (pipeline) => {
      pipeline[1]['$project'].year = { $year: "$observationTimeUtc" };
      pipeline[1]['$project'].days = { $dayOfYear: "$observationTimeUtc" };
      pipeline[1]['$project'].hours = { $hour: "$observationTimeUtc" };
      pipeline[1]['$project'].minutes = {
        $subtract: [
          {
            $minute: "$observationTimeUtc"
          }, {
            $mod: [{ $minute: "$observationTimeUtc" }, 30.0]
          }
        ]
      };
      pipeline[2]['$group']['_id'] = {
        year: "$year",
        days: "$days",
        hours: "$hours",
        minutes: "$minutes"
      };
      pipeline[3]['$sort'] = { '_id.year': 1, '_id.days': 1, '_id.hours': 1, '_id.minutes': 1 };
    },
    setDate : (date, i) => {
        var minutes = date.getMinutes();
        if (minutes < 30) {
          minutes = 0;
        } else {
          minutes = 30;
        }
        minutes += i * 30;
        date.setMilliseconds(0);
        date.setSeconds(0);
        date.setMinutes(minutes);
    }
  },
  hours : {
    updatePipeline : (pipeline) => {
      pipeline[1]['$project'].year = { $year: "$observationTimeUtc" };
      pipeline[1]['$project'].days = { $dayOfYear: "$observationTimeUtc" };
      pipeline[1]['$project'].hours = { $hour: "$observationTimeUtc" };
      pipeline[2]['$group']['_id'] = {
        year: "$year",
        days: "$days",
        hours: "$hours"
      };
      pipeline[3]['$sort'] = { '_id.year': 1, '_id.days': 1, '_id.hours': 1 };
    },
    setDate : (date, i) => {
        var hours = date.getHours();
        date.setMilliseconds(0);
        date.setSeconds(0);
        date.setMinutes(0);
        date.setHours(hours + i)
    }
  },
  days : {
    updatePipeline : (pipeline) => {
      pipeline[1]['$project'].year = { $year: "$observationTimeUtc" };
      pipeline[1]['$project'].days = { $dayOfYear: "$observationTimeUtc" };
      pipeline[2]['$group']['_id'] = {
        year: "$year",
        days: "$days"
      };
      pipeline[3]['$sort'] = { '_id.year': 1, '_id.days': 1 };
    },
    setDate : (date, i) => {
        var days = date.getDate();
        date.setMilliseconds(0);
        date.setSeconds(0);
        date.setMinutes(0);
        date.setHours(0)
        date.date(days + i)
    }
  },
  month : {
    updatePipeline : (pipeline) => {
      pipeline[1]['$project'].year = { $year: "$observationTimeUtc" };
      pipeline[1]['$project'].months = { month: "$observationTimeUtc" };
      pipeline[2]['$group']['_id'] = {
        year: "$year",
        months: "$months"
      };
      pipeline[3]['$sort'] = { '_id.year': 1, '_id.months': 1 };
    },
    setDate : (date, i) => {
        var months = date.getMonth();
        date.setMilliseconds(0);
        date.setSeconds(0);
        date.setMinutes(0);
        date.setHours(0)
        date.date(0)
        date.setMonth(months + i)
    }
  },
  year : {
    updatePipeline : (pipeline) => {
      pipeline[1]['$project'].year = { $year: "$observationTimeUtc" };
      pipeline[2]['$group']['_id'] = { year: "$year" };
      pipeline[3]['$sort'] = { '_id.year': 1 };
    },
    setDate : (date, i) => {
        var years = date.geYear();
        date.setMilliseconds(0);
        date.setSeconds(0);
        date.setMinutes(0);
        date.setHours(0)
        date.date(0)
        date.setMonth(0)
        date.setYear(years + i)
    }
  }
}

module.exports = async (collection, range, timeDivison) => {
  var timeDivisionSetting = settings[timeDivison];
  if (timeDivisionSetting == undefined) {
    throw { error : "Inknown time division " + timeDivison };
  }

  var pipeline = [
    {
      $match: {
        $and : [
          { observationTimeUtc : { $gt : range.start } },
          { observationTimeUtc : { $lt : range.end } }
        ]
      }
    },
    {
      $project : {
        temperature : "$outsideTemperature",
        humidity: "$outsideHumidity",
        windDirection: "$windDirection",
        windSpeed: "$windSpeed",
        windGust: "$windGust",
        rainRate: "$rainRate",
        light: "$light",
        uv: "$uv",
        pressure: "$relativeBarometer"
      }
    },
    {
      $group: {
        _id: null,
        temperature: { $avg: "$temperature" },
        humidity: { $avg: "$humidity"},
        windDirection: { $avg: "$windDirection" },
        windSpeed: { $avg: "$windSpeed" },
        windGust: { $avg: "$windGust" },
        light: { $avg: "$light" },
        uv: { $avg: "$uv" },
        pressure: { $avg: "$pressure" }
      }
    },
    {
      $sort: { }
    }
  ];
  timeDivisionSetting.updatePipeline(pipeline);
  var data = await collection.aggregate(pipeline).toArray();
  for (var i=0; i<data.length;i++) {
    var date = new Date (range.start);
    timeDivisionSetting.setDate (date, i);
    delete data[i]['_id'];
    data[i].time = date;
  }
  return data;
}
