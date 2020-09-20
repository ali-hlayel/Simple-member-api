FROM java:8
VOLUME /tmp
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
COPY ${JAR_FILE} application.jar
ENV JAVA_OPTS="-Duser.timezone=GMT"
ENTRYPOINT ["java","-cp","app:app/lib/*","com.assecor.personService.PersonServiceApplication"]
EXPOSE 6565
