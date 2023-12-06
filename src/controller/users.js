const UsersModel = require('../models/users');
const {google} = require('googleapis');
const {PrismaClient} = require('@prisma/client');
const prisma = new PrismaClient();
const jwt = require('jsonwebtoken');
const bcrypt = require('bcrypt');
const {createTokens} = require('../config/jwt');

// start google auth
const oauth2Client = new google.auth.OAuth2(
    process.env.GOOGLE_CLIENT_ID,
    process.env.GOOGLE_CLIENT_SECRET,
    'http://localhost:3001/auth/google/callback',
);
// end google auth

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

const authLogin = async (req, res) => {
  const {code} = req.query;

  const {tokens} = await oauth2Client.getToken(code);

  oauth2Client.setCredentials(tokens);

  const oauth2 = google.oauth2({
    auth: oauth2Client,
    version: 'v2',
  });

  const {data} = await oauth2.userinfo.get();

  if (!data.email || !data.name) {
    return res.json({
      data: data,
    });
  }

  let user = await prisma.user.findUnique({
    where: {
      email: data.email,
    },
  });

  if (!user) {
    user = await prisma.user.create({
      data: {
        name: data.name,
        email: data.email,
        address: '-',
        password: '-',
        rating: 0,
      },
    });
  }

  const payload = {
    id: user?.id,
    name: user?.name,
    address: user?.address,
  };

  const secret = 'jwtsecret';

  const expiresIn = 60 * 60;

  const token = jwt.sign(payload, secret, {expiresIn: expiresIn});

  // return res.redirect(`http://localhost:3000/auth-success?token=${token}`)

  return res.json({
    data: {
      id: user.id,
      name: user.name,
      address: user.address,
    },
    token: token,
  });
};

const registerUser = (req, res) => {
  const {email, password} = req.body;

  try {
    bcrypt.hash(password, 10).then((hash) => {
      prisma.user.create({
        data: {
          email: email,
          name: req.body.name,
          password: hash,
          rating: 0,
          address: req.body.address,
        },
      }).then(() => {
        res.status(201).json({
          status: 'success',
          message: `Created ${email} successfully`,
        });
      });
    });
  } catch (err) {
    res.status(400).json({
      status: 'error',
      message: err.message,
    });
  }
};

const loginUser = async (req, res)=> {
  const {email, password} = req.body;

  const user = await prisma.user.findUnique({
    where: {
      email: email,
    },
  });

  // cek email, udah ada email yang di
  // cari di database atau belum (KALAU GA ADA)
  if (!user) {
    res.status(400).json({error: 'User Doesn\'t Exist'});
  }

  // (KALAU ADA)
  if (user) {
    const dbPassword = user.password;
    bcrypt.compare(password, dbPassword).then((match) => {
      if (!match) {
        res.status(401).json({
          message: 'wrong credentials',
        });
      } else {
        const accessTokens = createTokens(user);

        res.cookie('token', accessTokens, {
          maxAge: 60 * 60 * 24 * 1000,
          httpOnly: true,
        });

        res.status(200).json({
          message: 'buat token',
        });
      }
    });

    // res.json('login')
  }
};


module.exports = {
  getAllUsers,
  createNewUser,
  updateUser,
  deleteUser,
  authLogin,
  registerUser,
  loginUser,
};
