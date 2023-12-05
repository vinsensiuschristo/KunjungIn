const {sign, verify} = require('jsonwebtoken');

const createTokens = (user) => {
  return sign({email: user.email, id: user.id}, 'jwtsecret');
};

const validateToken = (req, res, next) => {
  const existToken = req.cookies['token'];

  if (!existToken) {
    return res.status(403).json({
      status: 'Invalid',
      message: 'user not authenticated',
    });
  }

  try {
    const validToken = verify(existToken, 'jwtsecret');

    if (validToken) {
      req.authenticated = true;
      return next();
    }
  } catch (err) {
    return res.json({
      status: 'Invalid',
      message: err.message,
    });
  }
};

module.exports = {createTokens, validateToken};
