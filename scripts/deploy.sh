#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp -i ~/.ssh/id_rsa \
    target/sweater-0.0.1-SNAPSHOT.jar \
    jamol@10.211.55.4:/home/jamol

echo 'Restart server...'

ssh -tt -i ~/.ssh/id_rsa jamol@10.211.55.4 << EOF

pgrep java | xargs kill -9
nohup java -jar sweater-0.0.1-SNAPSHOT.jar > log.txt &

EOF

echo 'Bye'