package shared.io.jsonParse.implementations.custom.models

import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._
import io.circe.{ Decoder, Encoder, _ }
import shared.com.ortb.model.attributes.GenericControllerResponseAttributesModel
import shared.com.ortb.model.results.ControllerSuccessResultsModel
import shared.io.jsonParse.implementations.JsonCirceDefaultEncodersImplementation
import shared.io.jsonParse.traits.custom.models.ControllerSuccessResultsModelCustomCodec

class ControllerSuccessResultsModelCustomCodecImplementation[TRow, TKey](
  jsonCirceDefaultEncoders : JsonCirceDefaultEncodersImplementation[TRow])
  extends ControllerSuccessResultsModelCustomCodec[TRow, TKey] {
  lazy val attributesEncoder =
    new JsonCirceDefaultEncodersImplementation[GenericControllerResponseAttributesModel]()

  /**
   * From model to JSON
   *
   * @param a
   *
   * @return
   */
  override def getEncoder : Encoder[ControllerSuccessResultsModel[TRow, TKey]] =
    (a : ControllerSuccessResultsModel[TRow, TKey]) => {
      //noinspection DuplicatedCode
      val attributesJsonObject = attributesEncoder.genericJsonParser
        .toJsonObjectDirect(a.attributes.get)
      val rows = a.data
      val genericJsonParser = jsonCirceDefaultEncoders.genericJsonParser
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
  override def getDecoder : Decoder[ControllerSuccessResultsModel[TRow, TKey]] =
    (c : HCursor) =>
      for {
        attributes <- c
          .downField(fieldNames.attributes)
          .as[GenericControllerResponseAttributesModel](
            attributesEncoder.getDecoder)
        data <- c
          .downField(fieldNames.data)
          .as[List[TRow]](jsonCirceDefaultEncoders.defaultListDecoder)
      } yield {
        new ControllerSuccessResultsModel[TRow, TKey](Some(attributes), data)
      }

  object fieldNames {
    lazy val attributes : String = "attributes"
    lazy val data : String = "data"
  }
}
