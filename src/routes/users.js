const express = require('express');
const router = express.Router();

const UserController = require('../controller/users');

// CREATE USER - POST
router.post('/', UserController.createNewUser);

// READ USER - GET
router.get('/', UserController.getAllUsers);

// UPDATE USER - PATCH
router.patch('/:id', UserController.updateUser);

// DELETE USER - DELETE
router.delete('/:id', UserController.deleteUser);


module.exports = router;
