HOW TO RUN PROJECT:

This project uses the IntelliJ IDE along with Springboot Framework and the Jackson parser. The project was made using Java and Gradle.

To run any of the functions, you must first run the following command: .\gradlew.bat bootRun

1. @GetMapping("/getAllTweets"): To run this, first run the given gradle command, then go to your web browser or postman
 and include this link:
 http://localhost:8080/getAllTweets

 This will return all available tweets in the archive

2. @GetMapping("/urlList"): To run this, first run the given gradle command, then go to your web browser or postman
 and include this link:
 http://localhost:8080/urlList

 This will return external links based on tweet ids

3. @RequestMapping(value = "/users/{id}"): To run this, first run the given gradle command, then go to your web browser or postman
 and include you may include one of four links as the id:
 http://localhost:8080/users/311975360667459585
 http://localhost:8080/users/311828115477372928
 http://localhost:8080/users/311964132205268992
 http://localhost:8080/users/311468922962587651
 http://localhost:8080/users/311432631726264320

 This will return the screen_name, created_at, text, and lang given the tweet id

4. @RequestMapping("/userProfile/{screenName}"): To run this, first run the given gradle command, then go to your web browser or postman
 and include you may include one of four links as the screen name:

 http://localhost:8080/userProfile/timoreilly
 http://localhost:8080/userProfile/MarkUry
 http://localhost:8080/userProfile/zephoria
 http://localhost:8080/userProfile/SarahPrevette
 http://localhost:8080/userProfile/johnmaeda

This will return the twitter users's name, location, and description given the user's screen_name