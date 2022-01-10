package infrastructures

import models.{User, UserId, UserRepository}
import scalikejdbc.{DB, SQLInterpolation}

class UserRepositoryImpl extends UserRepository with SQLInterpolation {
  override def findById(id: UserId): Option[User] = DB readOnly { implicit session =>
    sql"""select * from main.public."user" where user_id = ${id.value}"""
      .map { rs =>
        new User(
          id = rs.int("user_id"),
          firstName = rs.string("first_name"),
          lastName = rs.string("last_name")
        )
      }
      .single()
      .apply()
  }

  override def insert(user: User): UserId = {

    val firstName = user.firstName
    val lastName  = user.lastName

    val generateKey = DB localTx { implicit session =>
      sql"""insert into main.public."user" (user_id, first_name, last_name) values (default, $firstName, $lastName)"""
        .updateAndReturnGeneratedKey("user_id")
        .apply()
    }
    UserId(generateKey.toInt)
  }
}
