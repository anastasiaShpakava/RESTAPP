FROM openjdk:8
COPY target/blog-0.0.1-SNAPSHOT.war blog-0.0.1-SNAPSHOT.war
#WORKDIR /tmp
EXPOSE 8085
ENTRYPOINT ["java","-war","blog-0.0.1-SNAPSHOT.war"]
