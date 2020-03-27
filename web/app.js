var express = require('express');
//accessing the routes
var routes = require('./routes/routes.js');
var app = express();





app.get('/', routes.login);

app.get('/console', routes.console);


app.listen(3000, function () {
  console.log('listening on port 3000!');
});
