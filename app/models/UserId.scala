package models

final class UserId(val value: Int) {

  override def equals(other: Any): Boolean = other match {
    case that: UserId => value == that.value
    case _            => false
  }

  override def hashCode(): Int = value.##

  override def toString = s"UserId($value)"
}

object UserId {
  def apply(value: Int) = new UserId(value)
}
