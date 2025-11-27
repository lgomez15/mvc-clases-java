#!/usr/bin/env bash
set -e

echo "Compilando fuentes..."
mkdir -p out
find src -name "*.java" > sources.txt
javac -d out @sources.txt

echo "Ejecutando aplicación..."
java -cp out ejemplomvp.Main