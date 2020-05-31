package shared.com.ortb.serverAkka

import shared.com.ortb.serverAkka.traits.akkaMethods.{ AkkaGetPostMethod, AkkaRequestHandlerGetPostMethods }

import scala.collection.mutable

class AkkaRequestHandlerGetPostConcreteMethods
(akkaMethods : AkkaGetPostMethod*) extends AkkaRequestHandlerGetPostMethods {
  lazy override val akkaMethodsInnerMap : mutable.HashMap[String, AkkaGetPostMethod] = {
    val hashMap = new mutable.HashMap[String, AkkaGetPostMethod]()

    akkaMethods
      .foreach(w => hashMap.addOne(w.additionalEndPointSuffix -> w))

    hashMap
  }

  override def addMethod(method : AkkaGetPostMethod) : Unit =
    akkaMethodsInnerMap.addOne(method.additionalEndPointSuffix -> method)
}
