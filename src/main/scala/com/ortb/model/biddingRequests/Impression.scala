package com.ortb.model.biddingRequests

case class Impression(
  id : String, wmin : Option[Int], wmax : Option[Int], w : Option[Int], hmin : Option[Int], hmax : Option[Int],
  h  : Option[Int], bidFloor : Option[Double])
