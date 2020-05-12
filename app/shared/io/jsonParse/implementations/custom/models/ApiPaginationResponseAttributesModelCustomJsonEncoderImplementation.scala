package shared.io.jsonParse.implementations.custom.models

import io.circe.generic.semiauto.{ deriveDecoder, deriveEncoder }
import io.circe.{ Decoder, Encoder }
import shared.com.ortb.model.attributes.ApiPaginationResponseAttributesModel
import shared.io.jsonParse.traits.custom.ApiPaginationResponseAttributesModelCustomJsonEncoder

class ApiPaginationResponseAttributesModelCustomJsonEncoderImplementation
  extends ApiPaginationResponseAttributesModelCustomJsonEncoder {
  override def getEncoder : Encoder[ApiPaginationResponseAttributesModel] = deriveEncoder[ApiPaginationResponseAttributesModel]

  override def getDecoder : Decoder[ApiPaginationResponseAttributesModel] = deriveDecoder[ApiPaginationResponseAttributesModel]
}
