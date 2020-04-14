package com.ortb.persistent.repositoryPattern.traits

import java.awt.dnd.InvalidDnDOperationException

import com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import slick.lifted.AbstractTable

import scala.concurrent.{Future, Await}
import slick.dbio.{NoStream, DBIOAction, Effect}
import slick.sql._
import io.AppLogger
import com.ortb.model.results.RepositoryOperationResult
import com.ortb.persistent.repositoryPattern.Repository
//
//trait DatabaseActionExecutor[TTable <: AbstractTable[_], TRow >: Null, TKey] {
//  this: Repository[TTable, TRow, TKey] =>
//
//}
