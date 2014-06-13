#export sparkasse_password=<password>
#export sparkasse_username=<username>
export CLEARDB_DATABASE_URL=mysql://bfa19bff3390d4:aca2973b@us-cdbr-east-06.cleardb.net/heroku_fc1f148f8971137?reconnect=true
export SSLPW=Korn4711
sbt -DSSLPW=Korn4711 -DCLEARDB_DATABASE_URL=mysql://bfa19bff3390d4:aca2973b@us-cdbr-east-06.cleardb.net/heroku_fc1f148f8971137 "run-main app.tools.DBMigration"
