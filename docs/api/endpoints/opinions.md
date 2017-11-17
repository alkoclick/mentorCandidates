# /opinions/:id #

## Supports ##
* Get
* Post

### Get ###
**Parameters:**
* id
    * The opinion ID to fetch
    * Type: long
    * Optional

**Body:**
Empty

**Returns:**
A JSONArray of JSONObjects. Each opinion entry found in the db is parsed to a JSONObject and added to this array. If the optional path parameter id is specified, the response is limited to a single JSONObject, representing the opinion with the requested id, if one exists. 

Json keys: 

* id (long)
* name (String)
* description (String)

**Example:**
```
[{
  "id": 43,
  "name": "Student A",
  "description": "This is an opinion by a mentor"
}]
```

## Codes ##

* 200 (Get) : resource(s) was/were successfully retrieved from the database 
* 202 (Post) : resource(s) was/were successfully added to the database
* 400 (Post) : request was not properly formatted, see error message for further information
* 404 (Get) : no entry was found for the requested id
* 404 (Post) : no entry was found for provided mentor id
* 405 (Post) : method not allowed on this URI. Did you try to post to a specific /id?
* 413 (Post) : body was too large
* 500 (Post) : an internal error occurred in the insert procedure. More information will be returned in the body, if available