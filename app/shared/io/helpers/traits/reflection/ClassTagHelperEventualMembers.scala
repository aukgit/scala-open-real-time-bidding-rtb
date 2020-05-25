package shared.io.helpers.traits.reflection

import java.lang.reflect.Member

import shared.com.ortb.model.reflection.traits.ClassMembersInfoBaseModel
import shared.com.ortb.model.reflection.{ MemberWrapperBaseModel, MemberWrapperConcreteModel }
import shared.com.ortb.model.results.ResultWithCountSuccessModel
import shared.io.helpers.ParallelTaskHelper
import shared.io.helpers.implementations.ConcurrentArrayBufferWrapper
import shared.io.helpers.implementations.reflection.ClassTagHelperConcreteImplementation
import shared.io.helpers.traits.ParallelTaskHelperBase

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future

trait ClassTagHelperEventualMembers {
  this : ClassTagHelperConcreteImplementation =>
  def getEventualMembers[T](
    classMembersInfo : ClassMembersInfoBaseModel[T]) :
  Future[ResultWithCountSuccessModel[scala.Array[MemberWrapperBaseModel]]] = {
    Future {
      val arrayBuffer = new ArrayBuffer[MemberWrapperBaseModel](defaultMemberPossibility)
      val abW = new ConcurrentArrayBufferWrapper(arrayBuffer)
      var totalCount = 0

      def processMember(member : Member) : Unit = {
        abW.addOne(new MemberWrapperConcreteModel(member))
        totalCount += 1
      }

      ParallelTaskHelper.runInThreads(
        "members collector",
        () =>
          classMembersInfo
            .classType
            .getFields
            .foreach(processMember),
        () => classMembersInfo
          .classType
          .getMethods
          .foreach(processMember),
        () => classMembersInfo
          .classType
          .getConstructors
          .foreach(processMember)
      )

      ResultWithCountSuccessModel(
        result = Some(arrayBuffer.toArray),
        count = totalCount,
        isSuccess = true
      )
    }(createDefaultContext())
  }
}
