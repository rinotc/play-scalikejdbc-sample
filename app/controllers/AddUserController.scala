package controllers

import models.{User, UserId, UserRepository}
import play.api.libs.json.{Json, OFormat}
import play.api.mvc._

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class AddUserController @Inject() (
    cc: ControllerComponents,
    userRepository: UserRepository
)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  implicit val parsers: PlayBodyParsers = cc.parsers

  def addUser(): Action[AddUserRequest] = Action(AddUserRequest.validateJson) { implicit request =>
    val newUser   = new User(request.body.firstName, request.body.lastName)
    val newUserId = userRepository.insert(newUser)
    Ok(Json.toJson(AddUserResponse.from(newUserId)))
  }

  case class AddUserRequest(firstName: String, lastName: String)
  object AddUserRequest {

    def validateJson: BodyParser[AddUserRequest] = jsonValidate[AddUserRequest]

    implicit val jsonFormat: OFormat[AddUserRequest] = Json.format[AddUserRequest]
  }

  case class AddUserResponse(newUserId: Int)
  object AddUserResponse {
    implicit val jsonFormat: OFormat[AddUserResponse] = Json.format[AddUserResponse]
    def from(newUserId: UserId): AddUserResponse      = apply(newUserId.value)
  }
}
