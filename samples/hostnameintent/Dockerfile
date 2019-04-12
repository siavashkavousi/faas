FROM watchdog:0.0.1-SNAPSHOT

RUN curl -sL https://deb.nodesource.com/setup_8.x | bash -\
    && apt-get install -y nodejs

COPY package.json .
COPY handler.js .
COPY sample.json .
RUN npm i

ENV fprocess="node handler.js"