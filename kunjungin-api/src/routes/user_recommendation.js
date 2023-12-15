const express = require('express');
const router = express.Router();
const {validateToken} = require('../config/jwt');

const RecommendationController = require('../controller/user_recommendation');

// ADD RECOMMENDATION TO USER
router.post('/:id/recommendation', validateToken,
    RecommendationController.createRecommendation);

// ADD RECOMMENDATION TO USER
router.delete('/:id/recommendation', validateToken,
    RecommendationController.deleteRecommendation);

module.exports = router;
