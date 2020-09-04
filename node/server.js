const express = require('express');
const app = express();

const MongoClient = require('mongodb').MongoClient
const connectionString = 'mongodb://weatherApplication:weatherPassword@nas:27017/weather';

const timespanParameter = require('./query/timespanParameter');
//const measurementParameter = require('./query/measurementParameter');
const weatherHistory = require('./query/weatherHistory');
const weatherExtremes = require('./query/weatherExtremes');

var weatherDB = null;

MongoClient.connect(connectionString, { useNewUrlParser: true, useUnifiedTopology: true }, (err, database) => {
  // ... start the server
  if (err) throw err;
  weatherDB = database.db("weather");
  app.listen(7000, function() {
    console.log ("Server started and listening on port 7000");
  });
})

app.get("/", function(request, response) {
  response.sendFile ("/Users/eliot/weather/node/html/index.html");
});

app.get("/latest", async (request, response, next) => {
    var sort = {observationTimeUtc : -1};
    var documents = await weatherDB.collection("weather_sleuth_observations").find().sort(sort).limit(1).toArray();
    if (documents.length > 0) {
        response.status(200).json({
            time: documents[0].observationTimeUtc,
            temperature : documents[0].outsideTemperature,
            humidity: documents[0].outsideHumidity,
            windDirection: documents[0].windDirection,
            windSpeed: documents[0].windSpeed,
            windGust: documents[0].windGust,
            rainRate: documents[0].rainRate,
            light: documents[0].light,
            uv: documents[0].uv,
            pressure: documents[0].relativeBarometer
        });
    } else {
        response.status(500).json({message : "No observations"})
    }
});

app.get("/history/timespan/:timespan/steps/:steps", async (request, response, next) => {
  var range = timespanParameter(request.params.timespan);

  if (request.params.steps == null || request.params.steps == undefined) {
    response.status(500).json({ message : "No 'steps' parameter defined in query string" });
    return;
  }

  var collection = weatherDB.collection("weather_sleuth_observations");
  try {
    result = await weatherHistory(collection, range, request.params.steps);
    response.status(200).json(result);
  } catch (err) {
    response.status(500).json(err);
  }
});

app.get("/extremes/timespan/:timespan", async (request, response, next) => {
  var range = timespanParameter(request.params.timespan);

  var collection = weatherDB.collection("weather_sleuth_observations");

  var result = {
    minimumTemperature: await weatherExtremes.minimumTemperature (collection, range),
    maximumTemperature: await weatherExtremes.maximumTemperature (collection, range),
    minimumPressure: await weatherExtremes.minimumPressure (collection, range),
    maximumPressure: await weatherExtremes.maximumPressure (collection, range)
  };
  response.status(200).json(result);
});
