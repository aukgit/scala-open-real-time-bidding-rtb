create table BannerAdvertiseType
(
    BannerAdvertiseTypeID INTEGER not null
        primary key autoincrement,
    TypeName              TEXT    not null
);

create table ContentCategory
(
    ContentCategoryID   text not null
        primary key,
    ContentCategoryName TEXT not null
);

create table ContentContext
(
    ContentContextId INTEGER not null
        primary key autoincrement,
    Context          TEXT    not null
);

create table DemandSidePlatform
(
    DemandSidePlatformId   INTEGER not null
        primary key autoincrement,
    DemandSidePlatformName TEXT    not null
);

create table BidRequest
(
    BidRequestId         INTEGER not null
        primary key autoincrement,
    DemandSidePlatformID INTEGER not null
        constraint DemandSidePlatformIdFK
            references DemandSidePlatform,
    IsBanner             integer default 0 not null,
    IsVideo              integer default 0 not null,
    Height               integer,
    Width                integer,
    Countries            TEXT,
    Cities               TEXT,
    TargetedSites        TEXT,
    TargetedCities       TEXT,
    RawBidRequest        TEXT
);

create table Campaign
(
    CampaignID            INTEGER not null
        primary key autoincrement,
    CampaignName          TEXT    not null,
    ContentCategoryID     text    not null
        constraint ContentCategoryIDFK
            references ContentCategory,
    TotalBudgetCPM        real    default 0 not null,
    SpendAlready          real    default 0 not null,
    RemainingAmount       real    default 0 not null,
    StartDate             real    default 0,
    EndDate               real    default 0,
    ImpressionCount       integer default 0 not null,
    DemandSidePlatformId  integer not null
        constraint DemandSidePlatformIdFK
            references DemandSidePlatform
            on update cascade on delete cascade,
    IsRunning             integer default 0 not null,
    Priority              integer default 999 not null,
    IsRetrictToUserGender integer default 0 not null,
    ExpectedUserGender    TEXT(2)
);

create table Advertise
(
    AdvertiseID           INTEGER not null
        primary key autoincrement,
    CampaignID            INTEGER not null
        constraint CampaignFK
            references Campaign
            on update cascade on delete cascade,
    BannerAdvertiseTypeID INTEGER not null
        constraint BannerAdvertiseTypeIDFK
            references BannerAdvertiseType
            on update cascade on delete cascade,
    AdvertiseTitle        TEXT    not null,
    ContentContextId      INTEGER not null
        constraint ContentContextIdFK
            references ContentContext
            on update cascade on delete cascade,
    BidUrl                TEXT    not null,
    IFrameHtml            TEXT,
    IsCountrySpecific     INTEGER default 0 not null,
    IsVideo               INTEGER default 0 not null,
    ImpressionCount       INTEGER default 0 not null,
    Height                REAL    default 0 not null,
    Weight                REAL    default 0 not null,
    MinHeight             REAL    default 0 not null,
    MinWeight             REAL    default 0 not null,
    MaxHeight             REAL    default 0 not null,
    MaxWeight             REAL    default 0 not null,
    HasAgeRestriction     INTEGER not null,
    MinAge                INTEGER default 0,
    MaxAge                INTEGER default 0
);

create table BidResponse
(
    BidResponseID integer not null
        primary key autoincrement,
    Price         REAL    not null,
    Currency      TEXT    not null,
    Adm           text,
    NUrl          text    not null,
    IUrl          TEXT,
    AdvertiseID   INTEGER not null
        constraint AdvertiseIDFK
            references Advertise
            on update cascade on delete cascade,
    BidRequestID  INTEGER
        constraint BidRequestIDFK
            references BidRequest
);

create table CampaignTargetCity
(
    CampaignTargetCityID integer not null
        primary key autoincrement,
    CampaignTargetCity   TEXT    not null,
    CampaignID           integer not null
        constraint CampaignIDFK
            references Campaign
            on update cascade on delete cascade
);

create table CampaignTargetSite
(
    CampaignTargetSiteID integer not null
        primary key autoincrement,
    CampaignTargetSite   TEXT    not null,
    CampaignID           integer not null
        constraint CampaignIDFK
            references Campaign
            on update cascade on delete cascade
);

create table GeoMapping
(
    GeoMappingId INTEGER not null
        primary key autoincrement,
    AdvertiseId  INTEGER not null
        constraint AdvertiseIdFK
            references Advertise
            on update cascade on delete cascade,
    Country      TEXT    not null,
    HasCity      integer(1) default 0 not null,
    City         TEXT       default NULL
);

create table Impression
(
    ImpressionID TEXT    not null
        primary key,
    AdvertiseID  INTEGER not null
        constraint AdvertiseIDFK
            references Advertise,
    Dated        INTEGER not null,
    BidID        INTEGER not null,
    Price        REAL    not null,
    Currency     TEXT    not null
);

create table NoBidResponseType
(
    NoBidResponseTypeId INTEGER not null
        primary key autoincrement,
    NoBidResponseType   TEXT
);

create table NoBidResponse
(
    NoBidResponseId     INTEGER not null
        primary key autoincrement,
    BidRequestID        INTEGER
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
    AdvertiseID   INTEGER not null
        constraint AdvertiseIDFK
            references Advertise,
    Spend         real    not null,
    ImpressionID  INTEGER not null
        constraint ImpressionIDFK
            references Impression,
    Currency      TEXT    not null
);

