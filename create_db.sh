export CLEARDB_DATABASE_URL=<insert the jdbc url to create the tables for here>
sbt "run-main tools.imports.DBMigration"
