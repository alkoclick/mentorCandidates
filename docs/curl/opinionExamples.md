## /opinion curl examples ## 

# Get all opinions #
curl -H "Content-Type: application/json" http://localhost:8080/opinion

# Add a headless opinion #
curl -H "Content-Type: application/json" -X POST -d '{"name":"Student A","description":"This is a headless opinion"}' http://localhost:8080/opinion