package model

import java.net.URI
import scala.util.Properties
import com.mchange.v2.c3p0.ComboPooledDataSource
import scala.slick.session.Database
import scala.slick.driver.{H2Driver, MySQLDriver}


object DB {

  lazy val db = sys.props.get("Database").getOrElse("mysql") match {
    case "mysql" => DB.getSlickMysqlConnection()
    case "h2" => DB.getSlickHSQLDatabase()
  }
  lazy val dal = sys.props.get("Database").getOrElse("mysql") match {
    case "mysql" => new DAL(MySQLDriver)
    case "h2" => new DAL(H2Driver)
  }

  def dbConfigUrl: String = {
    val p = Properties.envOrElse("CLEARDB_DATABASE_URL", "mysql://root:mysql@127.0.0.1:3306/stock_manager")
    println(p)
    p
  }

  def WithSSLDebug() = System.setProperty("javax.net.debug", "all")

  def WithSSL() {
    System.setProperty("javax.net.ssl.keyStore", "keystore")
    System.setProperty("javax.net.ssl.keyStorePassword", "Korn4711")
    System.setProperty("javax.net.ssl.trustStore", "truststore")
    System.setProperty("javax.net.ssl.trustStorePassword", "Korn4711")
  }

  def getSlickMysqlConnection(jdbcUrl: String = dbConfigUrl) = {
    val dbConnectionInfo = parseDbUrl(jdbcUrl)
    val ds = new ComboPooledDataSource
    ds.setDriverClass("com.mysql.jdbc.Driver")
    ds.setJdbcUrl(dbConnectionInfo._1)
    ds.setUser(dbConnectionInfo._2)
    ds.setPassword(dbConnectionInfo._3)
    ds.setMaxPoolSize(15)
    ds.setPreferredTestQuery("Select 1")
    Database.forDataSource(ds)
  }

  def getSlickHSQLDatabase(jdbcUrl: String = "jdbc:hsqldb:mem:test1") = {
    val ds = new ComboPooledDataSource
    ds.setDriverClass("org.hsqldb.jdbc.JDBCDriver")
    ds.setJdbcUrl(jdbcUrl + ";sql.enforce_size=false")
    Database.forDataSource(ds)
  }

  def parseConfiguredDbUrl() = parseDbUrl()

  def parseDbUrl(mysqlUrl: String = dbConfigUrl) = {
    val dbUri = new URI(mysqlUrl);

    val username = dbUri.getUserInfo().split(":").head
    val password = dbUri.getUserInfo().split(":").last
    val port = if (dbUri.getPort() == -1) "" else s":${dbUri.getPort()}"

    val dbUrl = "jdbc:mysql://" + dbUri.getHost() + port + dbUri.getPath() + "?useSSL=true&useUnicode=yes&characterEncoding=UTF-8"
    (dbUrl, username, password)
  }
}
