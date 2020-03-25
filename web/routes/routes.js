//accessing the database functions
var db = require('../database/database.js');


var displayLogin = function (req, res){
	res.render('login.ejs', {message : null});
};


var routes = {
  login: displayLogin
};

//exporting the routes
module.exports = routes;
