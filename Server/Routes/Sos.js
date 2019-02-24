const express = require("express");
const router = express.Router();
const { Sos } = require('../Models/Sos')


router.post("/newSOS", (req, res) => {

        const newSOS = new Sos({
            userID: req.body.uid,
            longitude: req.body.longitude,
            latitude: req.body.latitude
        })
    
        newSOS.save().then(sos => {
            res.send(sos)
        }).catch(error => {
            res.status(400).send(error);
        })
    });


module.exports = router;