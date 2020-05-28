/*
 Navicat Premium Data Transfer

 Source Server         : rtb
 Source Server Type    : SQLite
 Source Server Version : 3030001
 Source Schema         : main

 Target Server Type    : SQLite
 Target Server Version : 3030001
 File Encoding         : 65001

 Date: 28/05/2020 18:01:54
*/

PRAGMA foreign_keys = false;

-- ----------------------------
-- Table structure for Advertise
-- ----------------------------
DROP TABLE IF EXISTS "Advertise";
CREATE TABLE "Advertise" (
  "AdvertiseId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "CampaignId" INTEGER NOT NULL,
  "BannerAdvertiseTypeId" INTEGER NOT NULL,
  "AdvertiseTitle" TEXT NOT NULL,
  "ContentContextId" INTEGER,
  "BidUrl" TEXT NOT NULL,
  "IFrameHtml" TEXT,
  "IsCountrySpecific" INTEGER(1) NOT NULL DEFAULT 0,
  "IsBanner" INTEGER(1) NOT NULL DEFAULT 0,
  "IsVideo" INTEGER(1) NOT NULL DEFAULT 0,
  "IsNative" INTEGER(1) NOT NULL DEFAULT 0,
  "ImpressionCount" INTEGER NOT NULL DEFAULT 0,
  "Height" INTEGER NOT NULL DEFAULT 0,
  "Width" INTEGER NOT NULL DEFAULT 0,
  "MinHeight" INTEGER NOT NULL DEFAULT 0,
  "MinWidth" INTEGER NOT NULL DEFAULT 0,
  "MaxHeight" INTEGER NOT NULL DEFAULT 0,
  "MaxWidth" INTEGER NOT NULL DEFAULT 0,
  "IsHeightWidthEmpty" INTEGER(1) NOT NULL DEFAULT 1,
  "IsMaxHeightWidthEmpty" INTEGER(1) NOT NULL DEFAULT 1,
  "IsMinHeightWidthEmpty" INTEGER(1) NOT NULL DEFAULT 1,
  "HasAgeRestriction" INTEGER(1) NOT NULL DEFAULT 0,
  "MinAge" INTEGER DEFAULT 0,
  "MaxAge" INTEGER DEFAULT 0,
  "Duration" INTEGER NOT NULL DEFAULT 0,
  "Protocol" INTEGER NOT NULL DEFAULT 0,
  "CreatedDateTimestamp" TIMESTAMP NOT NULL,
  CONSTRAINT "CampaignFK" FOREIGN KEY ("CampaignId") REFERENCES "Campaign" ("CampaignId") ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT "BannerAdvertiseTypeIdFK" FOREIGN KEY ("BannerAdvertiseTypeId") REFERENCES "BannerAdvertiseType" ("BannerAdvertiseTypeId") ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT "ContentContextIdFK" FOREIGN KEY ("ContentContextId") REFERENCES "ContentContext" ("ContentContextId") ON DELETE CASCADE ON UPDATE CASCADE
);

-- ----------------------------
-- Table structure for Auction
-- ----------------------------
DROP TABLE IF EXISTS "Auction";
CREATE TABLE "Auction" (
  "AuctionId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "WinningBidRequestId" INTEGER DEFAULT NULL,
  "IsPrecachedAdvertiseServed" INTEGER(1) NOT NULL DEFAULT 0,
  "WinningPrice" REAL NOT NULL DEFAULT -1,
  "Currency" TEXT(5) DEFAULT 'USD',
  "CreatedDateTimestamp" TIMESTAMP NOT NULL,
  CONSTRAINT "WinningBidRequestIdFK" FOREIGN KEY ("WinningBidRequestId") REFERENCES "BidRequest" ("BidRequestId") ON DELETE NO ACTION ON UPDATE NO ACTION DEFERRABLE INITIALLY DEFERRED
);

-- ----------------------------
-- Table structure for BannerAdvertiseType
-- ----------------------------
DROP TABLE IF EXISTS "BannerAdvertiseType";
CREATE TABLE "BannerAdvertiseType" (
  "BannerAdvertiseTypeId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "TypeName" TEXT NOT NULL
);

-- ----------------------------
-- Table structure for Bid
-- ----------------------------
DROP TABLE IF EXISTS "Bid";
CREATE TABLE "Bid" (
  "BidId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "BidRawJson" TEXT DEFAULT NULL,
  "DealBiddingPrice" REAL DEFAULT 0,
  "ActualWiningPrice" REAL DEFAULT 0,
  "IsImpressionServedOrWonByAuction" INTEGER(1) DEFAULT 0,
  "SeatBidId" INTEGER NOT NULL,
  "CampaignId" INTEGER DEFAULT NULL,
  "ImpressionId" INTEGER DEFAULT NULL,
  "AdvertiseId" INTEGER DEFAULT NULL,
  "CreativeAttributeId" INTEGER DEFAULT NULL,
  "Adm" TEXT DEFAULT NULL,
  "NUrl" TEXT DEFAULT NULL,
  "IUrl" TEXT DEFAULT NULL,
  "Height" INTEGER DEFAULT NULL,
  "Width" INTEGER DEFAULT NULL,
  "CreatedDateTimestamp" TIMESTAMP NOT NULL,
  CONSTRAINT "CampaignIdFK" FOREIGN KEY ("CampaignId") REFERENCES "Campaign" ("CampaignId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "AdvertiseIdFK" FOREIGN KEY ("AdvertiseId") REFERENCES "Advertise" ("AdvertiseId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "ImpressionIdFk" FOREIGN KEY ("ImpressionId") REFERENCES "Impression" ("ImpressionId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "SeatBidIdFK" FOREIGN KEY ("SeatBidId") REFERENCES "SeatBid" ("SeatBidId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "CreativeAttributeIdFK" FOREIGN KEY ("CreativeAttributeId") REFERENCES "CreativeAttribute" ("CreativeAttributeId") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Table structure for BidContentCategoriesMapping
-- ----------------------------
DROP TABLE IF EXISTS "BidContentCategoriesMapping";
CREATE TABLE "BidContentCategoriesMapping" (
  "BidContentCategoriesMappingId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "BidId" INTEGER NOT NULL,
  "ContentCategoryId" TEXT NOT NULL,
  CONSTRAINT "BidIdFK" FOREIGN KEY ("BidId") REFERENCES "Bid" ("BidId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "ContentCategoryIdFK" FOREIGN KEY ("ContentCategoryId") REFERENCES "ContentCategory" ("ContentCategoryId") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Table structure for BidRequest
-- ----------------------------
DROP TABLE IF EXISTS "BidRequest";
CREATE TABLE "BidRequest" (
  "BidRequestId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "DemandSidePlatformId" INTEGER NOT NULL,
  "AuctionId" INTEGER DEFAULT NULL,
  "IsBanner" INTEGER(1) NOT NULL DEFAULT 0,
  "IsVideo" INTEGER(1) NOT NULL DEFAULT 0,
  "Countries" TEXT DEFAULT NULL,
  "Cities" TEXT DEFAULT NULL,
  "TargetedSites" TEXT DEFAULT NULL,
  "TargetedCities" TEXT DEFAULT NULL,
  "RawBidRequestJson" TEXT NOT NULL DEFAULT '',
  "Currency" TEXT(5) DEFAULT 'USD',
  "ContentContextId" INTEGER DEFAULT NULL,
  "IsWonTheAuction" INTEGER(1) NOT NULL DEFAULT 0,
  "CreatedDateTimestamp" TIMESTAMP NOT NULL,
  CONSTRAINT "DemandSidePlatformIdFK" FOREIGN KEY ("DemandSidePlatformId") REFERENCES "DemandSidePlatform" ("DemandSidePlatformId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "AuctionIdFK" FOREIGN KEY ("AuctionId") REFERENCES "Auction" ("AuctionId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "ContentContextIdFK" FOREIGN KEY ("ContentContextId") REFERENCES "ContentContext" ("ContentContextId") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Table structure for BidResponse
-- ----------------------------
DROP TABLE IF EXISTS "BidResponse";
CREATE TABLE "BidResponse" (
  "BidResponseId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "Currency" TEXT(5) DEFAULT 'USD',
  "BidRequestId" INTEGER DEFAULT NULL,
  "IsAnyBidWonTheAuction" INTEGER(1) NOT NULL DEFAULT 0,
  "IsAuctionOccured" INTEGER(1) NOT NULL DEFAULT 0,
  "IsPreCachedBidServed" INTEGER(1) NOT NULL DEFAULT 0,
  "IsSendNoBidResponse" INTEGER(1) NOT NULL DEFAULT 0,
  "NoBidResponseTypeId" INTEGER DEFAULT NULL,
  "CreatedDateTimestamp" TIMESTAMP NOT NULL,
  CONSTRAINT "BidRequestIdFK" FOREIGN KEY ("BidRequestId") REFERENCES "BidRequest" ("BidRequestId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "NoBidResponseTypeIdFK" FOREIGN KEY ("NoBidResponseTypeId") REFERENCES "NoBidResponseType" ("NoBidResponseTypeId") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Table structure for Campaign
-- ----------------------------
DROP TABLE IF EXISTS "Campaign";
CREATE TABLE "Campaign" (
  "CampaignId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "CampaignName" TEXT NOT NULL,
  "ContentCategoryId" TEXT DEFAULT NULL,
  "TotalBudgetCPM" REAL NOT NULL DEFAULT 0,
  "SpendAlready" REAL NOT NULL DEFAULT 0,
  "RemainingAmount" REAL NOT NULL DEFAULT 0,
  "StartDate" REAL DEFAULT 0,
  "EndDate" REAL DEFAULT 0,
  "ImpressionCount" INTEGER NOT NULL DEFAULT 0,
  "PublisherId" INTEGER DEFAULT NULL,
  "DemandSidePlatformId" INTEGER NOT NULL,
  "IsRunning" INTEGER(1) NOT NULL DEFAULT 0,
  "Priority" INTEGER(3) NOT NULL DEFAULT 999,
  "IsRetrictToUserGender" INTEGER(1) NOT NULL DEFAULT 0,
  "ExpectedUserGender" TEXT(2) DEFAULT NULL,
  "ModifiedDate" TIMESTAMP NOT NULL,
  "CreatedDateTimestamp" TIMESTAMP NOT NULL,
  CONSTRAINT "DemandSidePlatformIdFK" FOREIGN KEY ("DemandSidePlatformId") REFERENCES "DemandSidePlatform" ("DemandSidePlatformId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "ContentCategoryIdFK" FOREIGN KEY ("ContentCategoryId") REFERENCES "ContentCategory" ("ContentCategoryId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "PublisherIdCampaignFK" FOREIGN KEY ("PublisherId") REFERENCES "Publisher" ("PublisherId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  UNIQUE ("CampaignId" ASC)
);

-- ----------------------------
-- Table structure for CampaignTargetCity
-- ----------------------------
DROP TABLE IF EXISTS "CampaignTargetCity";
CREATE TABLE "CampaignTargetCity" (
  "CampaignTargetCityId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "CampaignTargetCity" TEXT NOT NULL,
  "CampaignId" INTEGER NOT NULL,
  CONSTRAINT "CampaignIdFK" FOREIGN KEY ("CampaignId") REFERENCES "Campaign" ("CampaignId") ON DELETE CASCADE ON UPDATE CASCADE
);

-- ----------------------------
-- Table structure for CampaignTargetOperatingSystem
-- ----------------------------
DROP TABLE IF EXISTS "CampaignTargetOperatingSystem";
CREATE TABLE "CampaignTargetOperatingSystem" (
  "CampaignTargetOperatingSystemId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "CampaignTargetOperatingSystem" TEXT NOT NULL,
  "CampaignId" INTEGER NOT NULL,
  CONSTRAINT "CampaignIdFK" FOREIGN KEY ("CampaignId") REFERENCES "Campaign" ("CampaignId") ON DELETE CASCADE ON UPDATE CASCADE
);

-- ----------------------------
-- Table structure for CampaignTargetSite
-- ----------------------------
DROP TABLE IF EXISTS "CampaignTargetSite";
CREATE TABLE "CampaignTargetSite" (
  "CampaignTargetSiteId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "CampaignTargetSite" TEXT NOT NULL,
  "CampaignId" INTEGER NOT NULL,
  CONSTRAINT "CampaignIdFK" FOREIGN KEY ("CampaignId") REFERENCES "Campaign" ("CampaignId") ON DELETE CASCADE ON UPDATE CASCADE
);

-- ----------------------------
-- Table structure for ContentCategory
-- ----------------------------
DROP TABLE IF EXISTS "ContentCategory";
CREATE TABLE "ContentCategory" (
  "ContentCategoryId" TEXT NOT NULL,
  "ContentCategoryName" TEXT NOT NULL,
  PRIMARY KEY ("ContentCategoryId")
);

-- ----------------------------
-- Table structure for ContentContext
-- ----------------------------
DROP TABLE IF EXISTS "ContentContext";
CREATE TABLE "ContentContext" (
  "ContentContextId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "Context" TEXT NOT NULL
);

-- ----------------------------
-- Table structure for CreativeAttribute
-- ----------------------------
DROP TABLE IF EXISTS "CreativeAttribute";
CREATE TABLE "CreativeAttribute" (
  "CreativeAttributeId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "CreativeAttributeDescription" TEXT
);

-- ----------------------------
-- Table structure for DemandSidePlatform
-- ----------------------------
DROP TABLE IF EXISTS "DemandSidePlatform";
CREATE TABLE "DemandSidePlatform" (
  "DemandSidePlatformId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "DemandSidePlatformName" TEXT NOT NULL
);

-- ----------------------------
-- Table structure for DeviceType
-- ----------------------------
DROP TABLE IF EXISTS "DeviceType";
CREATE TABLE "DeviceType" (
  "DeviceTypeId" INTEGER NOT NULL,
  "Device TypeDescription" TEXT NOT NULL,
  PRIMARY KEY ("DeviceTypeId")
);

-- ----------------------------
-- Table structure for GeoMapping
-- ----------------------------
DROP TABLE IF EXISTS "GeoMapping";
CREATE TABLE "GeoMapping" (
  "GeoMappingId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "AdvertiseId" INTEGER DEFAULT NULL,
  "Country" TEXT DEFAULT NULL,
  "HasCity" INTEGER(1) NOT NULL DEFAULT 0,
  "City" TEXT DEFAULT NULL,
  CONSTRAINT "AdvertiseIdFK" FOREIGN KEY ("AdvertiseId") REFERENCES "Advertise" ("AdvertiseId") ON DELETE CASCADE ON UPDATE CASCADE
);

-- ----------------------------
-- Table structure for Impression
-- ----------------------------
DROP TABLE IF EXISTS "Impression";
CREATE TABLE "Impression" (
  "ImpressionId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "BidResponseId" INTEGER DEFAULT NULL,
  "BidRequestId" INTEGER DEFAULT NULL,
  "RawImpressionJson" TEXT NOT NULL DEFAULT '',
  "IsImpressionWonByAuction" INTEGER(1) NOT NULL DEFAULT 0,
  "IsImpressionServed" INTEGER(1) NOT NULL DEFAULT 0,
  "Bidfloor" REAL NOT NULL DEFAULT 0,
  "BidfloorCur" TEXT NOT NULL DEFAULT 'USD',
  "Hash" TEXT DEFAULT NULL,
  "AdvertiseDisplayedDate" TIMESTAMP DEFAULT NULL,
  "CreatedDateTimestamp" TIMESTAMP NOT NULL,
  CONSTRAINT "BidResponseIdFK" FOREIGN KEY ("BidResponseId") REFERENCES "BidResponse" ("BidResponseId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "BidRequestIdFK" FOREIGN KEY ("BidRequestId") REFERENCES "BidRequest" ("BidRequestId") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Table structure for ImpressionPlaceholder
-- ----------------------------
DROP TABLE IF EXISTS "ImpressionPlaceholder";
CREATE TABLE "ImpressionPlaceholder" (
  "ImpressionPlaceholderId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "ImpressionId" INTEGER NOT NULL,
  "IsBanner" INTEGER(1) NOT NULL DEFAULT 0,
  "IsVideo" INTEGER(1) NOT NULL DEFAULT 0,
  "IsNative" INTEGER(1) NOT NULL DEFAULT 0,
  "Height" INTEGER NOT NULL DEFAULT 0,
  "Width" INTEGER NOT NULL DEFAULT 0,
  "MinHeight" INTEGER NOT NULL DEFAULT 0,
  "MinWidth" INTEGER NOT NULL DEFAULT 0,
  "MaxHeight" INTEGER NOT NULL DEFAULT 0,
  "MaxWidth" INTEGER NOT NULL DEFAULT 0,
  "IsHeightWidthEmpty" INTEGER(1) NOT NULL DEFAULT 1,
  "IsMaxHeightWidthEmpty" INTEGER(1) NOT NULL DEFAULT 1,
  "IsMinHeightWidthEmpty" INTEGER(1) NOT NULL DEFAULT 1,
  "Mimes" TEXT DEFAULT NULL,
  "Position" INTEGER DEFAULT NULL,
  "IsTopFrame" INTEGER(1) NOT NULL DEFAULT 0,
  "CreatedDateTimestamp" TIMESTAMP NOT NULL,
  CONSTRAINT "ImpressionIdFK" FOREIGN KEY ("ImpressionId") REFERENCES "Impression" ("ImpressionId") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Table structure for Keyword
-- ----------------------------
DROP TABLE IF EXISTS "Keyword";
CREATE TABLE "Keyword" (
  "KeywordId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "Keyword" TEXT
);

-- ----------------------------
-- Table structure for KeywordAdvertiseMapping
-- ----------------------------
DROP TABLE IF EXISTS "KeywordAdvertiseMapping";
CREATE TABLE "KeywordAdvertiseMapping" (
  "KeywordAdvertiseMappingId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "AdvertiseId" INTEGER NOT NULL,
  "KeywordId" INTEGER NOT NULL,
  CONSTRAINT "AdvertiseIdFK" FOREIGN KEY ("AdvertiseId") REFERENCES "Advertise" ("AdvertiseId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "KeywordIdFK" FOREIGN KEY ("KeywordId") REFERENCES "Keyword" ("KeywordId") ON DELETE CASCADE ON UPDATE CASCADE
);

-- ----------------------------
-- Table structure for LogTrace
-- ----------------------------
DROP TABLE IF EXISTS "LogTrace";
CREATE TABLE "LogTrace" (
  "LogTraceId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "MethodName" TEXT,
  "ClassName" TEXT,
  "Request" TEXT,
  "Message" TEXT,
  "EntityData" TEXT,
  "DatabaseTransactionType" TEXT,
  "CreatedDateTimestamp" TIMESTAMP NOT NULL
);

-- ----------------------------
-- Table structure for LostBid
-- ----------------------------
DROP TABLE IF EXISTS "LostBid";
CREATE TABLE "LostBid" (
  "LostBidId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "BidId" INTEGER,
  "Reason" TEXT,
  "LosingPrice" REAL,
  "Currency" TEXT(5) DEFAULT 'USD',
  "DemandSidePlatformId" INTEGER NOT NULL,
  "CreatedDateTimestamp" TIMESTAMP NOT NULL,
  CONSTRAINT "BidIdFK" FOREIGN KEY ("BidId") REFERENCES "Bid" ("BidId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "DemandSidePlatformFK" FOREIGN KEY ("DemandSidePlatformId") REFERENCES "DemandSidePlatform" ("DemandSidePlatformId") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Table structure for NoBidResponseType
-- ----------------------------
DROP TABLE IF EXISTS "NoBidResponseType";
CREATE TABLE "NoBidResponseType" (
  "NoBidResponseTypeId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "NoBidResponseType" TEXT
);

-- ----------------------------
-- Table structure for PrivateMarketPlaceDeal
-- ----------------------------
DROP TABLE IF EXISTS "PrivateMarketPlaceDeal";
CREATE TABLE "PrivateMarketPlaceDeal" (
  "PrivateMarketPlaceDealId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "ImpressionId" INTEGER DEFAULT NULL,
  "BidRequestId" INTEGER DEFAULT NULL,
  "BidFloor" REAL DEFAULT 0,
  "BidFloorCurrency" TEXT(5) DEFAULT "USD"
);

-- ----------------------------
-- Table structure for Publisher
-- ----------------------------
DROP TABLE IF EXISTS "Publisher";
CREATE TABLE "Publisher" (
  "PublisherId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "PublisherName" TEXT NOT NULL,
  "PublisherWebsite" TEXT NOT NULL,
  "PublisherAddress" TEXT NOT NULL,
  "DemandSidePlatformId" INTEGER NOT NULL,
  CONSTRAINT "DemandSidePlatformIdFK" FOREIGN KEY ("DemandSidePlatformId") REFERENCES "DemandSidePlatform" ("DemandSidePlatformId") ON DELETE CASCADE ON UPDATE CASCADE
);

-- ----------------------------
-- Table structure for SeatBid
-- ----------------------------
DROP TABLE IF EXISTS "SeatBid";
CREATE TABLE "SeatBid" (
  "SeatBidId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "BidRequestId" INTEGER DEFAULT NULL,
  "BidResponseId" INTEGER DEFAULT NULL,
  "AuctionId" INTEGER DEFAULT NULL,
  "DemandSidePlatformId" INTEGER DEFAULT NULL,
  "IsGroupBid" INTEGER(1) DEFAULT 0,
  "SeatBidRawJson" TEXT DEFAULT NULL,
  "CreatedDateTimestamp" TIMESTAMP NOT NULL,
  CONSTRAINT "BidRequestIdFK" FOREIGN KEY ("BidRequestId") REFERENCES "BidRequest" ("BidRequestId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "BidResponseIdFK" FOREIGN KEY ("BidResponseId") REFERENCES "BidResponse" ("BidResponseId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "AuctionIdFK" FOREIGN KEY ("AuctionId") REFERENCES "Auction" ("AuctionId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "DemandSidePlatformIdFK" FOREIGN KEY ("DemandSidePlatformId") REFERENCES "DemandSidePlatform" ("DemandSidePlatformId") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Table structure for Transaction
-- ----------------------------
DROP TABLE IF EXISTS "Transaction";
CREATE TABLE "Transaction" (
  "TransactionId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "AdvertiseId" INTEGER NOT NULL,
  "Spend" REAL NOT NULL,
  "ImpressionId" INTEGER NOT NULL,
  "Currency" TEXT(5) DEFAULT 'USD',
  "CreatedDateTimestamp" TIMESTAMP NOT NULL,
  CONSTRAINT "AdvertiseIdFK" FOREIGN KEY ("AdvertiseId") REFERENCES "Advertise" ("AdvertiseId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "ImpressionIdFK" FOREIGN KEY ("ImpressionId") REFERENCES "Impression" ("ImpressionId") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Table structure for UserClassification
-- ----------------------------
DROP TABLE IF EXISTS "UserClassification";
CREATE TABLE "UserClassification" (
  "UserClassificationId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "UserKeywordsContains" TEXT DEFAULT NULL,
  "InternetProtocol" TEXT DEFAULT NULL,
  "Country" TEXT DEFAULT NULL,
  "lat" REAL DEFAULT NULL,
  "lon" REAL DEFAULT NULL,
  "IsWhiteList" INTEGER(1) DEFAULT 0,
  "IsBlackList" INTEGER(1) DEFAULT 0
);

-- ----------------------------
-- Table structure for VideoPlaybackMethod
-- ----------------------------
DROP TABLE IF EXISTS "VideoPlaybackMethod";
CREATE TABLE "VideoPlaybackMethod" (
  "VideoPlaybackMethodId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "VideoPlaybackMethodDescription" TEXT NOT NULL
);

-- ----------------------------
-- Table structure for VideoResponseProtocol
-- ----------------------------
DROP TABLE IF EXISTS "VideoResponseProtocol";
CREATE TABLE "VideoResponseProtocol" (
  "VideoResponseProtocolId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "VideoResponseProtocolDescription" TEXT NOT NULL
);

-- ----------------------------
-- Table structure for sqlite_sequence
-- ----------------------------
DROP TABLE IF EXISTS "sqlite_sequence";
CREATE TABLE "sqlite_sequence" (
  "name" ,
  "seq" 
);

-- ----------------------------
-- Table structure for sqlite_stat1
-- ----------------------------
DROP TABLE IF EXISTS "sqlite_stat1";
CREATE TABLE "sqlite_stat1" (
  "tbl" ,
  "idx" ,
  "stat" 
);

-- ----------------------------
-- View structure for AdvertiseIsRunningView
-- ----------------------------
DROP VIEW IF EXISTS "AdvertiseIsRunningView";
CREATE VIEW "AdvertiseIsRunningView" AS SELECT
	Advertise.*, 
	Campaign.IsRunning
FROM
	Advertise
	INNER JOIN
	Campaign
	ON 
		Advertise.CampaignId = Campaign.CampaignId;

-- ----------------------------
-- View structure for BidRelatedIdsView
-- ----------------------------
DROP VIEW IF EXISTS "BidRelatedIdsView";
CREATE VIEW "BidRelatedIdsView" AS SELECT DISTINCT
	Bid.BidId, 
	Impression.ImpressionId, 
	Bid.SeatBidId, 
	Bid.CampaignId, 	
	Auction.AuctionId, 
	SeatBid.BidRequestId, 
	SeatBid.BidResponseId, 	
	Bid.AdvertiseId, 
	SeatBid.DemandSidePlatformId	
FROM
	Impression
	LEFT JOIN
	Bid
	ON 		
		Impression.ImpressionId = Bid.ImpressionId
	LEFT JOIN
	SeatBid
	ON 
		Bid.SeatBidId = SeatBid.SeatBidId
	LEFT JOIN
	Auction
	ON 
		SeatBid.AuctionId = Auction.AuctionId;

-- ----------------------------
-- View structure for BidRequestImpressionWithPlaceholderView
-- ----------------------------
DROP VIEW IF EXISTS "BidRequestImpressionWithPlaceholderView";
CREATE VIEW "BidRequestImpressionWithPlaceholderView" AS SELECT DISTINCT
	BidRequest.BidRequestId, 
	BidRequest.CreatedDateTimestamp, 
	BidRequest.IsWonTheAuction, 
	BidRequest.ContentContextId, 
	BidRequest.Currency, 
	BidRequest.RawBidRequestJson, 
	BidRequest.TargetedCities, 
	BidRequest.TargetedSites, 
	BidRequest.Cities, 
	BidRequest.Countries, 
	BidRequest.IsVideo, 
	BidRequest.IsBanner, 
	BidRequest.AuctionId, 
	BidRequest.DemandSidePlatformId, 
	ContentContext.Context AS ContentContext, 
	ImpressionPlaceholder.IsNative AS IsNativePlaceHolder, 
	ImpressionPlaceholder.IsVideo AS IsVideoPlaceHolder, 
	ImpressionPlaceholder.IsBanner AS IsBannnerPlaceHolder, 
	ImpressionPlaceholder.CreatedDateTimestamp AS CreatedDateTimestampImpressionPlaceholder, 
	ImpressionPlaceholder.IsTopFrame, 
	ImpressionPlaceholder.Position, 
	ImpressionPlaceholder.Mimes, 
	ImpressionPlaceholder.IsMinHeightWidthEmpty, 
	ImpressionPlaceholder.IsHeightWidthEmpty, 
	ImpressionPlaceholder.MaxWidth, 
	ImpressionPlaceholder.MaxHeight, 
	ImpressionPlaceholder.MinWidth, 
	ImpressionPlaceholder.MinHeight, 
	ImpressionPlaceholder.Width, 
	ImpressionPlaceholder.Height, 
	Impression.CreatedDateTimestamp as CreatedDateTimestampImpression, 
	ImpressionPlaceholder.IsMaxHeightWidthEmpty, 
	Impression.AdvertiseDisplayedDate, 
	Impression.ImpressionId, 
	Impression.Hash, 
	Impression.BidfloorCur, 
	Impression.Bidfloor, 
	Impression.IsImpressionServed, 
	Impression.IsImpressionWonByAuction, 
	Impression.RawImpressionJson, 
	Impression.BidResponseId
FROM
	BidRequest
	LEFT JOIN
	Impression
	ON 
		BidRequest.BidRequestId = Impression.BidRequestId
	LEFT JOIN
	ImpressionPlaceholder
	ON 
		Impression.ImpressionId = ImpressionPlaceholder.ImpressionId
	LEFT JOIN
	ContentContext
	ON 
		BidRequest.ContentContextId = ContentContext.ContentContextId;

-- ----------------------------
-- View structure for KeywordAdvertiseMappingIdsView
-- ----------------------------
DROP VIEW IF EXISTS "KeywordAdvertiseMappingIdsView";
CREATE VIEW "KeywordAdvertiseMappingIdsView" AS SELECT
	Keyword.KeywordId,
	Keyword.Keyword,
	KeywordAdvertiseMapping.KeywordAdvertiseMappingId,
	Advertise.AdvertiseId,
	Advertise.CampaignId,
	Advertise.BannerAdvertiseTypeId,
	Advertise.IsVideo,
	Advertise.IsBanner,
	Advertise.IsNative,
	Advertise.ContentContextId 
FROM
	Advertise
	INNER JOIN KeywordAdvertiseMapping ON Advertise.AdvertiseId = KeywordAdvertiseMapping.AdvertiseId
	JOIN Keyword ON KeywordAdvertiseMapping.KeywordId = Keyword.KeywordId;

-- ----------------------------
-- View structure for WinningPriceInfoView
-- ----------------------------
DROP VIEW IF EXISTS "WinningPriceInfoView";
CREATE VIEW "WinningPriceInfoView" AS SELECT
	Bid.BidId, 
	Bid.IsImpressionServedOrWonByAuction AS IsWon, 
	Bid.SeatBidId, 
	Bid.CampaignId, 
	Bid.AdvertiseId, 
	Bid.DealBiddingPrice, 
	Bid.ActualWiningPrice, 
	Bid.CreatedDateTimestamp AS BiddingCreatedDateTimestamp, 
	Impression.ImpressionId, 
	SeatBid.BidRequestId, 
	SeatBid.BidResponseId, 
	SeatBid.DemandSidePlatformId, 
	Auction.AuctionId, 
	Auction.WinningPrice AS AuctionWinningPrice, 
	Auction.CreatedDateTimestamp AS AuctionCreatedDateTimestamp, 
	Impression.CreatedDateTimestamp AS ImpressionCreatedDateTimestamp, 
	SeatBid.IsGroupBid, 
	Impression.BidfloorCur, 
	Impression.Bidfloor
FROM
	Impression
	LEFT JOIN
	Bid
	ON 
		Impression.ImpressionId = Bid.ImpressionId
	LEFT JOIN
	SeatBid
	ON 
		Bid.SeatBidId = SeatBid.SeatBidId
	LEFT JOIN
	Auction
	ON 
		SeatBid.AuctionId = Auction.AuctionId;

-- ----------------------------
-- Auto increment value for Advertise
-- ----------------------------

-- ----------------------------
-- Auto increment value for BannerAdvertiseType
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 4 WHERE name = 'BannerAdvertiseType';

-- ----------------------------
-- Auto increment value for BidRequest
-- ----------------------------

-- ----------------------------
-- Auto increment value for BidResponse
-- ----------------------------

-- ----------------------------
-- Auto increment value for Campaign
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 2 WHERE name = 'Campaign';

-- ----------------------------
-- Auto increment value for ContentContext
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 7 WHERE name = 'ContentContext';

-- ----------------------------
-- Auto increment value for CreativeAttribute
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 16 WHERE name = 'CreativeAttribute';

-- ----------------------------
-- Auto increment value for DemandSidePlatform
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 3 WHERE name = 'DemandSidePlatform';

-- ----------------------------
-- Auto increment value for Impression
-- ----------------------------

-- ----------------------------
-- Auto increment value for ImpressionPlaceholder
-- ----------------------------

-- ----------------------------
-- Auto increment value for LogTrace
-- ----------------------------

-- ----------------------------
-- Auto increment value for NoBidResponseType
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 8 WHERE name = 'NoBidResponseType';

-- ----------------------------
-- Auto increment value for Publisher
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 3 WHERE name = 'Publisher';

-- ----------------------------
-- Auto increment value for VideoPlaybackMethod
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 4 WHERE name = 'VideoPlaybackMethod';

-- ----------------------------
-- Auto increment value for VideoResponseProtocol
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 6 WHERE name = 'VideoResponseProtocol';

PRAGMA foreign_keys = true;
