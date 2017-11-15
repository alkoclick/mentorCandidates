# /mentors/:id #

## Supports ##
* Get
* Post

### Get ###
**Parameters:**
* id
    * The mentor ID to fetch
    * Type: long
    * Optional

**Body:**
Empty

**Returns:**
A JSONArray of JSONObjects. Each mentor entry found in the db is parsed to a JSONObject and added to this array. If the optional path parameter id is specified, the response is limited to a single JSONObject, representing the mentor with the requested id, if one exists. 

Json keys: 

* id (long)
* firstName (String)
* lastName (String)
* email (String, email-validated)
* opinions (JSONArray)
* description (String)

**Example:**
```
[{
  "id": 42,
  "firstName": "Alex",
  "lastName": "Pap",
  "email": "testMe@test.com",
  "opinions": [
    {
      "id": 43,
      "name": "Student A",
      "description": "This is an opinion by a mentor"
    }
  ],
  "description": "Alex is writing java code"
}]
```

## Codes ##

* 200 (Get) : resource(s) was/were successfully retrieved from the database 
* 202 (Post) : resource(s) was/were successfully added to the database
* 400 (Post) : request was not properly formatted, see error message for further information
* 404 (Get) : no entry was found for the requested id
* 413 (Post) : body was too large
* 500 (Post) : an internal error occurred in the insert procedure. Information will be returned in the body, if possible