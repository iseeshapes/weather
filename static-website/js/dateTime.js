const second_in_millisecs = 1000;
const minute_in_millisecs = 60 * second_in_millisecs;
const hour_in_millisecs = 60 * minute_in_millisecs;
const day_in_millisecs = 24 * hour_in_millisecs;

function timePad (number) {
  var result = "";
  if (number < 10) {
    result += "0";
  }
  return result + number;
}

function dateToString (date) {
  var result =  date.getFullYear() + "-" + timePad (date.getMonth()) + "-" + timePad (date.getDate());
  return result;
}

function timeToString (date) {
  return  timePad (date.getHours()) + ":" + timePad (date.getMinutes()) + ":" + timePad (date.getSeconds());
}

function dateTimeToString (date) {
 return dateToString(date) + " " + timeToString(date);
}

function print12HourClock (date) {
  var hours = date.getHours(date);
  if (hours == 0) {
    return "12 AM";
  } else if (hours < 12) {
    return hours + " AM";
  } else if (hours == 12) {
    return  "12 PM";
  }
  hours -= 12;
  return hours + " PM";
}
