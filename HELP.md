# Getting Started
A simple Spring Boot project with a RESTful endpoint to upload files.
Alternative Frameworks could be a Reactive RESTful. To prevent memory leak, files will be streamed during uploading and processing.



### Reference Documentation
* [Appache Common FileUpload ](https://commons.apache.org/proper/commons-fileupload/)
* [Google Code Gson](https://mvnrepository.com/artifact/com.google.code.gson/gson)


### How to run :
mvn clean spring-boot:run
#### Send a request and set your file with '-F' :
example : curl -X POST 'http://localhost:8080/upload' -F 'record=@"/C://dev-assignment-surepay/records.csv"'



### To run tests :
mvn clean test

