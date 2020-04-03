var Post = require('../database/posts.js');

var createPost = function(post, route_callback) {
	// save the user to the database
	post.save( (err) => {
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    route_callback(err, null);
		}
		else {
		   route_callback(null, "success creating post");
		}
	    } );
}

var getPosts = function(post, route_callback) {
	Post.find(post).exec(route_callback);
}

var getAdminPosts = function(post, route_callback) {
	Post.find(post).exec(route_callback);
}


module.exports = {
	createPost: createPost,
	getPosts: getPosts,
	getAdminPosts: getAdminPosts
}
