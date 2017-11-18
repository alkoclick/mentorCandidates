# /Mentor #

Json keys: 

* id (long)
* firstName (String)
* lastName (String)
* email (String, email-validated according to [org.hibernate.validator.constraints.Email](https://docs.jboss.org/hibernate/validator/5.1/api/org/hibernate/validator/constraints/Email.html))
* opinions (JSONArray of Opinion entities)
* description (String, Optional)

**Example:**
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