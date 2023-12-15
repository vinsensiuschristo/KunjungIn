require('dotenv').config();
const express = require('express');
const app = express();
const port = process.env.PORT;
const morgan = require('morgan');
const userRoutes = require('./routes/users');

const placeRecommendationRoutes = require('./routes/place_recommendation');
const userRecommendationRoutes = require('./routes/recommendation');

const googleAuthRoutes = require('./routes/googleAuth');
const {google} = require('googleapis');
const bodyParser = require('body-parser');
const cookieParser = require('cookie-parser');

app.use(morgan('tiny'));
app.use(cookieParser());
// parse application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({extended: false}));
app.use(bodyParser.json());

// start google auth
const oauth2Client = new google.auth.OAuth2(
    process.env.GOOGLE_CLIENT_ID,
    process.env.GOOGLE_CLIENT_SECRET,
    'https://kunjungin-api-dot-kunjunginapp.et.r.appspot.com/auth/google/callback',
);

const scopes = [
  'https://www.googleapis.com/auth/userinfo.email',
  'https://www.googleapis.com/auth/userinfo.profile',
];

const authorizationURL = oauth2Client.generateAuthUrl({
  access_type: 'online',
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

// python routes, baru buat post aja kalau ada
// fungsi lain mungkin nanti bisa diganti jadi app.use
// ini juga langsung ke controller
app.use('/api/v1/placeRecommendation/', placeRecommendationRoutes);

// user recommendation
app.use('/api/v1/userRecommendation', userRecommendationRoutes);


app.listen(port, ()=> {
  console.log(`listening on port ${port}`);
});
