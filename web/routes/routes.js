//accessing the database functions
var user_db = require('../database/userdatabase.js');
var post_db = require('../database/postdatabase.js');
var User = require('../database/users.js');
var Post = require('../database/posts.js');

var testArray = ["Alex", "Taki", "Vatsin", "Katherine"]

var getLogin = function (req, res){
	res.render('login.ejs', {message : null});
};

var getLogout = function(req, res) {
	//req.session.destroy();
	res.redirect('/');
};


var getCreateAccount = function(req, res) {
	res.render('signup.ejs');
};


var createNewPost = function(req, res) {
	var newPost = new Post({
		description: req.query.description,
		location: req.query.location,
		postedBy: req.query.poster,
		contactInfo: req.query.contact,
		marked: req.query.marked
	});

	post_db.createPost(newPost, function(err, data){
		if (err) {
			console.log(err);
		} else {
			console.log(data);
		}
	});
}

var getPosts = function(req, res) {
	post_db.getPosts({marked: 'user'}, function(err, data){
		if (err) {
			console.log(err);
		}else {
			console.log(data);
			res.send(data);
		}
	});
}

var getAdminPosts = function(req, res) {
	post_db.getPosts({marked: 'admin'}, function(err, data){
		if (err) {
			console.log(err);
		}else {
			console.log(data);
			res.render('home.ejs', {message : data });
		}
	});
}

var deletePost = function(req, res) {
	post_db.deletePost({_id: req.query.id}, function(err, data){
		if (err) {
			console.log(err);
		}else {
			console.log(data);
			res.redirect('/home');
		}
	});
}

var editPostMarked = function(req, res) {
	post_db.editMarked({_id: req.query.id}, function(err, data){
		if (err) {
			console.log(err);
		}else {
			console.log(data);
			res.redirect('/home');
		}
	});
}

var createNewUser = function(req, res) {
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
	user_db.createUser(newUser, function(err, data) {
		if (err) {
			console.log(err);
		} else {
			console.log(data);
			res.send(newUser);
		}
	});

}

var displayConsole = function (req, res){
	user_db.getUser("Test", function(err, data) {
		if (err) {
			console.log(err);
			res.render('console.ejs', {message : null, results:testArray});

		} else {
			console.log("CONSOLE DATA");
			console.log(data);
			testArray = data;
			res.render('console.ejs', {message : null, results:testArray});
			// res.send(data);
		}
	});
	console.log("Async Test");
};

var getUser = function(req, res) {
	user_db.getUser("Test", function(err, data) {
		if (err) {
			console.log(err);
			res.send(404);
		} else {
			console.log(data);
			res.send(data);
		}
	});
}

var getPost = function(req, res) {
	post_db.get_Post("Test", function(err, data) {
		if (err) {
			console.log(err);
			res.send(404);
		} else {
			console.log(data);
			res.send(data);
		}
	});
}

var checkPassword = function(req, res) {
	var user = req.query.username;
	var password = req.query.password;
	user_db.getPassword(user, function(err, data) {
		if (err) {
			console.log(err);
		} else {
			console.log(data);
			var correctPassword = data.get('password');
			res.send(correctPassword === password);
		}
	});

}


var routes = {
	admin_approve: editPostMarked,
	admin_disapprove: deletePost,
	create_post: createNewPost,
	get_post: getPosts,
	get_admin_post: getAdminPosts,
  login: getLogin,
  logout: getLogout,
  account_creation: getCreateAccount,
  create_user: createNewUser,
  console: displayConsole,
  get_user: getUser,
  get_post: getPost,
  check_password: checkPassword
};

//exporting the routes
module.exports = routes;
