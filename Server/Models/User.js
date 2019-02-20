const mongoose = require('mongoose');

const UserSchema = new mongoose.Schema({
    userId: String,
    firstName: String,
    lastName: String,
    memStatus: String,
    bloodType: String,
    email: String,
    password: String,
    emergencyContact: String,
    medicalHistory: MedicalHistoryForm,
    otherMedicalConditions: String,
    notifications: [Notification]
})

const MedicalHistoryForm = new mongoose.Schema({
    highBloodPressue: Boolean,
    highCholesterol: Boolean,
    kidneyDisease: Boolean,
    thyroidProblems: Boolean,
    jointReplacement: Boolean,
    lungDisease: Boolean,
    stroke: Boolean,
    asthmas: Boolean,
    heartProblem: Boolean
})

const Notification = new mongoose.Schema({
    description: String,
    severity: String
})



UserSchema.statics.findByEmail = function(email) {
	const User = this

	return User.findOne({email: email}).then((user) => {
		if (user) {
			return user
		} else {
            return false
        }
	})
}


const User = mongoose.model('User', UserSchema);
const medInfo = mongoose.model('medInfo', MedicalHistoryForm);

module.exports = { User, medInfo };