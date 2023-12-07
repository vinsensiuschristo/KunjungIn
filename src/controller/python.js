// const express = require('express');
// const bodyParser = require('body-parser');
const axios = require('axios');

const sendToPython = async (req, res, next) => {
  console.log('welcome to python');

  try {
    const dataToSend = req.body;

    // Kirim data JSON ke aplikasi Python dengan mengatur header Content-Type
    const pythonResponse = await axios.post('http://localhost:5000/process-data', dataToSend, {
      headers: {
        'Content-Type': 'application/json',
      },
    });

    console.log('Response from Python:', pythonResponse.data);

    // Kirim respons dari Python kembali ke client (Express.js)
    res.json({
      message: 'Data processed successfully', result: pythonResponse.data,
    });
  } catch (error) {
    console.error('Error communicating with Python:', error.message);
    res.status(500).json({error: 'Internal server error'});
  }
};

module.exports = {sendToPython};
