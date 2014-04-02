
To launch the server:

sbt
> ~container:start


To test:
curl 'http://localhost:8080/allmessages'
curl 'http://localhost:8080/messages?lat=-123.15&long=49.554'
curl -X POST -H "Content-Type: application/json" -d '{"userid": "user1", "body": "hello world", "loc": {"lat": "-123.456","long": "49.551" }, "time": "1 min ago"}' 'http://localhost:8080/message'
