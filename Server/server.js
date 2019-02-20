// importing dependencies
const express = require("express");
const mongoose = require("mongoose");

const medInfoSave= require("./Routes/medInfo");

// import key
const db = require("./Config/keys").mongoURI;

// connect to mongo server
mongoose
  .connect(db)
  .then(() => console.log("MongoDb server connected..."))
  .catch(err => console.log(err));

// create an express app
const app = express();

// testing out the connection (Try this on your computer)
app.get("/work", (req, res) =>
  res.json({ work: "THE SERVER IS WORKING :D!!!!" })
);

app.use("/medInfo", medInfoSave);

// listening to port 3000
app.listen(3000, () => console.log(`Server running on port 3000`));
