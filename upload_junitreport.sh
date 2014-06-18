#!/bin/bash
FILES=./target/test-reports/*
for f in $FILES
do
  echo "Processing $f file..."
  # take action on each file. $f store current file name
  curl -H "Content-Type:application/xml" -X POST --data-binary @$f http://unitcover.herokuapp.com/api/pussinboots/bankapp
done

curl -H "Content-Type:application/xml" -X POST -d @test-result.xml http://unitcover.herokuapp.com/api/pussinboots/bankapp

