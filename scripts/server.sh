#!/bin/bash

cd $(dirname $0)

cd ..

java -cp scripts/hsqldb.jar org.hsqldb.server.Server --database.0 mem:mydb --dbname.0 workdb

#java -cp lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:/tmp/mydb --dbname.0 workdb
