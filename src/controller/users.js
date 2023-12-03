const UsersModel = require('../models/users');
const createNewUser = async (req, res) => {
  const rating = 0;
  const dateOb = new Date();
  // current date
  // adjust 0 before single digit date
  const date = ('0' + dateOb.getDate()).slice(-2);
  // current month
  const month = ('0' + (dateOb.getMonth() + 1)).slice(-2);
  // current year
  const year = dateOb.getFullYear();
  // current hours
  const hours = dateOb.getHours();
  // current minutes
  const minutes = dateOb.getMinutes();
  // current seconds
  const seconds = dateOb.getSeconds();

  // prints date & time in YYYY-MM-DD HH:MM:SS format
  const createdAt = year + '-' + month + '-' + date +
      ' ' + hours + ':' + minutes + ':' + seconds;

  const {body} = req;

  const newBody = {
    name: body.name,
    email: body.email,
    date_of_birth: body.date_of_birth,
    rating: rating,
    address: body.address,
    created_at: createdAt,
  };

  // console.log(newBody);

  try {
    await UsersModel.addUser(newBody);

    res.status(200).json(
        {
          status: `Create User ${req.body.name} successfully added`,
          data: body,
        },
    );
  } catch (err) {
    res.status(500).json(
        {
          status: `Create User ${req.body.name} failed`,
          message: err.message,
        },
    );
  }
};

const getAllUsers = async (req, res) => {
  try {
    const [data] = await UsersModel.getAllUsers();

    res.json({
      message: 'success',
      data: data,
    });
  } catch (err) {
    res.status(500).json({
      message: 'Server error',
      serverMessage: err.message,
    });
  }
};

const updateUser = async (req, res) => {
  const {id} = req.params;
  const {body} = req;

  try {
    await UsersModel.updateUser(body, id);

    res.json({
      message: `Update User ${id} successfully`,
      data: req.body,
    });
  } catch (err) {
    res.status(500).json({
      status: 'database error',
      message: err.message,
    });
  }
};

const deleteUser = async (req, res) => {
  const {id} = req.params;

  try {
    await UsersModel.deleteUser(id);

    res.json({
      status: 'success',
      message: `Delete User ${id} successfully`,
    });
  } catch (err) {
    res.status(500).json({
      status: 'database error',
      message: err.message,
    });
  }
};


module.exports = {
  getAllUsers,
  createNewUser,
  updateUser,
  deleteUser,
};
