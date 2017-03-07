#!/bin/bash
javac -cp json.jar:mysql.jar *.java && java -cp .:json.jar:mysql.jar Server
