package shared.com.ortb.serverAkka

import shared.com.ortb.serverAkka.traits.akkaMethods.{ AkkaNonImplementGetMethod, AkkaNonImplementPostMethod }

class AkkaMessageGetPostConcreteMethod(
  getMessage : String = "GET : Hello World",
  postMessage : String = "POST : Hello World",
  additionalEndPointSuffix : String = "")
  extends AkkaGetPostMethodConcrete(
    new AkkaMessagePostConcreteMethod(postMessage),
    new AkkaMessageGetConcreteMethod(getMessage),
    additionalEndPointSuffix)
