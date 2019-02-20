// importing dependencies
const express = require("express");
const mongoose = require("mongoose");
const bodyparser = require("body-parser");

const users = require("./Routes/user");

// import key
const db = require("./Config/keys").mongoURI;

// create an express app
const app = express();

// Body parser middleware
app.use(bodyparser.urlencoded({ extended: false }));
app.use(bodyparser.json());

// connect to mongo server
mongoose
  .connect(db)
  .then(() => console.log("MongoDb server connected..."))
  .catch(err => console.log(err));

// routes
app.use("/user", users);

// testing out the connection (Try this on your computer)
app.get("/work", (req, res) =>
  res.json({ work: "THE SERVER IS WORKING :D!!!!" })
);

// listening to port 3000
app.listen(3000, () => console.log(`Server running on port 3000`));
