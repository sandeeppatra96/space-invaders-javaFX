#!/bin/bash

OPTS="-q -e" # quiet and produce execution error messages

set -ex
mvn $OPTS clean compile site

if [ $(hostname) == "csci-odin.cs.uga.edu" ]; then
    mvn $OPTS site:deploy
    URL=$(mvn $OPTS help:evaluate -Dexpression=cs1302.webwork.url -DforceStdout)
fi
