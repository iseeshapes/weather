function lastMinute () {
  var range = { start: null, end: null };

  range.end = new Date();
  range.start = new Date (range.end);
  range.start.setMilliseconds(0);
  range.start.setSeconds(0);

  return range
}

function lastHour () {
  var range = lastMinute ();

  range.start.setMinutes(0);

  return range;
}

function today () {
  var range = lastHour ();

  range.start.setHours(0);

  return range;
}

function thisMonth () {
    var range = today ();

    range.start.setDate (1);

    return range;
}

function thisYear () {
    var range = thisMonth ();

    range.start.setMonth (1);

    return range;
}

function last1Hour () {
  var end = new Date();
  var start = new Date (end);
  var hours = start.getHours ();
  start.getHours (hours - 1);
  return {
    start: start,
    end: end
  }
}

function last12Hours () {
  var end = new Date();
  var start = new Date (end);
  var hours = start.getHours ();
  start.setDate (hours - 12);
  return {
    start: start,
    end: end
  }
}

function last24Hours () {
  var end = new Date();
  var start = new Date (end);
  var hours = start.getHours ();
  start.setHours (days - 24);
  return {
    start: start,
    end: end
  }
}

function last3Days () {
  var end = new Date();
  var start = new Date (end);
  var days = start.getDate ();
  start.setDate (days - 3);
  return {
    start: start,
    end: end
  }
}

function last7Days () {
  var end = new Date();
  var start = new Date (end);
  var days = start.getDate ();
  start.setDate (days - 7);
  return {
    start: start,
    end: end
  }
}

function last30Days () {
  var end = new Date();
  var start = new Date (end);
  var days = start.getDate ();
  start.setDate (days - 30);
  return {
    start: start,
    end: end
  }
}

var timespans = {
  lastMinute: lastMinute,
  lastHour: lastHour,
  today: today,
  thisMonth: thisMonth,
  thisYear: thisYear,
  last1Hour: lastHour,
  last12Hours: last12Hours,
  last24Hours: last24Hours,
  last3Days: last3Days,
  last7Days: last7Days,
  last30Days: last30Days
};

module.exports = (timespan) => {
  if (timespan == null || timespan == undefined) {
    throw "no time span";
  }
  rangeFunc = timespans[timespan];
  if (rangeFunc == null || timespan == undefined) {
    throw "no time span";
  }
  return rangeFunc ();
}
