import pandas as pd
from sqlalchemy import create_engine
from sklearn.metrics.pairwise import cosine_similarity
from geopy.distance import great_circle
from sklearn.feature_extraction.text import TfidfVectorizer
import mysql.connector
from dotenv import load_dotenv
import os

# Load environment variables from .env file
load_dotenv()
DATABASE_HOST = os.getenv("DATABASE_HOST")
DATABASE_USER = os.getenv("DATABASE_USER")
DATABASE_PASSWORD = os.getenv("DATABASE_PASSWORD")
DATABASE_NAME = os.getenv("DATABASE_NAME")

# Buat koneksi ke database
connection = mysql.connector.connect(
    host=DATABASE_HOST,
    user=DATABASE_USER,
    password=DATABASE_PASSWORD,
    database=DATABASE_NAME
)

# Create SQLAlchemy engine
engine = create_engine(f'mysql+mysqlconnector://{DATABASE_USER}:{DATABASE_PASSWORD}@{DATABASE_HOST}/{DATABASE_NAME}')

# Function for recommending tourist destinations based on distance user:
def recommend(place_name, user_location):
    # Read data from MySQL using SQLAlchemy engine
    query = "SELECT * FROM historical_place"
    df = pd.read_sql(query, engine)

    # Convert the place_name and user_location to lowercase
    place_name = place_name.lower()

    # Calculate the cosine similarity between place_name and names in the dataset
    tf = TfidfVectorizer(analyzer='word', stop_words='english')
    tfidf_matrix = tf.fit_transform(df['name'])
    similarity_scores = cosine_similarity(tf.transform([place_name]), tfidf_matrix).flatten()

    # Get the indices of the most similar places
    similar_indices = similarity_scores.argsort()[::-1][1:6]

    # Filter recommendations based on distance
    def calculate_distance(row):
        place_location = (row['latitude'], row['longitude'])
        return great_circle(user_location, place_location).kilometers

    df['distance'] = df.apply(calculate_distance, axis=1)

    # Combine similarity and distance to rank recommendations
    rec = df[['name', 'types', 'distance', 'rating']].iloc[similar_indices]
    rec = rec.sort_values(by=['distance', 'rating'], ascending=[True, False])

    # return rec

user_location = (-7.760882, 110.415348)

print(recommend("candi", user_location))
print(recommend("Museum", user_location))
