var _measurement = {
  temperature : "$outsideTemperature",
  humidity: "$outsideHumidity",
  windDirection: "$windDirection",
  windSpeed: "$windSpeed",
  windGust: "$windGust",
  rainRate: "$rainRate",
  light: "$light",
  uv: "$uv",
  pressure: "$relativeBarometer"
};

module.exports = (measurement) => {
  if (measurement == null) {
    throw "Unknown mesurement";
  }
  if (measurement === 'all') {
    return {
      name: "all",
      observationName: _measurement
    };
  }
  if (_measurement[measurement] == undefined) {
    throw "Unknown measurement " + measurement;
  }
  return {
    name : measurement,
    observationName: _measurement[measurement]
  };
}
