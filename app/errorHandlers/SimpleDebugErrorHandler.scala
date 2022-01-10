package errorHandlers

import play.api.Logging
import play.api.http.HttpErrorHandler
import play.api.mvc.Results.{InternalServerError, Status}
import play.api.mvc.{RequestHeader, Result}

import scala.concurrent.Future

class SimpleDebugErrorHandler extends HttpErrorHandler with Logging {
  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    Future.successful(
      Status(statusCode)(s"A client error occurred: $message")
    )
  }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    logger.error(exception.getMessage, exception)
    Future.successful {
      InternalServerError(s"A server error occurred: ${exception.getMessage}")
    }
  }
}
