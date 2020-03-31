package com.ortb.biddingRequests

case class BidRequest(id: String, imp: Option[List[Impression]], site: Site, user: Option[User], device: Option[Device])