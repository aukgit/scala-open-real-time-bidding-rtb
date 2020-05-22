package shared.io.helpers.traits.reflection

import java.util.concurrent.ConcurrentHashMap

import shared.com.ortb.model.reflection.MemberWrapperBaseModel
import shared.com.ortb.model.reflection.traits.ClassMembersInfoBaseModel
import shared.com.ortb.model.results.ResultWithCountSuccessModel
import shared.io.helpers.traits.ParallelTaskHelperBase
import shared.io.helpers.{ MapHelper, ParallelTaskHelper }
import shared.io.helpers.implementations.reflection.ClassTagHelperConcreteImplementation

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future

trait ClassTagHelperEventualMembersMap {
  this : ClassTagHelperConcreteImplementation =>
  def getEventualMembersMap[T](
    classMembersInfo : ClassMembersInfoBaseModel[T]) :
  Future[ResultWithCountSuccessModel[ConcurrentHashMap[String, ArrayBuffer[MemberWrapperBaseModel]]]] = {
    Future {
      val map = new ConcurrentHashMap[String, ArrayBuffer[MemberWrapperBaseModel]]
      var totalCount = 0

      def processMember(memberWrapperModel : MemberWrapperBaseModel) : Unit = {
        MapHelper
          .hashMapWithArrayBufferAdder
          .concurrentMapAddToArrayBuffer(
            map,
            memberWrapperModel.name,
            memberWrapperModel)

        totalCount += 1
      }

      ParallelTaskHelper.runInThreads(
        "members collector",
        () =>
          classMembersInfo
            .fields
            .values
            .flatten
            .foreach(processMember),
        () => classMembersInfo
          .methods
          .values
          .flatten
          .foreach(processMember),
        () => classMembersInfo
          .constructors
          .values
          .flatten
          .foreach(processMember)
      )

      ResultWithCountSuccessModel(
        result = Some(map),
        count = totalCount,
        isSuccess = true
      )
    }(createDefaultContext())
  }
}
