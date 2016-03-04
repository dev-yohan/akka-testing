import akka.actor.ActorSystem

import akka.event.{LoggingAdapter, Logging}
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.{HttpResponse, HttpRequest}
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.{ActorMaterializer, Materializer}
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import java.io.IOException
import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.math._
import spray.json.DefaultJsonProtocol


package util.http {

  class HttpDispatcher { 

   implicit val system = ActorSystem()
   implicit val executor = system.dispatcher
   implicit val materializer = ActorMaterializer()

    lazy val setlisterConnectionFlow: Flow[HttpRequest, HttpResponse, Any] = Http().outgoingConnection("www.setlister.me", 80)  

    def setlisterRequest(request: HttpRequest): Future[HttpResponse] = Source.single(request).via(setlisterConnectionFlow).runWith(Sink.head)

    def getURL(url: String) {
      setlisterRequest(RequestBuilding.Get(url)).flatMap { response =>
        response.status match {
          case OK =>  {
            Unmarshal(response.entity).to[String].flatMap { entity =>
              Future.successful(println(entity))
            }
          }
          case BadRequest => Future.successful(Left(s"incorrect IP format"))
          case _ => Unmarshal(response.entity).to[String].flatMap { entity =>
            val error = s"FreeGeoIP request failed with status code ${response.status} and entity $entity"
            //logger.error(error)
            println("error")
            Future.failed(new IOException(error))
          }
        }
      }
    }
  }
}