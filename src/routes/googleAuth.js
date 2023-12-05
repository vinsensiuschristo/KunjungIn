const express = require('express');
const router = express.Router();

const UserController = require('../controller/users');

// AUTH GOOGLE CALLBACK
router.get('/callback', UserController.authLogin);


module.exports = router;
