FROM watchdog:0.0.1-SNAPSHOT

WORKDIR /root/

COPY Handler.java .
RUN javac Handler.java

ENV fprocess="java Handler"

HEALTHCHECK --interval=1s CMD [ -e /tmp/.lock ] || exit 1