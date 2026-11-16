#!/usr/bin/env bash
set -e
cd "$(dirname "$0")"

PROPS=src/main/resources/db.properties
prop() {
    if [ -f "$PROPS" ]; then
        sed -n "s/^$1=//p" "$PROPS" | tr -d '\r' | head -n 1
    fi
}

DB_USER="${1:-$(prop db.user)}"
DB_USER="${DB_USER:-root}"
DB_PASS="$(prop db.password)"
if [ -n "$1" ] || [ -z "$DB_PASS" ]; then
    read -rsp "MySQL password for $DB_USER: " DB_PASS
    echo
fi

if command -v mysql >/dev/null 2>&1; then
    MYSQL="mysql"
elif [ -x "/c/Program Files/MySQL/MySQL Server 8.0/bin/mysql.exe" ]; then
    MYSQL="/c/Program Files/MySQL/MySQL Server 8.0/bin/mysql.exe"
else
    echo "MySQL client not found. Install MySQL Server 8.0 or add its bin folder to PATH."
    exit 1
fi

MYSQL_PWD="$DB_PASS" "$MYSQL" -u "$DB_USER" --default-character-set=utf8mb4 < database/libman06.sql
echo "Database libman06 is ready."
