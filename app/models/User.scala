package models

final class User(maybeId: Option[Int], val firstName: String, val lastName: String) extends {

  def this(id: Int, firstName: String, lastName: String) = this(Some(id), firstName, lastName)

  def this(firstName: String, lastName: String) = this(None, firstName, lastName)

  def id: UserId =
    maybeId.map(UserId.apply).getOrElse { throw new IllegalStateException(s"id is not defined yet. $fullName") }

  def fullName = s"$lastName $firstName"

  override def equals(other: Any): Boolean = other match {
    case that: User => id == that.id
    case _          => false
  }

  override def hashCode(): Int = id.##
}
