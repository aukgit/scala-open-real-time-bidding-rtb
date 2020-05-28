package shared.com.ortb.model.config

abstract class ServiceBaseModel extends DomainPortBaseModel {
  val title : String
  val description : String
  val routing : Array[String]
}
