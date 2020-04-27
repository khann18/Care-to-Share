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
	    });
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


var deleteUserPosts = function (user, route_callback) {
	Post.remove({postedBy: user}).exec(route_callback);
}

var getTopUsersByNumPosts = function(num_users, route_callback) {
	Post.find({}, function(err, docs) {
		var post_count = {};
		docs.forEach((doc) => {
			user = doc.postedBy;
			if (user in post_count) {
				post_count[user] = post_count[user] + 1;
			} else {
				post_count[user] = 1;
			}
		});
		var items = Object.keys(post_count).map(function(key) {
  			return [key, post_count[key]];
		});
		items.sort(function(first, second) {
  			return second[1] - first[1];
		});

		// Create a new array with only the first num_users items
		//console.log(items.slice(0, num_users));
		var data = items.slice(0, num_users);

		//return dictionary with key username and value number of posts, sorted in decreasing number of posts
		route_callback(null, data);
	});
}

var getTopLocationsByNumPosts = function(num_locations, route_callback) {
	Post.find({}, function(err, docs) {
		var post_count = {};
		docs.forEach((doc) => {
			location = doc.location;
			if (location in post_count) {
				post_count[location] = post_count[location] + 1;
			} else {
				post_count[location] = 1;
			}
		});
		var items = Object.keys(post_count).map(function(key) {
  			return [key, post_count[key]];
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

var getPortionData = function(datapoints, route_callback) {
	Post.find({}, function(err, docs) {
		var post_count = {};

		docs.forEach((doc) => {
			numPortions = doc.numPortions;
			if (doc.postedBy in post_count) {
				if (numPortions > post_count[doc.postedBy]) {
					post_count[doc.postedBy] = numPortions;
				}
			} else {
				post_count[doc.postedBy] = doc.numPortions;
			}
		});
		console.log()

		var items = Object.keys(post_count).map(function(key) {
  			return [key, post_count[key]];
		});
		items.sort(function(first, second) {
  			return second[1] - first[1];
		});

		// Create a new array with only the first num_users items
		//console.log(items.slice(0, num_locations));
		var data = items.slice(0, datapoints);

		//return dictionary with key username and value number of posts, sorted in decreasing number of posts
		route_callback(null, data);
	});
}

module.exports = {
	editMarked: updatePostMark,
	deletePost: removePost,
	createPost: createPost,
	getPosts: getPosts,
  getAdminPosts: getAdminPosts,
  setClaimMessage: setClaimMessage,
	deletePosts: deleteUserPosts,
	findPostById: findPostById,
  getTopUsersByNumPosts: getTopUsersByNumPosts, 
  getTopLocationsByNumPosts: getTopLocationsByNumPosts,
  getAllPosts: getPost,
  getPortionData: getPortionData
}
