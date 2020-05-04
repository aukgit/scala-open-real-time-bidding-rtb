/*
 Navicat Premium Data Transfer

 Source Server         : rtb
 Source Server Type    : SQLite
 Source Server Version : 3030001
 Source Schema         : main

 Target Server Type    : SQLite
 Target Server Version : 3030001
 File Encoding         : 65001

 Date: 04/05/2020 15:14:44
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
  "IsBanner" integer(1) DEFAULT 0,
  "IsVideo" INTEGER(1) NOT NULL DEFAULT 0,
  "ImpressionCount" INTEGER NOT NULL DEFAULT 0,
  "Height" integer NOT NULL DEFAULT 0,
  "Width" integer NOT NULL DEFAULT 0,
  "MinHeight" integer NOT NULL DEFAULT 0,
  "MinWidth" integer NOT NULL DEFAULT 0,
  "MaxHeight" integer NOT NULL DEFAULT 0,
  "MaxWidth" integer NOT NULL DEFAULT 0,
  "HasAgeRestriction" INTEGER(1) NOT NULL,
  "MinAge" INTEGER DEFAULT 0,
  "MaxAge" INTEGER DEFAULT 0,
  "CreatedDate" real,
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
  "IsPrecachedAdvertiseServed" integer(1) NOT NULL DEFAULT 0,
  "WinningPrice" REAL NOT NULL DEFAULT -1,
  "Currency" TEXT NOT NULL DEFAULT "USD",
  "CreatedDated" real NOT NULL,
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
-- Records of BannerAdvertiseType
-- ----------------------------
INSERT INTO "BannerAdvertiseType" VALUES (1, 'XHTML Text Ad');
INSERT INTO "BannerAdvertiseType" VALUES (2, 'XHTML Banner Ad. (Usually movile)');
INSERT INTO "BannerAdvertiseType" VALUES (3, 'Javascript Ad; must be valid XHTML');
INSERT INTO "BannerAdvertiseType" VALUES (4, 'iframe');

-- ----------------------------
-- Table structure for Bid
-- ----------------------------
DROP TABLE IF EXISTS "Bid";
CREATE TABLE "Bid" (
  "BidId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "BidRawJson" TEXT DEFAULT NULL,
  "DealBiddingPrice" real DEFAULT 0,
  "ActualWiningPrice" REAL DEFAULT 0,
  "IsImpressionServedOrWonByAuction" integer(1) DEFAULT 0,
  "SeatBidId" INTEGER NOT NULL,
  "CampaignId" INTEGER DEFAULT NULL,
  "ImpressionId" INTEGER DEFAULT NULL,
  "AdvertiseId" INTEGER DEFAULT NULL,
  "CreativeAttributeId" INTEGER DEFAULT NULL,
  "Adm" text DEFAULT NULL,
  "NUrl" text DEFAULT NULL,
  "IUrl" TEXT DEFAULT NULL,
  "Height" integer DEFAULT NULL,
  "Width" integer DEFAULT NULL,
  "CreatedDate" NUMERIC DEFAULT (datetime('now','utc')),
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
  "BidId" integer NOT NULL,
  "ContentCategoryId" text NOT NULL,
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
  "IsBanner" integer(1) NOT NULL DEFAULT 0,
  "IsVideo" integer(1) NOT NULL DEFAULT 0,
  "Height" integer DEFAULT NULL,
  "Width" integer DEFAULT NULL,
  "Countries" TEXT DEFAULT NULL,
  "Cities" TEXT DEFAULT NULL,
  "TargetedSites" TEXT DEFAULT NULL,
  "TargetedCities" TEXT DEFAULT NULL,
  "RawBidRequestJson" TEXT NOT NULL DEFAULT '',
  "Currency" TEXT(5) DEFAULT 'USD',
  "ContentContextId" INTEGER DEFAULT NULL,
  "IsWonTheAuction" integer(1) NOT NULL DEFAULT 0,
  "CreatedDate" NUMERIC NOT NULL DEFAULT (datetime('now','utc')),
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
  "Currency" TEXT NOT NULL DEFAULT 'USD',
  "BidRequestId" INTEGER DEFAULT NULL,
  "IsAnyBidWonTheAuction" integer(1) NOT NULL DEFAULT 0,
  "IsAuctionOccured" integer(1) NOT NULL DEFAULT 0,
  "IsPreCachedBidServed" integer(1) NOT NULL DEFAULT 0,
  "IsSendNoBidResponse" integer(1) NOT NULL DEFAULT 0,
  "NoBidResponseTypeId" INTEGER DEFAULT NULL,
  "CreatedDate" real DEFAULT NULL,
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
  "ContentCategoryId" text DEFAULT NULL,
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
  "ExpectedUserGender" TEXT(2) DEFAULT NULL,
  "PublisherId" INTEGER DEFAULT NULL,
  "CreatedDate" NUMERIC NOT NULL DEFAULT (datetime('now','utc')),
  "ModifiedDate" NUMERIC NOT NULL DEFAULT (datetime('now','utc')),
  CONSTRAINT "DemandSidePlatformIdFK" FOREIGN KEY ("DemandSidePlatformId") REFERENCES "DemandSidePlatform" ("DemandSidePlatformId") ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT "ContentCategoryIdFK" FOREIGN KEY ("ContentCategoryId") REFERENCES "ContentCategory" ("ContentCategoryId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "PublisherIdCampaignFK" FOREIGN KEY ("PublisherId") REFERENCES "Publisher" ("PublisherId") ON DELETE SET NULL ON UPDATE SET NULL,
  UNIQUE ("CampaignId" ASC)
);

-- ----------------------------
-- Records of Campaign
-- ----------------------------
INSERT INTO "Campaign" VALUES (1, 'First Cricket Campaign', 'IAB17', 10.0, 0.0, 10.0, 0.0, 0.0, 0, 1, 1, 999, 0, '', 1, '2020-05-04 03:03:46', '2020-05-04 03:03:46');
INSERT INTO "Campaign" VALUES (2, 'Business Campaing', 'IAB3', 5.0, 0.0, 5.0, 0.0, 0.0, 0, 2, 1, 999, 0, '', 2, '2020-05-04 03:03:46', '2020-05-04 03:03:46');

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
-- Table structure for CampaignTargetOperatingSystem
-- ----------------------------
DROP TABLE IF EXISTS "CampaignTargetOperatingSystem";
CREATE TABLE "CampaignTargetOperatingSystem" (
  "CampaignTargetOperatingSystemId" integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  "CampaignTargetOperatingSystem" TEXT NOT NULL,
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
-- Table structure for CreativeAttribute
-- ----------------------------
DROP TABLE IF EXISTS "CreativeAttribute";
CREATE TABLE "CreativeAttribute" (
  "CreativeAttributeId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "CreativeAttributeDescription" TEXT
);

-- ----------------------------
-- Records of CreativeAttribute
-- ----------------------------
INSERT INTO "CreativeAttribute" VALUES (1, 'Audio Ad (Auto-Play)');
INSERT INTO "CreativeAttribute" VALUES (2, 'Audio Ad (User Initiated)');
INSERT INTO "CreativeAttribute" VALUES (3, 'Expandable (Automatic)');
INSERT INTO "CreativeAttribute" VALUES (4, 'Expandable (User Initiated - Click)');
INSERT INTO "CreativeAttribute" VALUES (5, 'Expandable (User Initiated - Rollover)');
INSERT INTO "CreativeAttribute" VALUES (6, 'In-Banner Video Ad (Auto-Play)');
INSERT INTO "CreativeAttribute" VALUES (7, 'In-Banner Video Ad (User Initiated)');
INSERT INTO "CreativeAttribute" VALUES (8, 'Pop (e.g., Over, Under, or Upon Exit)');
INSERT INTO "CreativeAttribute" VALUES (9, 'Provocative or Suggestive Imagery');
INSERT INTO "CreativeAttribute" VALUES (10, 'Shaky, Flashing, Flickering, Extreme Animation, Smileys');
INSERT INTO "CreativeAttribute" VALUES (11, 'Surveys');
INSERT INTO "CreativeAttribute" VALUES (12, 'Text Only');
INSERT INTO "CreativeAttribute" VALUES (13, 'User Interactive (e.g., Embedded Games)');
INSERT INTO "CreativeAttribute" VALUES (14, 'Windows Dialog or Alert Style');
INSERT INTO "CreativeAttribute" VALUES (15, 'Has Audio On/Off Button');
INSERT INTO "CreativeAttribute" VALUES (16, 'Ad Can be Skipped (e.g., Skip Button on Pre-Roll Video)');

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
-- Table structure for DeviceType
-- ----------------------------
DROP TABLE IF EXISTS "DeviceType";
CREATE TABLE "DeviceType" (
  "DeviceTypeId" INTEGER NOT NULL,
  "Device TypeDescription" TEXT NOT NULL,
  PRIMARY KEY ("DeviceTypeId")
);

-- ----------------------------
-- Records of DeviceType
-- ----------------------------
INSERT INTO "DeviceType" VALUES (1, 'Mobile/Tablet');
INSERT INTO "DeviceType" VALUES (2, 'Personal Computer');
INSERT INTO "DeviceType" VALUES (3, 'Connected TV');
INSERT INTO "DeviceType" VALUES (4, 'Phone');
INSERT INTO "DeviceType" VALUES (5, 'Tablet');
INSERT INTO "DeviceType" VALUES (6, 'Connected Device');
INSERT INTO "DeviceType" VALUES (7, 'Set Top Box');

-- ----------------------------
-- Table structure for GeoMapping
-- ----------------------------
DROP TABLE IF EXISTS "GeoMapping";
CREATE TABLE "GeoMapping" (
  "GeoMappingId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "AdvertiseId" INTEGER DEFAULT NULL,
  "Country" TEXT DEFAULT NULL,
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
  "RawImpressionJson" TEXT NOT NULL DEFAULT "",
  "BidId" INTEGER DEFAULT NULL,
  "IsImpressionServedOrWonByAuction" integer(1) DEFAULT 0,
  "Bidfloor" real DEFAULT 0,
  "BidfloorCur" TEXT DEFAULT "USD",
  "Hash" TEXT DEFAULT NULL,
  "DisplayedDate" real DEFAULT 0,
  "CreatedDate" real NOT NULL DEFAULT 0,
  CONSTRAINT "BidIdFK" FOREIGN KEY ("BidId") REFERENCES "Bid" ("BidId") ON DELETE NO ACTION ON UPDATE NO ACTION
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
  "DatabaseTransactionTypeId" integer,
  "CreatedDate" NUMERIC DEFAULT (datetime('now','utc'))
);

-- ----------------------------
-- Records of LogTrace
-- ----------------------------
INSERT INTO "LogTrace" VALUES (1, 'Sample Method', NULL, NULL, NULL, NULL, NULL, '2020-05-04 02:16:15');

-- ----------------------------
-- Table structure for LostBid
-- ----------------------------
DROP TABLE IF EXISTS "LostBid";
CREATE TABLE "LostBid" (
  "LostBidId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "BidId" INTEGER,
  "Reason" TEXT,
  "LosingPrice" real,
  "Currency" TEXT NOT NULL DEFAULT 'USD',
  "DemandSidePlatformId" integer NOT NULL,
  "CreatedDate" NUMERIC DEFAULT (datetime('now','utc')),
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
-- Table structure for PrivateMarketPlaceDeal
-- ----------------------------
DROP TABLE IF EXISTS "PrivateMarketPlaceDeal";
CREATE TABLE "PrivateMarketPlaceDeal" (
  "PrivateMarketPlaceDealId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "ImpressionId" INTEGER DEFAULT NULL,
  "BidRequestId" INTEGER DEFAULT NULL,
  "BidFloor" real DEFAULT 0,
  "BidFloorCurrency" TEXT(3) DEFAULT "USD"
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
-- Records of Publisher
-- ----------------------------
INSERT INTO "Publisher" VALUES (1, 'Alim Advertise Server', 'alim1.com', 'alim address', 1);
INSERT INTO "Publisher" VALUES (2, 'Alim Advertise Server 2', 'alim2.com', 'alim 2 address', 2);
INSERT INTO "Publisher" VALUES (3, 'Alim Advertise Server 3', 'alim3.com', 'alim 3 address', 3);

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
  "IsGroupBid" integer(1) DEFAULT 0,
  "SeatBidRawJson" TEXT DEFAULT NULL,
  "CreatedDate" NUMERIC DEFAULT (datetime('now','utc')),
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
  "Spend" real NOT NULL,
  "ImpressionId" INTEGER NOT NULL,
  "Currency" TEXT NOT NULL DEFAULT 'USD',
  "CreatedDate" NUMERIC NOT NULL DEFAULT (datetime('now','utc')),
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
  "lat" real DEFAULT NULL,
  "lon" real DEFAULT NULL,
  "IsWhiteList" integer(1) DEFAULT 0,
  "IsBlackList" integer(1) DEFAULT 0
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
-- Records of VideoPlaybackMethod
-- ----------------------------
INSERT INTO "VideoPlaybackMethod" VALUES (1, 'Auto-Play Sound On');
INSERT INTO "VideoPlaybackMethod" VALUES (2, 'Auto-Play Sound Off');
INSERT INTO "VideoPlaybackMethod" VALUES (3, 'Click-to-Play');
INSERT INTO "VideoPlaybackMethod" VALUES (4, 'Mouse-Over');

-- ----------------------------
-- Table structure for VideoResponseProtocol
-- ----------------------------
DROP TABLE IF EXISTS "VideoResponseProtocol";
CREATE TABLE "VideoResponseProtocol" (
  "VideoResponseProtocolId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "VideoResponseProtocolDescription" TEXT NOT NULL
);

-- ----------------------------
-- Records of VideoResponseProtocol
-- ----------------------------
INSERT INTO "VideoResponseProtocol" VALUES (1, 'VAST 1.0');
INSERT INTO "VideoResponseProtocol" VALUES (2, 'VAST 2.0');
INSERT INTO "VideoResponseProtocol" VALUES (3, 'VAST 3.0');
INSERT INTO "VideoResponseProtocol" VALUES (4, 'VAST 1.0 Wrapper');
INSERT INTO "VideoResponseProtocol" VALUES (5, 'VAST 2.0 Wrapper');
INSERT INTO "VideoResponseProtocol" VALUES (6, 'VAST 3.0 Wrapper');

-- ----------------------------
-- Table structure for _BidRequest_old_20200504
-- ----------------------------
DROP TABLE IF EXISTS "_BidRequest_old_20200504";
CREATE TABLE "_BidRequest_old_20200504" (
  "BidRequestId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "DemandSidePlatformId" INTEGER NOT NULL,
  "AuctionId" INTEGER DEFAULT NULL,
  "IsBanner" integer(1) NOT NULL DEFAULT 0,
  "IsVideo" integer(1) NOT NULL DEFAULT 0,
  "Height" integer DEFAULT NULL,
  "Width" integer DEFAULT NULL,
  "Countries" TEXT DEFAULT NULL,
  "Cities" TEXT DEFAULT NULL,
  "TargetedSites" TEXT DEFAULT NULL,
  "TargetedCities" TEXT DEFAULT NULL,
  "RawBidRequestJson" TEXT NOT NULL DEFAULT '',
  "Currency" TEXT(5) DEFAULT USD,
  "ContentContextId" INTEGER DEFAULT NULL,
  "IsWonTheAuction" integer(1) NOT NULL DEFAULT 0,
  "CreatedDate" real DEFAULT 0,
  CONSTRAINT "DemandSidePlatformIdFK" FOREIGN KEY ("DemandSidePlatformId") REFERENCES "DemandSidePlatform" ("DemandSidePlatformId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "AuctionIdFK" FOREIGN KEY ("AuctionId") REFERENCES "Auction" ("AuctionId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "ContentContextIdFK" FOREIGN KEY ("ContentContextId") REFERENCES "ContentContext" ("ContentContextId") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Table structure for _BidRequest_old_20200504_1
-- ----------------------------
DROP TABLE IF EXISTS "_BidRequest_old_20200504_1";
CREATE TABLE "_BidRequest_old_20200504_1" (
  "BidRequestId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "DemandSidePlatformId" INTEGER NOT NULL,
  "AuctionId" INTEGER DEFAULT NULL,
  "IsBanner" integer(1) NOT NULL DEFAULT 0,
  "IsVideo" integer(1) NOT NULL DEFAULT 0,
  "Height" integer DEFAULT NULL,
  "Width" integer DEFAULT NULL,
  "Countries" TEXT DEFAULT NULL,
  "Cities" TEXT DEFAULT NULL,
  "TargetedSites" TEXT DEFAULT NULL,
  "TargetedCities" TEXT DEFAULT NULL,
  "RawBidRequestJson" TEXT NOT NULL DEFAULT '',
  "Currency" TEXT(5) DEFAULT 'USD',
  "ContentContextId" INTEGER DEFAULT NULL,
  "IsWonTheAuction" integer(1) NOT NULL DEFAULT 0,
  "CreatedDate" real DEFAULT 0,
  CONSTRAINT "DemandSidePlatformIdFK" FOREIGN KEY ("DemandSidePlatformId") REFERENCES "DemandSidePlatform" ("DemandSidePlatformId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "AuctionIdFK" FOREIGN KEY ("AuctionId") REFERENCES "Auction" ("AuctionId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "ContentContextIdFK" FOREIGN KEY ("ContentContextId") REFERENCES "ContentContext" ("ContentContextId") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Table structure for _BidRequest_old_20200504_2
-- ----------------------------
DROP TABLE IF EXISTS "_BidRequest_old_20200504_2";
CREATE TABLE "_BidRequest_old_20200504_2" (
  "BidRequestId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "DemandSidePlatformId" INTEGER NOT NULL,
  "AuctionId" INTEGER DEFAULT NULL,
  "IsBanner" integer(1) NOT NULL DEFAULT 0,
  "IsVideo" integer(1) NOT NULL DEFAULT 0,
  "Height" integer DEFAULT NULL,
  "Width" integer DEFAULT NULL,
  "Countries" TEXT DEFAULT NULL,
  "Cities" TEXT DEFAULT NULL,
  "TargetedSites" TEXT DEFAULT NULL,
  "TargetedCities" TEXT DEFAULT NULL,
  "RawBidRequestJson" TEXT NOT NULL DEFAULT '',
  "Currency" TEXT(5) DEFAULT 'USD',
  "ContentContextId" INTEGER DEFAULT NULL,
  "IsWonTheAuction" integer(1) NOT NULL DEFAULT 0,
  "CreatedDate" NUMERIC DEFAULT (datetime('now','utc')),
  CONSTRAINT "DemandSidePlatformIdFK" FOREIGN KEY ("DemandSidePlatformId") REFERENCES "DemandSidePlatform" ("DemandSidePlatformId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "AuctionIdFK" FOREIGN KEY ("AuctionId") REFERENCES "Auction" ("AuctionId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "ContentContextIdFK" FOREIGN KEY ("ContentContextId") REFERENCES "ContentContext" ("ContentContextId") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Table structure for _BidResponse_old_20200504
-- ----------------------------
DROP TABLE IF EXISTS "_BidResponse_old_20200504";
CREATE TABLE "_BidResponse_old_20200504" (
  "BidResponseId" integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  "Currency" TEXT NOT NULL DEFAULT "USD",
  "BidRequestId" INTEGER DEFAULT NULL,
  "IsAnyBidWonTheAuction" integer(1) NOT NULL DEFAULT 0,
  "IsAuctionOccured" integer(1) NOT NULL DEFAULT 0,
  "IsPreCachedBidServed" integer(1) NOT NULL DEFAULT 0,
  "IsSendNoBidResponse" integer(1) NOT NULL DEFAULT 0,
  "NoBidResponseTypeId" INTEGER DEFAULT NULL,
  "CreatedDate" real DEFAULT NULL,
  CONSTRAINT "BidRequestIdFK" FOREIGN KEY ("BidRequestId") REFERENCES "BidRequest" ("BidRequestId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "NoBidResponseTypeIdFK" FOREIGN KEY ("NoBidResponseTypeId") REFERENCES "NoBidResponseType" ("NoBidResponseTypeId") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Table structure for _BidResponse_old_20200504_1
-- ----------------------------
DROP TABLE IF EXISTS "_BidResponse_old_20200504_1";
CREATE TABLE "_BidResponse_old_20200504_1" (
  "BidResponseId" integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  "Currency" TEXT NOT NULL DEFAULT USD,
  "BidRequestId" INTEGER DEFAULT NULL,
  "IsAnyBidWonTheAuction" integer(1) NOT NULL DEFAULT 0,
  "IsAuctionOccured" integer(1) NOT NULL DEFAULT 0,
  "IsPreCachedBidServed" integer(1) NOT NULL DEFAULT 0,
  "IsSendNoBidResponse" integer(1) NOT NULL DEFAULT 0,
  "NoBidResponseTypeId" INTEGER DEFAULT NULL,
  "CreatedDate" real DEFAULT NULL,
  CONSTRAINT "BidRequestIdFK" FOREIGN KEY ("BidRequestId") REFERENCES "BidRequest" ("BidRequestId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "NoBidResponseTypeIdFK" FOREIGN KEY ("NoBidResponseTypeId") REFERENCES "NoBidResponseType" ("NoBidResponseTypeId") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Table structure for _BidResponse_old_20200504_2
-- ----------------------------
DROP TABLE IF EXISTS "_BidResponse_old_20200504_2";
CREATE TABLE "_BidResponse_old_20200504_2" (
  "BidResponseId" integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  "Currency" TEXT NOT NULL DEFAULT '',
  "BidRequestId" INTEGER DEFAULT NULL,
  "IsAnyBidWonTheAuction" integer(1) NOT NULL DEFAULT 0,
  "IsAuctionOccured" integer(1) NOT NULL DEFAULT 0,
  "IsPreCachedBidServed" integer(1) NOT NULL DEFAULT 0,
  "IsSendNoBidResponse" integer(1) NOT NULL DEFAULT 0,
  "NoBidResponseTypeId" INTEGER DEFAULT NULL,
  "CreatedDate" real DEFAULT NULL,
  CONSTRAINT "BidRequestIdFK" FOREIGN KEY ("BidRequestId") REFERENCES "BidRequest" ("BidRequestId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "NoBidResponseTypeIdFK" FOREIGN KEY ("NoBidResponseTypeId") REFERENCES "NoBidResponseType" ("NoBidResponseTypeId") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Table structure for _BidResponse_old_20200504_3
-- ----------------------------
DROP TABLE IF EXISTS "_BidResponse_old_20200504_3";
CREATE TABLE "_BidResponse_old_20200504_3" (
  "BidResponseId" integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  "Currency" TEXT NOT NULL DEFAULT NULL,
  "BidRequestId" INTEGER DEFAULT NULL,
  "IsAnyBidWonTheAuction" integer(1) NOT NULL DEFAULT 0,
  "IsAuctionOccured" integer(1) NOT NULL DEFAULT 0,
  "IsPreCachedBidServed" integer(1) NOT NULL DEFAULT 0,
  "IsSendNoBidResponse" integer(1) NOT NULL DEFAULT 0,
  "NoBidResponseTypeId" INTEGER DEFAULT NULL,
  "CreatedDate" real DEFAULT NULL,
  CONSTRAINT "BidRequestIdFK" FOREIGN KEY ("BidRequestId") REFERENCES "BidRequest" ("BidRequestId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "NoBidResponseTypeIdFK" FOREIGN KEY ("NoBidResponseTypeId") REFERENCES "NoBidResponseType" ("NoBidResponseTypeId") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Table structure for _Bid_old_20200504
-- ----------------------------
DROP TABLE IF EXISTS "_Bid_old_20200504";
CREATE TABLE "_Bid_old_20200504" (
  "BidId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "BidRawJson" TEXT DEFAULT NULL,
  "DealBiddingPrice" real DEFAULT 0,
  "ActualWiningPrice" REAL DEFAULT 0,
  "IsImpressionServedOrWonByAuction" integer(1) DEFAULT 0,
  "SeatBidId" INTEGER NOT NULL,
  "CampaignId" INTEGER DEFAULT NULL,
  "ImpressionId" INTEGER DEFAULT NULL,
  "AdvertiseId" INTEGER DEFAULT NULL,
  "CreativeAttributeId" INTEGER DEFAULT NULL,
  "Adm" text DEFAULT NULL,
  "NUrl" text DEFAULT NULL,
  "IUrl" TEXT DEFAULT NULL,
  "Height" integer DEFAULT NULL,
  "Width" integer DEFAULT NULL,
  "CreatedDate" real DEFAULT 0,
  CONSTRAINT "CampaignIdFK" FOREIGN KEY ("CampaignId") REFERENCES "Campaign" ("CampaignId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "AdvertiseIdFK" FOREIGN KEY ("AdvertiseId") REFERENCES "Advertise" ("AdvertiseId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "ImpressionIdFk" FOREIGN KEY ("ImpressionId") REFERENCES "Impression" ("ImpressionId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "SeatBidIdFK" FOREIGN KEY ("SeatBidId") REFERENCES "SeatBid" ("SeatBidId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "CreativeAttributeIdFK" FOREIGN KEY ("CreativeAttributeId") REFERENCES "CreativeAttribute" ("CreativeAttributeId") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Table structure for _LogTrace_old_20200504
-- ----------------------------
DROP TABLE IF EXISTS "_LogTrace_old_20200504";
CREATE TABLE "_LogTrace_old_20200504" (
  "LogTraceId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "MethodName" TEXT,
  "ClassName" TEXT,
  "Request" TEXT,
  "Message" TEXT,
  "EntityData" TEXT,
  "DatabaseTransactionTypeId" integer,
  "CreatedDate" real
);

-- ----------------------------
-- Table structure for _LostBid_old_20200504
-- ----------------------------
DROP TABLE IF EXISTS "_LostBid_old_20200504";
CREATE TABLE "_LostBid_old_20200504" (
  "LostBidId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "BidId" INTEGER,
  "Reason" TEXT,
  "LosingPrice" real,
  "CreatedDate" real,
  "DemandSidePlatformId" integer NOT NULL,
  CONSTRAINT "BidIdFK" FOREIGN KEY ("BidId") REFERENCES "Bid" ("BidId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "DemandSidePlatformFK" FOREIGN KEY ("DemandSidePlatformId") REFERENCES "DemandSidePlatform" ("DemandSidePlatformId") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Table structure for _LostBid_old_20200504_1
-- ----------------------------
DROP TABLE IF EXISTS "_LostBid_old_20200504_1";
CREATE TABLE "_LostBid_old_20200504_1" (
  "LostBidId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "BidId" INTEGER,
  "Reason" TEXT,
  "LosingPrice" real,
  "DemandSidePlatformId" integer NOT NULL,
  "CreatedDate" NUMERIC DEFAULT (datetime('now','utc')),
  CONSTRAINT "BidIdFK" FOREIGN KEY ("BidId") REFERENCES "Bid" ("BidId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "DemandSidePlatformFK" FOREIGN KEY ("DemandSidePlatformId") REFERENCES "DemandSidePlatform" ("DemandSidePlatformId") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Table structure for _SeatBid_old_20200504
-- ----------------------------
DROP TABLE IF EXISTS "_SeatBid_old_20200504";
CREATE TABLE "_SeatBid_old_20200504" (
  "SeatBidId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "BidRequestId" INTEGER DEFAULT NULL,
  "BidResponseId" INTEGER DEFAULT NULL,
  "AuctionId" INTEGER DEFAULT NULL,
  "DemandSidePlatformId" INTEGER DEFAULT NULL,
  "IsGroupBid" integer(1) DEFAULT 0,
  "SeatBidRawJson" TEXT DEFAULT NULL,
  "CreatedDate" real DEFAULT 0,
  CONSTRAINT "BidRequestIdFK" FOREIGN KEY ("BidRequestId") REFERENCES "BidRequest" ("BidRequestId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "BidResponseIdFK" FOREIGN KEY ("BidResponseId") REFERENCES "BidResponse" ("BidResponseId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "AuctionIdFK" FOREIGN KEY ("AuctionId") REFERENCES "Auction" ("AuctionId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "DemandSidePlatformIdFK" FOREIGN KEY ("DemandSidePlatformId") REFERENCES "DemandSidePlatform" ("DemandSidePlatformId") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Table structure for _Transaction_old_20200504
-- ----------------------------
DROP TABLE IF EXISTS "_Transaction_old_20200504";
CREATE TABLE "_Transaction_old_20200504" (
  "TransactionId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "AdvertiseId" INTEGER NOT NULL,
  "Spend" real NOT NULL,
  "ImpressionId" INTEGER NOT NULL,
  "Currency" TEXT NOT NULL,
  "CreatedDate" real,
  CONSTRAINT "AdvertiseIdFK" FOREIGN KEY ("AdvertiseId") REFERENCES "Advertise" ("AdvertiseId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "ImpressionIdFK" FOREIGN KEY ("ImpressionId") REFERENCES "Impression" ("ImpressionId") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Table structure for _Transaction_old_20200504_1
-- ----------------------------
DROP TABLE IF EXISTS "_Transaction_old_20200504_1";
CREATE TABLE "_Transaction_old_20200504_1" (
  "TransactionId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "AdvertiseId" INTEGER NOT NULL,
  "Spend" real NOT NULL,
  "ImpressionId" INTEGER NOT NULL,
  "Currency" TEXT NOT NULL DEFAULT 'USD',
  "CreatedDate" real,
  CONSTRAINT "AdvertiseIdFK" FOREIGN KEY ("AdvertiseId") REFERENCES "Advertise" ("AdvertiseId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "ImpressionIdFK" FOREIGN KEY ("ImpressionId") REFERENCES "Impression" ("ImpressionId") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Table structure for _Transaction_old_20200504_2
-- ----------------------------
DROP TABLE IF EXISTS "_Transaction_old_20200504_2";
CREATE TABLE "_Transaction_old_20200504_2" (
  "TransactionId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "AdvertiseId" INTEGER NOT NULL,
  "Spend" real NOT NULL,
  "ImpressionId" INTEGER NOT NULL,
  "Currency" TEXT NOT NULL DEFAULT 'USD',
  "CreatedDate" NUMERIC,
  CONSTRAINT "AdvertiseIdFK" FOREIGN KEY ("AdvertiseId") REFERENCES "Advertise" ("AdvertiseId") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "ImpressionIdFK" FOREIGN KEY ("ImpressionId") REFERENCES "Impression" ("ImpressionId") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Table structure for _Transaction_old_20200504_3
-- ----------------------------
DROP TABLE IF EXISTS "_Transaction_old_20200504_3";
CREATE TABLE "_Transaction_old_20200504_3" (
  "TransactionId" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "AdvertiseId" INTEGER NOT NULL,
  "Spend" real NOT NULL,
  "ImpressionId" INTEGER NOT NULL,
  "Currency" TEXT NOT NULL DEFAULT 'USD',
  "CreatedDate" NUMERIC DEFAULT (datetime('now','utc')),
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
INSERT INTO "sqlite_sequence" VALUES ('ContentContext', 7);
INSERT INTO "sqlite_sequence" VALUES ('DemandSidePlatform', 3);
INSERT INTO "sqlite_sequence" VALUES ('NoBidResponseType', 8);
INSERT INTO "sqlite_sequence" VALUES ('DemandSidePlatform', 3);
INSERT INTO "sqlite_sequence" VALUES ('ContentContext', 7);
INSERT INTO "sqlite_sequence" VALUES ('NoBidResponseType', 8);
INSERT INTO "sqlite_sequence" VALUES ('CampaignTargetCity', 0);
INSERT INTO "sqlite_sequence" VALUES ('CampaignTargetSite', 0);
INSERT INTO "sqlite_sequence" VALUES ('Publisher', 3);
INSERT INTO "sqlite_sequence" VALUES ('CampaignTargetOperatingSystem', 0);
INSERT INTO "sqlite_sequence" VALUES ('_Transaction_old_20200504', 0);
INSERT INTO "sqlite_sequence" VALUES ('_LogTrace_old_20200504', 0);
INSERT INTO "sqlite_sequence" VALUES ('BannerAdvertiseType', 4);
INSERT INTO "sqlite_sequence" VALUES ('Advertise', 0);
INSERT INTO "sqlite_sequence" VALUES ('_BidRequest_old_20200504', 0);
INSERT INTO "sqlite_sequence" VALUES ('GeoMapping', 0);
INSERT INTO "sqlite_sequence" VALUES ('_BidResponse_old_20200504', 0);
INSERT INTO "sqlite_sequence" VALUES ('_SeatBid_old_20200504', 0);
INSERT INTO "sqlite_sequence" VALUES ('CreativeAttribute', 16);
INSERT INTO "sqlite_sequence" VALUES ('VideoResponseProtocol', 6);
INSERT INTO "sqlite_sequence" VALUES ('VideoPlaybackMethod', 4);
INSERT INTO "sqlite_sequence" VALUES ('_LostBid_old_20200504', 0);
INSERT INTO "sqlite_sequence" VALUES ('_Bid_old_20200504', 0);
INSERT INTO "sqlite_sequence" VALUES ('BidContentCategoriesMapping', 0);
INSERT INTO "sqlite_sequence" VALUES ('Impression', 0);
INSERT INTO "sqlite_sequence" VALUES ('_BidResponse_old_20200504_1', 0);
INSERT INTO "sqlite_sequence" VALUES ('_BidResponse_old_20200504_2', 0);
INSERT INTO "sqlite_sequence" VALUES ('_BidResponse_old_20200504_3', 0);
INSERT INTO "sqlite_sequence" VALUES ('BidResponse', 0);
INSERT INTO "sqlite_sequence" VALUES ('_BidRequest_old_20200504_1', 0);
INSERT INTO "sqlite_sequence" VALUES ('_Transaction_old_20200504_1', 0);
INSERT INTO "sqlite_sequence" VALUES ('_Transaction_old_20200504_2', 0);
INSERT INTO "sqlite_sequence" VALUES ('_Transaction_old_20200504_3', 0);
INSERT INTO "sqlite_sequence" VALUES ('SeatBid', 0);
INSERT INTO "sqlite_sequence" VALUES ('_LostBid_old_20200504_1', 0);
INSERT INTO "sqlite_sequence" VALUES ('LostBid', 0);
INSERT INTO "sqlite_sequence" VALUES ('LogTrace', 1);
INSERT INTO "sqlite_sequence" VALUES ('Campaign', 2);
INSERT INTO "sqlite_sequence" VALUES ('_BidRequest_old_20200504_2', 0);
INSERT INTO "sqlite_sequence" VALUES ('Bid', 0);
INSERT INTO "sqlite_sequence" VALUES ('Transaction', 0);
INSERT INTO "sqlite_sequence" VALUES ('BidRequest', 0);

-- ----------------------------
-- View structure for BidRelatedIdsView
-- ----------------------------
DROP VIEW IF EXISTS "BidRelatedIdsView";
CREATE VIEW "BidRelatedIdsView" AS SELECT
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
	INNER JOIN
	Bid
	ON 
		Impression.BidId = Bid.BidId AND
		Impression.ImpressionId = Bid.ImpressionId
	INNER JOIN
	SeatBid
	ON 
		Bid.SeatBidId = SeatBid.SeatBidId
	INNER JOIN
	Auction
	ON 
		SeatBid.AuctionId = Auction.AuctionId;

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
	Advertise.ContentContextId
FROM
	Advertise
	INNER JOIN
	KeywordAdvertiseMapping
	ON 
		Advertise.AdvertiseId = KeywordAdvertiseMapping.AdvertiseId
	INNER JOIN
	Keyword
	ON 
		KeywordAdvertiseMapping.KeywordId = Keyword.KeywordId;

-- ----------------------------
-- View structure for WinningPriceInfoView
-- ----------------------------
DROP VIEW IF EXISTS "WinningPriceInfoView";
CREATE VIEW "WinningPriceInfoView" AS SELECT
	Bid.BidId, 
	Impression.ImpressionId, 	
	Bid.SeatBidId, 
	Bid.CampaignId, 	
	Auction.AuctionId, 
	SeatBid.BidRequestId, 
	SeatBid.BidResponseId, 	
	Bid.AdvertiseId, 
	SeatBid.DemandSidePlatformId,
	Auction.WinningPrice as AuctionWinningPrice, 	
	Bid.DealBiddingPrice, 
	Bid.ActualWiningPrice, 
	Auction.CreatedDated AS AuctionCreatedDate, 
	Bid.CreatedDate AS BiddingCreatedDate, 
	Impression.CreatedDate AS ImpressionCreatedDate, 
	Bid.IsImpressionServedOrWonByAuction AS IsWon, 
	SeatBid.IsGroupBid	
FROM
	Impression
	INNER JOIN
	Bid
	ON 
		Impression.BidId = Bid.BidId AND
		Impression.ImpressionId = Bid.ImpressionId
	INNER JOIN
	SeatBid
	ON 
		Bid.SeatBidId = SeatBid.SeatBidId
	INNER JOIN
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
-- Auto increment value for Bid
-- ----------------------------

-- ----------------------------
-- Auto increment value for BidContentCategoriesMapping
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
-- Auto increment value for CampaignTargetOperatingSystem
-- ----------------------------

-- ----------------------------
-- Auto increment value for CampaignTargetSite
-- ----------------------------

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
-- Auto increment value for GeoMapping
-- ----------------------------

-- ----------------------------
-- Auto increment value for Impression
-- ----------------------------

-- ----------------------------
-- Auto increment value for LogTrace
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 1 WHERE name = 'LogTrace';

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
-- Auto increment value for SeatBid
-- ----------------------------

-- ----------------------------
-- Auto increment value for Transaction
-- ----------------------------

-- ----------------------------
-- Auto increment value for VideoPlaybackMethod
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 4 WHERE name = 'VideoPlaybackMethod';

-- ----------------------------
-- Auto increment value for VideoResponseProtocol
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 6 WHERE name = 'VideoResponseProtocol';

-- ----------------------------
-- Auto increment value for _BidRequest_old_20200504
-- ----------------------------

-- ----------------------------
-- Auto increment value for _BidRequest_old_20200504_1
-- ----------------------------

-- ----------------------------
-- Auto increment value for _BidRequest_old_20200504_2
-- ----------------------------

-- ----------------------------
-- Auto increment value for _BidResponse_old_20200504
-- ----------------------------

-- ----------------------------
-- Auto increment value for _BidResponse_old_20200504_1
-- ----------------------------

-- ----------------------------
-- Auto increment value for _BidResponse_old_20200504_2
-- ----------------------------

-- ----------------------------
-- Auto increment value for _BidResponse_old_20200504_3
-- ----------------------------

-- ----------------------------
-- Auto increment value for _Bid_old_20200504
-- ----------------------------

-- ----------------------------
-- Auto increment value for _LogTrace_old_20200504
-- ----------------------------

-- ----------------------------
-- Auto increment value for _LostBid_old_20200504
-- ----------------------------

-- ----------------------------
-- Auto increment value for _LostBid_old_20200504_1
-- ----------------------------

-- ----------------------------
-- Auto increment value for _SeatBid_old_20200504
-- ----------------------------

-- ----------------------------
-- Auto increment value for _Transaction_old_20200504
-- ----------------------------

-- ----------------------------
-- Auto increment value for _Transaction_old_20200504_1
-- ----------------------------

-- ----------------------------
-- Auto increment value for _Transaction_old_20200504_2
-- ----------------------------

-- ----------------------------
-- Auto increment value for _Transaction_old_20200504_3
-- ----------------------------

PRAGMA foreign_keys = true;
