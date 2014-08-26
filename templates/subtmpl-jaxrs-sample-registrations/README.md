curl -i -H "Accept: application/json" http://localhost:8080/jee7/resources/registrations/1
=> 204, no content

curl -i -H "Content-Type: application/json" -H "Accept: application/json" -X POST -d "{\"numberOfDays\": 2, \"numberOfAttendees\": 3, \"vatIdAvailable\": \"true\"}" http://localhost:8080/jee7/resources/registrations

=> 201, Created
=> {"price":1800,"confirmation-id":1}

curl -i -H "Accept: application/json" http://localhost:8080/jee7/resources/registrations/1
=> 200, OK
=> {"numberOfDays":2,"numberOfAttendees":3,"vatIdAvailable":true,"totalPrice":0}
