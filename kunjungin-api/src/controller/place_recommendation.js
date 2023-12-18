// const express = require('express');
// const bodyParser = require('body-parser');
const {PrismaClient} = require('@prisma/client');
const prisma = new PrismaClient();
const axios = require('axios');

const getPlaceByDistance = async (req, res, next) => {
  // console.log('welcome to python');

  const existToken = req.cookies['token'];
  // console.log(existToken);

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

    // console.log('Response from Python:', pythonResponse.data);

    // Kirim respons dari Python kembali ke client (Express.js)
    res.json({
      error: false,
      message: 'Data processed successfully',
      result: pythonResponse.data,
    });
  } catch (error) {
    console.error('Error communicating with Python:', error.message);
    res.status(500).json({
      error: true,
      message: 'Internal server error',
    });
  }
};

const getPlaceByRating = async (req, res, next) => {
  // console.log('welcome to python');

  const existToken = req.cookies['token'];
  // console.log(existToken);

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

    // console.log('Response from Python:', pythonResponse.data);

    // Kirim respons dari Python kembali ke client (Express.js)
    res.json({
      error: false,
      message: 'Data processed successfully',
      result: pythonResponse.data,
    });
  } catch (error) {
    console.error('Error communicating with Python:', error.message);
    res.status(500).json({
      error: true,
      message: 'Internal server error'});
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
      error: false,
      message: 'Data processed successfully',
      result: pythonResponse.data,
    });
  } catch (e) {
    res.status(500).json({
      error: true,
      message: e.message,
    });
  }
};

const getDetailPlaces = async (req, res, next) => {
  const placeId = parseInt(req.params.id, 10);

  try {
    const placeDetails = await prisma.historical_place.findUnique({
      where: {
        id: placeId,
      },
    });

    if (!placeDetails) {
      return res.status(404).json({
        error: true,
        message: 'Place is not found'});
    }

    res.json({
      error: false,
      message: 'Data processed successfully',
      result: placeDetails});
  } catch (error) {
    console.error('Error:', error);
    res.status(500).json({
      error: true,
      message: 'Internal server error'});
  } finally {
    await prisma.$disconnect(); // Pastikan untuk memutus koneksi setelah selesai
  }
};

module.exports = {getAllPlaces, getDetailPlaces, getPlaceByDistance, getPlaceByRating};
