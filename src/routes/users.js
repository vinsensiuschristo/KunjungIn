const express = require('express');
const router = express.Router();
const {validateToken} = require('../config/jwt');

const UserController = require('../controller/users');

// CREATE USER - POST
router.post('/', validateToken, UserController.createNewUser);

// READ USER - GET
router.get('/', UserController.getAllUsers);

// UPDATE USER - PATCH
router.patch('/:id', UserController.updateUser);

// DELETE USER - DELETE
router.delete('/:id', UserController.deleteUser);

// AUTH GOOGLE CALLBACK
// router.get('/', UserController.authLogin);


module.exports = router;
