import shared.io.helpers.PrimitiveTypeHelper._
import com.github.dwickern.macros.NameOf._
import shared.io.extensions.TypeConvertExtensions._

import io.scalaland.chimney.dsl._
import io.scalaland.chimney.Transformer


import slick.jdbc.SQLiteProfile.api._
import shared.com.ortb.persistent.schema.Tables._

import slick.jdbc.SQLiteProfile.api._

import scala.reflect.runtime.universe._

import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._
import io.circe.derivation._
import io.circe.optics.JsonPath._
import io.circe.optics._
import io.circe.derivation._
import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._

import io.circe.parser._
import io.circe.generic.auto._
import io.circe.generic.encoding.DerivedAsObjectEncoder
import io.circe.generic.encoding._
import io.circe.{ Decoder, Encoder }, io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._
import io.circe.syntax._
import io.circe.Decoder.AccumulatingResult
import io.circe.generic.JsonCodec



import scala.concurrent.ExecutionContext.Implicits.global
(classRefection : Class[_]) :

import scala.jdk.CollectionConverters._

    val logTraceModel = LogTraceModel(
      methodName = methodName,
      request = Some(request),
      entity = Some(bidFailedInfoWithRowsModel))

    coreProperties
      .databaseLogger
      .trace(logTraceModel)


import com.github.dwickern.macros.NameOf._
nameOf(getLastFailedDealsAsBidFailedInfoWithRows _)

"CreatedDateTimestamp" TIMESTAMP NOT NULL DEFAULT (CAST((julianday('now') - 2440587.5)*86400000 AS INTEGER)),

CAST((julianday('now') - 2440587.5)*86400000 AS INTEGER)

(0 until 20).foreach { i => if (someCondition) return }