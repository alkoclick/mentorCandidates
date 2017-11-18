# /mentor/{:id} #

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
A JSONArray of Mentor entities. Each Mentor POJO found in the db is parsed to an Mentor entity and added to this array. If the optional path parameter id is specified, the response is limited to a single Mentor, representing the record with the requested id, if one exists. 

**Example output**
```json
{
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
}
```

### POST ###
**Parameters:**
None

**Body:**
A Mentor  entity

**Returns:**
If the insertion was successful, then the given Opinion entity, with the id it has taken in the database. If not, then an empty body with one of the status codes below

**Example output**
```json
{
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
}
```

## Codes ##

* 200 (Get) : resource(s) was/were successfully retrieved from the database 
* 202 (Post) : resource(s) was/were successfully inserted into the database
* 400 (Post) : request was not properly formatted, see error message for further information
* 404 (Get) : no entry was found for the requested id
* 404 (Post) : no entry was found for provided mentor id
* 405 (Post) : method not allowed on this URI. Did you try to post to a specific /id?
* 500 (Post) : an internal error occurred in the insert procedure. More information will be returned in the body, if available