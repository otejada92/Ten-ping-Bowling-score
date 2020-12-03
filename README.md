# Ten ping bowling score

## About

Ten ping bowling is a spring boot app for calculating bowling score

## Built with

* [Java](https://www.java.com/en/download/) - Back end programming language
* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring](https://spring.io/) -  Application framework 

## Prerequisite

```
Maven 3.3+
```
```
Java 8 
```

## Installation reference

* [Java](https://www.oracle.com/java/technologies/javase-jre8-downloads.html) 
* [Maven](https://maven.apache.org/download.cgi)

## How to run

#### First alternative
1. Move to the project folder which contains the pom.xml file and use the below command.
    * ```
        maven package
        
        Note: This will generate a new folder named target.
        ``` 
2. Move to the /target folder then use below command.
    *  ````
         java -jar ten-ping-bowling-0.0.1-SNAPSHOT.jar --file={file_path}  
        ````
### Second alternative
1. Move to the project folder which contains the pom.xml file and use the below command.
2. Run below command.
    ```
    mvn spring-boot:run -Dspring-boot.run.arguments=--file=X:\\xxx\\xxx\\src\\main\\resources\\normal-game.txt
    ```
**NOTE:** File path is passing through --file arg so don't forget it! :shipit:

## Test

#### Test naming convention
* MethodName_StateUnderTest_ExpectedBehavior
#### Running the tests

#### Run all the unit test classes.
```mvn test```test

#### Run a single test class.
```mvn -Dtest=ScoreParseTest test```

#### Run multiple test classes.
```mvn -Dtest=ScoreParseTest,ScoreFrameCreatorTest test```

##### Run a single test method from a test class.
```mvn -Dtest=ScoreParseTest#retrievePlayers_NormalGameTwoPlayers_AssertEqualTrue test```

## Resources for testing

* [Normal game(2 players)](https://github.com/otejada92/Ten-ping-Bowling-score/blob/master/src/main/resources/normal-game.txt) 
* [Perfect score](https://github.com/otejada92/Ten-ping-Bowling-score/blob/master/src/main/resources/perfect-score.txt) 
* [Zero score](https://github.com/otejada92/Ten-ping-Bowling-score/blob/master/src/main/resources/zero-score.txt)


