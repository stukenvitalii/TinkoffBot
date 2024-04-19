FROM openjdk:21
LABEL authors="stukenvitalii"

COPY ./target/scrapper.jar scrapper.jar

ENTRYPOINT ["java","-jar","/bot.jar"]
