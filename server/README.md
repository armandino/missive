
To launch the server:

sbt
> test
> ~container:start


To test:
curl 'http://localhost:8080/messages'
curl 'http://localhost:8080/messages?id=123456'
curl -X POST -H "Content-Type: application/json" -d '{"id": "123", "userid": "user1", "body": "hello world", "loc": {"lat": "-123.456","long": "49.551" }, "time": "1 min ago"}' 'http://localhost:8080/messages'
