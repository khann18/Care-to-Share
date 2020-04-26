var express = require('express');
var app = express();
const {MongoClient} = require('mongodb');
var request = require("request");
var path = require("path");

// set up EJS
app.set('view engine', 'ejs');
app.use(express.static(__dirname + '/styles'));

// set up BodyParser
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
var routes = require('./routes/routes.js');

async function listDatabases(client){
    databasesList = await client.db().admin().listDatabases();

    console.log("Databases:");
    databasesList.databases.forEach(db => console.log(` - ${db.name}`));
};

app.get('/', routes.login);
app.get('/post', routes.create_post);
app.get('/disapprove', routes.admin_disapprove);
app.get('/approve', routes.admin_approve);
app.get('/home', routes.get_admin_post);
app.get('/createaccount', routes.create_user);
app.get('/createpost', routes.create_post);
app.get('/setPostIsClaimed', routes.set_post_is_claimed);
app.get('/loginCheck', routes.check_password);
app.get('/usernameTaken', routes.check_username);
app.get('/updateaccount', routes.update_account);
app.get('/getUsers', routes.get_users);
app.get('/deleteaccount', routes.deleteaccount);
app.get('/console', routes.console);
app.get('/getUser', routes.get_user);
app.get('/getPost', routes.get_post);
app.post('/user', routes.displayUser);
app.post('/deleteUser', routes.deleteUser);
app.get('/getClaimsByDonor', routes.get_claims_by_donor);
app.get('/getClaimsByObtainer', routes.get_claims_by_obtainer);
app.get('/deleteAllClaimsAfterAccepting', routes.delete_all_claims_after_accepting);
app.get('/createClaim', routes.create_claim);
app.get('/findPostById', routes.find_post_by_id);
app.get('/getClaimById', routes.get_claim_by_id);
app.get('/updateClaimStatus', routes.update_claim_status);
app.get('/updateClaimsForAcceptedPost', routes.update_claims_for_accepted_post);
app.get('/getCPost', routes.get_close_posts);
app.get('/data', routes.get_data);
app.get('/visualize', routes.visualizeData);
app.get('/getAllUsers', routes.getAllUsers);
app.get('/getStatsForProfile', routes.get_stats_for_profile);




app.listen(3000, function () {
  console.log('listening on port 3000!');
});
