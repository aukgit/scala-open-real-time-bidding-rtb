package shared.com.ortb.serverAkka

import shared.com.ortb.serverAkka.traits.akkaMethods.{ AkkaGetMethod, AkkaGetPostMethod, AkkaPostMethod }

class AkkaMessageGetPostConcreteMethod(
  getMessage : String = "GET : Hello World",
  postMessage : String = "POST : Hello World") extends AkkaGetPostMethod {
  lazy override protected val akkaPost : AkkaPostMethod = new AkkaMessagePostConcreteMethod(postMessage)
  lazy override protected val akkaGet : AkkaGetMethod = new AkkaMessageGetConcreteMethod(getMessage)
}
