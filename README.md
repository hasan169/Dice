# Dice Game
It is a simple board game, where we play in turns using dice. This dice is
provided by us because it is a special dice, you could say it is our family’s
lucky charm!

The rules of the game are:
* There is a maximum of 4 players.
* Each player has a name and age.
* The first player to get a total sum of 25 is the winner. A player does
not have to get 25 exactly (>=25 is OK). The number 25 is configurable.
* To get started the player will need to get 6. If the player gets 1-5 they
will then have to wait for their turn before having another go.
* When finally hitting the number 6 the player will have to throw again
to determine the starting point. Getting a 6 on the first try will give you 0.
* Each time a player hits number 4, he will get -4 from the total score.
* If a player hits a 4 after hitting the first 6, they do not get a negative
score but will have to roll another 6 before they start accumulating points.
* Each time a player hits the number 6 he will then get one extra throw.

* The game can be played by running both in local machine and in docker container.
* The project is built using Spring Boot and Java. 
* Swagger and Swagger ui is used for api documentation.

### Instruction for running the game in local machine ###
### Requirements ###
* Java version minimum 1.8
* Maven

### Build instruction ##
* Clone the repository and checkout the main branch.
* Under the root directory Dice, run mvn clean package.
* After build is completed, run the start script at location: `bin/dice-game.sh`. E.x. nohup bin/dice-game.sh &.
* After running the game, logs can be found at location: logs/dice.log.
* After running the game, for api documentation swagger ui can be loaded in browser by opening http://localhost:port/swagger-ui.html. E.x http://localhost:8080/swagger-ui.html. Here port is server.port mentioned in config file.

For changing configs we have to change the file config/application.properties.
Below properties are available:
* dice.maximum.score -> Maximum score a player has to achieve to win the game. Default is 25.
* dice.roll.url -> Api endpoint for rolling dice. Default is http://developer-test.hishab.io/api/v1/roll-dice.
* server.port -> Port for api endpoints. Default is 8080.

### Instruction for running the game in Docker container ###
### Requirements ###
* Docker installed on the machine.

### Build instruction ##
* Clone the repository and checkout the main branch.
* Under the root directory Dice, run `docker build . -t ${imagename:tag}`. E.x `docker build . -t dice:1.0`.
* After image build is completed, run `docker run --publish [host_port]:[container_port] ${imagename:tag}`. e.x  `docker run --publish 8080:8080 dice:1.0`. Here container port is the server.port mentioned in the config file.
* After running the container, game will start.
* For api documentation swagger ui can be loaded in browser by opening http://localhost:port/swagger-ui.html. E.x http://localhost:8080/swagger-ui.html. Here port is [host_port] mentioned in docker run command.
