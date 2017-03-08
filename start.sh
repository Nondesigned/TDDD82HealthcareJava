#!/bin/bash
echo "Compiling..."
javac -cp json.jar:mysql.jar *.java
echo "Server started"
java -cp .:json.jar:mysql.jar Server
