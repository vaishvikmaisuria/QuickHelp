const mongoose = require("mongoose");

const UserSchema = new mongoose.Schema({
  username: {
    type: String,
    required: true
  },
  firstName: {
    type: String,
    required: true
  },
  lastName: {
    type: String,
    required: true
  },
  memStatus: {
    type: String,
    required: true
  },
  bloodType: {
    type: String
  },
  email: {
    type: String,
    required: true
  },
  password: {
    type: String
  },
  emergencyContact: {
    type: String
  },
  medicalHistory: {
    type: Object
  },
  otherMedicalConditions: {
    type: String
  },
  notifications: {
    type: Array
  }
});

const MedicalHistoryForm = new mongoose.Schema({
  highBloodPressue: {
    type: Boolean
  },
  highCholesterol: {
    type: Boolean
  },
  kidneyDisease: {
    type: Boolean
  },
  thyroidProblems: {
    type: Boolean
  },
  jointReplacement: {
    type: Boolean
  },
  lungDisease: {
    type: Boolean
  },
  stroke: {
    type: Boolean
  },
  asthmas: {
    type: Boolean
  },
  heartProblem: {
    type: Boolean
  }
});

const Notification = new mongoose.Schema({
  description: String,
  severity: String
});

UserSchema.statics.findByEmail = function(email) {
  const User = this;

  return User.findOne({ email: email }).then(user => {
    if (user) {
      return user;
    } else {
      return false;
    }
  });
};

const User = mongoose.model("User", UserSchema);

module.exports = { User };
