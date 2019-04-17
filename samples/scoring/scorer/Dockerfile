FROM watchdog:0.0.1-SNAPSHOT

RUN apt-get update -yq && apt-get install -yqq \
    git \
    python \
    python-pip

RUN pip install pymongo

COPY handler.py .

ENV fprocess="python handler.py"