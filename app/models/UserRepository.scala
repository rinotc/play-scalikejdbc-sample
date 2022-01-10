package models

trait UserRepository {

  def findById(id: UserId): Option[User]

  def insert(user: User): UserId
}
