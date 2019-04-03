const mongoose = require("mongoose");

const SosSchema = new mongoose.Schema({
    userID: {
    	type: String,
    	required: true
    },
    helperID: {
    	type: String,
    },
    injuryDetails: {
        type: String
    }
    ,
    longitude: {
    	type: Number,
    	required: true
    },
    latitude: {
    	type: Number,
    	required: true
    },
    Active: {
        type: Boolean,
    }
    
});


const Sos = mongoose.model("Sos", SosSchema);

module.exports = { Sos };
