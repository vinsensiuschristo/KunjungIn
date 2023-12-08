const express = require('express');
const router = express.Router();
const {validateToken} = require('../config/jwt');

const PythonController = require('../controller/python');

// CREATE USER - POST
// router.post('/', PythonController.sendToPython);

// GET ALL PLACES
router.get('/places', validateToken, PythonController.getAllPlaces);

// GET ALL CITY
// router.get('/cities/', validateToken, PythonController.getCity)

// GET PLACE WITH CITY
// router.get('/cities/:city_id', validateToken, PythonController.getPlaceWithCity)

// GET PLACE DETAIL
// router.get('/cities/:city_id/places/:place_id', validateToken, PythonController.getPlaceDetail)

// GET PLACE WITH DISTANCE RECOMMENDATION
router.post('/places/nearby', validateToken, PythonController.getPlaceNearby);

// GET PLACE WITH RATING
// router.get('/places/rating', validateToken, PythonController.getRating)

// GET ALL GUIDER
// router.get('/guiders/', validateToken, PythonController.getAllGuiders)

// GET ALL GUIDER BY RATING
// router.get('/guiders/byRating', validateToken, PythonController.)

module.exports = router;
