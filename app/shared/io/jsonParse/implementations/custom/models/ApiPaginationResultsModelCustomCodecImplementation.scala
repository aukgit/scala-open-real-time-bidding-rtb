package shared.io.jsonParse.implementations.custom.models

import io.circe._
import shared.com.ortb.model.repository.ApiPaginationResponseAttributesModel
import shared.com.ortb.model.repository.response.ApiPaginationResultsModel
import shared.io.jsonParse.implementations.JsonCirceDefaultEncodersImplementation
import shared.io.jsonParse.traits.custom.models.ApiPaginationResultsModelCustomCodec

class ApiPaginationResultsModelCustomCodecImplementation[TRow, TKey](
    jsonCirceDefaultEncoders: JsonCirceDefaultEncodersImplementation[TRow])
    extends ApiPaginationResultsModelCustomCodec[TRow, TKey] {
  lazy val attributesEncoder =
    new ApiPaginationResponseAttributesModelCustomJsonEncoderImplementation

  object fieldNames {
    lazy val attributes: String = "attributes"
    lazy val data: String = "data"
  }

  /**
    * From model to JSON
    *
    * @param a
    *
    * @return
    */
  override def getEncoder: Encoder[ApiPaginationResultsModel[TRow, TKey]] =
    (a: ApiPaginationResultsModel[TRow, TKey]) => {
      val attributesJsonObject = attributesEncoder.getJsonGenericParser
        .toJsonObjectDirect(a.attributes.get)
      val dataToJson =
        Json.fromString(jsonCirceDefaultEncoders.toJsonString(a.data))

      val jsonObject = Json.obj(
        (fieldNames.attributes, attributesJsonObject),
        (fieldNames.data, dataToJson)
      )

      jsonObject
    }

  /**
    * From Json String to Model
    *
    * @param c
    *
    * @return
    */
  override def getDecoder: Decoder[ApiPaginationResultsModel[TRow, TKey]] =
    (c: HCursor) =>
      for {
        attributes <- c
          .downField(fieldNames.attributes)
          .as[ApiPaginationResponseAttributesModel](attributesEncoder.getDecoder)
        data <- c.downField(fieldNames.data).as[List[TRow]](jsonCirceDefaultEncoders.defaultListDecoder)
      } yield {
        new ApiPaginationResultsModel[TRow, TKey](Some(attributes), data)
    }
}
