@echo off
cls

echo Starting rmiregistry...
start rmiregistry
pause

echo Compiling sources...
javac *.java
pause

echo "Starting server..."
java -Djava.security.policy=server.policy EchoServer

