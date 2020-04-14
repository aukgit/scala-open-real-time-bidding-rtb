package com.ortb.model.results

case class RepositoryOperationResult[T, TKey](
  isSuccess : Boolean, entityId : Option[TKey], entity : Option[T], message : String = null)
