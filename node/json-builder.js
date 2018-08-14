var regexString = "/weatherstation/updateweatherstation.php?ID=ICALNE10&PASSWORD=%2Bmkogwyrl&tempf=60.3&humidity=93&dewptf=58.3&windchillf=60.3&winddir=152&windspeedmph=0.00&windgustmph=0.00&rainin=0.00&dailyrainin=0.94&weeklyrainin=0.94&monthlyrainin=0.94&yearlyrainin=0.94&solarradiation=76.98&UV=1&indoortempf=76.1&indoorhumidity=48&baromin=30.05&lowbatt=0&dateutc=2018-08-08%2017:35:34&softwaretype=WH2600GEN_V2.2.7&action=updateraw&realtime=1&rtfreq=5 HTTP/1.0\" 404 178 \"-" \"-\"";

var fs = require ('fs');

fs.readFile ('data.txt', "utf8", function (err, data) {
  var lines = data.split('\n');
  var regEx = new RegExp('.*\?username=(.*)&password=(.*)&data1=(.*)&data2=(.*)&.*');

  var output = [];
  var count=0;
  lines.forEach (function (line) {
    if (regEx.test(line)) {
      var groups = regEx.exec(line);

      /*groups.forEach(function(group) {
        console.log(group);
      });*/

      output.push({
        username : groups[1],
        password: groups[2],
        data1: groups[3],
        data2: groups[4]
      });


    } else {
      console.log ("Cannot match: " + line);
    }
  });

  var json = JSON.stringify (output);
  console.log (json);

  /*fs.writeFile('output.json', json, 'utf8', function (written, err) {
    console.log(err);
  });*/
});
