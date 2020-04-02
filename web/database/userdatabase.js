var User = require('../database/users.js');

var createUser = function(user, route_callback) {

	// save the user to the database
	user.save( (err) => { 
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    route_callback(err, null);
		}
		else {
		   route_callback(null, "success creating user");
		}
	    } ); 
}

var getPassword = function(username, route_callback) {
	User.findOne({username : username}).select('password').exec(route_callback);
}

//returns the User object or null if there is no User in the database with that username
var checkUsernameTaken = function(username, route_callback) {
	User.findOne({username : username}).exec(route_callback);
}

module.exports = {
	createUser: createUser,
	getPassword: getPassword,
	checkUsernameTaken: checkUsernameTaken,
}