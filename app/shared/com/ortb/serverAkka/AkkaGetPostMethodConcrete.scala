package shared.com.ortb.serverAkka

import shared.com.ortb.serverAkka.traits.akkaMethods.{ AkkaGetMethod, AkkaGetPostMethod, AkkaPostMethod }

class AkkaGetPostMethodConcrete(
  protected val akkaPost : AkkaPostMethod,
  protected val akkaGet : AkkaGetMethod)
  extends AkkaGetPostMethod
