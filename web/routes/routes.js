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

var getHome = function(req, res) {
	res.render('home.ejs'); 
}

var getCreateAccount = function(req, res) {
	res.render('signup.ejs');
};

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
			//Returns the entire User object that was created
			res.send(newUser);
		}
	});

}

var createNewPost = function(req, res) {
	var newPost = new Post ({
		description: req.query.description,
		location: req.query.location,
		pickupTime: req.query.pickupTime,
		postedBy: req.query.postedBy, //username of the poster
		contactInfo: req.query.contactInfo, //could be email or phone number
		isClaimed: req.query.isClaimed,
		claimMessage: req.query.claimMessage
	});
	post_db.createPost(newPost, function(err, data) {
		if (err) {
			console.log(err);
		} else {
			console.log(data);
			//Returns the entire User object that was created
			res.send(newPost);
		}
	});

}

//returns true if the username and password match
var checkPassword = function(req, res) {
	var user = req.query.username;
	var password = req.query.password;
	user_db.getPassword(user, function(err, data) {
		if (err) {
			console.log(err);
		} else {
			console.log(data);
			var correctPassword = data.get('password');
			res.send({result: correctPassword === password});
		}
	});

}

var setPostClaimMessage = function(req, res) {
	var description = req.query.description;
	var message = req.query.message;

	post_db.setClaimMessage(description, function(err, data) {
		if (err) {
			console.log(err);
		} else {
			data.claimMessage = message;
			data.isClaimed = true;
			console.log(data);
			data.save( (err) => {
				if (err) {
					console.log(err);
				} else {
				 	res.send({result: message});
			    }
			})
		}
	});

}

var displayConsole = function (req, res){
	res.render('console.ejs', {message : null, results:testArray});
};

var routes = {
  login: getLogin,
  logout: getLogout,
  home: getHome,
  account_creation: getCreateAccount,
  create_user: createNewUser,
  create_post: createNewPost,
  console: displayConsole,
  check_password: checkPassword,
  set_claim_message: setPostClaimMessage
};



// var displayLogin = function (req, res){
// 	res.render('login.ejs', {message : null, results:[]});
// };


//exporting the routes
module.exports = routes;