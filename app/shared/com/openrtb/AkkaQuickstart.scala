//#full-example
package shared.com.ortb

import akka.actor.typed._
import akka.actor.typed.scaladsl.Behaviors
import shared.com.ortb.GreeterMain.SayHello

//#greeter-actor
object Greeter {

  def apply() : Behavior[Greet] = Behaviors.receive { (context, message) =>
    context.log.info("Hello {}!", message.whom)
    //#greeter-send-messages
    message.replyTo ! Greeted(message.whom, context.self)
    //#greeter-send-messages
    Behaviors.same
  }

  final case class Greet(whom : String, replyTo : ActorRef[Greeted])

  final case class Greeted(whom : String, from : ActorRef[Greet])
}
//#greeter-actor

//#greeter-bot
object GreeterBot {

  def apply(max : Int) : Behavior[Greeter.Greeted] = {
    bot(0, max)
  }

  private def bot(greetingCounter : Int, max : Int) : Behavior[Greeter.Greeted] =
    Behaviors.receive { (context, message) =>
      val n = greetingCounter + 1
      context.log.info("Greeting {} for {}", n, message.whom)
      if (n == max) {
        Behaviors.stopped
      }
      else {
        message.from ! Greeter.Greet(message.whom, context.self)
        bot(n, max)
      }
    }
}
//#greeter-bot

//#greeter-main
object GreeterMain {

  def apply() : Behavior[SayHello] =
    Behaviors.setup { context =>
      //#create-actors
      val greeter = context.spawn(Greeter(), "greeter")
      //#create-actors

      Behaviors.receiveMessage { message =>
        //#create-actors
        val replyTo = context.spawn(GreeterBot(max = 3), message.name)
        //#create-actors
        greeter ! Greeter.Greet(message.name, replyTo)
        Behaviors.same
      }
    }

  final case class SayHello(name : String)
}
//#greeter-main

//#main-class
object AkkaQuickstart extends App {
  //#actor-system
  val greeterMain : ActorSystem[GreeterMain.SayHello] = ActorSystem(GreeterMain(), "AkkaQuickStart")
  //#actor-system

  //#main-send-messages
  greeterMain ! SayHello("Charles")
  //#main-send-messages
}
//#main-class
//#full-example
