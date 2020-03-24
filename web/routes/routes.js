//accessing the database
var db = require('../database/users.js');

var displayLogin = function (req, res){
	res.render('login.ejs', {message : null});
};


var routes = {
  login: displayLogin
};

//exporting the routes
module.exports = routes;
