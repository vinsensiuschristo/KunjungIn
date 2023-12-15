from flask import Flask, request, jsonify
import pandas as pd
from sqlalchemy import create_engine
from sklearn.metrics.pairwise import cosine_similarity
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
def recommend(place_name, types):
    # Read data from MySQL using SQLAlchemy engine
    query = "SELECT * FROM historical_place"
    df = pd.read_sql(query, engine)

    data = df[df['types'] == types]
    data.reset_index(inplace=True)
    indices = pd.Series(data.index, index=data['name'])

    tf = TfidfVectorizer(analyzer='word', ngram_range=(2, 2), min_df=1, stop_words='english')
    tfidf_matrix = tf.fit_transform(data['name'])

    sg = cosine_similarity(tfidf_matrix, tfidf_matrix)

    idx = indices[place_name]
    sig = list(enumerate(sg[idx]))
    sig = sorted(sig, key=lambda x: x[1][0], reverse=True)
    sig = sig[1:6]
    tourist_indices = [i[0] for i in sig]

    rec = data[['name', 'types', 'rating']].iloc[tourist_indices]
    rec = rec.sort_values(by='rating', ascending=False)
    return rec.to_dict('records')
    
@app.route('/recommendation', methods=['POST'])
def get_recommendation():
    data = request.get_json()
    place_name = data['place_name']
    types = data['types']
    recommendations = recommend(place_name, types)
    return jsonify(recommendations)

if __name__ == '__main__':
    app.run(port=5000,debug=True)