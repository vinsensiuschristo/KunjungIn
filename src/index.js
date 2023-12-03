require('dotenv').config();
const express = require('express');
const app = express();
const port = process.env.PORT;
const morgan = require('morgan');
const userRoutes = require('./routes/users');
// const middlewareLogRequest = require('./middleware/logs');

app.use(morgan('tiny'));

app.use(express.json());

app.use('/api/v1/users', userRoutes);

app.listen(port, ()=> {
  console.log(`listening on port ${port}`);
});
