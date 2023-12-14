<h1 align="center"> KunjungIn - Cloud Computing</h1> <br>

<h1>REST API Documentation</h1>

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
   status: 'success',
   message: `Delete User ${id} successfully`,
}


