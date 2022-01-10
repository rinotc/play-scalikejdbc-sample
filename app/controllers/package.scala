import play.api.libs.json.{Json, Reads}
import play.api.mvc.{BodyParser, PlayBodyParsers}
import play.api.mvc.Results.BadRequest

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}

package object controllers {
  def jsonValidate[A: Reads](implicit parsers: PlayBodyParsers, ec: ExecutionContext): BodyParser[A] = {
    parsers.json.validate[A] { jsValue =>
      Try(jsValue.validate[A]) match {
        case Success(value) => value.asEither.left.map { _ => BadRequest(Json.toJson("parse error")) }
        case Failure(exception) =>
          exception match {
            case e: RequestValidationError => Left(BadRequest(Json.toJson(Json.toJson(e.msg))))
          }
      }
    }
  }

  private[controllers] class RequestValidationError(val msg: String) extends Exception(msg)
}
