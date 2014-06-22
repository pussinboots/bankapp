#!/bin/bash
owner=pussinboots
project=banbapp
#endpoint=localhost:9000
endpoint=unitcover.herokuapp.com
FILES=./target/test-reports/*
trigger="trigger=Travis"
travisBuildId=""
 if [[ -n ${TRAVIS} ]]
    then
        echo "travis build detected"
        travisBuildId="&travisBuildId=$TRAVIS_BUILD_ID"
    else
        trigger="trigger=Local"
    fi
#upload play junit reports
buildnumber=$(curl -s -X POST http://$endpoint/api/$owner/$project/builds?$trigger$travisBuildId | sed -E 's/.*"buildNumber":([0-9]*).*/\1/')
echo $buildnumber
echo "http://$endpoint/api/$owner/$project/$buildnumber"
for f in $FILES
do
  echo "Processing $f file..."
  curl -H "Content-Type:application/xml" -X POST -d @$f http://$endpoint/api/$owner/$project/$buildnumber
done

curl -H "Content-Type:application/xml" -X POST -d @test-results.xml http://$endpoint/api/$owner/$project/$buildnumber

curl -X POST http://$endpoint/api/$owner/$project/builds/$buildnumber/end
