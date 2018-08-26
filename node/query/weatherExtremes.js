function convert (observations) {
  if (observations != null && observations.length > 0) {
    return {
      observationTimeUtc: observations[0].observationTimeUtc,
      temperature: observations[0].outsideTemperature,
      pressure: observations[0].relativeBarometer
    };
  }
  return { error : "No Results" };
}

function searchParams(range) {
  return {
    observationTimeUtc : { $gte : range.start },
    observationTimeUtc : { $lte : range.end }
  }
}

module.exports = {
  maximumTemperature : async (collection, range) => {
    var observations = await collection.find (searchParams(range)).sort ({ outsideTemperature : -1 }).limit(1).toArray ();
    return convert (observations);
  },
  
  minimumTemperature : async (collection, range) => {
    var observations = await collection.find (searchParams(range)).sort ({ outsideTemperature : 1 }).limit(1).toArray ();
    return convert (observations);
  },

  maximumPressure : async (collection, range) => {
    var observations = await collection.find (searchParams(range)).sort ({ relativeBarometer : -1 }).limit(1).toArray ();
    return convert (observations);
  },

  minimumPressure : async (collection, range) => {
    var observations = await collection.find (searchParams(range)).sort ({ relativeBarometer : 1 }).limit(1).toArray ();
    return convert (observations);
  }
};
