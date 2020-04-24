var express = require('express');
var app = express();
const {MongoClient} = require('mongodb');
var request = require("request");


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
// app.get('/setclaimmessage', routes.set_claim_message);
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
app.get('/getAllUsers', routes.getAllUsers);

app.get('/testRoute', function(req, res) {
	async function main(){
    /**
     * Connection URI. Update <username>, <password>, and <your-cluster-url> to reflect your cluster.
     * See https://docs.mongodb.com/ecosystem/drivers/node/ for more details
     */
    const uri = "mongodb+srv://ayang015:La890729@0607@cluster0-qf07n.mongodb.net/test?retryWrites=true&w=majority";


    const client = new MongoClient(uri, {
        useNewUrlParser: true,
        useCreateIndex: true,
        useUnifiedTopology: false
      });

    try {
        // Connect to the MongoDB cluster
        await client.connect();

        // await client.getCollection('users');

        // Make the appropriate DB calls
        await  listDatabases(client);

    } catch (e) {
        console.error(e);
    } finally {
        await client.close();
    }
    res.send(200);
}

main().catch(console.error);
});


app.get('/testAPI', function(req, res) {
    var API_KEY = "AIzaSyD9L96DpB9wyP4Are37YqzlJlICplSR-B0";
    var BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    var address = "1600 Amphitheatre Parkway, Mountain View, CA";

    var url = BASE_URL + address + "&key=" + API_KEY;

    request(url, function (error, response, body) {
        if (!error && response.statusCode == 200) {
            // console.log(Object.keys(body));
            var response = JSON.parse(body);
            console.log(response.results['0'].geometry.location);
            console.log(Object.keys(response.results));

            res.json(body);
        }
        else {
            console.log("Fail");
            res.send(200);
            // The request failed, handle it
        }
    });
})

app.get('/testAPI', function(req, res) {
    var API_KEY = "AIzaSyD9L96DpB9wyP4Are37YqzlJlICplSR-B0";
    var BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    var address = "1600 Amphitheatre Parkway, Mountain View, CA";

    var url = BASE_URL + address + "&key=" + API_KEY;

    request(url, function (error, response, body) {
        if (!error && response.statusCode == 200) {
            // console.log(Object.keys(body));
            var response = JSON.parse(body);
            console.log(response.results['0'].geometry.location);
            console.log(Object.keys(response.results));

            res.json(body);
        }
        else {
            console.log("Fail");
            res.send(200);
            // The request failed, handle it
        }
    });
})


app.listen(3000, function () {
  console.log('listening on port 3000!');
});
