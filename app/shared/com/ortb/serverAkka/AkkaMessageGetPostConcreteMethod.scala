package shared.com.ortb.serverAkka

class AkkaMessageGetPostConcreteMethod(
  getMessage : String = "GET : Hello World",
  postMessage : String = "POST : Hello World",
  additionalEndPointSuffix : String = "")
  extends AkkaGetPostMethodConcrete(
    new AkkaMessagePostConcreteMethod(postMessage),
    new AkkaMessageGetConcreteMethod(getMessage),
    additionalEndPointSuffix)
