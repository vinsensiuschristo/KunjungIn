<h1 align="center"> KunjungIn - Cloud Computing</h1> <br>

<h1>REST API Documentation</h1>

### Base URL for NodeJS App Engine : `https://kunjungin-api-dot-kunjunginapp.et.r.appspot.com/`
### Base URL for Python App Engine : `https://kunjungin-python-dot-kunjunginapp.et.r.appspot.com/`

_______

### Installation

For NodeJS App : [Link](https://github.com/vinsensiuschristo/KunjungIn/tree/cc/kunjungin-api)

For Python with Flask App : [Link](https://github.com/vinsensiuschristo/KunjungIn/tree/cc/kunjungin-recommendation-flask)

_______

# Login

Used to collect a Token for a registered User.

**URL** : `/api/v1/users/login`

**Method** : `POST`

**Auth required** : NO

**Data constraints**

```json
{
    "username": "[valid email address]",
    "password": "[password in plain text]"
}
```

**Data example**

```json
{
    "username": "iloveauth@example.com",
    "password": "abcd1234"
}
```

## Success Response

**Code** : `200 OK`

**Content example**

```json
{
  "error": false,
  "message": "Success",
  "loginResult": {
    "userId": 1,
    "name": "dummy",
    "token": "token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Im5ld3Rhc2tAZ21haWwuY29tIiwiaWQiOjMwLCJpYXQiOjE3MDI4Nzc0NTV9.PoL4VJxpftxcmi7BSAJnuVmlggY93EUebzNtz0nA4VQ; Path=/; HttpOnly; Expires:Tue, 19 Dec 2023 05:30:55 GMT;"
  }
}
```

## Error Response

**Condition** : If 'username' and 'password' combination is wrong.

**Code** : `401 Unauthorized`

**Content** :

```json
{
  "error": true,
  "message": "Invalid password"
}
```
_______

# Register

Used to Create / Register User.

**URL** : `/api/v1/users/register`

**Method** : `POST`

**Auth required** : NO

**Data constraints**

```json
{
  "name":"[valid name]",
  "email": "[valid email address]",
  "password": "[password in plain text]",
  "address": "[valid address]",
  "city_id": "[valid city]"
}
```

**Data example**

```json
{
  "name":"vinsensius Gonzaga",
  "email": "testing4cv4cccc4@gmail.com",
  "password": "password",
  "address": "address vinsensius"
}
```

## Success Response

**Code** : `200 OK`

**Content example**

```json
{
  "error": false,
  "message": "User Created"
}
```
_______

# Login with Google

Used to Create / Register User.

**URL** : `/auth/google`

**Method** : `GET`

**Auth required** : NO

_______

# Get All Users

Used to Get all the Users.

**URL** : `/api/v1/users/`

**Method** : `GET`

**Auth required** : YES

## Success Response

**Code** : `200 OK`

**Content example**

```json
{
  "message": "success",
  "data": [
    {
      "id": 1,
      "email": "dummy@gmail.com",
      "date_of_birth": null,
      "address": "dummy 213",
      "rating": 0
    },
    {
      "id": 4,
      "email": "dummy1@gmail.com",
      "date_of_birth": null,
      "address": "dummy 213",
      "rating": 0
    }
  ]
}
```
_______

# Update User

Used to Update User.

**URL** : `/api/v1/users/:id`

**Method** : `PATCH`

**Auth required** : YES

**Data constraints**

```json
{
  "name":"[valid name]",
  "email": "[valid email address]",
  "password": "[password in plain text]",
  "address": "[valid address]"
}
```

**Data example**

```json
{
  "name":"vinsensius Gonzaga",
  "email": "testing4cv4cccc4@gmail.com",
  "password": "password",
  "address": "address vinsensius"
}
```

## Success Response

**Code** : `200 OK`

**Content example**

```json
{
  "message": "Update User 5 successfully",
  "data": {
    "name": "ivan updated 2",
    "email": "ivanupdated2@gmail.com",
    "address": "address updated2"
  }
}
```
_______

# Delete User

Used to Update User.

**URL** : `/api/v1/users/:id`

**Method** : `DELETE`

**Auth required** : YES

## Success Response

**Code** : `200 OK`

**Content example**

```json
{
   "status": "success",
   "message": "Delete User ${id} successfully"
}
```

_______

# Place Recommendation By Distance

Used to get a list of historical places nearby the user location.

**URL** : `/api/v1/placeRecommendation/places/nearby`

**Method** : `POST`

**Auth required** : YES

**Data constraints**

```json
{
  "PlaceName":"[hint for place name]",
  "latitude": "[user latitude position location]",
  "longitude": "[user longitude position location]",
  "city_id": "[city id]"
}
```

**Data example**

```json
{
  "PlaceName": "Museum",
  "latitude" : -6.5980046,
  "longitude" : 106.7948917,
  "city_id" : 8
}
```

## Success Response

**Code** : `200 OK`

**Content example**

```json
{
  "message": "Data processed successfully",
    "result": [
        {
            "distance": 0.07054605348299524,
            "id": 834,
            "name": "Museum Kepresidenan Balai Kirti",
            "photo_reference": "ATJ83ziSIi0n9NfWYfEF9-ER4Y5gDhzIX8RWm9EOLggqQJ49e7ldZKGENIWUVBrLnuA3KUZXu1YVwZzW3b8_gDxu-FlSn_USc77KGgCU60-K8w4cPxk0UrQTNPFQgcALrmj_XiJlcKsgSaTRsFJRRrudfFIFapuTB25C12-B0YZs3IK3lnlr",
            "rating": 4.7,
            "types": "museum|tourist_attraction|point_of_interest|establishment"
        },
        {
            "distance": 0.16097798593307444,
            "id": 29,
            "name": "Indonesian Museum of Ethnobotany",
            "photo_reference": "ATJ83zgKN8-RFxFcKsPMmQ5cGzEPQPTO8vjbgHu4rOIdRwJwmZuzp-BnMShdrhV3ZU4ZSOC_DRxzeZOvTk-iXnd7QOEPIiDUNeT3w81FSKMPbeM24oXKm-AseEIHkAS64Mr7tXsZDivopfRukFcZkfEFvYTj17AdU7rLoIuzlRYF2XePfDAr",
            "rating": 4.6,
            "types": "museum|tourist_attraction|point_of_interest|establishment"
        },
        {
            "distance": 0.713848081408731,
            "id": 235,
            "name": "Museum Tanah dan Pertanian",
            "photo_reference": "ATJ83zgDYYH6iBw5Pu5mgBP64rseEefindzMPaZwxvY2xZsUzlrGCHTivCNd7uCRsti2v74RqubigRuSjkahFQbozJZ2A0D7jj4xRi06-fV88cYgEIhmwXgngDs34Ywwg2tcOekn6L1r1JF0SmQUjsEeKGTqFplMmuHxiRw50Iomiu40_OrO",
            "rating": 4.7,
            "types": "museum|tourist_attraction|point_of_interest|establishment"
        }
    ]
}
```
_______

# Place Recommendation By Rating

Used to get a list of historical places around the user location based on highest rating.

**URL** : `/api/v1/placeRecommendation/places/rating`

**Method** : `POST`

**Auth required** : YES

**Data constraints**

```json
{
  "PlaceName":"[full name of place]"
}
```

**Data example**

```json
{
  "PlaceName": "Pura Candi Untoroyono"
}
```

## Success Response

**Code** : `200 OK`

**Content example**

```json
{
    "error": false,
    "message": "Data processed successfully",
    "result": [
        {
            "id": 3,
            "name": "Pura Sonosewu",
            "rating": 4.5,
            "types": "hindu_temple|tourist_attraction|place_of_worship|point_of_interest|establishment"
        },
        {
            "id": 175,
            "name": "Candi Sari",
            "rating": 4.5,
            "types": "tourist_attraction|place_of_worship|point_of_interest|establishment"
        },
        {
            "id": 180,
            "name": "Candi Kalasan",
            "rating": 4.5,
            "types": "tourist_attraction|place_of_worship|point_of_interest|establishment"
        }
    ]
}
```
_______

# Place Details

Used to get a details of historical places.

**URL** : `/api/v1/placeRecommendation/places/:id`

**Method** : `GET`

**Auth required** : YES

## Success Response

**Code** : `200 OK`

**Content example**

```json
{
    "error": false,
    "message": "Data processed successfully",
    "result": {
        "id": 5,
        "name": "Gereja Katedral Santa Perawan Maria Ratu Rosario Suci",
        "description": "Early 20th-century Roman Catholic cathedral with grand dome & arch features, plus a small courtyard.",
        "address": "Jl. Pandanaran No.9, Randusari, Kec. Semarang Sel., Kota Semarang, Jawa Tengah 50244, Indonesia",
        "latitude": "-6.985479700000001",
        "longitude": "110.4098457",
        "city_id": 4,
        "opening_hours": "Monday: 8:00 AM – 6:00 PM|Tuesday: 8:00 AM – 6:00 PM|Wednesday: 8:00 AM – 6:00 PM|Thursday: 8:00 AM – 6:00 PM|Friday: 8:00 AM – 6:00 PM|Saturday: 8:00 AM – 6:00 PM|Sunday: 8:00 AM – 6:00 PM",
        "rating": "4.8",
        "user_rating": 3592,
        "types": "tourist_attraction|church|place_of_worship|point_of_interest|establishment",
        "phone_number": "(024) 8310036",
        "createdAt": "2023-10-13T21:38:52.000Z",
        "updatedAt": "2023-10-13T21:38:52.000Z",
        "photo_reference": "ATJ83zg_bbWpxhzvcdU6E0zEWmXoIK-GRNjhysYp_w4HncVJqGx6KdkB5JA9VAZcPqL2NcmKPMxWkrjRp96ajDg53vWrWFnnp23TvQ2M6N4F5HGC0KetSS_ojw4Aj7ANSfmxC-dVsOuuC0WsGJR1p_LmmqmLT3NQXe6_Gb_YmZMFITciHHG6"
    }
}
```
_______

# Add User Recommendation

Used to add a destination type that will determine the recommended destination type according to the user's wishes.

**URL** : `/api/v1/userRecommendation/:id/recommendation`

**Method** : `POST`

**Auth required** : NO

**Data constraints**

```json
{
  "place_type_id": ["valid place_type_id","valid place_type_id","valid place_type_id"]
}
```

**Data example**

```json
{
  "place_type_id": ["2","3","4"]
}
```

## Success Response

**Code** : `200 OK`

**Content example**

```json
{
  "error": false,
  "message": "Recommendation for user {idUser} has been created"
}
```
_______