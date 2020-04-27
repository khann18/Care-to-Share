//accessing the database functions
var user_db = require('../database/userdatabase.js');
var post_db = require('../database/postdatabase.js');
var claim_db = require('../database/claimdatabase.js');

var User = require('../database/users.js');
var Post = require('../database/posts.js');
var Claim = require('../database/claims.js');

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
					isClaimed: false,
					marked: req.query.marked,
					latlng: coords,
					numPortions: req.query.numPortions,
					tags: req.query.tags
				});
				console.log(req.query.tags);
				post_db.createPost(newPost, function(err, data){
					if (err) {
						console.log(err);
					} else {
						console.log(data);
					}
				});
				res.sendStatus(200);
       	} else {
	    	var newPost = new Post({
				description: req.query.description,
				location: req.query.location,
				postedBy: req.query.poster,
				pickupTime: req.query.pickupTime,
				contactInfo: req.query.contact,
				isClaimed: false,
				marked: req.query.marked,
				latlng: "",
				numPortions: req.query.numPortions,
				tags: req.query.tags
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
}

var createNewClaim = function(req, res) {
	var newClaim = new Claim({
		donorUsername: req.query.donorUsername,
		obtainerUsername: req.query.obtainerUsername,
		postId: req.query.postId,
		claimMessage: req.query.claimMessage,
		claimStatus: req.query.claimStatus
	});

	claim_db.createClaim(newClaim, function(err, data){
		if (err) {
			console.log(err);
		}else {
			console.log(data);
			res.send(data);
		}
	});
}

var getPosts = function(req, res) {

	post_db.getPosts({marked: 'user', isClaimed: false}, function(err, data) {

		if (err) {
			console.log(err);
		}else {
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

	post_db.getPosts({marked: 'user', isClaimed: false}, function(err, data) {
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

	post_db.getPosts({marked: 'user', isClaimed: false}, function(err, data) {
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

var getClaimsByObtainer = function(req, res) {
	claim_db.getClaimsByObtainer(req.query.obtainerUsername, function(err, data){
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

var setPostIsClaimed = function(req, res) {
	post_db.findPostById(req.query.postId, function(err, data) {
		if (err) {
			console.log(err);
		} else {
			data.isClaimed = true;
			console.log(data);
			data.save( (err) => {
				if (err) {
					console.log(err);
				} else {
				 	res.send({result : req.query.isClaimed});
			    }
			});

		}
	});
}

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
	user_db.getUser("", function(err, data) {
		if (err) {
			console.log(err);
		} else {
			testArray = data;
			post_db.getPosts({marked: "user"}, function (err, post_data) {
				if (err) {
					console.log(err);
				} else {

					var arr = [];

					for (var i = 0; i < post_data.length; i++) {
						var toAdd = JSON.stringify({"contactInfo": post_data[i].contactInfo, "lat": post_data[i].latlng.split(',')[0], "lng": post_data[i].latlng.split(',')[1], "description": post_data[i].description, "postedBy": post_data[i].postedBy});
						arr.push(toAdd);
					}

					claim_db.getAcceptedClaims("hi", function(err, claim_data){
						var claims_arr = [];

						for (var i = 0; i < claim_data.length; i++) {
							var toAdd = JSON.stringify({"obtainerLocation": claim_data[i].obtainerLocation,
							"latlng": claim_data[i].postLatlng});
							claims_arr.push(toAdd);
						}

						res.render('console.ejs', {message : null, results:testArray, posts: arr, claims: claims_arr});

					});
				}
			});
		}
	});
};

var getUser = function(req, res) {
	user_db.getUser("", function(err, data) {
		if (err) {
			console.log(err);
			res.send(404);
		} else {
			console.log(data);
			res.send(data);
		}
	});
}

var dataVis = async function(req, res) {
	var postNum = 0;
	var claimNum = 0;
	var userNum = 0;
	var metaStats = [];
	stats = [];
	var finalData = [];

	const promise_0A = new Promise(function(resolve, reject) {
		post_db.getTopUsersByNumPosts(10, function(err, data) {
			resolve(data);
		});
	})

	const promise_0B = new Promise(function(resolve, reject) {

		post_db.getTopLocationsByNumPosts(10, function(err, data) {
			resolve(data);
		});
	})

	const promise_0C = new Promise(function(resolve, reject) {

		user_db.getTopLocationsByNumUsers(10, function(err, data) {
			resolve(data);
		});
	})


	const promise1 = new Promise(function(resolve, reject) {
	  user_db.getUser("", function(err, data) {
		userNum = data.length;
		metaStats.push(['User Number', userNum]);
		resolve();
	  });
	});

	const promise2 = new Promise(function(resolve, reject) {
		post_db.getAllPosts("User", function(err, data) {
			console.log(data.length);
			postNum = data.length;
			metaStats.push(['Post Number', postNum]);
			resolve();
		});
	});


	const promise3 = new Promise(function(resolve, reject) {
		claim_db.getAllClaims("Claim", function(err, data) {
			claimNum = data.length;
			metaStats.push(['Claim Number', claimNum]);
			resolve();
		});
	});

	const promise4 = new Promise(function(resolve, reject) {
		user_db.getUserTypes(2, function(err, data) {
			resolve(data);
		});
	});

	const promise5 = new Promise(function(resolve, reject) {
		post_db.getPortionData(10, function(err, data) {
			resolve(data);
		});
	});

	Promise.all([promise_0A, promise_0B, promise_0C, promise1, promise2, promise3, promise4, promise5]).then(function(values) {

		finalData.push(values[0]);
		finalData.push(values[1]);
		finalData.push(values[2]);
		finalData.push(values[6]);
		finalData.push(values[7]);
		finalData.push(metaStats);
		res.send(finalData);
	});
}


var get_data = function(req, res) {


	post_db.getTopUsersByNumPosts(10, function(err, data) {
		var postNum = 0;
		var claimNum = 0;
		var userNum = 0;

		stats = [];
		stats.push(data);
		post_db.getTopLocationsByNumPosts(10, function(err, data) {
			stats.push(data)
			user_db.getTopLocationsByNumUsers(10, function(err, data) {
				stats.push(data)
				post_db.getAllPosts("User", function(err, data) {
					console.log(data.length);
					res.render('data.ejs', {stats: stats})
				});
				console.log("DATASTAT")
			});
		});
	});
}

var getStatsForProfile = function(req, res) {
	post_db.getTopUsersByNumPosts(10, function(err, data) {
		if (err) {
			console.log("Error getting stats");
		} else {
			console.log(data);
			res.send(data);
		}
	});
}

var getUserProfile = function (req, res) {
	user_db.getUser({username : req.body.username}, function(err, user_data) {
		if (err) {
			console.log(err);
		} else {
			post_db.getPosts({marked: "user", postedBy: req.body.username}, function (err, post_data) {
				if (err) {
					console.log(err);
				} else {
					console.log(user_data);
					console.log(post_data);
					res.render('profile.ejs', {user : user_data, message: post_data});
				}
			});
		}
	});
};

var deleteUserAdmin = function (req, res) {
	post_db.deletePosts(req.body.username, function(err, data){
		if (err) {
			console.log("error deleting all of users posts");
		} else {
			user_db.deleteUser(req.body.username, function(err, data) {
				if (err) {
					console.log(err);
				} else {
					res.redirect('/console');
				}
		});
	}
	});
};

var getAllUsers = function(req, res) {
	console.log("getting all users");
	user_db.get_users(function(err, data){
		if (err) {
			console.log("error getting all users");
		} else {
			console.log(data);
			res.send(data);
		}});

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
 	console: displayConsole,
  	check_password: checkPassword,
  	set_post_is_claimed: setPostIsClaimed,
  	check_username: checkUsername,
  	get_user: userInfo,
  	update_account: updateAccount,
  	deleteaccount: deleteaccount,
	delete_all_claims_after_accepting: deleteAllClaimsAfterAccepting,
	get_claims_by_donor: getClaimsByDonor,
	get_claims_by_obtainer: getClaimsByObtainer,
	find_post_by_id: findPostById,
	get_claim_by_id: getClaimById,
	update_claim_status: updateClaimStatus,
	update_claims_for_accepted_post: updateClaimsForAcceptedPost,
	get_close_posts: getClosePosts,
	get_data: get_data,
  	displayUser: getUserProfile,
	deleteUser: deleteUserAdmin,
	create_claim: createNewClaim,
	visualizeData: dataVis,
	getAllUsers: getAllUsers,
	get_stats_for_profile: getStatsForProfile
};
//exporting the routes
module.exports = routes;
