FROM java:8
ADD build/lib/*.jar user-service.jar
EXPOSE 8088
ENTRYPOINT ["java","-jar","user-service.jar"]