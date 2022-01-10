package controllers

import models.{User, UserId, UserRepository}
import play.api.libs.json.{Json, OFormat}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class FindUserController @Inject() (
    cc: ControllerComponents,
    userRepository: UserRepository
)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  def findUser(id: Int): Action[AnyContent] = Action {
    userRepository.findById(UserId(id)) match {
      case None       => NotFound(s"id: $id user not found")
      case Some(user) => Ok(Json.toJson(FindUserResponse.from(user)))
    }
  }

  case class FindUserResponse(id: Int, firstName: String, lastName: String)
  object FindUserResponse {
    implicit val jsonFormat: OFormat[FindUserResponse] = Json.format[FindUserResponse]

    def from(user: User): FindUserResponse = {
      apply(
        user.id.value,
        firstName = user.firstName,
        lastName = user.lastName
      )
    }
  }
}
