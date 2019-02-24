const mongoose = require("mongoose");

const SosSchema = new mongoose.Schema({
    userID: {
    	type: String,
    	required: true
    },
    longitude: {
    	type: Number,
    	required: true
    },
    latitude: {
    	type: Number,
    	required: true
    }
});


const Sos = mongoose.model("Sos", SosSchema);

module.exports = { Sos };
