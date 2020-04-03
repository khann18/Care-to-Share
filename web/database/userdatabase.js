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
	User.find({username : username}).select('password').exec(route_callback);
}

var getUser = function(username, route_callback) {
	console.log("Finding Users")
	User.find().exec(route_callback);
}

module.exports = {
	createUser: createUser,
	getPassword: getPassword,
	getUser: getUser,
}