// importing dependencies
const express = require("express");
const mongoose = require("mongoose");
const bodyparser = require("body-parser");
const sos = require("./Routes/Sos");
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
app.use("/sos", sos);


// testing out the connection (Try this on your computer)

app.get("/work", (req, res) => {
  res.send({ work: "THE SERVER IS WORKING :D!!!!" })
});


const port = process.env.PORT || 3000;

// listening to port 3000
app.listen(port, () => console.log(`Server running on port: ` + port));
