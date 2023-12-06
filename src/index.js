require('dotenv').config();
const express = require('express');
const app = express();
const port = process.env.PORT;
const morgan = require('morgan');
const userRoutes = require('./routes/users');
const googleAuthRoutes = require('./routes/googleAuth');
const {google} = require('googleapis');
const bodyParser = require('body-parser');
const cookieParser = require('cookie-parser');

app.use(morgan('tiny'));
app.use(cookieParser());
// parse application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({extended: false}));

// start google auth
const oauth2Client = new google.auth.OAuth2(
    process.env.GOOGLE_CLIENT_ID,
    process.env.GOOGLE_CLIENT_SECRET,
    'http://localhost:3001/auth/google/callback',
);

const scopes = [
  'https://www.googleapis.com/auth/userinfo.email',
  'https://www.googleapis.com/auth/userinfo.profile',
];

const authorizationURL = oauth2Client.generateAuthUrl({
  access_type: 'offline',
  scope: scopes,
  include_granted_scopes: true,
});
// end google auth

app.use(express.json());

// user routes
app.use('/api/v1/users', userRoutes);

// route for Google auth
app.get('/auth/google', (req, res) => {
  res.redirect(authorizationURL);
});

// callback login
app.use('/auth/google', googleAuthRoutes);

app.listen(port, ()=> {
  console.log(`listening on port ${port}`);
});
