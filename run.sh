#!/bin/sh
set -eu
cd "$(dirname "$0")"
mkdir -p out
javac -d out src/org/uob/a1/*.java
java -cp out org.uob.a1.Game
