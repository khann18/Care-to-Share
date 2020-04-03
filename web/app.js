var express = require('express');
var app = express();

// set up EJS
app.set('view engine', 'ejs');

// set up BodyParser
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
var routes = require('./routes/routes.js');


app.get('/', routes.login);
app.get('/post', routes.create_post);
app.get('/home', routes.home);
app.get('/createaccount', routes.create_user);
app.get('/console', routes.console);


app.listen(3000, function () {
  console.log('listening on port 3000!');
});
