#!/bin/bash
owner=pussinboots
project=bankapp
FILES=./target/test-reports/*
#upload play junit reports
for f in $FILES
do
  echo "Processing $f file..."
  curl -H "Content-Type:application/xml" -X POST -d @$f http://unitcover.herokuapp.com/api/$owner/$project
done

curl -H "Content-Type:application/xml" -X POST -d @test-results.xml http://unitcover.herokuapp.com/api/$owner/$project


