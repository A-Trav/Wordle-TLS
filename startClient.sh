#1/bin/bash

if [ $# -ne 2 ]; then
  echo "Unsupported number of command-line args, only run the script with a hostname and port number as the command-line arg"
  sleep 8
else
  HOSTNAME=$1
  PORT=$2
  echo "Starting client with hostname: $HOSTNAME and port number: $PORT"
  javac Client/ClientMain.java
  java Client/ClientMain $HOSTNAME $PORT
fi 

