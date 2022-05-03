#1/bin/bash

if [ $# -ne 1 ]; then
  echo "Unsupported number of command-line args, only run the script with a port number as the command-line arg"
  sleep 8
else
  PORT=$1
  echo "Starting server with port number: $PORT"
  javac Server/ServerMain.java
  java Server/ServerMain $PORT
fi 

