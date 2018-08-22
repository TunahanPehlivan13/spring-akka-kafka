FROM java:8
VOLUME /tmp
ADD target/collector-0.0.1-SNAPSHOT.jar collector.jar
RUN bash -c 'touch /collector.jar'
EXPOSE 8080
CMD ["java", "-jar", "collector.jar"]