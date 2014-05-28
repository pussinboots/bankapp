package model

object SlickHelpers {

  import DB.dal._
  import DB.dal.profile.simple._

  implicit class QueryExtensionSort[E, T <: Table[E]](val query: Query[T, E]) {
    def sorts(sort: String, direction: String) = {
      var _query = query
      sort.reverse.split(" ").foreach { s =>
        println("sort " + s.reverse + " " + direction)
        _query = sortKey(s.reverse, direction)
      }
      _query
    }

    private def sortKey(sort: String, direction: String): Query[T, E] = {
      query.sortBy(table =>
        direction match {
          case "asc" =>
            println("sort2 " + sort + " asc")
            table.column[String](sort).asc
          case "desc" =>
            println("sort2 " + sort + " desc")
            table.column[String](sort).desc
        })
    }
  }

  implicit class QueryExtensionSort2[E, T <: Table[E]](val query: Query[(T, _), (E, Option[String])]) {
    def sorts(sort: String, direction: String) = {
      var _query = query
      sort.reverse.split(" ") foreach { s =>
        _query = sortKey(s.reverse, direction)
      }
      _query
    }

    private def sortKey(sort: String, direction: String): Query[(T, _), (E, Option[String])] = {
      query.sortBy(table =>
        direction match {
          case "asc" => table._1.column[String](sort).asc
          case "desc" => table._1.column[String](sort).desc
        })
    }
  }
}
