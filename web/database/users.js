/*  You each have your account. It might work if you use mine, but you should change "mkalil" for your username and
    MADDIE for your password in the below uri. Username is your email username and password is your first name all caps 
*/
var mongoose = require('mongoose');
const db = 'mongodb+srv://ayang015:La890729@0607@cluster0-qf07n.mongodb.net/test?retryWrites=true&w=majority';
mongoose
    .connect(db, { 
        useNewUrlParser: true,
        useCreateIndex: true,
        useUnifiedTopology: true
      })
    .then(() => console.log('MongoDB connected...'))
    .catch(err => console.log(err));

var Schema = mongoose.Schema;

var userSchema = new Schema({
    firstName: String,
    lastName: String,
    location: String,
    userType: String,
    username: String,
    password: String,
    phoneNumber: Number,
    email: String,
    organization: String
    });

// export personSchema as a class called User
module.exports = mongoose.model('User', userSchema);

userSchema.methods.standardizeName = function() {
    this.name = this.name.toLowerCase();
    return this.name;
}

