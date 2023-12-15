<h1 align="center"> KunjungIn - Cloud Computing</h1> <br>

<h1>REST API Documentation</h1>

### Base URL for NodeJS App Engine : `https://kunjungin-api-dot-kunjunginapp.et.r.appspot.com/`
### Base URL for Python App Engine : `https://kunjungin-python-dot-kunjunginapp.et.r.appspot.com/`

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
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImR1bW15QGdtYWlsLmNvbSIsImlkIjoxLCJpYXQiOjE3MDI1MjcxNjB9.2JlYeusUZC3GZOJAzKegPLmdyAR5dpJsmE1oesJdkKg"
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
  "latitude": "[user latitude position locatin]",
  "longitude": "[user longitude position locatin]",
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
            "name": "Museum Kepresidenan Balai Kirti",
            "photo_reference": "ATJ83ziSIi0n9NfWYfEF9-ER4Y5gDhzIX8RWm9EOLggqQJ49e7ldZKGENIWUVBrLnuA3KUZXu1YVwZzW3b8_gDxu-FlSn_USc77KGgCU60-K8w4cPxk0UrQTNPFQgcALrmj_XiJlcKsgSaTRsFJRRrudfFIFapuTB25C12-B0YZs3IK3lnlr",
            "rating": 4.7,
            "types": "museum|tourist_attraction|point_of_interest|establishment"
        },
        {
            "distance": 0.16097798593307444,
            "name": "Indonesian Museum of Ethnobotany",
            "photo_reference": "ATJ83zgKN8-RFxFcKsPMmQ5cGzEPQPTO8vjbgHu4rOIdRwJwmZuzp-BnMShdrhV3ZU4ZSOC_DRxzeZOvTk-iXnd7QOEPIiDUNeT3w81FSKMPbeM24oXKm-AseEIHkAS64Mr7tXsZDivopfRukFcZkfEFvYTj17AdU7rLoIuzlRYF2XePfDAr",
            "rating": 4.6,
            "types": "museum|tourist_attraction|point_of_interest|establishment"
        },
        {
            "distance": 0.713848081408731,
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

**URL** : `/api/v1/placeRecommendation/places/top`

**Method** : `POST`

**Auth required** : YES

**Data constraints**

```json
{
  "PlaceName":"[hint for place name]",
  "types": "[user latitude position locatin]",
  "city_id": "[city id]"
}
```

**Data example**

```json
{
  "PlaceName": "Museum",
  "types": "museum|tourist_attraction|point_of_interest|establishment",
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
            "name": "Perpustakaan BBalitvet",
            "photo_reference": "ATJ83zhwny_FutwnEqwLix0TivFzPPuy1xWbuyypELpoKDgPU2yDhoi05O2D3Sl-__qSEYQcMEbtY-ZgNk45sdO6-GIZ3tWHJf_b6t0qn5RGmbdsKEDTanD6aZHKOPJucLXs6KSi0KsQ-nv_QTurC0fPi2EPlK0Pb2-vlN7hUZVxdkoveWcE",
            "rating": 4.8,
            "types": "museum|tourist_attraction|point_of_interest|establishment"
        },
        {
            "name": "Museum Tanah dan Pertanian",
            "photo_reference": "ATJ83zgDYYH6iBw5Pu5mgBP64rseEefindzMPaZwxvY2xZsUzlrGCHTivCNd7uCRsti2v74RqubigRuSjkahFQbozJZ2A0D7jj4xRi06-fV88cYgEIhmwXgngDs34Ywwg2tcOekn6L1r1JF0SmQUjsEeKGTqFplMmuHxiRw50Iomiu40_OrO",
            "rating": 4.7,
            "types": "museum|tourist_attraction|point_of_interest|establishment"
        },
        {
            "name": "Museum Kepresidenan Balai Kirti",
            "photo_reference": "ATJ83ziSIi0n9NfWYfEF9-ER4Y5gDhzIX8RWm9EOLggqQJ49e7ldZKGENIWUVBrLnuA3KUZXu1YVwZzW3b8_gDxu-FlSn_USc77KGgCU60-K8w4cPxk0UrQTNPFQgcALrmj_XiJlcKsgSaTRsFJRRrudfFIFapuTB25C12-B0YZs3IK3lnlr",
            "rating": 4.7,
            "types": "museum|tourist_attraction|point_of_interest|establishment"
        }
    ]
}
```
_______