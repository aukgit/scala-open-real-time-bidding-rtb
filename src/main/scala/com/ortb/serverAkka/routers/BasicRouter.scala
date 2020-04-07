package com.ortb.serverAkka.routers

import akka.http.scaladsl.model.HttpEntity

abstract class BasicRouter[T](
  prefix: String,
  routerName: String) {
// abstract def get(item: T): HttpEntity
//
//  abstract def post(item: T): HttpEntity
//
//  def getRouter() = {
//    if(prefix.isEmpty){
//      path(routerName){
//        get{
//
//        }
//      }
//    }
//  }
}
