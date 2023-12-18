const {PrismaClient} = require('@prisma/client');
const prisma = new PrismaClient({errorFormat: 'minimal'});

const createRecommendation = async (req, res) =>{
  const {id} = req.params;
  const {place_type_id} = req.body;

  const newId = parseInt(id);
  const newPlaceTypeId = place_type_id.toString();

  const user = await prisma.user.findUnique({
    where: {
      id: newId,
    },
  });

  if (!user) {
    return res.status(409).json({
      error: true,
      message: 'ID is not registered',
    });
  }

  try {
    await prisma.user_recommendation.create({
      data: {
        user_id: newId,
        place_type_id: newPlaceTypeId,
      },
    });

    await prisma.user.update({
      where: {
        id: newId,
      },
      data: {
        recommendation_status: true,
      },
    });

    res.status(201).json({
      error: false,
      message: 'Recommendation for user ' + req.params.id + ' has been created',
    });
  } catch (e) {
    res.status(500).json({
      error: true,
      message: e.message,
    });
  }
};


const deleteRecommendation = (req, res)=> {
  console.log('deleteRecommendation');
};

module.exports = {createRecommendation, deleteRecommendation};
