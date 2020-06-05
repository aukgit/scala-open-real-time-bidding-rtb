package shared.com.ortb.serverAkka.implementations

class AkkaMonitorStartServiceGetPostConcreteMethod(
  additionalEndPointSuffix : String = "start-services")
  extends AkkaGetPostMethodConcrete(
    new AkkaNonImplementPostMethod,
    new AkkaMonitorStartServiceConcreteGetMethod,
    additionalEndPointSuffix)
