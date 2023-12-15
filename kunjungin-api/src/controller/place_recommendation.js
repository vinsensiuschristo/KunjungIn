// const express = require('express');
// const bodyParser = require('body-parser');
const axios = require('axios');

const getPlaceByDistance = async (req, res, next) => {
  // console.log('welcome to python');

  const existToken = req.cookies['token'];
  console.log(existToken);

  try {
    const dataToSend = req.body;
    // const dataToSend = {
    // "PlaceName": "Museum",
    // "latitude" : -6.5980046,
    // "longitude" : 106.7948917,
    // "city_id" : 8
    // };
    // Kirim data JSON ke aplikasi Python dengan mengatur header Content-Type
    const pythonResponse = await axios.post('https://kunjungin-python-dot-kunjunginapp.et.r.appspot.com/recommend-distance', dataToSend, {
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

const getPlaceByRating = async (req, res, next) => {
  // console.log('welcome to python');

  const existToken = req.cookies['token'];
  console.log(existToken);

  try {
    const dataToSend = req.body;
    // const dataToSend = {
    // "PlaceName": "Museum",
    // "types": "museum|tourist_attraction|point_of_interest|establishment",
    // "city_id" : 8
    // };
    // Kirim data JSON ke aplikasi Python dengan mengatur header Content-Type
    const pythonResponse = await axios.post('https://kunjungin-python-dot-kunjunginapp.et.r.appspot.com/recommend-rating', dataToSend, {
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
    // Kirim data JSON ke aplikasi Python dengan mengatur header Content-Type
    const pythonResponse = await axios.get('https://kunjungin-python-dot-kunjunginapp.et.r.appspot.com/places', {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + existToken,
      },
    });

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

module.exports = {getAllPlaces, getPlaceByDistance, getPlaceByRating};
