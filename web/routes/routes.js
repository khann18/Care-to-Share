//accessing the database functions
var user_db = require('../database/userdatabase.js');
var post_db = require('../database/userdatabase.js');
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

//returns "true" if the username is already taken
var checkUsername = function(req, res) {
	var username = req.query.username;
	user_db.checkUsernameTaken(username, function(err, data) {
		if (err) {
			console.log(err);
		} else {
			if (data) {
				res.send({result: "true"});
			} else {
				res.send({result: "false"})
			}
			
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
  console: displayConsole,
  check_password: checkPassword,
  check_username: checkUsername,
};



// var displayLogin = function (req, res){
// 	res.render('login.ejs', {message : null, results:[]});
// };





//exporting the routes
module.exports = routes;
