FROM openjdk:8-jre-alpine

VOLUME /tmp

ARG DEPENDENCY=target/dependency

COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 8070

ENTRYPOINT ["java","-cp","app:app/lib/*","com.siavash.faas.provider.ProviderApplication"]