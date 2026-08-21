FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN javac -cp ".:lib/ojdbc11.jar:lib/postgresql-42.7.4.jar" src/*.java

CMD ["java","-cp","src:lib/ojdbc11.jar:lib/postgresql-42.7.4.jar","Server"]