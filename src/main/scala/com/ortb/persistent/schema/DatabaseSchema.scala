package com.ortb.persistent.schema

import com.ortb.persistent.schema.Tables._
import slick.lifted.TableQuery

class DatabaseSchema {
  lazy val advertises = TableQuery[Advertise]
  lazy val auctions = TableQuery[Auction]
  lazy val bannerAdvertiseTypes = TableQuery[Banneradvertisetype]
  lazy val bidRequests = TableQuery[Bidrequest]
  lazy val bidResponses = TableQuery[Bidresponse]
  lazy val campaigns = TableQuery[Campaign]
  lazy val campaignTargetCities = TableQuery[Campaigntargetcity]
  lazy val campaignTargetSites = TableQuery[Campaigntargetsite]

  /**
   * Content types
   */
  lazy val contentCategories = TableQuery[Contentcategory]

  /**
   * Where the video will be displayed as
   */
  lazy val contentContexts = TableQuery[Contentcontext]

  /**
   * Simulation of demand side platforms
   */
  lazy val demandSidePlatforms = TableQuery[Demandsideplatform]

  /**
   * Relationship with geo and advertise
   */
  lazy val geoMappings = TableQuery[Geomapping]

  /**
   * Winning bid info
   */
  lazy val impressions = TableQuery[Impression]

  /**
   * Keywords in the system
   */
  lazy val keywords = TableQuery[Keyword]

  /**
   * Relationship with Keywords and Advertises
   */
  lazy val keywordAdvertiseMappings = TableQuery[Keywordadvertisemapping]

  /**
   * Lost bid info
   */
  lazy val lostBids = TableQuery[Lostbid]

  /**
   * types for no bid responses
   */
  lazy val noBidResponseTypes = TableQuery[Nobidresponsetype]

  /**
   * publisher who (content owner of the advertise)
   */
  lazy val publishers = TableQuery[Publisher]

  /**
   * accounting for how much spend
   */
  lazy val transactions = TableQuery[Transaction]

  lazy val tables = List(
    advertises,
    auctions,
    bannerAdvertiseTypes,
    bidRequests,
    bidResponses,
    campaigns,
    campaignTargetCities,
    campaignTargetSites,
    contentCategories,
    contentContexts,
    demandSidePlatforms,
    geoMappings,
    impressions,
    keywords,
    keywordAdvertiseMappings,
    lostBids,
    noBidResponseTypes,
    publishers,
    transactions
  )
}
