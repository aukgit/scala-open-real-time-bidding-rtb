/*
 Navicat Premium Data Transfer

 Source Server         : rtbDB
 Source Server Type    : SQLite
 Source Server Version : 3030001
 Source Schema         : main

 Target Server Type    : SQLite
 Target Server Version : 3030001
 File Encoding         : 65001

 Date: 11/04/2020 16:10:47
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
  "AuctionId" INTEGER NOT NULL,
  "IsBanner" integer(1) NOT NULL DEFAULT 0,
  "IsVideo" integer(1) NOT NULL DEFAULT 0,
  "Height" integer,
  "Width" integer,
  "Countries" TEXT,
  "Cities" TEXT,
  "TargetedSites" TEXT,
  "TargetedCities" TEXT,
  "RawBidRequest" TEXT,
  "Price1" real NOT NULL DEFAULT 0,
  "Price2" real NOT NULL DEFAULT 0,
  "Price3" real NOT NULL DEFAULT 0,
  "Currency" TEXT(5) DEFAULT USD,
  "ContentContextId" INTEGER,
  "IsWonTheAuction" integer(1) NOT NULL DEFAULT 0,
  CONSTRAINT "DemandSidePlatformIdFK" FOREIGN KEY ("DemandSidePlatformId") REFERENCES "DemandSidePlatform" ("DemandSidePlatformId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "AuctionIdFK" FOREIGN KEY ("AuctionId") REFERENCES "Auction" ("AuctionId") ON DELETE CASCADE ON UPDATE CASCADE,
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
  CONSTRAINT "AdvertiseIdFK" FOREIGN KEY ("AdvertiseId") REFERENCES "Advertise" ("AdvertiseId") ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT "BidRequestIdFK" FOREIGN KEY ("BidRequestId") REFERENCES "BidRequest" ("BidRequestId") ON DELETE NO ACTION ON UPDATE NO ACTION
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
  "Priority" integer NOT NULL DEFAULT 999,
  "IsRetrictToUserGender" integer(1) NOT NULL DEFAULT 0,
  "ExpectedUserGender" TEXT(2),
  CONSTRAINT "DemandSidePlatformIdFK" FOREIGN KEY ("DemandSidePlatformId") REFERENCES "DemandSidePlatform" ("DemandSidePlatformId") ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT "ContentCategoryIdFK" FOREIGN KEY ("ContentCategoryId") REFERENCES "ContentCategory" ("ContentCategoryId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  UNIQUE ("CampaignId" ASC)
);

-- ----------------------------
-- Records of Campaign
-- ----------------------------
INSERT INTO "Campaign" VALUES (1, 'First Cricket Campaign', 'IAB17', 10.0, 0.0, 10.0, NULL, NULL, 0, 1, 1, 999, 0, NULL);
INSERT INTO "Campaign" VALUES (2, 'Business Campaing', 'IAB3', 5.0, 0.0, 5.0, 0.0, 0.0, 0, 2, 1, 999, 0, NULL);

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
-- Records of ContentCategory
-- ----------------------------
INSERT INTO "ContentCategory" VALUES ('IAB1-1', 'Books & Literature');
INSERT INTO "ContentCategory" VALUES ('IAB1-5', 'Movies');
INSERT INTO "ContentCategory" VALUES ('IAB1-6', 'Music');
INSERT INTO "ContentCategory" VALUES ('IAB1-7', 'Television');
INSERT INTO "ContentCategory" VALUES ('IAB2', 'Automotive');
INSERT INTO "ContentCategory" VALUES ('IAB1', 'Arts & Entertainment');
INSERT INTO "ContentCategory" VALUES ('IAB3', 'Business');
INSERT INTO "ContentCategory" VALUES ('IAB3-1', 'Advertising');
INSERT INTO "ContentCategory" VALUES ('IAB3-2', 'Agriculture');
INSERT INTO "ContentCategory" VALUES ('IAB4', 'Careers');
INSERT INTO "ContentCategory" VALUES ('IAB5', 'Education');
INSERT INTO "ContentCategory" VALUES ('IAB6', 'Family & Parenting');
INSERT INTO "ContentCategory" VALUES ('IAB7', 'Health & Fitness');
INSERT INTO "ContentCategory" VALUES ('IAB8', 'Food & Drink');
INSERT INTO "ContentCategory" VALUES ('IAB9', 'Hobbies & Interests');
INSERT INTO "ContentCategory" VALUES ('IAB10', 'Home & Garden');
INSERT INTO "ContentCategory" VALUES ('IAB11
', 'Law, Government & Politics');
INSERT INTO "ContentCategory" VALUES ('IAB12', 'News');
INSERT INTO "ContentCategory" VALUES ('IAB13', 'Personal Finance');
INSERT INTO "ContentCategory" VALUES ('IAB14', 'Society');
INSERT INTO "ContentCategory" VALUES ('IAB15', 'Science');
INSERT INTO "ContentCategory" VALUES ('IAB16', 'Pets');
INSERT INTO "ContentCategory" VALUES ('IAB17', 'Sports');
INSERT INTO "ContentCategory" VALUES ('IAB18', 'Style & Fashion');
INSERT INTO "ContentCategory" VALUES ('IAB19', 'Technology & Computing');
INSERT INTO "ContentCategory" VALUES ('IAB20', 'Travel');
INSERT INTO "ContentCategory" VALUES ('IAB21', 'Real Estate');
INSERT INTO "ContentCategory" VALUES ('IAB22', 'Shopping');
INSERT INTO "ContentCategory" VALUES ('IAB23', 'Religion & Spirituality');
INSERT INTO "ContentCategory" VALUES ('IAB24', 'Uncategorized');
INSERT INTO "ContentCategory" VALUES ('IAB25', 'Non-Standard Content');
INSERT INTO "ContentCategory" VALUES ('IAB26', 'Illegal Content');

-- ----------------------------
-- Table structure for ContentContext
-- ----------------------------
DROP TABLE IF EXISTS "ContentContext";
CREATE TABLE "ContentContext" (
  "ContentContextId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "Context" TEXT NOT NULL
);

-- ----------------------------
-- Records of ContentContext
-- ----------------------------
INSERT INTO "ContentContext" VALUES (1, 'Video');
INSERT INTO "ContentContext" VALUES (2, 'Game');
INSERT INTO "ContentContext" VALUES (3, 'Music');
INSERT INTO "ContentContext" VALUES (4, 'Application');
INSERT INTO "ContentContext" VALUES (5, 'Text');
INSERT INTO "ContentContext" VALUES (6, 'Other');
INSERT INTO "ContentContext" VALUES (7, 'Unknown');

-- ----------------------------
-- Table structure for DemandSidePlatform
-- ----------------------------
DROP TABLE IF EXISTS "DemandSidePlatform";
CREATE TABLE "DemandSidePlatform" (
  "DemandSidePlatformId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "DemandSidePlatformName" TEXT NOT NULL
);

-- ----------------------------
-- Records of DemandSidePlatform
-- ----------------------------
INSERT INTO "DemandSidePlatform" VALUES (1, 'Demand Side Platform 1');
INSERT INTO "DemandSidePlatform" VALUES (2, 'Demand Side Platform 2');
INSERT INTO "DemandSidePlatform" VALUES (3, 'Demand Side Platform 3');

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
  "ImpressionId" TEXT NOT NULL,
  "AdvertiseId" INTEGER NOT NULL,
  "Dated" INTEGER NOT NULL,
  "BidRequestId" INTEGER NOT NULL,
  "Price" REAL NOT NULL,
  "Currency" TEXT NOT NULL,
  PRIMARY KEY ("ImpressionId"),
  CONSTRAINT "AdvertiseIdFK" FOREIGN KEY ("AdvertiseId") REFERENCES "Advertise" ("AdvertiseId") ON DELETE NO ACTION ON UPDATE NO ACTION
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
-- Table structure for NoBidResponse
-- ----------------------------
DROP TABLE IF EXISTS "NoBidResponse";
CREATE TABLE "NoBidResponse" (
  "NoBidResponseId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "BidRequestId" INTEGER,
  "NoBidResponseTypeId" INTEGER,
  CONSTRAINT "NoBidResponseTypeIdFK" FOREIGN KEY ("NoBidResponseTypeId") REFERENCES "NoBidResponseType" ("NoBidResponseTypeId") ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT "BidRequestIdFK" FOREIGN KEY ("BidRequestId") REFERENCES "BidRequest" ("BidRequestId") ON DELETE CASCADE ON UPDATE CASCADE
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
-- Records of NoBidResponseType
-- ----------------------------
INSERT INTO "NoBidResponseType" VALUES (0, 'Unknown Error');
INSERT INTO "NoBidResponseType" VALUES (1, 'Technical Error');
INSERT INTO "NoBidResponseType" VALUES (2, 'Invalid Request');
INSERT INTO "NoBidResponseType" VALUES (3, 'Known Web Spider');
INSERT INTO "NoBidResponseType" VALUES (4, 'Suspected Non Human Traffic');
INSERT INTO "NoBidResponseType" VALUES (5, 'Cloud, Data Center or Proxy IP');
INSERT INTO "NoBidResponseType" VALUES (6, 'Unsupported Device');
INSERT INTO "NoBidResponseType" VALUES (7, 'Blocked Publisher or Site');
INSERT INTO "NoBidResponseType" VALUES (8, 'Unmatched User');

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
-- Records of sqlite_sequence
-- ----------------------------
INSERT INTO "sqlite_sequence" VALUES ('DemandSidePlatform', 3);
INSERT INTO "sqlite_sequence" VALUES ('ContentContext', 7);
INSERT INTO "sqlite_sequence" VALUES ('NoBidResponse', 0);
INSERT INTO "sqlite_sequence" VALUES ('NoBidResponseType', 8);
INSERT INTO "sqlite_sequence" VALUES ('LostBid', 0);
INSERT INTO "sqlite_sequence" VALUES ('BidRequest', 0);
INSERT INTO "sqlite_sequence" VALUES ('Advertise', 0);
INSERT INTO "sqlite_sequence" VALUES ('BidResponse', 0);
INSERT INTO "sqlite_sequence" VALUES ('Campaign', 2);
INSERT INTO "sqlite_sequence" VALUES ('CampaignTargetCity', 0);
INSERT INTO "sqlite_sequence" VALUES ('CampaignTargetSite', 0);
INSERT INTO "sqlite_sequence" VALUES ('Transaction', 0);

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
-- Auto increment value for LostBid
-- ----------------------------

-- ----------------------------
-- Auto increment value for NoBidResponse
-- ----------------------------

-- ----------------------------
-- Auto increment value for NoBidResponseType
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 8 WHERE name = 'NoBidResponseType';

-- ----------------------------
-- Auto increment value for Transaction
-- ----------------------------

PRAGMA foreign_keys = true;
