package org.stock.manager

import scala.slick.driver.ExtendedProfile
import java.sql.{Timestamp, Date}
import java.util.{Date, Calendar}
import scala.slick.session.{Database, Session}
import scala.slick.jdbc.meta.MTable
import org.akkreditierung.DateUtil

trait Profile {
  val profile: ExtendedProfile
}

/**
 * The Data Access Layer contains all components and a profile
 */
class DAL(override val profile: ExtendedProfile) extends BalanceComponent with StockComponent with Profile {
  import profile.simple._
  def recreate(implicit session: Session) {
    drop(session)
    create(session)
  }
  def create(implicit session: Session) {
    if (MTable.getTables(Balances.tableName).list().isEmpty) {
      (Balances.ddl).create
    }
    if (MTable.getTables(Stocks.tableName).list().isEmpty) {
      (Stocks.ddl).create
    }
  }
  def drop(implicit session: Session) = try {(Balances.ddl).drop} catch {case ioe: Exception =>}
}

case class Balance(var id: Option[Int] = None, name: String, value: Double, date: Option[Timestamp])

trait BalanceComponent { this: Profile => //requires a Profile to be mixed in...
  import profile.simple._ //...to be able import profile.simple._
  import profile.simple.Database.threadLocalSession

  object Balances extends Table[Balance]("balances") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc) // This is the primary key column
    def name = column[String]("name")
    def value = column[Double]("value")
    def date = column[Option[Timestamp]]("date")
    def * = id.? ~ name ~ value ~ date <> (Balance, Balance.unapply _)
    def forInsert = name ~ value ~ date <> ({ t => Balance(None,t._1, t._2, t._3)}, {(u: Balance) => Some((u.name, u.value, u.date))}) returning id
    def insert(balance: Balance): Balance = balance.copy(id = Some(forInsert.insert(balance)))
    def insert(name: String, value: Double): Balance = insert(Balance(None, name, value, DateUtil.nowDateTimeOpt()))
    def findByName(name: String) =  (for {a <- Balances if a.name === name} yield (a))
    def findAll() =  (for {a <- Balances} yield (a))
  }
}

case class Stock(var id: Option[Int] = None, name: String, value: Double, date: Option[Timestamp])

trait StockComponent { this: Profile => //requires a Profile to be mixed in...
  import profile.simple._ //...to be able import profile.simple._
  import profile.simple.Database.threadLocalSession

  object Stocks extends Table[Stock]("stocks") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc) // This is the primary key column
    def name = column[String]("name")
    def value = column[Double]("value")
    def date = column[Option[Timestamp]]("date")
    def * = id.? ~ name ~ value ~ date <> (Stock, Stock.unapply _)
    def forInsert = name ~ value ~ date <> ({ t => Stock(None,t._1, t._2, t._3)}, {(u: Stock) => Some((u.name, u.value, u.date))}) returning id
    def insert(stock: Stock) = stock.copy(id = Some(forInsert.insert(stock)))
    def insert(name: String, value: Double): Stock = insert(Stock(None, name, value, DateUtil.nowDateTimeOpt()))
    def findByName(name: String) =  (for {a <- Stocks if a.name === name} yield (a))
    def findAll() =  (for {a <- Stocks} yield (a))
  }
}