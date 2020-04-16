package shared.com.ortb.serverAkka.framework.sampleCodes

import akka.actor.{Actor, ActorLogging}

class GuitarDB extends Actor
  with ActorLogging {

  import shared.com.ortb.serverAkka.framework.sampleCodes.GuitarDB._

  var guitars         : Map[Int, Guitar] = Map()
  var currentGuitarId : Int              = 0

  override def receive : Receive = {
    case FindAllGuitars =>
      log.info("Searching for all guitars")
      sender() ! guitars.values.toList

    case FindGuitar(id) =>
      log.info(s"Searching guitar by id: $id")
      sender() ! guitars.get(id)

    case CreateGuitar(guitar) =>
      log.info(s"Adding guitar $guitar with id $currentGuitarId")
      guitars = guitars + (currentGuitarId -> guitar)
      sender() ! GuitarCreated(currentGuitarId)
      currentGuitarId += 1

    case AddQuantity(id, quantity) =>
      log.info(s"Trying to add $quantity items for guitar $id")
      val guitar    : Option[Guitar] = guitars.get(id)
      val newGuitar : Option[Guitar] = guitar.map {
        case Guitar(make, model, q) => Guitar(make, model, q + quantity)
      }

      newGuitar.foreach(guitar => guitars = guitars + (id -> guitar))
      sender() ! newGuitar

    case FindGuitarsInStock(inStock) =>
      log.info(s"Searching for all guitars ${
        if (inStock) "in"
        else "out of"
      } stock")
      if (inStock)
        sender() ! guitars.values.filter(_.quantity > 0)
      else
        sender() ! guitars.values.filter(_.quantity == 0)

  }
}

object GuitarDB {

  case class CreateGuitar(guitar : Guitar)

  case class GuitarCreated(id : Int)

  case class FindGuitar(id : Int)

  case class AddQuantity(id : Int, quantity : Int)

  case class FindGuitarsInStock(inStock : Boolean)

  case object FindAllGuitars

}