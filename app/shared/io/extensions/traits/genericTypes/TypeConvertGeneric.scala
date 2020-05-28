package shared.io.extensions.traits.genericTypes

import shared.io.helpers.EmptyValidateHelper

import scala.reflect.ClassTag

trait TypeConvertGeneric[T] {
  lazy val isEmpty : Boolean = EmptyValidateHelper.isEmptyAny(anyItem)
  lazy val isDefined : Boolean = !isEmpty
  lazy val toSome : Option[T] = Some(anyItem)
  lazy val toOption : Option[T] = Some(anyItem)
  lazy val toMaybe : Option[T] = Some(anyItem)
  protected val anyItem : T

  lazy val toMakeList : List[T] = List(anyItem)
  lazy val toMakeListSome : Option[List[T]] = Some(List(anyItem))
  lazy val toMakeSeq : Seq[T] = Seq(anyItem)
  lazy val toMakeSeqSome : Option[Seq[T]] = Some(Seq(anyItem))
  lazy val toMakeVector : Seq[T] = Vector(anyItem)
  lazy val toMakeVectorSome : Option[Seq[T]] = Some(Vector(anyItem))

  def toMakeArray(implicit T : ClassTag[T]) : Array[T] = Array(anyItem)

  def toMakeArraySome(implicit T : ClassTag[T]) : Option[Array[T]] = Some(Array(anyItem))
}
