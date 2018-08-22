FROM java:8
VOLUME /tmp
ADD target/recorder-0.0.1-SNAPSHOT.jar recorder.jar
RUN bash -c 'touch /recorder.jar'
EXPOSE 8081
CMD ["java", "-jar", "recorder.jar"]