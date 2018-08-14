function dateToString (date) {
  return date.getFullYear() + "-" + date.getMonth() + "-" + date.getDay();
}

function timeToString (date) {
  return date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
}

function dateTimeToString (date) {
 return dateToString(date) + " " + timeToString(date);
}
