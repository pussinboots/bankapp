package model

import scala.slick.driver.ExtendedProfile
import java.sql.{Timestamp, Date}
import java.util.{Date, Calendar}
import scala.slick.session.{Database, Session}
import scala.slick.jdbc.meta.MTable

trait Profile {
  val profile: ExtendedProfile
}

/**
 * The Data Access Layer contains all components and a profile
 */
class DAL(override val profile: ExtendedProfile) extends BalanceComponent with StockComponent with SymbolComponent with Profile {
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
    if (MTable.getTables(Symbols.tableName).list().isEmpty) {
      (Symbols.ddl).create
    }
  }
  def drop(implicit session: Session) = try {(Balances.ddl).drop} catch {case ioe: Exception =>}
}

case class Balance(var id: Option[Int] = None, name: String, value: String, date: Option[Timestamp])

trait BalanceComponent { this: Profile => //requires a Profile to be mixed in...
  import profile.simple._ //...to be able import profile.simple._
  import profile.simple.Database.threadLocalSession

  object Balances extends Table[Balance]("balances") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc) // This is the primary key column
    def name = column[String]("name")
    def value = column[String]("value")
    def date = column[Option[Timestamp]]("date")
    def * = id.? ~ name ~ value ~ date <> (Balance, Balance.unapply _)
    def forInsert = name ~ value ~ date <> ({ t => Balance(None,t._1, t._2, t._3)}, {(u: Balance) => Some((u.name, u.value, u.date))}) returning id
    def insert(balance: Balance): Balance = balance.copy(id = Some(forInsert.insert(balance)))
    def insert(name: String, value: String): Balance = insert(Balance(None, name, value, DateUtil.nowDateTimeOpt()))
    def findByName(name: String) =  (for {a <- Balances if a.name === name} yield (a))
    def findAll() =  (for {a <- Balances} yield (a))
  }
}

case class StockJson(id: Int, name: String, value: String, symbol: String, date: Timestamp)
case class Stock(var id: Option[Int] = None, name: String, value: String, date: Option[Timestamp])

trait StockComponent { this: Profile with SymbolComponent => //requires a Profile to be mixed in...
  import profile.simple._ //...to be able import profile.simple._
  import profile.simple.Database.threadLocalSession

  object Stocks extends Table[Stock]("stocks") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc) // This is the primary key column
    def name = column[String]("name")
    def value = column[String]("value")
    def date = column[Option[Timestamp]]("date")
    def * = id.? ~ name ~ value ~ date <> (Stock, Stock.unapply _)
    def forInsert = name ~ value ~ date <> ({ t => Stock(None,t._1, t._2, t._3)}, {(u: Stock) => Some((u.name, u.value, u.date))}) returning id
    def insert(stock: Stock) = stock.copy(id = Some(forInsert.insert(stock)))
    def insert(name: String, value: String): Stock = insert(Stock(None, name, value, DateUtil.nowDateTimeOpt()))
    def findByName(name: String) = {
	(for {(a, s) <- Stocks leftJoin Symbols on (_.name === _.name) if a.name === name} 
	yield (a, s.symbol.?))
    }
    def findAll() = {
    	(for {	a <- Stocks
		s <- Symbols if s.name === a.name} 
	yield (a, s))
    }
    def findAllWithJoin() = {
    	(for { (a, s) <- Stocks leftJoin Symbols on (_.name === _.name)	
	} 
	yield (a, s.symbol.?))
    }
  }
}

case class Symbol(var id: Option[Int] = None, name: String, symbol: String)

trait SymbolComponent { this: Profile => //requires a Profile to be mixed in...
  import profile.simple._ //...to be able import profile.simple._
  import profile.simple.Database.threadLocalSession

  object Symbols extends Table[Symbol]("symbols") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc) // This is the primary key column
    def name = column[String]("name")
    def symbol = column[String]("symbol")
    def * = id.? ~ name ~ symbol <> (Symbol, Symbol.unapply _)
    def forInsert = name ~ symbol <> ({ t => Symbol(None, t._1, t._2)}, {(u: Symbol) => Some((u.name, u.symbol))}) returning id
    def insert(symbol: Symbol) = symbol.copy(id = Some(forInsert.insert(symbol)))
    def insert(name: String, symbol: String): Symbol = insert(Symbol(None, name, symbol))
    def findByName(name: String) =  (for {a <- Symbols if a.name === name} yield (a))
    def insertIfNotExists(name: String, symbol: String) = findByName(name).firstOption.getOrElse(insert(name, symbol))
    def findAll() =  (for {a <- Symbols} yield (a))
  }
}
