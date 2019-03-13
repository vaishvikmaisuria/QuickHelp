const express = require("express");
const router = express.Router();

// Load User model
const User = require("../Models/User");

router.post("/login", (req, res) => {
  const username = req.body.username;
  const password = req.body.password;

  // Find user by email
  User.findOne({ email }).then(user => {
    // Check for user
    if (!user) {
      return res.status(404).json({ RIP: "SHIT!" });
    }
  });
});

router.post("/register", (req, res) => {
  User.findOne({ email: req.body.email }).then(user => {
    if (user) {
      res.send({ email: "Email already exists" });
    }
    const newUser = new User(req.body);
  });
});

router.get("/userInfo/:Username", (req, res) => {
  const username = req.params.Username;
  User.findOne({ username : username }).then(user => {
    console.log("us: "+username);
    if (user) {
      res.send({ user });
    }
  });
});

module.exports = router;
