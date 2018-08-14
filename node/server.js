var fs = require('fs');
var url = require('url');
var http = require('http');
var mongo = require('mongodb');

http.createServer(function(request, response){
  response.writeHeader (200, { 'Content-Type': 'application/json' });
  var result = {
    temperature: 25.3,
    pressure: {
        last: [ 1020.0, 1019.8, 1019.7, 1019.5, 1019.4 ],
        hour: [ 1018.7, 1018.2, 1017.5, 1017.0, 1016.3, 1015.0 ],
        days: [ 1015.0, 1017.0, 998.0, 995.0 ]
    }
  };

  response.write(JSON.stringify(result));
  response.end();
}).listen(7000);
