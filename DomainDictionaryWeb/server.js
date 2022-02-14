const express = require('express');
const path = require('path');

const app = express();

// Serve only the static files form the angularapp directory
app.use(express.static('./dist/'));
app.get('/*', function(req,res) {

    res.sendFile('index.html',{root:'./dist'});
});
// Start the app by listening on the default Heroku port
app.listen(process.env.PORT || 4200);