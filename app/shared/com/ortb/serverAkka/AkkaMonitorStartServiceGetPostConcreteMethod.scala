package shared.com.ortb.serverAkka

import shared.com.ortb.serverAkka.traits.akkaMethods.AkkaNonImplementPostMethod

class AkkaMonitorStartServiceGetPostConcreteMethod(
  additionalEndPointSuffix : String = "")
  extends AkkaGetPostMethodConcrete(
    new AkkaNonImplementPostMethod,
    new AkkaMonitorStartServiceConcreteGetMethod,
    additionalEndPointSuffix)
