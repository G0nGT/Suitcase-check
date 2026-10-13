#!/bin/sh
set -eu
cd "$(dirname "$0")"
mkdir -p bin
javac -cp "lib/*" -d bin src/*.java test/*.java
java -jar lib/junit-platform-console-standalone-1.10.2.jar execute --class-path bin --scan-class-path --disable-ansi-colors
