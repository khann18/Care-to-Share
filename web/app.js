var express = require('express');
var app = express();

// set up EJS
app.set('view engine', 'ejs');

// set up BodyParser
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

var User = require('./database/users.js');
var Post = require('./database/posts.js');
var routes = require('./routes/routes.js');



//routes below are for testing purposes

app.use('/newUser', (req, res) => {

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

	// save the user to the database
	newUser.save( (err) => { 
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    console.log(err);
		    res.end();
		}
		else {
		   res.send('successful creation of new user: ' + req.query.firstName + ' ' + req.query.lastName);
		}
	    } ); 
    }
);

app.use('/newPost', (req, res) => {

	var newPost = new Post ({
		description: req.query.description,
    	location: req.query.location,
    	pickupTime: req.query.pickupTime,
    	postedBy: req.query.postedBy, //username of the poster
    	contactInfo: req.query.contactInfo //could be email or phone number
	    });

	// save the user to the database
	newPost.save( (err) => { 
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    console.log(err);
		    res.end();
		}
		else {
		   res.send('successful creation of new post: ' + req.query.description);
		}
	    } ); 
    }
);

//find user by username
app.get('/getUser', (req, res) => {
	User.findOne({username: req.query.username}, function(err, user) { 
		if (err || !user) {
			res.send('error: no user found');
		} else {
			res.send(user); 
		}
	});
});

//get all posts
app.get('/getPosts', (req, res) => {
	Post.find({}, function(err, posts) { 
		if (err || !posts) {
			res.send('error: no posts found');
		} else {
			res.send(posts); 
		}
	});
});


app.get('/', routes.login);

app.listen(3000, function () {
  console.log('listening on port 3000!');
});

