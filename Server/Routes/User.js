const express = require("express");
const router = express.Router();

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
  const username = req.body.username;
  const password = req.body.password;
  const email = req.body.email;

  const status = req.body.memStatus;
  const bloodtype = req.bloodtype.bloodType;
  const emergencyContact = req.body.emergencyContact;
  const med_hist = req.body.medicalHistory;
  const condition = req.body.otherMedicalConditions;
});

module.exports = router;
