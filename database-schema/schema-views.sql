
DROP VIEW "main"."AdvertiseIsRunningView";

CREATE VIEW "main"."AdvertiseIsRunningView" AS SELECT
	Advertise.*, 
	Campaign.IsRunning
FROM
	Advertise
	INNER JOIN
	Campaign
	ON 
		Advertise.CampaignId = Campaign.CampaignId ;

DROP VIEW "main"."BidRelatedIdsView";

CREATE VIEW "main"."BidRelatedIdsView" AS SELECT DISTINCT
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
	LEFT JOIN Bid ON Impression.BidId = Bid.BidId 
	AND Impression.ImpressionId = Bid.ImpressionId
	LEFT JOIN SeatBid ON Bid.SeatBidId = SeatBid.SeatBidId
	LEFT JOIN Auction ON SeatBid.AuctionId = Auction.AuctionId ;

DROP VIEW "main"."KeywordAdvertiseMappingIdsView";

CREATE VIEW "main"."KeywordAdvertiseMappingIdsView" AS SELECT
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
		KeywordAdvertiseMapping.KeywordId = Keyword.KeywordId ;

DROP VIEW "main"."WinningPriceInfoView";

CREATE VIEW "main"."WinningPriceInfoView" AS SELECT
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
	Auction.CreatedDateTimestamp AS AuctionCreatedDateTimestamp, 
	Bid.CreatedDateTimestamp AS BiddingCreatedDateTimestamp, 
	Impression.CreatedDateTimestamp AS ImpressionCreatedDateTimestamp, 
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
		SeatBid.AuctionId = Auction.AuctionId ;