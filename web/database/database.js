//database functions for posts and users

var User = require('../database/users.js');
var Post = require('../database/posts.js');


var createUser = function(req, res) {
	var newUser = new User ({
		firstName: req.query.firstName,
		lastName: req.query.lastName,
		location: req.query.location,
		userType: req.query.userType,
		username: req.query.username,
		password: req.query.password,
		phoneNumber: req.query.phoneNumber,
		email: req.query.email,
		organization: req.query.organization
	    });

	// save the user to the database
	newUser.save( (err) => { 
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    console.log(err);
		}
		else {
		   console.log('success creating user: ' + req.query.username)
		}
	    } ); 
}



var createPost = function(req, res) {
		var newPost = new Post ({
		description: req.query.description,
    	location: req.query.location,
    	pickupTime: req.query.pickupTime,
    	postedBy: req.query.postedBy, //username of the poster
    	contactInfo: req.query.contactInfo //could be email or phone number
	    });

	// save the post to the database
	newPost.save( (err) => { 
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    console.log(err);
		}
		else {
		   console.log('success creating post')
		}
	    } ); 

}

module.exports = {
	createUser: createUser,
	createPost: createPost,
}