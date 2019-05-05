cursor = db.weather_sleuth_observations.aggregate(
  [
    {
      $match : {
        $and : [
          { observationTimeUtc : { $gte : ISODate("2019-01-01T18:00:00Z") } },
          { observationTimeUtc : { $lte : ISODate("2019-01-02T00:00:00Z") } }
        ],
      }
    },
    {
      $project : {
        date: "$observationTimeUtc",
        time: {
          $dateToString : {
            format: "%H:%M:%S",
            date: "$observationTimeUtc"
          }
        },
        temperature : "$outsideTemperature",
        humidity: "$outsideHumidity",
        windSpeed: "$windSpeed",
        pressure: "$relativeBarometer"
      }
    },
    {
      $sort : {
        time : 1
      }
    }
  ]
);

print ("time, temperature, humidity, windSpeed, pressure,")
while (cursor.hasNext()) {
  data = cursor.next();
  print (data.time + "," + data.temperature + "," + data.humidity + "," + data.windSpeed + "," + data.pressure + ",");
}
