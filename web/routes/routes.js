//accessing the database
var db = require('../database/users.js');

testArray = ["Alex", "Taki", "Vatsin", "Katherine"]

var displayLogin = function (req, res){
	res.render('login.ejs', {message : null, results:[]});
};

var displayConsole = function (req, res){
	res.render('console.ejs', {message : null, results:testArray});
};

var routes = {
  login: displayLogin,
  console: displayConsole
};

//exporting the routes
module.exports = routes;
