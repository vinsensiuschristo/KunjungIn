// const express = require('express');
// const bodyParser = require('body-parser');
const axios = require('axios');

const sendToPython = async (req, res, next) => {
  // console.log('welcome to python');

  const existToken = req.cookies['token'];
  console.log(existToken);

  try {
    const dataToSend = req.body;

    // Kirim data JSON ke aplikasi Python dengan mengatur header Content-Type
    const pythonResponse = await axios.post('https://kunjungin-python-dot-kunjunginapp.et.r.appspot.com/process-data', dataToSend, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + existToken,
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

const getAllPlaces = async (req, res, next) => {
  const existToken = req.cookies['token'];

  try {
    const dataToSend = req.body;

    // Kirim data JSON ke aplikasi Python dengan mengatur header Content-Type
    const pythonResponse = await axios.post('https://kunjungin-python-dot-kunjunginapp.et.r.appspot.com/places', dataToSend, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + existToken,
      },
    });

    console.log('Response from Python:', pythonResponse.data);

    // Kirim respons dari Python kembali ke client (Express.js)
    res.json({
      message: 'Data processed successfully', result: pythonResponse.data,
    });
  } catch (e) {
    res.status(500).json({
      error: true,
      message: e.message,
    });
  }
};

module.exports = {sendToPython, getAllPlaces};
