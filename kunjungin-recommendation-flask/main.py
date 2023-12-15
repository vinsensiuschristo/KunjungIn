# pip install flask
# pip install requests
# pip install jsonify

from flask import Flask, request, jsonify
import mysql.connector, jwt
import pandas as pd
from sqlalchemy import create_engine
from sklearn.metrics.pairwise import cosine_similarity
from geopy.distance import great_circle
from sklearn.feature_extraction.text import TfidfVectorizer
from dotenv import load_dotenv
import os

# Load environment variables from .env file
load_dotenv()

DATABASE_HOST = os.getenv("DATABASE_HOST")
DATABASE_USER = os.getenv("DATABASE_USER")
DATABASE_PASSWORD = os.getenv("DATABASE_PASSWORD")
DATABASE_NAME = os.getenv("DATABASE_NAME")

JWT_SECRET = os.getenv("JWT_SECRET")

# Buat koneksi ke database
connection = mysql.connector.connect(
    host=DATABASE_HOST,
    user=DATABASE_USER,
    password=DATABASE_PASSWORD,
    database=DATABASE_NAME
)

app = Flask(__name__)

 # Create SQLAlchemy engine
engine = create_engine(f'mysql+mysqlconnector://{DATABASE_USER}:{DATABASE_PASSWORD}@{DATABASE_HOST}/{DATABASE_NAME}')

# Fungsi untuk melakukan validasi token
def validate_token(token):
    try:
        # Gantilah "secret-key" dengan kunci rahasia yang digunakan untuk menandatangani token
        decoded_token = jwt.decode(token, JWT_SECRET, algorithms=['HS256'])
        return decoded_token
    except jwt.ExpiredSignatureError:
        return {'error': 'Token has expired'}, 401
    except jwt.InvalidTokenError:
        return {'error': 'Invalid token'}, 401
    
# Tambahkan middleware untuk menangani tipe konten JSON
@app.before_request
def before_request():
    # print('before_request')

    token = request.headers.get('Authorization', '').split('Bearer ')[-1]
    # print(request.headers.get('Authorization', ''))
    # print(token)
    # # Validasi content type sebelum memproses permintaan
    # if request.headers['Content-Type'] != 'application/json':
    #     return jsonify({'error': 'Content-Type must be application/json'}), 415
    
    # Validasi token sebelum memproses permintaan
    if not token:
        return jsonify({'error': 'Token is missing'}), 401
    
    validation_result = validate_token(token)

    if 'error' in validation_result:
        return jsonify(validation_result), 401
    
@app.route('/recommend-distance', methods=['POST'])
def recommend_distance():
    try:
        data_received = request.get_json()
         # Extract user location information
        
        place_name = data_received.get('PlaceName', '')
        latitude = data_received.get('latitude', 0.0)
        longitude = data_received.get('longitude', 0.0)
        city_id = data_received.get('city_id', 0)

        # Read data from MySQL using SQLAlchemy engine
        query = f"SELECT * FROM historical_place where city_id ={city_id}"
        df = pd.read_sql(query, engine)

        # Function for recommending tourist destinations based on distance user:
        def recommend(place_name, user_location):
            
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

             # Convert the DataFrame to a list of dictionaries
            result_data = rec.to_dict(orient='records')

            return result_data

        user_location = (latitude, longitude)
        # print(recommend("candi", user_location))
        result_data = recommend(place_name, user_location)
        return jsonify(result_data)
    except Exception as e:
        print('Error processing data:', str(e))
        return jsonify({"error": "Internal server error"}), 500

# ...

@app.route('/recommend-rating', methods=['POST'])
def recommend_rating():
    try:
        data_received = request.get_json()

        place_name = data_received.get('PlaceName', '')
        types = data_received.get('types', '')
        city_id = data_received.get('city_id', 0)

        # Read data from MySQL using SQLAlchemy engine
        query = f"SELECT * FROM historical_place where city_id ={city_id}"
        df = pd.read_sql(query, engine)

        # Matching the types with the dataset and reset the index
        category = df['types']
        City = df['city_id']

        def calculate_similarity(row):
            place_types = row['types']
            return 1 if types == place_types else 0

        df['similarity'] = df.apply(calculate_similarity, axis=1)

        # Filter places with the same types
        filtered_df = df[df['similarity'] == 1]

        # Calculate the cosine similarity between place_name and names in the dataset
        tf = TfidfVectorizer(analyzer='word', ngram_range=(2, 2), min_df=1, stop_words='english')
        tfidf_matrix = tf.fit_transform(filtered_df['name'])
        similarity_scores = cosine_similarity(tf.transform([place_name]), tfidf_matrix).flatten()

        # Get the indices of the most similar places
        similar_indices = similarity_scores.argsort()[::-1][:5]

        # Get the top 5 recommendations
        rec = filtered_df[['name', 'types', 'rating']].iloc[similar_indices]
        rec = rec.sort_values(by='rating', ascending=False)

        # Convert the DataFrame to a list of dictionaries
        result_data = rec.to_dict(orient='records')

        return jsonify(result_data)
    except Exception as e:
        print('Error processing data:', str(e))
        return jsonify({"error": "Internal server error"}), 500

@app.route('/places', methods=['GET'])
def get_places():
    try:
        # Buat kursor
        cursor = connection.cursor()

        # Kirim kueri
        cursor.execute("SELECT * FROM historical_place limit 10")

         # Dapatkan nama kolom dari cursor.description
        column_names = [desc[0] for desc in cursor.description]

        # Baca hasil kueri dan konversi ke dalam bentuk dictionary
        # token = request.headers.get('Authorization', '').split('Bearer ')[-1]
        # print(token)
        
        result_data = [dict(zip(column_names, row)) for row in cursor.fetchall()]

        # Tutup koneksi
        # connection.close()

        return jsonify(result_data)
    except Exception as e:
        print('Error processing data:', str(e))
        return jsonify({"error": "Internal server error"}), 500

if __name__ == '__main__':
    app.run(port=5000, debug=True)
