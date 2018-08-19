const express = require('express');
const app = express();

const MongoClient = require('mongodb').MongoClient
const connectionString = 'mongodb://weatherApplication:weatherPassword@nas:27017/weather';

var weatherDB = null;

MongoClient.connect(connectionString, { useNewUrlParser: true }, (err, database) => {
  // ... start the server
  if (err) throw err;
  weatherDB = database.db("weather");
  app.listen(7000, function() {
    console.log ("Server started and listening on port 7000");
  });
})

app.get("/", function(request, response) {
  response.sendFile ("/Users/eliot/weather/node/index.html");
});

app.get("/latest", async (request, response, next) => {
  var sort = {observationTimeUtc : -1};
  var documents = await weatherDB.collection("weather_sleuth_observations").find().sort(sort).limit(1).toArray();
  response.status(200).json({
    date: documents[0].observationTimeUtc,
    temperature: documents[0].outsideTemperature,
    pressure: documents[0].absoluteBarometer
  });
});

app.get("/history", async (request, response, next) => {
  var dateLimit = new Date();
  console.log(dateLimit);
  dateLimit.setMinutes (0);
  dateLimit.setSeconds (0);
  console.log(dateLimit);

  var aggregationPipeline = [
    {
      $match: {
        observationTimeUtc : { $gt : dateLimit }
      }
    },
    {
      $project : {
        minute: { $divide: [ { $minute: "$observationTimeUtc" }, 6 ] },
        temperature: "$outsideTemperature",
        pressure: "$relativeBarometer"
      }
    },
    {
      $group: {
        _id: { $subtract: [ "$minute", { $mod: [ "$minute", 1 ] } ] },
        temperature: { $avg: "$temperature" },
        pressure: { $avg: "$pressure" },
      }
    },
    {
      $sort: {
        _id: 1
      }
    }
  ];

  var results = await weatherDB.collection("weather_sleuth_observations").aggregate(aggregationPipeline).toArray();
  response.status(200).json(results);
});
