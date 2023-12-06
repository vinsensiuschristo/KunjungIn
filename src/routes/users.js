const express = require('express');
const router = express.Router();
const {validateToken} = require('../config/jwt');

const UserController = require('../controller/users');

// CREATE USER - POST
router.post('/', validateToken, UserController.createNewUser);

// READ USER - GET
router.get('/', validateToken, UserController.getAllUsers);

// UPDATE USER - PATCH
router.patch('/:id', validateToken, UserController.updateUser);

// DELETE USER - DELETE
router.delete('/:id', validateToken, UserController.deleteUser);

// REGISTER USER
router.post('/register', UserController.registerUser);

// LOGIN USER
router.post('/login', UserController.loginUser);

module.exports = router;
