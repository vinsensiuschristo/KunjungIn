const dbPool = require('../config/database');

const getAllUsers = () => {
  const SQLQuery ='SELECT id, name, email, date_of_birth, ' +
                          'address, rating FROM user';
  return dbPool.execute(SQLQuery);
};

const addUser = (body) => {
  const SQLQuery = `INSERT INTO user 
            (name, email, date_of_birth, address, rating, created_at) 
            VALUES ('${body.name}', '${body.email}', 
            '${body.date_of_birth}', '${body.address}', 
            '${body.rating}','${body.created_at}')`;
  return dbPool.execute(SQLQuery);
};

const updateUser = (body, id) => {
  const SQLQuery = `UPDATE user
       SET name='${body.name}', email='${body.email}', address='${body.address}'
       WHERE id = '${id}'`;
  return dbPool.execute(SQLQuery);
};

const deleteUser = (id) => {
  const SQLQuery = `DELETE FROM user WHERE id = ${id}`;
  return dbPool.execute(SQLQuery);
};

module.exports = {getAllUsers, addUser, updateUser, deleteUser};
