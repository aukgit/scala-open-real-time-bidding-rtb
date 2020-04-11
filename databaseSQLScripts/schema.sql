-- we don't know how to generate root <with-no-name> (class Root) :(
create table BannerAdvertiseType
(
	BannerAdvertiseTypeID INTEGER not null
		primary key autoincrement,
	TypeName TEXT not null
);

create table ContentCategory
(
	ContentCategoryID text not null
		primary key,
	ContentCategoryName TEXT not null
);

create table ContentContext
(
	ContentContextId INTEGER not null
		primary key autoincrement,
	Context TEXT not null
);

create table DemandSidePlatform
(
	DemandSidePlatformId INTEGER not null
		primary key autoincrement,
	DemandSidePlatformName TEXT not null
);

create table Campaign
(
	CampaignID INTEGER not null
		primary key autoincrement
		unique,
	CampaignName TEXT not null,
	ContentCategoryId text not null,
	TotalBudgetCPM real default 0 not null,
	SpendAlready real default 0 not null,
	RemainingAmount real default 0 not null,
	StartDate real default 0,
	EndDate real default 0,
	ImpressionCount integer default 0 not null,
	DemandSidePlatformId integer not null
		constraint DemandSidePlatformIdFK
			references DemandSidePlatform
				on update cascade on delete cascade,
	IsRunning integer(1) default 0 not null,
	Priority integer default 999 not null,
	IsRetrictToUserGender integer(1) default 0 not null,
	ExpectedUserGender TEXT(2),
	constraint ContentCategoryIDFK
		foreign key (ContentCategoryID) references ContentCategory
);

create table Advertise
(
	AdvertiseID INTEGER not null
		primary key autoincrement,
	CampaignID INTEGER not null
		constraint CampaignFK
			references Campaign
				on update cascade on delete cascade,
	BannerAdvertiseTypeID INTEGER not null
		constraint BannerAdvertiseTypeIDFK
			references BannerAdvertiseType
				on update cascade on delete cascade,
	AdvertiseTitle TEXT not null,
	ContentContextId INTEGER not null
		constraint ContentContextIdFK
			references ContentContext
				on update cascade on delete cascade,
	BidUrl TEXT not null,
	IFrameHtml TEXT,
	IsCountrySpecific INTEGER(1) default 0 not null,
	IsVideo INTEGER(1) default 0 not null,
	ImpressionCount INTEGER default 0 not null,
	Height REAL default 0 not null,
	Weight REAL default 0 not null,
	MinHeight REAL default 0 not null,
	MinWeight REAL default 0 not null,
	MaxHeight REAL default 0 not null,
	MaxWeight REAL default 0 not null,
	HasAgeRestriction INTEGER(1) not null,
	MinAge INTEGER default 0,
	MaxAge INTEGER default 0
);

create table Auction
(
	AuctionId INTEGER not null
		primary key autoincrement,
	AdvertiseId INTEGER not null
		constraint AdvertiseIdFK
			references Advertise,
	Dated real not null,
	WinningBidderId INTEGER,
	IsPrecachedAdvertiseServed integer(1) default 0 not null,
	WinningPrice REAL default -1 not null,
	Currency TEXT default USD not null
);

create table BidRequest
(
	BidRequestId INTEGER not null
		primary key autoincrement,
	DemandSidePlatformID INTEGER not null
		constraint DemandSidePlatformIdFK
			references DemandSidePlatform,
	AuctionId INTEGER not null
		constraint AuctionIdFK
			references Auction
				on update cascade on delete cascade,
	IsBanner integer(1) default 0 not null,
	IsVideo integer(1) default 0 not null,
	Height integer,
	Width integer,
	Countries TEXT,
	Cities TEXT,
	TargetedSites TEXT,
	TargetedCities TEXT,
	RawBidRequest TEXT,
	Price1 real default 0 not null,
	Price2 real default 0 not null,
	Price3 real default 0 not null,
	Currency TEXT(5) default USD,
	ContentContextId INTEGER
		constraint ContentContextIdFK
			references ContentContext,
	IsWonTheAuction integer(1) default 0 not null
);

alter table Auction
	add constraint WinningBidderIdFK
		foreign key (WinningBidderId) references BidRequest
			deferrable initially deferred;

create table BidResponse
(
	BidResponseId integer not null
		primary key autoincrement,
	Price REAL not null,
	Currency TEXT not null,
	Adm text,
	NUrl text not null,
	IUrl TEXT,
	AdvertiseID INTEGER not null
		constraint AdvertiseIDFK
			references Advertise
				on update cascade on delete cascade,
	BidRequestID INTEGER
		constraint BidRequestIDFK
			references BidRequest
);

create table CampaignTargetCity
(
	CampaignTargetCityId integer not null
		primary key autoincrement,
	CampaignTargetCity TEXT not null,
	CampaignId integer not null,
	constraint CampaignIDFK
		foreign key (CampaignID) references Campaign
			on update cascade on delete cascade
);

create table CampaignTargetSite
(
	CampaignTargetSiteId integer not null
		primary key autoincrement,
	CampaignTargetSite TEXT not null,
	CampaignId integer not null,
	constraint CampaignIDFK
		foreign key (CampaignID) references Campaign
			on update cascade on delete cascade
);

create table GeoMapping
(
	GeoMappingId INTEGER not null
		primary key autoincrement,
	AdvertiseId INTEGER not null
		constraint AdvertiseIdFK
			references Advertise
				on update cascade on delete cascade,
	Country TEXT not null,
	HasCity integer(1) default 0 not null,
	City TEXT default NULL
);

create table Impression
(
	ImpressionID TEXT not null
		primary key,
	AdvertiseId INTEGER not null,
	Dated INTEGER not null,
	BidRequestId INTEGER not null,
	Price REAL not null,
	Currency TEXT not null,
	constraint AdvertiseIDFK
		foreign key (AdvertiseID) references Advertise
);

create table LostBid
(
	LostBidId INTEGER not null
		primary key autoincrement,
	BidRequestId INTEGER not null
		constraint BidRequestIdFK
			references BidRequest,
	Reason TEXT
);

create table NoBidResponseType
(
	NoBidResponseTypeId INTEGER not null
		primary key autoincrement,
	NoBidResponseType TEXT
);

create table NoBidResponse
(
	NoBidResponseId INTEGER not null
		primary key autoincrement,
	BidRequestID INTEGER
		constraint BidRequestIDFK
			references BidRequest
				on update cascade on delete cascade,
	NoBidResponseTypeId INTEGER
		constraint NoBidResponseTypeIdFK
			references NoBidResponseType
				on update cascade on delete cascade
);

create table "Transaction"
(
	TransactionId INTEGER not null
		primary key autoincrement,
	AdvertiseId INTEGER not null,
	Spend real not null,
	ImpressionId INTEGER not null,
	Currency TEXT not null,
	constraint AdvertiseIDFK
		foreign key (AdvertiseID) references Advertise,
	constraint ImpressionIDFK
		foreign key (ImpressionID) references Impression
);

