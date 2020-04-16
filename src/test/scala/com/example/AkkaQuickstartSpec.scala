//#full-example
package com.example

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import shared.com.ortb.Greeter.{Greet, Greeted}
import org.scalatest.WordSpecLike
import shared.com.ortb.Greeter

//#definition
class AkkaQuickstartSpec extends ScalaTestWithActorTestKit
  with WordSpecLike {
//#definition

  "A Greeter" must {
    //#test
    "reply to greeted" in {
      val replyProbe = createTestProbe[Greeted]()
      val underTest  = spawn(Greeter())
      underTest ! Greet("Santa", replyProbe.ref)
      replyProbe.expectMessage(Greeted("Santa", underTest.ref))
    }
    //#test
  }

}
//#full-example
