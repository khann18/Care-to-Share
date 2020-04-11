//accessing the database functions
var user_db = require('../database/userdatabase.js');
var post_db = require('../database/postdatabase.js');
var claim_db = require('../database/claimdatabase.js');

var User = require('../database/users.js');
var Post = require('../database/posts.js');
var Claim = require('../database/claims.js');


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
		pickupTime: req.query.pickupTime,
		contactInfo: req.query.contact,
		isClaimed: req.query.isClaimed,
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

var createNewClaim = function(req, res) {
	var newClaim = new Claim({
		donorUsername: req.query.donorUsername,
		obtainerUsername: req.query.obtainerUsername,
		postId: req.query.postId,
		claimMessage: req.query.claimMessage
	});

	claim_db.createClaim(newClaim, function(err, data){
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

var getClaimsByDonor = function(req, res) {
	claim_db.getClaimsByDonor(req.query.donorUsername, function(err, data){
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

var deleteAllClaimsAfterAccepting = function(req, res) {
	claim_db.deleteAllClaimsAfterAccepting(req.query.postId, function(err, data){
		if (err) {
			console.log(err);
		}else {
			console.log(data);
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
			if (data) {
				var correctPassword = data.get('password');
				res.send({result: correctPassword === password});
			} else {
				res.send({result: "false"});
			}
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

// var setPostClaimMessage = function(req, res) {
// 	var description = req.query.description;
// 	var message = req.query.message;

// 	post_db.setClaimMessage(description, function(err, data) {
// 		if (err) {
// 			console.log(err);
// 		} else {
// 			data.claimMessage = message;
// 			data.isClaimed = true;
// 			console.log(data);
// 			data.save( (err) => {
// 				if (err) {
// 					console.log(err);
// 				} else {
// 				 	res.send({result: message});
// 			    }
// 			});

// 		}
// 	});
// }

var updateClaimStatus = function(req, res) {

	claim_db.updateClaimStatus(req.query.claimId, function(err, data) {
		if (err) {
			console.log(err);
		} else {
			data.claimStatus = req.query.claimStatus;
			console.log(data);
			data.save( (err) => {
				if (err) {
					console.log(err);
				} else {
				 	res.send({result : req.query.claimStatus});
			    }
			});

		}
	});
}

var updateClaimsForAcceptedPost = function(req, res) {
	var postId = req.query.postId
	console.log(postId)
	claim_db.updateClaimsForAcceptedPost(postId, function(err, data) {
		if (err) {
			console.log(err);
		} else {
			// data.claimStatus = 'rejected';
			console.log(data);
			// data.save( (err) => {
			// 	if (err) {
			// 		console.log(err);
			// 	} else {
				// res.write('I hope this works :');
				 	res.send(data);
			//     }
			// });

		}
	});
}

var updateAccount = function(req, res) {
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
	user_db.saveUser(newUser, function(err, data) {
		if (err) {
			console.log(err);
		} else {
			console.log(data);
			//Returns the entire User object that was updated
			res.send(data);
		}
	});

}

//returns the user object matching this username
var userInfo = function(req, res) {
	var username = req.query.username;
	user_db.userInfo(username, function(err, data) {
		if (err) {
			console.log(err);
		} else {
			if (data) {
				res.send({result: data});
			} else {
				res.send({result: null});
			}
			
		}
	});

}

var findPostById = function(req, res) {
	var postId = req.query.postId;
	post_db.findPostById(postId, function(err, data) {
		if (err) {
			console.log(err);
		} else {
			if (data) {
				res.send({result: data});
			} else {
				res.send({result: null});
			}
			
		}
	});

}

var getClaimById = function(req, res) {
	var claimId = req.query.claimId;
	claim_db.getClaimById(claimId, function(err, data) {
		if (err) {
			console.log(err);
		} else {
			if (data) {
				res.send({result: data});
			} else {
				res.send({result: null});
			}
			
		}
	});

}

var deleteaccount = function(req, res) {
	var username = req.query.username;
	user_db.deleteUser(username, function(err, data) {
		if (err) {
			console.log(err);
		} else {
			res.send({result: data});
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



var routes = {
	admin_approve: editPostMarked,
	admin_disapprove: deletePost,
	create_post: createNewPost,
	get_post: getPosts,
	get_admin_post: getAdminPosts,
  	get_users: getUser,
  	login: getLogin,
  	logout: getLogout,
  	account_creation: getCreateAccount,
	create_user: createNewUser,
	create_claim: createNewClaim,
	console: displayConsole,
  	check_password: checkPassword,
//   set_claim_message: setPostClaimMessage,
  	check_username: checkUsername,
  	get_user: userInfo,
  	update_account: updateAccount,
	  deleteaccount: deleteaccount,
	  delete_all_claims_after_accepting: deleteAllClaimsAfterAccepting,
	get_claims_by_donor: getClaimsByDonor,
	find_post_by_id: findPostById,
	get_claim_by_id: getClaimById,
	update_claim_status: updateClaimStatus,
	update_claims_for_accepted_post: updateClaimsForAcceptedPost
};
//exporting the routes
module.exports = routes;