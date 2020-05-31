package shared.com.ortb.serverAkka.traits.akkaMethods

import scala.collection.mutable

trait AkkaRequestHandlerGetPostMethods {
  protected val akkaMethodsInnerMap : mutable.HashMap[String, AkkaGetPostMethod]

  def addMethod(method : AkkaGetPostMethod) : Unit

  def getRoutes : Iterable[String] = akkaMethodsMap.keys

  def akkaMethodsMap : Map[String, AkkaGetPostMethod] = akkaMethodsInnerMap.toMap
}
