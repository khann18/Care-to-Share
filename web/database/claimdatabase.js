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
	console.log("Finding claims by donor:")
	console.log(username);
	Claim.find({donorUsername : username, claimStatus : 'none'}).exec(route_callback);
}

var getClaimsByObtainer = function(username, route_callback) {
	console.log("Finding claims by obtainer:")
	console.log(username);
	Claim.find({obtainerUsername : username}).exec(route_callback);
}

var getClaimById = function(claimId, route_callback) {
	console.log("Finding claim for")
	console.log(claimId);
	Claim.findById(claimId).exec(route_callback);
}

var updateClaimStatus = function(claimId, route_callback) {
	Claim.findById(claimId).exec(route_callback);
}

var getAllClaims = function(username, route_callback) {
	Claim.find().exec(route_callback);
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

var updateClaimsForAcceptedPost = function(id, route_callback) {
	console.log("Updating claims to be rejected")
	Claim.updateMany({postId : id}, {$set : {claimStatus : 'rejected'}}).exec(route_callback);
}

var getAcceptedClaims = function(req, route_callback) {
	Claim.find({claimStatus: "accepted"}).exec(route_callback);
}

module.exports = {
	deleteAllClaimsAfterAccepting: deleteAllClaimsAfterAccepting,
	createClaim: createClaim,
	getClaimsByDonor: getClaimsByDonor,
	getClaimsByObtainer: getClaimsByObtainer,
	getClaimById: getClaimById,
	updateClaimStatus: updateClaimStatus,
	updateClaimsForAcceptedPost: updateClaimsForAcceptedPost,
	getAcceptedClaims: getAcceptedClaims,
	getAllClaims: getAllClaims
}
