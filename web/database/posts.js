 //this file only for the Post schema

var mongoose = require('mongoose');
const db = 'mongodb+srv://mkalil:MADDIE@cluster0-bm2zb.mongodb.net/350Project?retryWrites=true&w=majority';
mongoose
    .connect(db, {
        useNewUrlParser: true,
        useCreateIndex: true,
        useUnifiedTopology: true
      })
    .then(() => console.log('MongoDB connected...'))
    .catch(err => console.log(err));

var Schema = mongoose.Schema;

var postSchema = new Schema({
	description: String,
    location: String,
    pickupTime: Date,
    postedBy: String, //username of the poster
    contactInfo: String, //could be email or phone number
    marked: String
    });

// export personSchema as a class called User
module.exports = mongoose.model('Post', postSchema);

postSchema.methods.standardizeName = function() {
    this.name = this.name.toLowerCase();
    return this.name;
}
