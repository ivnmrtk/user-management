#!/usr/bin/env bash
java -jar target/users-management-0.0.1-SNAPSHOT.jar &
echo $! > target/users-management.pid
