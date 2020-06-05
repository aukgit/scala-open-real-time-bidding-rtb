package shared.com.ortb.serverAkka.framework.sampleCodes.serverRunner

import akka.actor.ActorSystem
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.{ HttpMethods, HttpRequest, HttpResponse, MessageEntity }
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.{ ActorMaterializer, Materializer }
import akka.util.ByteString
import shared.com.ortb.manager.traits.CreateDefaultContext._

import shared.io.extensions.TypeConvertExtensions._

import scala.concurrent.ExecutionContext

object UnMarshalling {

  def main(args : Array[String]) {

    implicit val sys : ActorSystem = ActorSystem("IntroductionToAkkaHttp")
    implicit val mat : Materializer = ActorMaterializer()

    //type FromStringUnmarshaller[T] = Unmarshaller[String, T]
    val intFuture = Unmarshal("42").to[Int]
    val int = intFuture.toRegular()
    println("int unmarshalling " + int)

    //type FromStringUnmarshaller[T] = Unmarshaller[String, T]
    val boolFuture = Unmarshal("off").to[Boolean]
    val bool = boolFuture.toRegular()
    println("off unmarshalling " + bool)

    //type ToEntityMarshaller[T] = Marshaller[T, MessageEntity]
    val string = "Yeah"
    val entityFuture = Marshal(string).to[MessageEntity]
    val entity = entityFuture.toRegular() // don't block in non-test code!
    println(entity)

    //type ToResponseMarshaller[T] = Marshaller[T, HttpResponse]
    val errorMsg = "Not found, pal!"
    val responseFuture = Marshal(404 -> errorMsg).to[HttpResponse]
    val response = responseFuture.toRegular()
    println(response)


    //type FromEntityUnmarshaller[T] = Unmarshaller[HttpEntity, T]
    val jsonByteString = ByteString("""{"name":"Hello"}""")
    val httpRequest = HttpRequest(HttpMethods.POST, entity = jsonByteString)
    val jsonDataUnmarshalledFuture = Unmarshal(httpRequest).to[String]
    val jsonDataUnmarshalled = jsonDataUnmarshalledFuture.toRegular()
    println(jsonDataUnmarshalled)

    sys.terminate()

  }
}
