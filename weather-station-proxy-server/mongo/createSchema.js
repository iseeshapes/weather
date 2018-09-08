db.createCollection("weather_sleuth_observations", {
  validator: {
      $jsonSchema: {
        bsonType: "object",
         required: [ "observationTimeUtc", "outsideTemperature", "outsideHumidity", "absoluteBarometer" ],
         properties: {
           observationTimeUtc : {
             bsonType: "date",
             description: "Date of Observation in UTC"
           },
           outsideTemperature : {
             bsonType: "double",
             description: "Outside Temperature in Degrees Celsius"
           },
           outsideHumidity : {
             bsonType: "int",
             description: "Outside Humidity %"
           },
           dewPoint: {
             bsonType: "double",
             description: "Dew Point in Degrees Celsius"
           },
           windChill: {
             bsonType: "double",
             description: "Wind Chill in Degrees Celsius"
           },
           windDirection: {
             bsonType: "int",
             description: "Wind Direction in Degrees from north"
           },
           windSpeed: {
             bsonType: "double",
             description: "Windspeed in m/s"
           },
           windGust: {
             bsonType: "double",
             description: "Wind gust in m/s"
           },
           rainRate: {
             bsonType: "double",
             description: "Rain Rate in mm/hour"
           },
           daylyRain: {
             bsonType: "double",
             description: "Dayly Rain in mm/hour"
           },
           weeklyRain: {
             bsonType: "double",
             description: "Weekly Rain in mm/hour"
           },
           monthlyRain: {
             bsonType: "double",
             description: "Monthly Rain in mm/hour"
           },
           yearlyRain: {
             bsonType: "double",
             description: "Yearly Rain in mm/hour"
           },
           light: {
             bsonType: "double",
             description: "Light meter in Watts / Meter ^ 2"
           },
           uv: {
             bsonType: "int",
             description: "UV meter in Watts / Meter ^ 2"
           },
           indoorTemperature: {
             bsonType: "double",
             description: "Indoor Temperature in Degrees Celsius"
           },
           indoorHumidity: {
             bsonType: "int",
             description: "Indoor Humidity in %"
           },
           absoluteBarometer: {
             bsonType: "double",
             description: "Absolute Barometer in mBar"
           },
           relativeBarometer: {
             bsonType: "double",
             description: "Relative Barometer in mBar"
           },
           lowBattery: {
             bsonType: "String",
             description: "Low Battery indicator"
           }
         }
       }
     }
 });

db.createCollection ("weather_observation_ids", {
  validator: {
      $jsonSchema: {
        bsonType: "object",
         required: [ "lastWeatherReadingId" ],
         properties: {
           lastWeatherReadingId : {
             bsonType: "long",
             description: "Date of Observation in UTC"
           }
         }
      }
   }
});
