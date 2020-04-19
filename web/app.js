var express = require('express');
var app = express();
const {MongoClient} = require('mongodb');


// set up EJS
app.set('view engine', 'ejs');

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
app.get('/setclaimmessage', routes.set_claim_message);
app.get('/loginCheck', routes.check_password);
app.get('/usernameTaken', routes.check_username);
app.get('/updateaccount', routes.update_account);
app.get('/getUsers', routes.get_users);
app.get('/deleteaccount', routes.deleteaccount);
app.get('/console', routes.console);
app.get('/getUser', routes.get_user);
app.get('/getPost', routes.get_post);
app.get('/map', routes.displayMap);
app.post('/user', routes.displayUser);

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


app.listen(3000, function () {
  console.log('listening on port 3000!');
});
