/*
 Navicat Premium Data Transfer

 Source Server         : rtbDB
 Source Server Type    : SQLite
 Source Server Version : 3030001
 Source Schema         : main

 Target Server Type    : SQLite
 Target Server Version : 3030001
 File Encoding         : 65001

 Date: 11/04/2020 17:43:23
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
  "ContentContextId" INTEGER NOT NULL,
  "BidUrl" TEXT NOT NULL,
  "IFrameHtml" TEXT,
  "IsCountrySpecific" INTEGER(1) NOT NULL DEFAULT 0,
  "IsVideo" INTEGER(1) NOT NULL DEFAULT 0,
  "ImpressionCount" INTEGER NOT NULL DEFAULT 0,
  "Height" REAL NOT NULL DEFAULT 0,
  "Weight" REAL NOT NULL DEFAULT 0,
  "MinHeight" REAL NOT NULL DEFAULT 0,
  "MinWeight" REAL NOT NULL DEFAULT 0,
  "MaxHeight" REAL NOT NULL DEFAULT 0,
  "MaxWeight" REAL NOT NULL DEFAULT 0,
  "HasAgeRestriction" INTEGER(1) NOT NULL,
  "MinAge" INTEGER DEFAULT 0,
  "MaxAge" INTEGER DEFAULT 0,
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
  "AdvertiseId" INTEGER NOT NULL,
  "Dated" real NOT NULL,
  "WinningBidderId" INTEGER,
  "IsPrecachedAdvertiseServed" integer(1) NOT NULL DEFAULT 0,
  "WinningPrice" REAL NOT NULL DEFAULT -1,
  "Currency" TEXT NOT NULL DEFAULT USD,
  CONSTRAINT "AdvertiseIdFK" FOREIGN KEY ("AdvertiseId") REFERENCES "Advertise" ("AdvertiseId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "WinningBidderIdFK" FOREIGN KEY ("WinningBidderId") REFERENCES "BidRequest" ("BidRequestId") ON DELETE NO ACTION ON UPDATE NO ACTION DEFERRABLE INITIALLY DEFERRED
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
-- Table structure for BidRequest
-- ----------------------------
DROP TABLE IF EXISTS "BidRequest";
CREATE TABLE "BidRequest" (
  "BidRequestId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "DemandSidePlatformId" INTEGER NOT NULL,
  "AuctionId" INTEGER,
  "IsBanner" integer(1) NOT NULL DEFAULT 0,
  "IsVideo" integer(1) NOT NULL DEFAULT 0,
  "Height" integer,
  "Width" integer,
  "Countries" TEXT,
  "Cities" TEXT,
  "TargetedSites" TEXT,
  "TargetedCities" TEXT,
  "RawBidRequest" TEXT,
  "Price1" real DEFAULT 0,
  "Price2" real DEFAULT 0,
  "Price3" real DEFAULT 0,
  "Currency" TEXT(5) DEFAULT USD,
  "ContentContextId" INTEGER,
  "IsWonTheAuction" integer(1) NOT NULL DEFAULT 0,
  CONSTRAINT "DemandSidePlatformIdFK" FOREIGN KEY ("DemandSidePlatformId") REFERENCES "DemandSidePlatform" ("DemandSidePlatformId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "AuctionIdFK" FOREIGN KEY ("AuctionId") REFERENCES "Auction" ("AuctionId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "ContentContextIdFK" FOREIGN KEY ("ContentContextId") REFERENCES "ContentContext" ("ContentContextId") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Table structure for BidResponse
-- ----------------------------
DROP TABLE IF EXISTS "BidResponse";
CREATE TABLE "BidResponse" (
  "BidResponseId" integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  "Price" REAL NOT NULL,
  "Currency" TEXT NOT NULL,
  "Adm" text,
  "NUrl" text NOT NULL,
  "IUrl" TEXT,
  "AdvertiseId" INTEGER NOT NULL,
  "BidRequestId" INTEGER,
  "IsWonTheAuction" integer(1) NOT NULL DEFAULT 0,
  "IsAuctionOccured" integer(1) NOT NULL DEFAULT 0,
  "IsPreCachedBidServed" integer(1) NOT NULL DEFAULT 0,
  "IsSendNoBidResponse" integer(1) NOT NULL DEFAULT 0,
  "NoBidResponseTypeId" INTEGER,
  CONSTRAINT "AdvertiseIdFK" FOREIGN KEY ("AdvertiseId") REFERENCES "Advertise" ("AdvertiseId") ON DELETE CASCADE ON UPDATE CASCADE,
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
  "ContentCategoryId" text NOT NULL,
  "TotalBudgetCPM" real NOT NULL DEFAULT 0,
  "SpendAlready" real NOT NULL DEFAULT 0,
  "RemainingAmount" real NOT NULL DEFAULT 0,
  "StartDate" real DEFAULT 0,
  "EndDate" real DEFAULT 0,
  "ImpressionCount" integer NOT NULL DEFAULT 0,
  "DemandSidePlatformId" integer NOT NULL,
  "IsRunning" integer(1) NOT NULL DEFAULT 0,
  "Priority" integer(3) NOT NULL DEFAULT 999,
  "IsRetrictToUserGender" integer(1) NOT NULL DEFAULT 0,
  "ExpectedUserGender" TEXT(2),
  "PublisherId" INTEGER,
  CONSTRAINT "DemandSidePlatformIdFK" FOREIGN KEY ("DemandSidePlatformId") REFERENCES "DemandSidePlatform" ("DemandSidePlatformId") ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT "ContentCategoryIdFK" FOREIGN KEY ("ContentCategoryId") REFERENCES "ContentCategory" ("ContentCategoryId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "PublisherIdCampaignFK" FOREIGN KEY ("PublisherId") REFERENCES "Publisher" ("PublisherId") ON DELETE SET NULL ON UPDATE SET NULL,
  UNIQUE ("CampaignId" ASC)
);

-- ----------------------------
-- Table structure for CampaignTargetCity
-- ----------------------------
DROP TABLE IF EXISTS "CampaignTargetCity";
CREATE TABLE "CampaignTargetCity" (
  "CampaignTargetCityId" integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  "CampaignTargetCity" TEXT NOT NULL,
  "CampaignId" integer NOT NULL,
  CONSTRAINT "CampaignIdFK" FOREIGN KEY ("CampaignId") REFERENCES "Campaign" ("CampaignId") ON DELETE CASCADE ON UPDATE CASCADE
);

-- ----------------------------
-- Table structure for CampaignTargetSite
-- ----------------------------
DROP TABLE IF EXISTS "CampaignTargetSite";
CREATE TABLE "CampaignTargetSite" (
  "CampaignTargetSiteId" integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  "CampaignTargetSite" TEXT NOT NULL,
  "CampaignId" integer NOT NULL,
  CONSTRAINT "CampaignIdFK" FOREIGN KEY ("CampaignId") REFERENCES "Campaign" ("CampaignId") ON DELETE CASCADE ON UPDATE CASCADE
);

-- ----------------------------
-- Table structure for ContentCategory
-- ----------------------------
DROP TABLE IF EXISTS "ContentCategory";
CREATE TABLE "ContentCategory" (
  "ContentCategoryId" text NOT NULL,
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
-- Table structure for DemandSidePlatform
-- ----------------------------
DROP TABLE IF EXISTS "DemandSidePlatform";
CREATE TABLE "DemandSidePlatform" (
  "DemandSidePlatformId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "DemandSidePlatformName" TEXT NOT NULL
);

-- ----------------------------
-- Table structure for GeoMapping
-- ----------------------------
DROP TABLE IF EXISTS "GeoMapping";
CREATE TABLE "GeoMapping" (
  "GeoMappingId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "AdvertiseId" INTEGER NOT NULL,
  "Country" TEXT NOT NULL,
  "HasCity" integer(1) NOT NULL DEFAULT 0,
  "City" TEXT DEFAULT NULL,
  CONSTRAINT "AdvertiseIdFK" FOREIGN KEY ("AdvertiseId") REFERENCES "Advertise" ("AdvertiseId") ON DELETE CASCADE ON UPDATE CASCADE
);

-- ----------------------------
-- Table structure for Impression
-- ----------------------------
DROP TABLE IF EXISTS "Impression";
CREATE TABLE "Impression" (
  "ImpressionId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "AdvertiseId" INTEGER NOT NULL,
  "Dated" INTEGER NOT NULL,
  "BidRequestId" INTEGER NOT NULL,
  "Price" REAL NOT NULL,
  "Currency" TEXT NOT NULL,
  CONSTRAINT "AdvertiseIdFK" FOREIGN KEY ("AdvertiseId") REFERENCES "Advertise" ("AdvertiseId") ON DELETE NO ACTION ON UPDATE NO ACTION
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
  "KeywordAdvertiseMappingId" integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  "AdvertiseId" INTEGER NOT NULL,
  "KeywordId" INTEGER NOT NULL,
  CONSTRAINT "AdvertiseIdFK" FOREIGN KEY ("AdvertiseId") REFERENCES "Advertise" ("AdvertiseId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "KeywordIdFK" FOREIGN KEY ("KeywordId") REFERENCES "Keyword" ("KeywordId") ON DELETE CASCADE ON UPDATE CASCADE
);

-- ----------------------------
-- Table structure for LostBid
-- ----------------------------
DROP TABLE IF EXISTS "LostBid";
CREATE TABLE "LostBid" (
  "LostBidId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "BidRequestId" INTEGER NOT NULL,
  "Reason" TEXT,
  CONSTRAINT "BidRequestIdFK" FOREIGN KEY ("BidRequestId") REFERENCES "BidRequest" ("BidRequestId") ON DELETE NO ACTION ON UPDATE NO ACTION
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
-- Table structure for Transaction
-- ----------------------------
DROP TABLE IF EXISTS "Transaction";
CREATE TABLE "Transaction" (
  "TransactionId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "AdvertiseId" INTEGER NOT NULL,
  "Spend" real NOT NULL,
  "ImpressionId" INTEGER NOT NULL,
  "Currency" TEXT NOT NULL,
  CONSTRAINT "AdvertiseIdFK" FOREIGN KEY ("AdvertiseId") REFERENCES "Advertise" ("AdvertiseId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "ImpressionIdFK" FOREIGN KEY ("ImpressionId") REFERENCES "Impression" ("ImpressionId") ON DELETE NO ACTION ON UPDATE NO ACTION
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
-- Auto increment value for Advertise
-- ----------------------------

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
-- Auto increment value for CampaignTargetCity
-- ----------------------------

-- ----------------------------
-- Auto increment value for CampaignTargetSite
-- ----------------------------

-- ----------------------------
-- Auto increment value for ContentContext
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 7 WHERE name = 'ContentContext';

-- ----------------------------
-- Auto increment value for DemandSidePlatform
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 3 WHERE name = 'DemandSidePlatform';

-- ----------------------------
-- Auto increment value for Impression
-- ----------------------------

-- ----------------------------
-- Auto increment value for LostBid
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
-- Auto increment value for Transaction
-- ----------------------------

PRAGMA foreign_keys = true;