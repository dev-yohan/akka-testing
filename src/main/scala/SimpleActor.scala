import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import util.http.HttpDispatcher

 
class HelloActor(myName: String) extends Actor {

  var httpDispatcher = new HttpDispatcher()

  def receive = {
    case "hello" => {
      httpDispatcher.getURL("/api/alphabetic_artists/m")
    }
    case "how are you" => {
      httpDispatcher.getURL("/api/alphabetic_artists/a")
    }
    case _ => {
      httpDispatcher.getURL("/api/alphabetic_artists/c")
    }
  }
}
 
object Main extends App {
  val system = ActorSystem("HelloSystem")
  val helloActor = system.actorOf(Props(new HelloActor("Yohan")), name = "helloactor")
  helloActor ! "hello"
  helloActor ! "buenos dias"
  helloActor ! "how are you"
}