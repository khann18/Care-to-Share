/*  You each have your account. It might work if you use mine, but you should change "mkalil" for your username and
    MADDIE for your password in the below uri. Username is your email username and password is your first name all caps
*/
var mongoose = require('mongoose');
const db = 'mongodb+srv://khann22:KATHERINE@cluster0-bm2zb.mongodb.net/350Project?retryWrites=true&w=majority';
mongoose
    .connect(db, {
        useNewUrlParser: true,
        useCreateIndex: true,
        useUnifiedTopology: true
      })
    .then(() => console.log('MongoDB Claims connected...'))
    .catch(err => console.log(err));

var Schema = mongoose.Schema;

var claimSchema = new Schema({
	obtainerUsername: String, //username of person claiming
	donorUsername: String, //username of original poster
	postId: String,  //mongo generated postID
  claimMessage: String, //claim message description
  claimStatus: String,
  obtainerLocation: String,
  postLatlng: String
});

// export personSchema as a class called User
module.exports = mongoose.model('Claim', claimSchema);
