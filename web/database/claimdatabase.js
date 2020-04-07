var Claim = require('../database/claims.js');

var createClaim = function(claim, route_callback) {
	// save the claim to the database
	claim.save( (err) => {
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    route_callback(err, null);
		}
		else {
		   route_callback(null, "success creating claim");
		}
	} );
}

var getClaimsByDonor = function(username, route_callback) {
	console.log("Finding Claims")
	Claim.find({donorUsername : username}).exec(route_callback);
}

// var saveClaim = function(claim, route_callback) {

// 	var updated = {
// 		description: user.firstName,
// 		lastName: user.lastName,
// 		username: user.username,
// 		password: user.password,
// 		phoneNumber: user.phoneNumber,
// 		email: user.email,
// 		location: user.location,
// 		organization: user.organization,
// 		userType: user.userType
// 	}
// 	console.log(updated);
// 	User.findOneAndUpdate({username : user.username}, updated, function(err, result){
// 		if (err) {
// 			route_callback(err, null);
// 		} else {
// 			route_callback(null, result);
// 		}
// 	});
// }

var deleteAllClaimsAfterAccepting = function(post, route_callback) {
	Claim.deleteMany({postId : post}).exec(route_callback);
}

// var getPassword = function(username, route_callback) {
// 	User.findOne({username : username}).select('password').exec(route_callback);
// }

module.exports = {
	deleteAllClaimsAfterAccepting: deleteAllClaimsAfterAccepting,
	createClaim: createClaim,
	getClaimsByDonor: getClaimsByDonor
}
