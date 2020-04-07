var Post = require('../database/posts.js');

var getPost = function(username, route_callback) {
	console.log("Finding Posts")
	Post.find().exec(route_callback);
}

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

var removePost = function(post, route_callback) {
	Post.remove(post).exec(route_callback);
}

var findPostById = function(postId, route_callback) {
	Post.findById(postId).exec(route_callback);
}

var updatePostMark = function(post, route_callback) {
	Post.updateOne(post, { $set: { "marked" : 'user' } }).exec(route_callback);
}

var setClaimMessage = function(description, route_callback) {
    Post.findOne({description : description}).exec(route_callback);
}

module.exports = {
	editMarked: updatePostMark,
	deletePost: removePost,
	createPost: createPost,
	getPosts: getPosts,
    getAdminPosts: getAdminPosts,
	setClaimMessage: setClaimMessage,
	findPostById: findPostById
}

