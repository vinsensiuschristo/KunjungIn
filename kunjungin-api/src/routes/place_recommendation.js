const express = require('express');
const router = express.Router();
const {validateToken} = require('../config/jwt');

const PlaceRecommendationController = require('../controller/place_recommendation');

// CREATE USER - POST
// router.post('/', PythonController.sendToPython);

// GET ALL PLACES
router.get('/places', validateToken, PlaceRecommendationController.getAllPlaces);

// GET ALL CITY
// router.get('/cities/', validateToken, PythonController.getCity)

// GET ALL PLACE_TYPE
// router.get('/cities/', validateToken, PythonController.getCity)

// GET PLACE WITH CITY
// router.get('/cities/:city_id', validateToken, PythonController.getPlaceWithCity)

// GET PLACE DETAIL
// router.get('/cities/:city_id/places/:place_id', validateToken, PythonController.getPlaceDetail)

// GET PLACE WITH DISTANCE RECOMMENDATION
router.post('/places/nearby', validateToken, PlaceRecommendationController.getPlaceByDistance);

// GET PLACE WITH RATING
router.post('/places/top', validateToken, PlaceRecommendationController.getPlaceByRating);

// GET ALL GUIDER
// router.get('/guiders/', validateToken, PythonController.getAllGuiders)

// GET ALL GUIDER BY RATING
// router.get('/guiders/byRating', validateToken, PythonController.)

module.exports = router;
