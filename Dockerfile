FROM maven:3.3-jdk-8-alpine

COPY src home/Dice/src
COPY bin home/Dice/bin
COPY config home/Dice/config
COPY pom.xml home/Dice/

WORKDIR home/Dice

RUN mvn clean package

ENTRYPOINT ["sh", "bin/dice-game.sh"]

