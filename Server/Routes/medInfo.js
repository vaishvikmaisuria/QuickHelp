const express = require("express");
const router = express.Router();

// Load User model
const User = require("../Models/User");

router.post("/save", (req, res) => {

  // Find user by email
  User.findOne({ email }).then(user => {
    // Check for user
    const newInfo = new medInfo(req.body);
  });
});


module.exports = router;