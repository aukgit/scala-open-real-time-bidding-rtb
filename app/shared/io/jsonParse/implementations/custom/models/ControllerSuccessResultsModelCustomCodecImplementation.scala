package shared.io.jsonParse.implementations.custom.models
import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.generic.auto._
import io.circe.generic.encoding.DerivedAsObjectEncoder
import io.circe.generic.encoding._
import io.circe.{ Decoder, Encoder }
import io.circe.generic.semiauto._
import io.circe.{ Decoder, Encoder }
import io.circe.generic.semiauto._
import io.circe.syntax._
import io.circe.Decoder.AccumulatingResult
import io.circe.generic.JsonCodec
import io.circe._
import shared.com.ortb.model.attributes.{ ApiPaginationResponseAttributesModel, GenericControllerResponseAttributesModel }
import shared.com.ortb.model.results.ApiPaginationResultsModel
import shared.io.jsonParse.implementations.JsonCirceDefaultEncodersImplementation
import shared.io.jsonParse.traits.custom.models.ApiPaginationResultsModelCustomCodec

class ControllerSuccessResultsModelCustomCodecImplementation[TRow, TKey](
  jsonCirceDefaultEncoders: JsonCirceDefaultEncodersImplementation[TRow])
  extends ApiPaginationResultsModelCustomCodec[TRow, TKey] {
  lazy val attributesEncoder : Encoder.AsObject[GenericControllerResponseAttributesModel[TKey]] =
    deriveEncoder[GenericControllerResponseAttributesModel[TKey]]

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
      val rows = a.data
      val genericJsonParser = jsonCirceDefaultEncoders.getJsonGenericParser
      val iterableJsonObjects = genericJsonParser.fromModelsToJsonObjects(Some(rows))
      val dataToJson = Json.fromValues(iterableJsonObjects.get)

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
          .as[ApiPaginationResponseAttributesModel](
            attributesEncoder.getDecoder)
        data <- c
          .downField(fieldNames.data)
          .as[List[TRow]](jsonCirceDefaultEncoders.defaultListDecoder)
      } yield {
        new ApiPaginationResultsModel[TRow, TKey](Some(attributes), data)
      }
}
