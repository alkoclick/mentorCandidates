## /mentor curl examples ## 

# Get all mentors #
curl -H "Content-Type: application/json" http://localhost:8080/mentor

# Add a mentor #
curl -H "Content-Type: application/json" -X POST -d '{"firstName":"Alex","lastName":"Pap","email":"testMe@test.com","description":"Alex is writing java code"}' http://localhost:8080/mentor

# Add a mentor with opinions #
curl -H "Content-Type: application/json" -X POST -d '{"firstName":"Alex","lastName":"Pap","email":"testMe@test.com","opinions":[{"name":"Student A","description":"This is an opinion by a mentor"}],"description":"Alex is writing java code"}' http://localhost:8080/mentor