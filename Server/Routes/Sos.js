const express = require("express");
const router = express.Router();
const { Sos } = require('../Models/Sos')


router.post("/newSOS", (req, res) => {

        const newSOS = new Sos({
            userID: req.body.uid,
            helperID: "",
            injuryDetails: "",
            longitude: req.body.longitude,
            latitude: req.body.latitude,
            Active: true
        })
    
        newSOS.save().then(sos => {
            res.send(sos)
        }).catch(error => {
            res.status(400).send(error);
        })
    });

router.patch("/updateSOS", (req, res) => {
    Sos.updateOne({_id: req.body.id}, {$set: { injuryDetails: req.body.info }}).catch(error => {
        res.status(400).send(error);
    })
});

router.patch("/updateDoc", (req, res) => {
    Sos.updateOne({_id: req.body.id}, {$set: { helperID: req.body.doc }}).catch(error => {
        res.status(400).send(error);
    })
});

router.get("/allSOS", (req, res) => {
    Sos.find({ Active: true }).then(allSos => {
        if (allSos) {
            res.send({ allSos });
        }
    })
});

module.exports = router;