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
class DAL(override val profile: ExtendedProfile) extends UserAccountComponent with BalanceComponent with StockComponent with SymbolComponent with Profile {

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
    if (MTable.getTables(UserAccounts.tableName).list().isEmpty) {
      (UserAccounts.ddl).create
    }
  }

  def drop(implicit session: Session) = try {
    (Balances.ddl ++ Stocks.ddl ++ Symbols.ddl ++ UserAccounts.ddl).drop
  } catch {
    case ioe: Exception =>
  }
}

case class UserAccount(var id: Option[Int] = None, googleId: String)

trait UserAccountComponent {
  this: Profile =>
  //requires a Profile to be mixed in...

  import profile.simple._

  //...to be able import profile.simple._

  import profile.simple.Database.threadLocalSession

  object UserAccounts extends Table[UserAccount]("accounts") {
    def id = column[Int]("userId", O.PrimaryKey, O.AutoInc)

    // This is the primary key column
    def googleId = column[String]("googleId")

    def * = id.? ~ googleId <>(UserAccount, UserAccount.unapply _)

    def forInsert = googleId returning id

    def insert(user: UserAccount): UserAccount = user.copy(id = Some(forInsert.insert(user.googleId)))

    def insert(googleId: String): UserAccount = insert(UserAccount(None, googleId))

    def findByGoogle(googleId: String) = (for {a <- UserAccounts if a.googleId === googleId} yield (a))
  }

}

case class BalanceJsonSave(name_enc: String, value_enc: String)

case class BalanceJson(id: Int, name_enc: String, value_enc: String, date: Timestamp)

case class Balance(var id: Option[Int] = None, name: String, value: String, date: Option[Timestamp], googleId: Option[String])

trait BalanceComponent {
  this: Profile =>
  //requires a Profile to be mixed in...

  import profile.simple._

  //...to be able import profile.simple._

  import profile.simple.Database.threadLocalSession

  object Balances extends Table[Balance]("balances") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    // This is the primary key column
    def name = column[String]("name")

    def value = column[String]("value")

    def date = column[Option[Timestamp]]("date")

    def googleId = column[Option[String]]("googleId")

    def * = id.? ~ name ~ value ~ date ~ googleId <>(Balance, Balance.unapply _)

    def forInsert = name ~ value ~ date ~ googleId <>( { t => Balance(None, t._1, t._2, t._3, t._4)}, { (u: Balance) => Some((u.name, u.value, u.date, u.googleId))}) returning id

    def insert(balance: Balance): Balance = balance.copy(id = Some(forInsert.insert(balance)))

    def insert(name: String, value: String)(implicit googleId: Option[String]): Balance = insert(Balance(None, name, value, DateUtil.nowDateTimeOpt(), googleId))

    def findByName(name: String)(implicit googleId: Option[String]) = (for {a <- Balances if a.name === name && a.googleId === googleId} yield (a))

    def findAll()(implicit googleId: Option[String]) = (for {a <- Balances if a.googleId === googleId} yield (a))
  }

}

case class StockJson(id: Int, name_enc: String, value_enc: String, symbol: String, date: Timestamp)

case class Stock(var id: Option[Int] = None, name: String, value: String, date: Option[Timestamp], googleId: Option[String])

trait StockComponent {
  this: Profile with SymbolComponent =>
  //requires a Profile to be mixed in...

  import profile.simple._

  //...to be able import profile.simple._

  import profile.simple.Database.threadLocalSession

  object Stocks extends Table[Stock]("stocks") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    // This is the primary key column
    def name = column[String]("name")

    def value = column[String]("value")

    def date = column[Option[Timestamp]]("date")

    def googleId = column[Option[String]]("googleId")

    def * = id.? ~ name ~ value ~ date ~ googleId <>(Stock, Stock.unapply _)

    def forInsert = name ~ value ~ date ~ googleId <>( { t => Stock(None, t._1, t._2, t._3, t._4)}, { (u: Stock) => Some((u.name, u.value, u.date, u.googleId))}) returning id

    def insert(stock: Stock) = stock.copy(id = Some(forInsert.insert(stock)))

    def insert(name: String, value: String)(implicit googleId: Option[String]): Stock = insert(Stock(None, name, value, DateUtil.nowDateTimeOpt(), None))

    def findByName(name: String)(implicit googleId: Option[String]) = {
      (for {(a, s) <- Stocks leftJoin Symbols on (_.name === _.name) if a.name === name && a.googleId === googleId}
      yield (a, s.symbol.?))
    }

    def findAll()(implicit googleId: Option[String]) = {
      (for {(a, s) <- Stocks leftJoin Symbols on (_.name === _.name) if a.googleId === googleId
      }
      yield (a, s.symbol.?))
    }
  }

}

case class Symbol(var id: Option[Int] = None, name: String, symbol: String)

trait SymbolComponent {
  this: Profile =>
  //requires a Profile to be mixed in...

  import profile.simple._

  //...to be able import profile.simple._

  import profile.simple.Database.threadLocalSession

  object Symbols extends Table[Symbol]("symbols") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    // This is the primary key column
    def name = column[String]("name")

    def symbol = column[String]("symbol")

    def * = id.? ~ name ~ symbol <>(Symbol, Symbol.unapply _)

    def forInsert = name ~ symbol <>( { t => Symbol(None, t._1, t._2)}, { (u: Symbol) => Some((u.name, u.symbol))}) returning id

    def insert(symbol: Symbol) = symbol.copy(id = Some(forInsert.insert(symbol)))

    def insert(name: String, symbol: String): Symbol = insert(Symbol(None, name, symbol))

    def findByName(name: String) = (for {a <- Symbols if a.name === name} yield (a))

    def insertIfNotExists(name: String, symbol: String) = findByName(name).firstOption.getOrElse(insert(name, symbol))

    def findAll() = (for {a <- Symbols} yield (a))
  }

}
