#!/bin/bash

echo "Localisation : "
read line
LOC=$line

echo "Debit : "
read line
Q=$line

echo "Temps : "
read line
T=$line

echo "Vitesse : "
read line
V=$line

curl -X POST -H "Content-Type: application/json" -d "{ \"vitesse\" : $V, \"localisation\" : \"$LOC\", \"temps\" : $T, \"debit\": $Q }" http://localhost:8080/qtv | jq

