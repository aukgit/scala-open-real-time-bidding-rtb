
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

DROP VIEW "main"."BidRequestImpressionWithPlaceholderView";

CREATE VIEW "main"."BidRequestImpressionWithPlaceholderView" AS SELECT DISTINCT
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
	Impression.CreatedDateTimestamp, 
	ImpressionPlaceholder.IsMaxHeightWidthEmpty, 
	Impression.AdvertiseDisplayedDate, 
	Impression.ImpressionId, 
	Impression.Hash, 
	Impression.BidfloorCur, 
	Impression.Bidfloor, 
	Impression.IsImpressionServedOrWonByAuction, 
	Impression.ImpressionPlaceholderId, 
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
		Impression.ImpressionPlaceholderId = ImpressionPlaceholder.ImpressionPlaceholderId
	LEFT JOIN
	ContentContext
	ON 
		BidRequest.ContentContextId = ContentContext.ContentContextId ;