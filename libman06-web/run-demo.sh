#!/usr/bin/env bash
set -e
cd "$(dirname "$0")"

PORT="${1:-8080}"

if [ -n "$JAVA_HOME" ]; then
    JAVA="$JAVA_HOME/bin/java"
elif [ -x "/c/Program Files/Android/Android Studio/jbr/bin/java.exe" ]; then
    export JAVA_HOME="/c/Program Files/Android/Android Studio/jbr"
    JAVA="$JAVA_HOME/bin/java"
elif command -v java >/dev/null 2>&1; then
    JAVA="java"
else
    echo "Java 17+ not found. Install a JDK 17+ and set JAVA_HOME."
    exit 1
fi

if [ ! -f src/main/resources/db.properties ]; then
    echo "Missing src/main/resources/db.properties"
    echo "Copy db.properties.example to db.properties and put your MySQL password in it."
    exit 1
fi

case "$(uname -s)" in
    MINGW* | MSYS* | CYGWIN*) SEP=";" ;;
    *) SEP=":" ;;
esac

sh ./mvnw -q -DskipTests package
exec "$JAVA" -cp "target/classes${SEP}target/lib/*" libman06.LibManApp06 "$PORT"
