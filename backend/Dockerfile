FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN javac -cp ".:lib/ojdbc11.jar" src/*.java

CMD ["java","-cp","src:lib/ojdbc11.jar","Server"]