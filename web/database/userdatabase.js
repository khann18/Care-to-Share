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

var getUser = function(username, route_callback) {
	console.log("Finding Users")
	if (username.length == 0) {
			User.find().exec(route_callback);
	} else {
		console.log(username);
		User.find(username).exec(route_callback);
	}
}

var saveUser = function(user, route_callback) {

	var updated = {
		firstName: user.firstName,
		lastName: user.lastName,
		username: user.username,
		password: user.password,
		phoneNumber: user.phoneNumber,
		email: user.email,
		location: user.location,
		organization: user.organization,
		userType: user.userType,
		profilePic: user.profilePic,
	}
	console.log(updated);
	User.findOneAndUpdate({username : user.username}, updated, function(err, result){
		if (err) {
			route_callback(err, null);
		} else {
			route_callback(null, result);
		}
	});
}

var getPassword = function(username, route_callback) {
	User.findOne({username : username}).select('password').exec(route_callback);
}

var userInfo = function(username, route_callback) {
	User.findOne({username : username}).exec(route_callback);
}


//returns the User object or null if there is no User in the database with that username
var checkUsernameTaken = function(username, route_callback) {
	User.findOne({username : username}).exec(route_callback);
}

var deleteUser = function(username, route_callback) {
	User.deleteOne({username : username}).exec(route_callback);
}

var getPassword = function(username, route_callback) {
	User.findOne({username : username}).select('password').exec(route_callback);
}

var getTopLocationsByNumUsers = function(num_locations, route_callback) {
	User.find({}, function(err, docs) {
		var user_count = {};
		docs.forEach((doc) => {
			location = doc.location;
			if (location in user_count) {
				user_count[location] = user_count[location] + 1;
			} else {
				user_count[location] = 1;
			}
		});
		var items = Object.keys(user_count).map(function(key) {
  			return [key, user_count[key]];
		});
		items.sort(function(first, second) {
  			return second[1] - first[1];
		});

		// Create a new array with only the first num_users items
		//console.log(items.slice(0, num_locations));
		var data = items.slice(0, num_locations);

		//return dictionary with key username and value number of posts, sorted in decreasing number of posts
		route_callback(null, data);
	});
}

var getUserTypes = function(num_types, route_callback) {
	User.find({}, function(err, docs) {
		var user_count = {};
		docs.forEach((doc) => {
			userType = doc.userType;
			if (userType in user_count) {
				user_count[userType] = user_count[userType] + 1;
			} else {
				user_count[userType] = 1;
			}
		});

		var items = Object.keys(user_count).map(function(key) {
  			return [key, user_count[key]];
		});

		items.sort(function(first, second) {
  			return second[1] - first[1];
		});

		// Create a new array with only the first num_users items
		//console.log(items.slice(0, num_locations));
		var data = items.slice(0, num_types);

		//return dictionary with key username and value number of posts, sorted in decreasing number of posts
		route_callback(null, data);
	});
}

var getAllUsers = function(route_callback) {
	User.find({}).exec(route_callback);
}

module.exports = {
	createUser: createUser,
	getPassword: getPassword,
	getUser: getUser,
	checkUsernameTaken: checkUsernameTaken,
	saveUser: saveUser,
	userInfo: userInfo,
	deleteUser: deleteUser,
	getTopLocationsByNumUsers, getTopLocationsByNumUsers,
	getUserTypes: getUserTypes,
	get_users: getAllUsers,
}
