var Post = require('../database/posts.js');

var getPost = function(username, route_callback) {
	console.log("Finding Posts")
	Post.find().exec(route_callback);
}

module.exports = {
	get_Post: getPost,
}