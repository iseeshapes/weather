var settings = {
  minutes : {
    project: { $minute: "$observationTimeUtc" },
    setDate: (date, i) => {
        var minutes = date.getMinutes();
        date.setMinutes(minutes + i)
    }
  },
  hours : {
    project: { $hour: "$observationTimeUtc" },
    setDate: (date, i) => {
        var hours = date.getHours();
        date.setHours(hours + i)
    }
  },
  days : {
    project: { $day: "$observationTimeUtc" },
    setDate: (date, i) => {
        var days = date.getDate();
        date.date(days + i)
    }
  },
  month : {
    project: { $month: "$observationTimeUtc" },
    setDate: (date, i) => {
        var months = date.getMonth();
        date.setMonth(months + i)
    }
  },
  year : {
    project: { $year: "$observationTimeUtc" },
    setDate: (date, i) => {
        var years = date.geYear();
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
        observationTimeUtc : { $gte : range.start },
        observationTimeUtc : { $lte : range.end }
      }
    },
    {
      $project : {
        timeDivison: timeDivisionSetting.project,
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
        _id: "$timeDivison",
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
      $sort: {
        _id: 1
      }
    }
  ];
  var data = await collection.aggregate(pipeline).toArray();
  for (var i=0; i<data.length;i++) {
    var date = new Date (range.start);
    timeDivisionSetting.setDate (date, i);
    delete data[i]['_id'];
    data[i].time = date;
  }
  return data;
}
