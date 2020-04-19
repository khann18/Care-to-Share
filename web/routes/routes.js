//accessing the database functions
var user_db = require('../database/userdatabase.js');
var post_db = require('../database/postdatabase.js');
var User = require('../database/users.js');
var Post = require('../database/posts.js');
var request = require("request");

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
	var API_KEY = "AIzaSyD9L96DpB9wyP4Are37YqzlJlICplSR-B0";
    var BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    var address = req.query.location;

    var url = BASE_URL + address + "&key=" + API_KEY;

    var coords = "";
    request(url, function (error, response, body) {
        if (!error && response.statusCode == 200) {
            // console.log(Object.keys(body));
            var response = JSON.parse(body);
            console.log(response.results['0'].geometry.location);
            console.log(Object.keys(response.results));
            coords = "" + response.results['0'].geometry.location.lat + "," + response.results['0'].geometry.location.lng;

            	var newPost = new Post({
					description: req.query.description,
					location: req.query.location,
					postedBy: req.query.poster,
					pickupTime: req.query.pickupTime,
					contactInfo: req.query.contact,
					isClaimed: req.query.isClaimed,
					claimMessage: req.query.claimMessage,
					marked: req.query.marked,
					latlng: coords
				});

				post_db.createPost(newPost, function(err, data){
					if (err) {
						console.log(err);
					} else {
						console.log(data);
					}
				});
				res.send(200);
       	} else {
	    	var newPost = new Post({
				description: req.query.description,
				location: req.query.location,
				postedBy: req.query.poster,
				pickupTime: req.query.pickupTime,
				contactInfo: req.query.contact,
				isClaimed: req.query.isClaimed,
				claimMessage: req.query.claimMessage,
				marked: req.query.marked,
				latlng: ""
			});

			post_db.createPost(newPost, function(err, data){
				if (err) {
					console.log(err);
				} else {
					console.log(data);
				}
			});
            console.log("Fail");
            res.send(200);
            // The request failed, handle it
        }
    });

    console.log(coords);

	// var newPost = new Post({
	// 	description: req.query.description,
	// 	location: req.query.location,
	// 	postedBy: req.query.poster,
	// 	pickupTime: req.query.pickupTime,
	// 	contactInfo: req.query.contact,
	// 	isClaimed: req.query.isClaimed,
	// 	claimMessage: req.query.claimMessage,
	// 	marked: req.query.marked,
	// 	latlng: coords
	// });

	// post_db.createPost(newPost, function(err, data){
	// 	if (err) {
	// 		console.log(err);
	// 	} else {
	// 		console.log(data);
	// 	}
	// });
}

var getPosts = function(req, res) {

	post_db.getPosts({marked: 'user'}, function(err, data) {
		if (err) {
			console.log(err);
		}else {
			console.log(data);
			console.log("SENT");
			res.send(data);
		}
	});
}

function distance(lat1, lon1, lat2, lon2, unit) {
	if ((lat1 == lat2) && (lon1 == lon2)) {
		return 0;
	}
	else {
		var radlat1 = Math.PI * lat1/180;
		var radlat2 = Math.PI * lat2/180;
		var theta = lon1-lon2;
		var radtheta = Math.PI * theta/180;
		var dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta);
		if (dist > 1) {
			dist = 1;
		}
		dist = Math.acos(dist);
		dist = dist * 180/Math.PI;
		dist = dist * 60 * 1.1515;
		if (unit=="K") { dist = dist * 1.609344 }
		if (unit=="N") { dist = dist * 0.8684 }
		return dist;
	}
}

var getClosePosts = function(req, res) {
    var latitude = req.query.lat;
    var long = req.query.lng;

    var jArray = [];
    var finalArray = [];

    var postID = 0;

	post_db.getPosts({marked: 'user'}, function(err, data) {
		if (err) {
			console.log(err);
		} else {
			console.log(data);
			for (var i = 0; i < data.length; i++) {
				var coordString = data[i].latlng;
				console.log(data[i].latlng);
				if (coordString != undefined && coordString.length > 0) {
					var coordArr = coordString.split(",");

					var dist = distance(parseFloat(coordArr[0]), 
						parseFloat(coordArr[1]),
						parseFloat(latitude),
						parseFloat(long), "K") ;
					jArray.push({data: data[i], id: dist});
					console.log(dist);
					jArray.sort((a, b) => (a.id > b.id) ? 1 : -1);
					postID++;
				}
			}
			for (var i = 0; i < jArray.length && i < 10; i++) {
				finalArray.push(jArray[i].data);
			}
			console.log(finalArray);
			console.log("SENT");
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
		organization: req.query.organization,
		profilePic: req.query.profilePic,
	 });
	user_db.createUser(newUser, function(err, data) {
		if (err) {
			console.log(err);
		} else {
			console.log(newUser);
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
			});

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
		organization: req.query.organization,
		profilePic: req.query.profilePic,
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

var get_data = function(req, res) {
	

	post_db.getTopUsersByNumPosts(10, function(err, data) {
		stats = [];
		stats.push(data);
		post_db.getTopLocationsByNumPosts(10, function(err, data) {
			stats.push(data)
			user_db.getTopLocationsByNumUsers(10, function(err, data) {
				stats.push(data)
				console.log(stats)
				res.render('data.ejs', {stats: stats})
			});
		});
	});
	
	


	//TODO: stuff with claims database

	
	
}




var routes = {
	admin_approve: editPostMarked,
	admin_disapprove: deletePost,
	create_post: createNewPost,
	get_post: getPosts,
	get_admin_post: getAdminPosts,
  get_users: getUser,
  get_close_posts: getClosePosts,
  login: getLogin,
  logout: getLogout,
  account_creation: getCreateAccount,
  create_user: createNewUser,
 	console: displayConsole,
  check_password: checkPassword,
  set_claim_message: setPostClaimMessage,
  check_username: checkUsername,
  get_user: userInfo,
  update_account: updateAccount,
  deleteaccount: deleteaccount,
  get_data: get_data,
};
//exporting the routes
module.exports = routes;