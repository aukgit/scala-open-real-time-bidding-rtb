package com.ortb.model.results

case class RepositoryOperationResult[T](isSuccess: Boolean, entity: Option[T], message: String = null)
