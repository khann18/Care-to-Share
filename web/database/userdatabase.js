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

module.exports = {
	createUser: createUser,
}