package shared.com.ortb.persistent.schema
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.SQLiteProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import com.github.tototoshi.slick.PostgresJodaSupport._
  import org.joda.time.DateTime
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Array(Advertise.schema, Auction.schema, Banneradvertisetype.schema, Bid.schema, Bidcontentcategoriesmapping.schema, Bidrelatedidsview.schema, Bidrequest.schema, Bidresponse.schema, Campaign.schema, Campaigntargetcity.schema, Campaigntargetoperatingsystem.schema, Campaigntargetsite.schema, Contentcategory.schema, Contentcontext.schema, Creativeattribute.schema, Demandsideplatform.schema, Devicetype.schema, Geomapping.schema, Impression.schema, Keyword.schema, Keywordadvertisemapping.schema, Keywordadvertisemappingidsview.schema, Logtrace.schema, Lostbid.schema, Nobidresponsetype.schema, Privatemarketplacedeal.schema, Publisher.schema, Seatbid.schema, Transaction.schema, Userclassification.schema, Videoplaybackmethod.schema, Videoresponseprotocol.schema, Winningpriceinfoview.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Advertise
   *  @param advertiseid Database column AdvertiseId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param campaignid Database column CampaignId SqlType(INTEGER)
   *  @param banneradvertisetypeid Database column BannerAdvertiseTypeId SqlType(INTEGER)
   *  @param advertisetitle Database column AdvertiseTitle SqlType(TEXT)
   *  @param contentcontextid Database column ContentContextId SqlType(INTEGER)
   *  @param bidurl Database column BidUrl SqlType(TEXT)
   *  @param iframehtml Database column IFrameHtml SqlType(TEXT)
   *  @param iscountryspecific Database column IsCountrySpecific SqlType(INTEGER), Length(1,false), Default(0)
   *  @param isbanner Database column IsBanner SqlType(INTEGER), Length(1,false), Default(Some(0))
   *  @param isvideo Database column IsVideo SqlType(INTEGER), Length(1,false), Default(0)
   *  @param impressioncount Database column ImpressionCount SqlType(INTEGER), Default(0)
   *  @param height Database column Height SqlType(INTEGER), Default(0)
   *  @param width Database column Width SqlType(INTEGER), Default(0)
   *  @param minheight Database column MinHeight SqlType(INTEGER), Default(0)
   *  @param minwidth Database column MinWidth SqlType(INTEGER), Default(0)
   *  @param maxheight Database column MaxHeight SqlType(INTEGER), Default(0)
   *  @param maxwidth Database column MaxWidth SqlType(INTEGER), Default(0)
   *  @param hasagerestriction Database column HasAgeRestriction SqlType(INTEGER), Length(1,false)
   *  @param minage Database column MinAge SqlType(INTEGER), Default(Some(0))
   *  @param maxage Database column MaxAge SqlType(INTEGER), Default(Some(0))
   *  @param createddatetimestamp Database column CreatedDateTimestamp SqlType(TIMESTAMP) */
  case class AdvertiseRow(advertiseid: Int, campaignid: Int, banneradvertisetypeid: Int, advertisetitle: String, contentcontextid: Option[Int], bidurl: String, iframehtml: Option[String], iscountryspecific: Int = 0, isbanner: Option[Int] = Some(0), isvideo: Int = 0, impressioncount: Int = 0, height: Int = 0, width: Int = 0, minheight: Int = 0, minwidth: Int = 0, maxheight: Int = 0, maxwidth: Int = 0, hasagerestriction: Int, minage: Option[Int] = Some(0), maxage: Option[Int] = Some(0), createddatetimestamp: java.time.Instant)
  /** GetResult implicit for fetching AdvertiseRow objects using plain SQL queries */
  implicit def GetResultAdvertiseRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[Int]], e3: GR[Option[String]], e4: GR[java.time.Instant]): GR[AdvertiseRow] = GR{
    prs => import prs._
    AdvertiseRow.tupled((<<[Int], <<[Int], <<[Int], <<[String], <<?[Int], <<[String], <<?[String], <<[Int], <<?[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<?[Int], <<?[Int], <<[java.time.Instant]))
  }
  /** Table description of table Advertise. Objects of this class serve as prototypes for rows in queries. */
  class Advertise(_tableTag: Tag) extends profile.api.Table[AdvertiseRow](_tableTag, "Advertise") {
    def * = (advertiseid, campaignid, banneradvertisetypeid, advertisetitle, contentcontextid, bidurl, iframehtml, iscountryspecific, isbanner, isvideo, impressioncount, height, width, minheight, minwidth, maxheight, maxwidth, hasagerestriction, minage, maxage, createddatetimestamp) <> (AdvertiseRow.tupled, AdvertiseRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(advertiseid), Rep.Some(campaignid), Rep.Some(banneradvertisetypeid), Rep.Some(advertisetitle), contentcontextid, Rep.Some(bidurl), iframehtml, Rep.Some(iscountryspecific), isbanner, Rep.Some(isvideo), Rep.Some(impressioncount), Rep.Some(height), Rep.Some(width), Rep.Some(minheight), Rep.Some(minwidth), Rep.Some(maxheight), Rep.Some(maxwidth), Rep.Some(hasagerestriction), minage, maxage, Rep.Some(createddatetimestamp))).shaped.<>({r=>import r._; _1.map(_=> AdvertiseRow.tupled((_1.get, _2.get, _3.get, _4.get, _5, _6.get, _7, _8.get, _9, _10.get, _11.get, _12.get, _13.get, _14.get, _15.get, _16.get, _17.get, _18.get, _19, _20, _21.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column AdvertiseId SqlType(INTEGER), AutoInc, PrimaryKey */
    val advertiseid: Rep[Int] = column[Int]("AdvertiseId", O.AutoInc, O.PrimaryKey)
    /** Database column CampaignId SqlType(INTEGER) */
    val campaignid: Rep[Int] = column[Int]("CampaignId")
    /** Database column BannerAdvertiseTypeId SqlType(INTEGER) */
    val banneradvertisetypeid: Rep[Int] = column[Int]("BannerAdvertiseTypeId")
    /** Database column AdvertiseTitle SqlType(TEXT) */
    val advertisetitle: Rep[String] = column[String]("AdvertiseTitle")
    /** Database column ContentContextId SqlType(INTEGER) */
    val contentcontextid: Rep[Option[Int]] = column[Option[Int]]("ContentContextId")
    /** Database column BidUrl SqlType(TEXT) */
    val bidurl: Rep[String] = column[String]("BidUrl")
    /** Database column IFrameHtml SqlType(TEXT) */
    val iframehtml: Rep[Option[String]] = column[Option[String]]("IFrameHtml")
    /** Database column IsCountrySpecific SqlType(INTEGER), Length(1,false), Default(0) */
    val iscountryspecific: Rep[Int] = column[Int]("IsCountrySpecific", O.Length(1,varying=false), O.Default(0))
    /** Database column IsBanner SqlType(INTEGER), Length(1,false), Default(Some(0)) */
    val isbanner: Rep[Option[Int]] = column[Option[Int]]("IsBanner", O.Length(1,varying=false), O.Default(Some(0)))
    /** Database column IsVideo SqlType(INTEGER), Length(1,false), Default(0) */
    val isvideo: Rep[Int] = column[Int]("IsVideo", O.Length(1,varying=false), O.Default(0))
    /** Database column ImpressionCount SqlType(INTEGER), Default(0) */
    val impressioncount: Rep[Int] = column[Int]("ImpressionCount", O.Default(0))
    /** Database column Height SqlType(INTEGER), Default(0) */
    val height: Rep[Int] = column[Int]("Height", O.Default(0))
    /** Database column Width SqlType(INTEGER), Default(0) */
    val width: Rep[Int] = column[Int]("Width", O.Default(0))
    /** Database column MinHeight SqlType(INTEGER), Default(0) */
    val minheight: Rep[Int] = column[Int]("MinHeight", O.Default(0))
    /** Database column MinWidth SqlType(INTEGER), Default(0) */
    val minwidth: Rep[Int] = column[Int]("MinWidth", O.Default(0))
    /** Database column MaxHeight SqlType(INTEGER), Default(0) */
    val maxheight: Rep[Int] = column[Int]("MaxHeight", O.Default(0))
    /** Database column MaxWidth SqlType(INTEGER), Default(0) */
    val maxwidth: Rep[Int] = column[Int]("MaxWidth", O.Default(0))
    /** Database column HasAgeRestriction SqlType(INTEGER), Length(1,false) */
    val hasagerestriction: Rep[Int] = column[Int]("HasAgeRestriction", O.Length(1,varying=false))
    /** Database column MinAge SqlType(INTEGER), Default(Some(0)) */
    val minage: Rep[Option[Int]] = column[Option[Int]]("MinAge", O.Default(Some(0)))
    /** Database column MaxAge SqlType(INTEGER), Default(Some(0)) */
    val maxage: Rep[Option[Int]] = column[Option[Int]]("MaxAge", O.Default(Some(0)))
    /** Database column CreatedDateTimestamp SqlType(TIMESTAMP) */
    val createddatetimestamp: Rep[java.time.Instant] = column[java.time.Instant]("CreatedDateTimestamp")

    /** Foreign key referencing Banneradvertisetype (database name BannerAdvertiseType_FK_1) */
    lazy val banneradvertisetypeFk = foreignKey("BannerAdvertiseType_FK_1", banneradvertisetypeid, Banneradvertisetype)(r => r.banneradvertisetypeid, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
    /** Foreign key referencing Campaign (database name Campaign_FK_2) */
    lazy val campaignFk = foreignKey("Campaign_FK_2", campaignid, Campaign)(r => r.campaignid, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
    /** Foreign key referencing Contentcontext (database name ContentContext_FK_3) */
    lazy val contentcontextFk = foreignKey("ContentContext_FK_3", contentcontextid, Contentcontext)(r => Rep.Some(r.contentcontextid), onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Advertise */
  lazy val Advertise = new TableQuery(tag => new Advertise(tag))

  /** Entity class storing rows of table Auction
   *  @param auctionid Database column AuctionId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param winningbidrequestid Database column WinningBidRequestId SqlType(INTEGER), Default(None)
   *  @param isprecachedadvertiseserved Database column IsPrecachedAdvertiseServed SqlType(INTEGER), Length(1,false), Default(0)
   *  @param winningprice Database column WinningPrice SqlType(REAL), Default(-1.0)
   *  @param currency Database column Currency SqlType(TEXT), Length(5,false), Default(Some(USD))
   *  @param createddatetimestamp Database column CreatedDateTimestamp SqlType(TIMESTAMP) */
  case class AuctionRow(auctionid: Int, winningbidrequestid: Option[Int] = None, isprecachedadvertiseserved: Int = 0, winningprice: Double = -1.0, currency: Option[String] = Some("USD"), createddatetimestamp: java.time.Instant)
  /** GetResult implicit for fetching AuctionRow objects using plain SQL queries */
  implicit def GetResultAuctionRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Double], e3: GR[Option[String]], e4: GR[java.time.Instant]): GR[AuctionRow] = GR{
    prs => import prs._
    AuctionRow.tupled((<<[Int], <<?[Int], <<[Int], <<[Double], <<?[String], <<[java.time.Instant]))
  }
  /** Table description of table Auction. Objects of this class serve as prototypes for rows in queries. */
  class Auction(_tableTag: Tag) extends profile.api.Table[AuctionRow](_tableTag, "Auction") {
    def * = (auctionid, winningbidrequestid, isprecachedadvertiseserved, winningprice, currency, createddatetimestamp) <> (AuctionRow.tupled, AuctionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(auctionid), winningbidrequestid, Rep.Some(isprecachedadvertiseserved), Rep.Some(winningprice), currency, Rep.Some(createddatetimestamp))).shaped.<>({r=>import r._; _1.map(_=> AuctionRow.tupled((_1.get, _2, _3.get, _4.get, _5, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column AuctionId SqlType(INTEGER), AutoInc, PrimaryKey */
    val auctionid: Rep[Int] = column[Int]("AuctionId", O.AutoInc, O.PrimaryKey)
    /** Database column WinningBidRequestId SqlType(INTEGER), Default(None) */
    val winningbidrequestid: Rep[Option[Int]] = column[Option[Int]]("WinningBidRequestId", O.Default(None))
    /** Database column IsPrecachedAdvertiseServed SqlType(INTEGER), Length(1,false), Default(0) */
    val isprecachedadvertiseserved: Rep[Int] = column[Int]("IsPrecachedAdvertiseServed", O.Length(1,varying=false), O.Default(0))
    /** Database column WinningPrice SqlType(REAL), Default(-1.0) */
    val winningprice: Rep[Double] = column[Double]("WinningPrice", O.Default(-1.0))
    /** Database column Currency SqlType(TEXT), Length(5,false), Default(Some(USD)) */
    val currency: Rep[Option[String]] = column[Option[String]]("Currency", O.Length(5,varying=false), O.Default(Some("USD")))
    /** Database column CreatedDateTimestamp SqlType(TIMESTAMP) */
    val createddatetimestamp: Rep[java.time.Instant] = column[java.time.Instant]("CreatedDateTimestamp")

    /** Foreign key referencing Bidrequest (database name BidRequest_FK_1) */
    lazy val bidrequestFk = foreignKey("BidRequest_FK_1", winningbidrequestid, Bidrequest)(r => Rep.Some(r.bidrequestid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Auction */
  lazy val Auction = new TableQuery(tag => new Auction(tag))

  /** Entity class storing rows of table Banneradvertisetype
   *  @param banneradvertisetypeid Database column BannerAdvertiseTypeId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param typename Database column TypeName SqlType(TEXT) */
  case class BanneradvertisetypeRow(banneradvertisetypeid: Int, typename: String)
  /** GetResult implicit for fetching BanneradvertisetypeRow objects using plain SQL queries */
  implicit def GetResultBanneradvertisetypeRow(implicit e0: GR[Int], e1: GR[String]): GR[BanneradvertisetypeRow] = GR{
    prs => import prs._
    BanneradvertisetypeRow.tupled((<<[Int], <<[String]))
  }
  /** Table description of table BannerAdvertiseType. Objects of this class serve as prototypes for rows in queries. */
  class Banneradvertisetype(_tableTag: Tag) extends profile.api.Table[BanneradvertisetypeRow](_tableTag, "BannerAdvertiseType") {
    def * = (banneradvertisetypeid, typename) <> (BanneradvertisetypeRow.tupled, BanneradvertisetypeRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(banneradvertisetypeid), Rep.Some(typename))).shaped.<>({r=>import r._; _1.map(_=> BanneradvertisetypeRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column BannerAdvertiseTypeId SqlType(INTEGER), AutoInc, PrimaryKey */
    val banneradvertisetypeid: Rep[Int] = column[Int]("BannerAdvertiseTypeId", O.AutoInc, O.PrimaryKey)
    /** Database column TypeName SqlType(TEXT) */
    val typename: Rep[String] = column[String]("TypeName")
  }
  /** Collection-like TableQuery object for table Banneradvertisetype */
  lazy val Banneradvertisetype = new TableQuery(tag => new Banneradvertisetype(tag))

  /** Entity class storing rows of table Bid
   *  @param bidid Database column BidId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param bidrawjson Database column BidRawJson SqlType(TEXT), Default(None)
   *  @param dealbiddingprice Database column DealBiddingPrice SqlType(REAL), Default(Some(0.0))
   *  @param actualwiningprice Database column ActualWiningPrice SqlType(REAL), Default(Some(0.0))
   *  @param isimpressionservedorwonbyauction Database column IsImpressionServedOrWonByAuction SqlType(INTEGER), Length(1,false), Default(Some(0))
   *  @param seatbidid Database column SeatBidId SqlType(INTEGER)
   *  @param campaignid Database column CampaignId SqlType(INTEGER), Default(None)
   *  @param impressionid Database column ImpressionId SqlType(INTEGER), Default(None)
   *  @param advertiseid Database column AdvertiseId SqlType(INTEGER), Default(None)
   *  @param creativeattributeid Database column CreativeAttributeId SqlType(INTEGER), Default(None)
   *  @param adm Database column Adm SqlType(TEXT), Default(None)
   *  @param nurl Database column NUrl SqlType(TEXT), Default(None)
   *  @param iurl Database column IUrl SqlType(TEXT), Default(None)
   *  @param height Database column Height SqlType(INTEGER), Default(None)
   *  @param width Database column Width SqlType(INTEGER), Default(None)
   *  @param createddatetimestamp Database column CreatedDateTimestamp SqlType(TIMESTAMP) */
  case class BidRow(bidid: Int, bidrawjson: Option[String] = None, dealbiddingprice: Option[Double] = Some(0.0), actualwiningprice: Option[Double] = Some(0.0), isimpressionservedorwonbyauction: Option[Int] = Some(0), seatbidid: Int, campaignid: Option[Int] = None, impressionid: Option[Int] = None, advertiseid: Option[Int] = None, creativeattributeid: Option[Int] = None, adm: Option[String] = None, nurl: Option[String] = None, iurl: Option[String] = None, height: Option[Int] = None, width: Option[Int] = None, createddatetimestamp: java.time.Instant)
  /** GetResult implicit for fetching BidRow objects using plain SQL queries */
  implicit def GetResultBidRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Double]], e3: GR[Option[Int]], e4: GR[java.time.Instant]): GR[BidRow] = GR{
    prs => import prs._
    BidRow.tupled((<<[Int], <<?[String], <<?[Double], <<?[Double], <<?[Int], <<[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[String], <<?[String], <<?[String], <<?[Int], <<?[Int], <<[java.time.Instant]))
  }
  /** Table description of table Bid. Objects of this class serve as prototypes for rows in queries. */
  class Bid(_tableTag: Tag) extends profile.api.Table[BidRow](_tableTag, "Bid") {
    def * = (bidid, bidrawjson, dealbiddingprice, actualwiningprice, isimpressionservedorwonbyauction, seatbidid, campaignid, impressionid, advertiseid, creativeattributeid, adm, nurl, iurl, height, width, createddatetimestamp) <> (BidRow.tupled, BidRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(bidid), bidrawjson, dealbiddingprice, actualwiningprice, isimpressionservedorwonbyauction, Rep.Some(seatbidid), campaignid, impressionid, advertiseid, creativeattributeid, adm, nurl, iurl, height, width, Rep.Some(createddatetimestamp))).shaped.<>({r=>import r._; _1.map(_=> BidRow.tupled((_1.get, _2, _3, _4, _5, _6.get, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column BidId SqlType(INTEGER), AutoInc, PrimaryKey */
    val bidid: Rep[Int] = column[Int]("BidId", O.AutoInc, O.PrimaryKey)
    /** Database column BidRawJson SqlType(TEXT), Default(None) */
    val bidrawjson: Rep[Option[String]] = column[Option[String]]("BidRawJson", O.Default(None))
    /** Database column DealBiddingPrice SqlType(REAL), Default(Some(0.0)) */
    val dealbiddingprice: Rep[Option[Double]] = column[Option[Double]]("DealBiddingPrice", O.Default(Some(0.0)))
    /** Database column ActualWiningPrice SqlType(REAL), Default(Some(0.0)) */
    val actualwiningprice: Rep[Option[Double]] = column[Option[Double]]("ActualWiningPrice", O.Default(Some(0.0)))
    /** Database column IsImpressionServedOrWonByAuction SqlType(INTEGER), Length(1,false), Default(Some(0)) */
    val isimpressionservedorwonbyauction: Rep[Option[Int]] = column[Option[Int]]("IsImpressionServedOrWonByAuction", O.Length(1,varying=false), O.Default(Some(0)))
    /** Database column SeatBidId SqlType(INTEGER) */
    val seatbidid: Rep[Int] = column[Int]("SeatBidId")
    /** Database column CampaignId SqlType(INTEGER), Default(None) */
    val campaignid: Rep[Option[Int]] = column[Option[Int]]("CampaignId", O.Default(None))
    /** Database column ImpressionId SqlType(INTEGER), Default(None) */
    val impressionid: Rep[Option[Int]] = column[Option[Int]]("ImpressionId", O.Default(None))
    /** Database column AdvertiseId SqlType(INTEGER), Default(None) */
    val advertiseid: Rep[Option[Int]] = column[Option[Int]]("AdvertiseId", O.Default(None))
    /** Database column CreativeAttributeId SqlType(INTEGER), Default(None) */
    val creativeattributeid: Rep[Option[Int]] = column[Option[Int]]("CreativeAttributeId", O.Default(None))
    /** Database column Adm SqlType(TEXT), Default(None) */
    val adm: Rep[Option[String]] = column[Option[String]]("Adm", O.Default(None))
    /** Database column NUrl SqlType(TEXT), Default(None) */
    val nurl: Rep[Option[String]] = column[Option[String]]("NUrl", O.Default(None))
    /** Database column IUrl SqlType(TEXT), Default(None) */
    val iurl: Rep[Option[String]] = column[Option[String]]("IUrl", O.Default(None))
    /** Database column Height SqlType(INTEGER), Default(None) */
    val height: Rep[Option[Int]] = column[Option[Int]]("Height", O.Default(None))
    /** Database column Width SqlType(INTEGER), Default(None) */
    val width: Rep[Option[Int]] = column[Option[Int]]("Width", O.Default(None))
    /** Database column CreatedDateTimestamp SqlType(TIMESTAMP) */
    val createddatetimestamp: Rep[java.time.Instant] = column[java.time.Instant]("CreatedDateTimestamp")

    /** Foreign key referencing Advertise (database name Advertise_FK_1) */
    lazy val advertiseFk = foreignKey("Advertise_FK_1", advertiseid, Advertise)(r => Rep.Some(r.advertiseid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Campaign (database name Campaign_FK_2) */
    lazy val campaignFk = foreignKey("Campaign_FK_2", campaignid, Campaign)(r => Rep.Some(r.campaignid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Creativeattribute (database name CreativeAttribute_FK_3) */
    lazy val creativeattributeFk = foreignKey("CreativeAttribute_FK_3", creativeattributeid, Creativeattribute)(r => Rep.Some(r.creativeattributeid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Impression (database name Impression_FK_4) */
    lazy val impressionFk = foreignKey("Impression_FK_4", impressionid, Impression)(r => Rep.Some(r.impressionid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Seatbid (database name SeatBid_FK_5) */
    lazy val seatbidFk = foreignKey("SeatBid_FK_5", seatbidid, Seatbid)(r => r.seatbidid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Bid */
  lazy val Bid = new TableQuery(tag => new Bid(tag))

  /** Entity class storing rows of table Bidcontentcategoriesmapping
   *  @param bidcontentcategoriesmappingid Database column BidContentCategoriesMappingId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param bidid Database column BidId SqlType(INTEGER)
   *  @param contentcategoryid Database column ContentCategoryId SqlType(TEXT) */
  case class BidcontentcategoriesmappingRow(bidcontentcategoriesmappingid: Int, bidid: Int, contentcategoryid: String)
  /** GetResult implicit for fetching BidcontentcategoriesmappingRow objects using plain SQL queries */
  implicit def GetResultBidcontentcategoriesmappingRow(implicit e0: GR[Int], e1: GR[String]): GR[BidcontentcategoriesmappingRow] = GR{
    prs => import prs._
    BidcontentcategoriesmappingRow.tupled((<<[Int], <<[Int], <<[String]))
  }
  /** Table description of table BidContentCategoriesMapping. Objects of this class serve as prototypes for rows in queries. */
  class Bidcontentcategoriesmapping(_tableTag: Tag) extends profile.api.Table[BidcontentcategoriesmappingRow](_tableTag, "BidContentCategoriesMapping") {
    def * = (bidcontentcategoriesmappingid, bidid, contentcategoryid) <> (BidcontentcategoriesmappingRow.tupled, BidcontentcategoriesmappingRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(bidcontentcategoriesmappingid), Rep.Some(bidid), Rep.Some(contentcategoryid))).shaped.<>({r=>import r._; _1.map(_=> BidcontentcategoriesmappingRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column BidContentCategoriesMappingId SqlType(INTEGER), AutoInc, PrimaryKey */
    val bidcontentcategoriesmappingid: Rep[Int] = column[Int]("BidContentCategoriesMappingId", O.AutoInc, O.PrimaryKey)
    /** Database column BidId SqlType(INTEGER) */
    val bidid: Rep[Int] = column[Int]("BidId")
    /** Database column ContentCategoryId SqlType(TEXT) */
    val contentcategoryid: Rep[String] = column[String]("ContentCategoryId")

    /** Foreign key referencing Bid (database name Bid_FK_1) */
    lazy val bidFk = foreignKey("Bid_FK_1", bidid, Bid)(r => r.bidid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Contentcategory (database name ContentCategory_FK_2) */
    lazy val contentcategoryFk = foreignKey("ContentCategory_FK_2", contentcategoryid, Contentcategory)(r => r.contentcategoryid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Bidcontentcategoriesmapping */
  lazy val Bidcontentcategoriesmapping = new TableQuery(tag => new Bidcontentcategoriesmapping(tag))

  /** Entity class storing rows of table Bidrelatedidsview
   *  @param bidid Database column BidId SqlType(INTEGER)
   *  @param impressionid Database column ImpressionId SqlType(INTEGER)
   *  @param seatbidid Database column SeatBidId SqlType(INTEGER)
   *  @param campaignid Database column CampaignId SqlType(INTEGER)
   *  @param auctionid Database column AuctionId SqlType(INTEGER)
   *  @param bidrequestid Database column BidRequestId SqlType(INTEGER)
   *  @param bidresponseid Database column BidResponseId SqlType(INTEGER)
   *  @param advertiseid Database column AdvertiseId SqlType(INTEGER)
   *  @param demandsideplatformid Database column DemandSidePlatformId SqlType(INTEGER) */
  case class BidrelatedidsviewRow(bidid: Option[Int], impressionid: Option[Int], seatbidid: Option[Int], campaignid: Option[Int], auctionid: Option[Int], bidrequestid: Option[Int], bidresponseid: Option[Int], advertiseid: Option[Int], demandsideplatformid: Option[Int])
  /** GetResult implicit for fetching BidrelatedidsviewRow objects using plain SQL queries */
  implicit def GetResultBidrelatedidsviewRow(implicit e0: GR[Option[Int]]): GR[BidrelatedidsviewRow] = GR{
    prs => import prs._
    BidrelatedidsviewRow.tupled((<<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int]))
  }
  /** Table description of table BidRelatedIdsView. Objects of this class serve as prototypes for rows in queries. */
  class Bidrelatedidsview(_tableTag: Tag) extends profile.api.Table[BidrelatedidsviewRow](_tableTag, "BidRelatedIdsView") {
    def * = (bidid, impressionid, seatbidid, campaignid, auctionid, bidrequestid, bidresponseid, advertiseid, demandsideplatformid) <> (BidrelatedidsviewRow.tupled, BidrelatedidsviewRow.unapply)

    /** Database column BidId SqlType(INTEGER) */
    val bidid: Rep[Option[Int]] = column[Option[Int]]("BidId")
    /** Database column ImpressionId SqlType(INTEGER) */
    val impressionid: Rep[Option[Int]] = column[Option[Int]]("ImpressionId")
    /** Database column SeatBidId SqlType(INTEGER) */
    val seatbidid: Rep[Option[Int]] = column[Option[Int]]("SeatBidId")
    /** Database column CampaignId SqlType(INTEGER) */
    val campaignid: Rep[Option[Int]] = column[Option[Int]]("CampaignId")
    /** Database column AuctionId SqlType(INTEGER) */
    val auctionid: Rep[Option[Int]] = column[Option[Int]]("AuctionId")
    /** Database column BidRequestId SqlType(INTEGER) */
    val bidrequestid: Rep[Option[Int]] = column[Option[Int]]("BidRequestId")
    /** Database column BidResponseId SqlType(INTEGER) */
    val bidresponseid: Rep[Option[Int]] = column[Option[Int]]("BidResponseId")
    /** Database column AdvertiseId SqlType(INTEGER) */
    val advertiseid: Rep[Option[Int]] = column[Option[Int]]("AdvertiseId")
    /** Database column DemandSidePlatformId SqlType(INTEGER) */
    val demandsideplatformid: Rep[Option[Int]] = column[Option[Int]]("DemandSidePlatformId")
  }
  /** Collection-like TableQuery object for table Bidrelatedidsview */
  lazy val Bidrelatedidsview = new TableQuery(tag => new Bidrelatedidsview(tag))

  /** Entity class storing rows of table Bidrequest
   *  @param bidrequestid Database column BidRequestId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param demandsideplatformid Database column DemandSidePlatformId SqlType(INTEGER)
   *  @param auctionid Database column AuctionId SqlType(INTEGER), Default(None)
   *  @param isbanner Database column IsBanner SqlType(INTEGER), Length(1,false), Default(0)
   *  @param isvideo Database column IsVideo SqlType(INTEGER), Length(1,false), Default(0)
   *  @param height Database column Height SqlType(INTEGER), Default(None)
   *  @param width Database column Width SqlType(INTEGER), Default(None)
   *  @param countries Database column Countries SqlType(TEXT), Default(None)
   *  @param cities Database column Cities SqlType(TEXT), Default(None)
   *  @param targetedsites Database column TargetedSites SqlType(TEXT), Default(None)
   *  @param targetedcities Database column TargetedCities SqlType(TEXT), Default(None)
   *  @param rawbidrequestjson Database column RawBidRequestJson SqlType(TEXT), Default()
   *  @param currency Database column Currency SqlType(TEXT), Length(5,false), Default(Some(USD))
   *  @param contentcontextid Database column ContentContextId SqlType(INTEGER), Default(None)
   *  @param iswontheauction Database column IsWonTheAuction SqlType(INTEGER), Length(1,false), Default(0)
   *  @param createddatetimestamp Database column CreatedDateTimestamp SqlType(TIMESTAMP) */
  case class BidrequestRow(bidrequestid: Int, demandsideplatformid: Int, auctionid: Option[Int] = None, isbanner: Int = 0, isvideo: Int = 0, height: Option[Int] = None, width: Option[Int] = None, countries: Option[String] = None, cities: Option[String] = None, targetedsites: Option[String] = None, targetedcities: Option[String] = None, rawbidrequestjson: String = "", currency: Option[String] = Some("USD"), contentcontextid: Option[Int] = None, iswontheauction: Int = 0, createddatetimestamp: java.time.Instant)
  /** GetResult implicit for fetching BidrequestRow objects using plain SQL queries */
  implicit def GetResultBidrequestRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[String]], e3: GR[String], e4: GR[java.time.Instant]): GR[BidrequestRow] = GR{
    prs => import prs._
    BidrequestRow.tupled((<<[Int], <<[Int], <<?[Int], <<[Int], <<[Int], <<?[Int], <<?[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<[String], <<?[String], <<?[Int], <<[Int], <<[java.time.Instant]))
  }
  /** Table description of table BidRequest. Objects of this class serve as prototypes for rows in queries. */
  class Bidrequest(_tableTag: Tag) extends profile.api.Table[BidrequestRow](_tableTag, "BidRequest") {
    def * = (bidrequestid, demandsideplatformid, auctionid, isbanner, isvideo, height, width, countries, cities, targetedsites, targetedcities, rawbidrequestjson, currency, contentcontextid, iswontheauction, createddatetimestamp) <> (BidrequestRow.tupled, BidrequestRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(bidrequestid), Rep.Some(demandsideplatformid), auctionid, Rep.Some(isbanner), Rep.Some(isvideo), height, width, countries, cities, targetedsites, targetedcities, Rep.Some(rawbidrequestjson), currency, contentcontextid, Rep.Some(iswontheauction), Rep.Some(createddatetimestamp))).shaped.<>({r=>import r._; _1.map(_=> BidrequestRow.tupled((_1.get, _2.get, _3, _4.get, _5.get, _6, _7, _8, _9, _10, _11, _12.get, _13, _14, _15.get, _16.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column BidRequestId SqlType(INTEGER), AutoInc, PrimaryKey */
    val bidrequestid: Rep[Int] = column[Int]("BidRequestId", O.AutoInc, O.PrimaryKey)
    /** Database column DemandSidePlatformId SqlType(INTEGER) */
    val demandsideplatformid: Rep[Int] = column[Int]("DemandSidePlatformId")
    /** Database column AuctionId SqlType(INTEGER), Default(None) */
    val auctionid: Rep[Option[Int]] = column[Option[Int]]("AuctionId", O.Default(None))
    /** Database column IsBanner SqlType(INTEGER), Length(1,false), Default(0) */
    val isbanner: Rep[Int] = column[Int]("IsBanner", O.Length(1,varying=false), O.Default(0))
    /** Database column IsVideo SqlType(INTEGER), Length(1,false), Default(0) */
    val isvideo: Rep[Int] = column[Int]("IsVideo", O.Length(1,varying=false), O.Default(0))
    /** Database column Height SqlType(INTEGER), Default(None) */
    val height: Rep[Option[Int]] = column[Option[Int]]("Height", O.Default(None))
    /** Database column Width SqlType(INTEGER), Default(None) */
    val width: Rep[Option[Int]] = column[Option[Int]]("Width", O.Default(None))
    /** Database column Countries SqlType(TEXT), Default(None) */
    val countries: Rep[Option[String]] = column[Option[String]]("Countries", O.Default(None))
    /** Database column Cities SqlType(TEXT), Default(None) */
    val cities: Rep[Option[String]] = column[Option[String]]("Cities", O.Default(None))
    /** Database column TargetedSites SqlType(TEXT), Default(None) */
    val targetedsites: Rep[Option[String]] = column[Option[String]]("TargetedSites", O.Default(None))
    /** Database column TargetedCities SqlType(TEXT), Default(None) */
    val targetedcities: Rep[Option[String]] = column[Option[String]]("TargetedCities", O.Default(None))
    /** Database column RawBidRequestJson SqlType(TEXT), Default() */
    val rawbidrequestjson: Rep[String] = column[String]("RawBidRequestJson", O.Default(""))
    /** Database column Currency SqlType(TEXT), Length(5,false), Default(Some(USD)) */
    val currency: Rep[Option[String]] = column[Option[String]]("Currency", O.Length(5,varying=false), O.Default(Some("USD")))
    /** Database column ContentContextId SqlType(INTEGER), Default(None) */
    val contentcontextid: Rep[Option[Int]] = column[Option[Int]]("ContentContextId", O.Default(None))
    /** Database column IsWonTheAuction SqlType(INTEGER), Length(1,false), Default(0) */
    val iswontheauction: Rep[Int] = column[Int]("IsWonTheAuction", O.Length(1,varying=false), O.Default(0))
    /** Database column CreatedDateTimestamp SqlType(TIMESTAMP) */
    val createddatetimestamp: Rep[java.time.Instant] = column[java.time.Instant]("CreatedDateTimestamp")

    /** Foreign key referencing Auction (database name Auction_FK_1) */
    lazy val auctionFk = foreignKey("Auction_FK_1", auctionid, Auction)(r => Rep.Some(r.auctionid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Contentcontext (database name ContentContext_FK_2) */
    lazy val contentcontextFk = foreignKey("ContentContext_FK_2", contentcontextid, Contentcontext)(r => Rep.Some(r.contentcontextid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Demandsideplatform (database name DemandSidePlatform_FK_3) */
    lazy val demandsideplatformFk = foreignKey("DemandSidePlatform_FK_3", demandsideplatformid, Demandsideplatform)(r => r.demandsideplatformid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Bidrequest */
  lazy val Bidrequest = new TableQuery(tag => new Bidrequest(tag))

  /** Entity class storing rows of table Bidresponse
   *  @param bidresponseid Database column BidResponseId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param currency Database column Currency SqlType(TEXT), Length(5,false), Default(Some(USD))
   *  @param bidrequestid Database column BidRequestId SqlType(INTEGER), Default(None)
   *  @param isanybidwontheauction Database column IsAnyBidWonTheAuction SqlType(INTEGER), Length(1,false), Default(0)
   *  @param isauctionoccured Database column IsAuctionOccured SqlType(INTEGER), Length(1,false), Default(0)
   *  @param isprecachedbidserved Database column IsPreCachedBidServed SqlType(INTEGER), Length(1,false), Default(0)
   *  @param issendnobidresponse Database column IsSendNoBidResponse SqlType(INTEGER), Length(1,false), Default(0)
   *  @param nobidresponsetypeid Database column NoBidResponseTypeId SqlType(INTEGER), Default(None)
   *  @param createddatetimestamp Database column CreatedDateTimestamp SqlType(TIMESTAMP) */
  case class BidresponseRow(bidresponseid: Int, currency: Option[String] = Some("USD"), bidrequestid: Option[Int] = None, isanybidwontheauction: Int = 0, isauctionoccured: Int = 0, isprecachedbidserved: Int = 0, issendnobidresponse: Int = 0, nobidresponsetypeid: Option[Int] = None, createddatetimestamp: Option[java.time.Instant])
  /** GetResult implicit for fetching BidresponseRow objects using plain SQL queries */
  implicit def GetResultBidresponseRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Int]], e3: GR[Option[java.time.Instant]]): GR[BidresponseRow] = GR{
    prs => import prs._
    BidresponseRow.tupled((<<[Int], <<?[String], <<?[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<?[Int], <<?[java.time.Instant]))
  }
  /** Table description of table BidResponse. Objects of this class serve as prototypes for rows in queries. */
  class Bidresponse(_tableTag: Tag) extends profile.api.Table[BidresponseRow](_tableTag, "BidResponse") {
    def * = (bidresponseid, currency, bidrequestid, isanybidwontheauction, isauctionoccured, isprecachedbidserved, issendnobidresponse, nobidresponsetypeid, createddatetimestamp) <> (BidresponseRow.tupled, BidresponseRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(bidresponseid), currency, bidrequestid, Rep.Some(isanybidwontheauction), Rep.Some(isauctionoccured), Rep.Some(isprecachedbidserved), Rep.Some(issendnobidresponse), nobidresponsetypeid, createddatetimestamp)).shaped.<>({r=>import r._; _1.map(_=> BidresponseRow.tupled((_1.get, _2, _3, _4.get, _5.get, _6.get, _7.get, _8, _9)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column BidResponseId SqlType(INTEGER), AutoInc, PrimaryKey */
    val bidresponseid: Rep[Int] = column[Int]("BidResponseId", O.AutoInc, O.PrimaryKey)
    /** Database column Currency SqlType(TEXT), Length(5,false), Default(Some(USD)) */
    val currency: Rep[Option[String]] = column[Option[String]]("Currency", O.Length(5,varying=false), O.Default(Some("USD")))
    /** Database column BidRequestId SqlType(INTEGER), Default(None) */
    val bidrequestid: Rep[Option[Int]] = column[Option[Int]]("BidRequestId", O.Default(None))
    /** Database column IsAnyBidWonTheAuction SqlType(INTEGER), Length(1,false), Default(0) */
    val isanybidwontheauction: Rep[Int] = column[Int]("IsAnyBidWonTheAuction", O.Length(1,varying=false), O.Default(0))
    /** Database column IsAuctionOccured SqlType(INTEGER), Length(1,false), Default(0) */
    val isauctionoccured: Rep[Int] = column[Int]("IsAuctionOccured", O.Length(1,varying=false), O.Default(0))
    /** Database column IsPreCachedBidServed SqlType(INTEGER), Length(1,false), Default(0) */
    val isprecachedbidserved: Rep[Int] = column[Int]("IsPreCachedBidServed", O.Length(1,varying=false), O.Default(0))
    /** Database column IsSendNoBidResponse SqlType(INTEGER), Length(1,false), Default(0) */
    val issendnobidresponse: Rep[Int] = column[Int]("IsSendNoBidResponse", O.Length(1,varying=false), O.Default(0))
    /** Database column NoBidResponseTypeId SqlType(INTEGER), Default(None) */
    val nobidresponsetypeid: Rep[Option[Int]] = column[Option[Int]]("NoBidResponseTypeId", O.Default(None))
    /** Database column CreatedDateTimestamp SqlType(TIMESTAMP) */
    val createddatetimestamp: Rep[Option[java.time.Instant]] = column[Option[java.time.Instant]]("CreatedDateTimestamp")

    /** Foreign key referencing Bidrequest (database name BidRequest_FK_1) */
    lazy val bidrequestFk = foreignKey("BidRequest_FK_1", bidrequestid, Bidrequest)(r => Rep.Some(r.bidrequestid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Nobidresponsetype (database name NoBidResponseType_FK_2) */
    lazy val nobidresponsetypeFk = foreignKey("NoBidResponseType_FK_2", nobidresponsetypeid, Nobidresponsetype)(r => Rep.Some(r.nobidresponsetypeid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Bidresponse */
  lazy val Bidresponse = new TableQuery(tag => new Bidresponse(tag))

  /** Entity class storing rows of table Campaign
   *  @param campaignid Database column CampaignId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param campaignname Database column CampaignName SqlType(TEXT)
   *  @param contentcategoryid Database column ContentCategoryId SqlType(TEXT), Default(None)
   *  @param totalbudgetcpm Database column TotalBudgetCPM SqlType(REAL), Default(0.0)
   *  @param spendalready Database column SpendAlready SqlType(REAL), Default(0.0)
   *  @param remainingamount Database column RemainingAmount SqlType(REAL), Default(0.0)
   *  @param startdate Database column StartDate SqlType(REAL), Default(Some(0.0))
   *  @param enddate Database column EndDate SqlType(REAL), Default(Some(0.0))
   *  @param impressioncount Database column ImpressionCount SqlType(INTEGER), Default(0)
   *  @param publisherid Database column PublisherId SqlType(INTEGER), Default(None)
   *  @param demandsideplatformid Database column DemandSidePlatformId SqlType(INTEGER)
   *  @param isrunning Database column IsRunning SqlType(INTEGER), Length(1,false), Default(0)
   *  @param priority Database column Priority SqlType(INTEGER), Length(3,false), Default(999)
   *  @param isretricttousergender Database column IsRetrictToUserGender SqlType(INTEGER), Length(1,false), Default(0)
   *  @param expectedusergender Database column ExpectedUserGender SqlType(TEXT), Length(2,false), Default(None)
   *  @param modifieddate Database column ModifiedDate SqlType(TIMESTAMP)
   *  @param createddatetimestamp Database column CreatedDateTimestamp SqlType(TIMESTAMP) */
  case class CampaignRow(campaignid: Int, campaignname: String, contentcategoryid: Option[String] = None, totalbudgetcpm: Double = 0.0, spendalready: Double = 0.0, remainingamount: Double = 0.0, startdate: Option[Double] = Some(0.0), enddate: Option[Double] = Some(0.0), impressioncount: Int = 0, publisherid: Option[Int] = None, demandsideplatformid: Int, isrunning: Int = 0, priority: Int = 999, isretricttousergender: Int = 0, expectedusergender: Option[String] = None, modifieddate: java.time.Instant, createddatetimestamp: java.time.Instant)
  /** GetResult implicit for fetching CampaignRow objects using plain SQL queries */
  implicit def GetResultCampaignRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[Double], e4: GR[Option[Double]], e5: GR[Option[Int]], e6: GR[java.time.Instant]): GR[CampaignRow] = GR{
    prs => import prs._
    CampaignRow.tupled((<<[Int], <<[String], <<?[String], <<[Double], <<[Double], <<[Double], <<?[Double], <<?[Double], <<[Int], <<?[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<?[String], <<[java.time.Instant], <<[java.time.Instant]))
  }
  /** Table description of table Campaign. Objects of this class serve as prototypes for rows in queries. */
  class Campaign(_tableTag: Tag) extends profile.api.Table[CampaignRow](_tableTag, "Campaign") {
    def * = (campaignid, campaignname, contentcategoryid, totalbudgetcpm, spendalready, remainingamount, startdate, enddate, impressioncount, publisherid, demandsideplatformid, isrunning, priority, isretricttousergender, expectedusergender, modifieddate, createddatetimestamp) <> (CampaignRow.tupled, CampaignRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(campaignid), Rep.Some(campaignname), contentcategoryid, Rep.Some(totalbudgetcpm), Rep.Some(spendalready), Rep.Some(remainingamount), startdate, enddate, Rep.Some(impressioncount), publisherid, Rep.Some(demandsideplatformid), Rep.Some(isrunning), Rep.Some(priority), Rep.Some(isretricttousergender), expectedusergender, Rep.Some(modifieddate), Rep.Some(createddatetimestamp))).shaped.<>({r=>import r._; _1.map(_=> CampaignRow.tupled((_1.get, _2.get, _3, _4.get, _5.get, _6.get, _7, _8, _9.get, _10, _11.get, _12.get, _13.get, _14.get, _15, _16.get, _17.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column CampaignId SqlType(INTEGER), AutoInc, PrimaryKey */
    val campaignid: Rep[Int] = column[Int]("CampaignId", O.AutoInc, O.PrimaryKey)
    /** Database column CampaignName SqlType(TEXT) */
    val campaignname: Rep[String] = column[String]("CampaignName")
    /** Database column ContentCategoryId SqlType(TEXT), Default(None) */
    val contentcategoryid: Rep[Option[String]] = column[Option[String]]("ContentCategoryId", O.Default(None))
    /** Database column TotalBudgetCPM SqlType(REAL), Default(0.0) */
    val totalbudgetcpm: Rep[Double] = column[Double]("TotalBudgetCPM", O.Default(0.0))
    /** Database column SpendAlready SqlType(REAL), Default(0.0) */
    val spendalready: Rep[Double] = column[Double]("SpendAlready", O.Default(0.0))
    /** Database column RemainingAmount SqlType(REAL), Default(0.0) */
    val remainingamount: Rep[Double] = column[Double]("RemainingAmount", O.Default(0.0))
    /** Database column StartDate SqlType(REAL), Default(Some(0.0)) */
    val startdate: Rep[Option[Double]] = column[Option[Double]]("StartDate", O.Default(Some(0.0)))
    /** Database column EndDate SqlType(REAL), Default(Some(0.0)) */
    val enddate: Rep[Option[Double]] = column[Option[Double]]("EndDate", O.Default(Some(0.0)))
    /** Database column ImpressionCount SqlType(INTEGER), Default(0) */
    val impressioncount: Rep[Int] = column[Int]("ImpressionCount", O.Default(0))
    /** Database column PublisherId SqlType(INTEGER), Default(None) */
    val publisherid: Rep[Option[Int]] = column[Option[Int]]("PublisherId", O.Default(None))
    /** Database column DemandSidePlatformId SqlType(INTEGER) */
    val demandsideplatformid: Rep[Int] = column[Int]("DemandSidePlatformId")
    /** Database column IsRunning SqlType(INTEGER), Length(1,false), Default(0) */
    val isrunning: Rep[Int] = column[Int]("IsRunning", O.Length(1,varying=false), O.Default(0))
    /** Database column Priority SqlType(INTEGER), Length(3,false), Default(999) */
    val priority: Rep[Int] = column[Int]("Priority", O.Length(3,varying=false), O.Default(999))
    /** Database column IsRetrictToUserGender SqlType(INTEGER), Length(1,false), Default(0) */
    val isretricttousergender: Rep[Int] = column[Int]("IsRetrictToUserGender", O.Length(1,varying=false), O.Default(0))
    /** Database column ExpectedUserGender SqlType(TEXT), Length(2,false), Default(None) */
    val expectedusergender: Rep[Option[String]] = column[Option[String]]("ExpectedUserGender", O.Length(2,varying=false), O.Default(None))
    /** Database column ModifiedDate SqlType(TIMESTAMP) */
    val modifieddate: Rep[java.time.Instant] = column[java.time.Instant]("ModifiedDate")
    /** Database column CreatedDateTimestamp SqlType(TIMESTAMP) */
    val createddatetimestamp: Rep[java.time.Instant] = column[java.time.Instant]("CreatedDateTimestamp")

    /** Foreign key referencing Contentcategory (database name ContentCategory_FK_1) */
    lazy val contentcategoryFk = foreignKey("ContentCategory_FK_1", contentcategoryid, Contentcategory)(r => Rep.Some(r.contentcategoryid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Demandsideplatform (database name DemandSidePlatform_FK_2) */
    lazy val demandsideplatformFk = foreignKey("DemandSidePlatform_FK_2", demandsideplatformid, Demandsideplatform)(r => r.demandsideplatformid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Publisher (database name Publisher_FK_3) */
    lazy val publisherFk = foreignKey("Publisher_FK_3", publisherid, Publisher)(r => Rep.Some(r.publisherid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Campaign */
  lazy val Campaign = new TableQuery(tag => new Campaign(tag))

  /** Entity class storing rows of table Campaigntargetcity
   *  @param campaigntargetcityid Database column CampaignTargetCityId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param campaigntargetcity Database column CampaignTargetCity SqlType(TEXT)
   *  @param campaignid Database column CampaignId SqlType(INTEGER) */
  case class CampaigntargetcityRow(campaigntargetcityid: Int, campaigntargetcity: String, campaignid: Int)
  /** GetResult implicit for fetching CampaigntargetcityRow objects using plain SQL queries */
  implicit def GetResultCampaigntargetcityRow(implicit e0: GR[Int], e1: GR[String]): GR[CampaigntargetcityRow] = GR{
    prs => import prs._
    CampaigntargetcityRow.tupled((<<[Int], <<[String], <<[Int]))
  }
  /** Table description of table CampaignTargetCity. Objects of this class serve as prototypes for rows in queries. */
  class Campaigntargetcity(_tableTag: Tag) extends profile.api.Table[CampaigntargetcityRow](_tableTag, "CampaignTargetCity") {
    def * = (campaigntargetcityid, campaigntargetcity, campaignid) <> (CampaigntargetcityRow.tupled, CampaigntargetcityRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(campaigntargetcityid), Rep.Some(campaigntargetcity), Rep.Some(campaignid))).shaped.<>({r=>import r._; _1.map(_=> CampaigntargetcityRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column CampaignTargetCityId SqlType(INTEGER), AutoInc, PrimaryKey */
    val campaigntargetcityid: Rep[Int] = column[Int]("CampaignTargetCityId", O.AutoInc, O.PrimaryKey)
    /** Database column CampaignTargetCity SqlType(TEXT) */
    val campaigntargetcity: Rep[String] = column[String]("CampaignTargetCity")
    /** Database column CampaignId SqlType(INTEGER) */
    val campaignid: Rep[Int] = column[Int]("CampaignId")

    /** Foreign key referencing Campaign (database name Campaign_FK_1) */
    lazy val campaignFk = foreignKey("Campaign_FK_1", campaignid, Campaign)(r => r.campaignid, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Campaigntargetcity */
  lazy val Campaigntargetcity = new TableQuery(tag => new Campaigntargetcity(tag))

  /** Entity class storing rows of table Campaigntargetoperatingsystem
   *  @param campaigntargetoperatingsystemid Database column CampaignTargetOperatingSystemId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param campaigntargetoperatingsystem Database column CampaignTargetOperatingSystem SqlType(TEXT)
   *  @param campaignid Database column CampaignId SqlType(INTEGER) */
  case class CampaigntargetoperatingsystemRow(campaigntargetoperatingsystemid: Int, campaigntargetoperatingsystem: String, campaignid: Int)
  /** GetResult implicit for fetching CampaigntargetoperatingsystemRow objects using plain SQL queries */
  implicit def GetResultCampaigntargetoperatingsystemRow(implicit e0: GR[Int], e1: GR[String]): GR[CampaigntargetoperatingsystemRow] = GR{
    prs => import prs._
    CampaigntargetoperatingsystemRow.tupled((<<[Int], <<[String], <<[Int]))
  }
  /** Table description of table CampaignTargetOperatingSystem. Objects of this class serve as prototypes for rows in queries. */
  class Campaigntargetoperatingsystem(_tableTag: Tag) extends profile.api.Table[CampaigntargetoperatingsystemRow](_tableTag, "CampaignTargetOperatingSystem") {
    def * = (campaigntargetoperatingsystemid, campaigntargetoperatingsystem, campaignid) <> (CampaigntargetoperatingsystemRow.tupled, CampaigntargetoperatingsystemRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(campaigntargetoperatingsystemid), Rep.Some(campaigntargetoperatingsystem), Rep.Some(campaignid))).shaped.<>({r=>import r._; _1.map(_=> CampaigntargetoperatingsystemRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column CampaignTargetOperatingSystemId SqlType(INTEGER), AutoInc, PrimaryKey */
    val campaigntargetoperatingsystemid: Rep[Int] = column[Int]("CampaignTargetOperatingSystemId", O.AutoInc, O.PrimaryKey)
    /** Database column CampaignTargetOperatingSystem SqlType(TEXT) */
    val campaigntargetoperatingsystem: Rep[String] = column[String]("CampaignTargetOperatingSystem")
    /** Database column CampaignId SqlType(INTEGER) */
    val campaignid: Rep[Int] = column[Int]("CampaignId")

    /** Foreign key referencing Campaign (database name Campaign_FK_1) */
    lazy val campaignFk = foreignKey("Campaign_FK_1", campaignid, Campaign)(r => r.campaignid, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Campaigntargetoperatingsystem */
  lazy val Campaigntargetoperatingsystem = new TableQuery(tag => new Campaigntargetoperatingsystem(tag))

  /** Entity class storing rows of table Campaigntargetsite
   *  @param campaigntargetsiteid Database column CampaignTargetSiteId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param campaigntargetsite Database column CampaignTargetSite SqlType(TEXT)
   *  @param campaignid Database column CampaignId SqlType(INTEGER) */
  case class CampaigntargetsiteRow(campaigntargetsiteid: Int, campaigntargetsite: String, campaignid: Int)
  /** GetResult implicit for fetching CampaigntargetsiteRow objects using plain SQL queries */
  implicit def GetResultCampaigntargetsiteRow(implicit e0: GR[Int], e1: GR[String]): GR[CampaigntargetsiteRow] = GR{
    prs => import prs._
    CampaigntargetsiteRow.tupled((<<[Int], <<[String], <<[Int]))
  }
  /** Table description of table CampaignTargetSite. Objects of this class serve as prototypes for rows in queries. */
  class Campaigntargetsite(_tableTag: Tag) extends profile.api.Table[CampaigntargetsiteRow](_tableTag, "CampaignTargetSite") {
    def * = (campaigntargetsiteid, campaigntargetsite, campaignid) <> (CampaigntargetsiteRow.tupled, CampaigntargetsiteRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(campaigntargetsiteid), Rep.Some(campaigntargetsite), Rep.Some(campaignid))).shaped.<>({r=>import r._; _1.map(_=> CampaigntargetsiteRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column CampaignTargetSiteId SqlType(INTEGER), AutoInc, PrimaryKey */
    val campaigntargetsiteid: Rep[Int] = column[Int]("CampaignTargetSiteId", O.AutoInc, O.PrimaryKey)
    /** Database column CampaignTargetSite SqlType(TEXT) */
    val campaigntargetsite: Rep[String] = column[String]("CampaignTargetSite")
    /** Database column CampaignId SqlType(INTEGER) */
    val campaignid: Rep[Int] = column[Int]("CampaignId")

    /** Foreign key referencing Campaign (database name Campaign_FK_1) */
    lazy val campaignFk = foreignKey("Campaign_FK_1", campaignid, Campaign)(r => r.campaignid, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Campaigntargetsite */
  lazy val Campaigntargetsite = new TableQuery(tag => new Campaigntargetsite(tag))

  /** Entity class storing rows of table Contentcategory
   *  @param contentcategoryid Database column ContentCategoryId SqlType(TEXT), PrimaryKey
   *  @param contentcategoryname Database column ContentCategoryName SqlType(TEXT) */
  case class ContentcategoryRow(contentcategoryid: String, contentcategoryname: String)
  /** GetResult implicit for fetching ContentcategoryRow objects using plain SQL queries */
  implicit def GetResultContentcategoryRow(implicit e0: GR[String]): GR[ContentcategoryRow] = GR{
    prs => import prs._
    ContentcategoryRow.tupled((<<[String], <<[String]))
  }
  /** Table description of table ContentCategory. Objects of this class serve as prototypes for rows in queries. */
  class Contentcategory(_tableTag: Tag) extends profile.api.Table[ContentcategoryRow](_tableTag, "ContentCategory") {
    def * = (contentcategoryid, contentcategoryname) <> (ContentcategoryRow.tupled, ContentcategoryRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(contentcategoryid), Rep.Some(contentcategoryname))).shaped.<>({r=>import r._; _1.map(_=> ContentcategoryRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ContentCategoryId SqlType(TEXT), PrimaryKey */
    val contentcategoryid: Rep[String] = column[String]("ContentCategoryId", O.PrimaryKey)
    /** Database column ContentCategoryName SqlType(TEXT) */
    val contentcategoryname: Rep[String] = column[String]("ContentCategoryName")
  }
  /** Collection-like TableQuery object for table Contentcategory */
  lazy val Contentcategory = new TableQuery(tag => new Contentcategory(tag))

  /** Entity class storing rows of table Contentcontext
   *  @param contentcontextid Database column ContentContextId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param context Database column Context SqlType(TEXT) */
  case class ContentcontextRow(contentcontextid: Int, context: String)
  /** GetResult implicit for fetching ContentcontextRow objects using plain SQL queries */
  implicit def GetResultContentcontextRow(implicit e0: GR[Int], e1: GR[String]): GR[ContentcontextRow] = GR{
    prs => import prs._
    ContentcontextRow.tupled((<<[Int], <<[String]))
  }
  /** Table description of table ContentContext. Objects of this class serve as prototypes for rows in queries. */
  class Contentcontext(_tableTag: Tag) extends profile.api.Table[ContentcontextRow](_tableTag, "ContentContext") {
    def * = (contentcontextid, context) <> (ContentcontextRow.tupled, ContentcontextRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(contentcontextid), Rep.Some(context))).shaped.<>({r=>import r._; _1.map(_=> ContentcontextRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ContentContextId SqlType(INTEGER), AutoInc, PrimaryKey */
    val contentcontextid: Rep[Int] = column[Int]("ContentContextId", O.AutoInc, O.PrimaryKey)
    /** Database column Context SqlType(TEXT) */
    val context: Rep[String] = column[String]("Context")
  }
  /** Collection-like TableQuery object for table Contentcontext */
  lazy val Contentcontext = new TableQuery(tag => new Contentcontext(tag))

  /** Entity class storing rows of table Creativeattribute
   *  @param creativeattributeid Database column CreativeAttributeId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param creativeattributedescription Database column CreativeAttributeDescription SqlType(TEXT) */
  case class CreativeattributeRow(creativeattributeid: Int, creativeattributedescription: Option[String])
  /** GetResult implicit for fetching CreativeattributeRow objects using plain SQL queries */
  implicit def GetResultCreativeattributeRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[CreativeattributeRow] = GR{
    prs => import prs._
    CreativeattributeRow.tupled((<<[Int], <<?[String]))
  }
  /** Table description of table CreativeAttribute. Objects of this class serve as prototypes for rows in queries. */
  class Creativeattribute(_tableTag: Tag) extends profile.api.Table[CreativeattributeRow](_tableTag, "CreativeAttribute") {
    def * = (creativeattributeid, creativeattributedescription) <> (CreativeattributeRow.tupled, CreativeattributeRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(creativeattributeid), creativeattributedescription)).shaped.<>({r=>import r._; _1.map(_=> CreativeattributeRow.tupled((_1.get, _2)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column CreativeAttributeId SqlType(INTEGER), AutoInc, PrimaryKey */
    val creativeattributeid: Rep[Int] = column[Int]("CreativeAttributeId", O.AutoInc, O.PrimaryKey)
    /** Database column CreativeAttributeDescription SqlType(TEXT) */
    val creativeattributedescription: Rep[Option[String]] = column[Option[String]]("CreativeAttributeDescription")
  }
  /** Collection-like TableQuery object for table Creativeattribute */
  lazy val Creativeattribute = new TableQuery(tag => new Creativeattribute(tag))

  /** Entity class storing rows of table Demandsideplatform
   *  @param demandsideplatformid Database column DemandSidePlatformId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param demandsideplatformname Database column DemandSidePlatformName SqlType(TEXT) */
  case class DemandsideplatformRow(demandsideplatformid: Int, demandsideplatformname: String)
  /** GetResult implicit for fetching DemandsideplatformRow objects using plain SQL queries */
  implicit def GetResultDemandsideplatformRow(implicit e0: GR[Int], e1: GR[String]): GR[DemandsideplatformRow] = GR{
    prs => import prs._
    DemandsideplatformRow.tupled((<<[Int], <<[String]))
  }
  /** Table description of table DemandSidePlatform. Objects of this class serve as prototypes for rows in queries. */
  class Demandsideplatform(_tableTag: Tag) extends profile.api.Table[DemandsideplatformRow](_tableTag, "DemandSidePlatform") {
    def * = (demandsideplatformid, demandsideplatformname) <> (DemandsideplatformRow.tupled, DemandsideplatformRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(demandsideplatformid), Rep.Some(demandsideplatformname))).shaped.<>({r=>import r._; _1.map(_=> DemandsideplatformRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column DemandSidePlatformId SqlType(INTEGER), AutoInc, PrimaryKey */
    val demandsideplatformid: Rep[Int] = column[Int]("DemandSidePlatformId", O.AutoInc, O.PrimaryKey)
    /** Database column DemandSidePlatformName SqlType(TEXT) */
    val demandsideplatformname: Rep[String] = column[String]("DemandSidePlatformName")
  }
  /** Collection-like TableQuery object for table Demandsideplatform */
  lazy val Demandsideplatform = new TableQuery(tag => new Demandsideplatform(tag))

  /** Entity class storing rows of table Devicetype
   *  @param devicetypeid Database column DeviceTypeId SqlType(INTEGER), PrimaryKey
   *  @param `device typedescription` Database column Device TypeDescription SqlType(TEXT) */
  case class DevicetypeRow(devicetypeid: Int, `device typedescription`: String)
  /** GetResult implicit for fetching DevicetypeRow objects using plain SQL queries */
  implicit def GetResultDevicetypeRow(implicit e0: GR[Int], e1: GR[String]): GR[DevicetypeRow] = GR{
    prs => import prs._
    DevicetypeRow.tupled((<<[Int], <<[String]))
  }
  /** Table description of table DeviceType. Objects of this class serve as prototypes for rows in queries. */
  class Devicetype(_tableTag: Tag) extends profile.api.Table[DevicetypeRow](_tableTag, "DeviceType") {
    def * = (devicetypeid, `device typedescription`) <> (DevicetypeRow.tupled, DevicetypeRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(devicetypeid), Rep.Some(`device typedescription`))).shaped.<>({r=>import r._; _1.map(_=> DevicetypeRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column DeviceTypeId SqlType(INTEGER), PrimaryKey */
    val devicetypeid: Rep[Int] = column[Int]("DeviceTypeId", O.PrimaryKey)
    /** Database column Device TypeDescription SqlType(TEXT) */
    val `device typedescription`: Rep[String] = column[String]("Device TypeDescription")
  }
  /** Collection-like TableQuery object for table Devicetype */
  lazy val Devicetype = new TableQuery(tag => new Devicetype(tag))

  /** Entity class storing rows of table Geomapping
   *  @param geomappingid Database column GeoMappingId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param advertiseid Database column AdvertiseId SqlType(INTEGER), Default(None)
   *  @param country Database column Country SqlType(TEXT), Default(None)
   *  @param hascity Database column HasCity SqlType(INTEGER), Length(1,false), Default(0)
   *  @param city Database column City SqlType(TEXT), Default(None) */
  case class GeomappingRow(geomappingid: Int, advertiseid: Option[Int] = None, country: Option[String] = None, hascity: Int = 0, city: Option[String] = None)
  /** GetResult implicit for fetching GeomappingRow objects using plain SQL queries */
  implicit def GetResultGeomappingRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[String]]): GR[GeomappingRow] = GR{
    prs => import prs._
    GeomappingRow.tupled((<<[Int], <<?[Int], <<?[String], <<[Int], <<?[String]))
  }
  /** Table description of table GeoMapping. Objects of this class serve as prototypes for rows in queries. */
  class Geomapping(_tableTag: Tag) extends profile.api.Table[GeomappingRow](_tableTag, "GeoMapping") {
    def * = (geomappingid, advertiseid, country, hascity, city) <> (GeomappingRow.tupled, GeomappingRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(geomappingid), advertiseid, country, Rep.Some(hascity), city)).shaped.<>({r=>import r._; _1.map(_=> GeomappingRow.tupled((_1.get, _2, _3, _4.get, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column GeoMappingId SqlType(INTEGER), AutoInc, PrimaryKey */
    val geomappingid: Rep[Int] = column[Int]("GeoMappingId", O.AutoInc, O.PrimaryKey)
    /** Database column AdvertiseId SqlType(INTEGER), Default(None) */
    val advertiseid: Rep[Option[Int]] = column[Option[Int]]("AdvertiseId", O.Default(None))
    /** Database column Country SqlType(TEXT), Default(None) */
    val country: Rep[Option[String]] = column[Option[String]]("Country", O.Default(None))
    /** Database column HasCity SqlType(INTEGER), Length(1,false), Default(0) */
    val hascity: Rep[Int] = column[Int]("HasCity", O.Length(1,varying=false), O.Default(0))
    /** Database column City SqlType(TEXT), Default(None) */
    val city: Rep[Option[String]] = column[Option[String]]("City", O.Default(None))

    /** Foreign key referencing Advertise (database name Advertise_FK_1) */
    lazy val advertiseFk = foreignKey("Advertise_FK_1", advertiseid, Advertise)(r => Rep.Some(r.advertiseid), onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Geomapping */
  lazy val Geomapping = new TableQuery(tag => new Geomapping(tag))

  /** Entity class storing rows of table Impression
   *  @param impressionid Database column ImpressionId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param rawimpressionjson Database column RawImpressionJson SqlType(TEXT)
   *  @param bidid Database column BidId SqlType(INTEGER), Default(None)
   *  @param isimpressionservedorwonbyauction Database column IsImpressionServedOrWonByAuction SqlType(INTEGER), Length(1,false), Default(Some(0))
   *  @param bidfloor Database column Bidfloor SqlType(REAL), Default(Some(0.0))
   *  @param bidfloorcur Database column BidfloorCur SqlType(TEXT)
   *  @param hash Database column Hash SqlType(TEXT), Default(None)
   *  @param displaydate Database column DisplayDate SqlType(TIMESTAMP)
   *  @param createddatetimestamp Database column CreatedDateTimestamp SqlType(TIMESTAMP) */
  case class ImpressionRow(impressionid: Int, rawimpressionjson: String, bidid: Option[Int] = None, isimpressionservedorwonbyauction: Option[Int] = Some(0), bidfloor: Option[Double] = Some(0.0), bidfloorcur: Option[String], hash: Option[String] = None, displaydate: Option[java.time.Instant], createddatetimestamp: java.time.Instant)
  /** GetResult implicit for fetching ImpressionRow objects using plain SQL queries */
  implicit def GetResultImpressionRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[Int]], e3: GR[Option[Double]], e4: GR[Option[String]], e5: GR[Option[java.time.Instant]], e6: GR[java.time.Instant]): GR[ImpressionRow] = GR{
    prs => import prs._
    ImpressionRow.tupled((<<[Int], <<[String], <<?[Int], <<?[Int], <<?[Double], <<?[String], <<?[String], <<?[java.time.Instant], <<[java.time.Instant]))
  }
  /** Table description of table Impression. Objects of this class serve as prototypes for rows in queries. */
  class Impression(_tableTag: Tag) extends profile.api.Table[ImpressionRow](_tableTag, "Impression") {
    def * = (impressionid, rawimpressionjson, bidid, isimpressionservedorwonbyauction, bidfloor, bidfloorcur, hash, displaydate, createddatetimestamp) <> (ImpressionRow.tupled, ImpressionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(impressionid), Rep.Some(rawimpressionjson), bidid, isimpressionservedorwonbyauction, bidfloor, bidfloorcur, hash, displaydate, Rep.Some(createddatetimestamp))).shaped.<>({r=>import r._; _1.map(_=> ImpressionRow.tupled((_1.get, _2.get, _3, _4, _5, _6, _7, _8, _9.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ImpressionId SqlType(INTEGER), AutoInc, PrimaryKey */
    val impressionid: Rep[Int] = column[Int]("ImpressionId", O.AutoInc, O.PrimaryKey)
    /** Database column RawImpressionJson SqlType(TEXT) */
    val rawimpressionjson: Rep[String] = column[String]("RawImpressionJson")
    /** Database column BidId SqlType(INTEGER), Default(None) */
    val bidid: Rep[Option[Int]] = column[Option[Int]]("BidId", O.Default(None))
    /** Database column IsImpressionServedOrWonByAuction SqlType(INTEGER), Length(1,false), Default(Some(0)) */
    val isimpressionservedorwonbyauction: Rep[Option[Int]] = column[Option[Int]]("IsImpressionServedOrWonByAuction", O.Length(1,varying=false), O.Default(Some(0)))
    /** Database column Bidfloor SqlType(REAL), Default(Some(0.0)) */
    val bidfloor: Rep[Option[Double]] = column[Option[Double]]("Bidfloor", O.Default(Some(0.0)))
    /** Database column BidfloorCur SqlType(TEXT) */
    val bidfloorcur: Rep[Option[String]] = column[Option[String]]("BidfloorCur")
    /** Database column Hash SqlType(TEXT), Default(None) */
    val hash: Rep[Option[String]] = column[Option[String]]("Hash", O.Default(None))
    /** Database column DisplayDate SqlType(TIMESTAMP) */
    val displaydate: Rep[Option[java.time.Instant]] = column[Option[java.time.Instant]]("DisplayDate")
    /** Database column CreatedDateTimestamp SqlType(TIMESTAMP) */
    val createddatetimestamp: Rep[java.time.Instant] = column[java.time.Instant]("CreatedDateTimestamp")

    /** Foreign key referencing Bid (database name Bid_FK_1) */
    lazy val bidFk = foreignKey("Bid_FK_1", bidid, Bid)(r => Rep.Some(r.bidid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Impression */
  lazy val Impression = new TableQuery(tag => new Impression(tag))

  /** Entity class storing rows of table Keyword
   *  @param keywordid Database column KeywordId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param keyword Database column Keyword SqlType(TEXT) */
  case class KeywordRow(keywordid: Int, keyword: Option[String])
  /** GetResult implicit for fetching KeywordRow objects using plain SQL queries */
  implicit def GetResultKeywordRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[KeywordRow] = GR{
    prs => import prs._
    KeywordRow.tupled((<<[Int], <<?[String]))
  }
  /** Table description of table Keyword. Objects of this class serve as prototypes for rows in queries. */
  class Keyword(_tableTag: Tag) extends profile.api.Table[KeywordRow](_tableTag, "Keyword") {
    def * = (keywordid, keyword) <> (KeywordRow.tupled, KeywordRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(keywordid), keyword)).shaped.<>({r=>import r._; _1.map(_=> KeywordRow.tupled((_1.get, _2)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column KeywordId SqlType(INTEGER), AutoInc, PrimaryKey */
    val keywordid: Rep[Int] = column[Int]("KeywordId", O.AutoInc, O.PrimaryKey)
    /** Database column Keyword SqlType(TEXT) */
    val keyword: Rep[Option[String]] = column[Option[String]]("Keyword")
  }
  /** Collection-like TableQuery object for table Keyword */
  lazy val Keyword = new TableQuery(tag => new Keyword(tag))

  /** Entity class storing rows of table Keywordadvertisemapping
   *  @param keywordadvertisemappingid Database column KeywordAdvertiseMappingId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param advertiseid Database column AdvertiseId SqlType(INTEGER)
   *  @param keywordid Database column KeywordId SqlType(INTEGER) */
  case class KeywordadvertisemappingRow(keywordadvertisemappingid: Int, advertiseid: Int, keywordid: Int)
  /** GetResult implicit for fetching KeywordadvertisemappingRow objects using plain SQL queries */
  implicit def GetResultKeywordadvertisemappingRow(implicit e0: GR[Int]): GR[KeywordadvertisemappingRow] = GR{
    prs => import prs._
    KeywordadvertisemappingRow.tupled((<<[Int], <<[Int], <<[Int]))
  }
  /** Table description of table KeywordAdvertiseMapping. Objects of this class serve as prototypes for rows in queries. */
  class Keywordadvertisemapping(_tableTag: Tag) extends profile.api.Table[KeywordadvertisemappingRow](_tableTag, "KeywordAdvertiseMapping") {
    def * = (keywordadvertisemappingid, advertiseid, keywordid) <> (KeywordadvertisemappingRow.tupled, KeywordadvertisemappingRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(keywordadvertisemappingid), Rep.Some(advertiseid), Rep.Some(keywordid))).shaped.<>({r=>import r._; _1.map(_=> KeywordadvertisemappingRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column KeywordAdvertiseMappingId SqlType(INTEGER), AutoInc, PrimaryKey */
    val keywordadvertisemappingid: Rep[Int] = column[Int]("KeywordAdvertiseMappingId", O.AutoInc, O.PrimaryKey)
    /** Database column AdvertiseId SqlType(INTEGER) */
    val advertiseid: Rep[Int] = column[Int]("AdvertiseId")
    /** Database column KeywordId SqlType(INTEGER) */
    val keywordid: Rep[Int] = column[Int]("KeywordId")

    /** Foreign key referencing Advertise (database name Advertise_FK_1) */
    lazy val advertiseFk = foreignKey("Advertise_FK_1", advertiseid, Advertise)(r => r.advertiseid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Keyword (database name Keyword_FK_2) */
    lazy val keywordFk = foreignKey("Keyword_FK_2", keywordid, Keyword)(r => r.keywordid, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Keywordadvertisemapping */
  lazy val Keywordadvertisemapping = new TableQuery(tag => new Keywordadvertisemapping(tag))

  /** Entity class storing rows of table Keywordadvertisemappingidsview
   *  @param keywordid Database column KeywordId SqlType(INTEGER)
   *  @param keyword Database column Keyword SqlType(TEXT)
   *  @param keywordadvertisemappingid Database column KeywordAdvertiseMappingId SqlType(INTEGER)
   *  @param advertiseid Database column AdvertiseId SqlType(INTEGER)
   *  @param campaignid Database column CampaignId SqlType(INTEGER)
   *  @param banneradvertisetypeid Database column BannerAdvertiseTypeId SqlType(INTEGER)
   *  @param isvideo Database column IsVideo SqlType(INTEGER), Length(1,false)
   *  @param isbanner Database column IsBanner SqlType(INTEGER), Length(1,false)
   *  @param contentcontextid Database column ContentContextId SqlType(INTEGER) */
  case class KeywordadvertisemappingidsviewRow(keywordid: Option[Int], keyword: Option[String], keywordadvertisemappingid: Option[Int], advertiseid: Option[Int], campaignid: Option[Int], banneradvertisetypeid: Option[Int], isvideo: Option[Int], isbanner: Option[Int], contentcontextid: Option[Int])
  /** GetResult implicit for fetching KeywordadvertisemappingidsviewRow objects using plain SQL queries */
  implicit def GetResultKeywordadvertisemappingidsviewRow(implicit e0: GR[Option[Int]], e1: GR[Option[String]]): GR[KeywordadvertisemappingidsviewRow] = GR{
    prs => import prs._
    KeywordadvertisemappingidsviewRow.tupled((<<?[Int], <<?[String], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int]))
  }
  /** Table description of table KeywordAdvertiseMappingIdsView. Objects of this class serve as prototypes for rows in queries. */
  class Keywordadvertisemappingidsview(_tableTag: Tag) extends profile.api.Table[KeywordadvertisemappingidsviewRow](_tableTag, "KeywordAdvertiseMappingIdsView") {
    def * = (keywordid, keyword, keywordadvertisemappingid, advertiseid, campaignid, banneradvertisetypeid, isvideo, isbanner, contentcontextid) <> (KeywordadvertisemappingidsviewRow.tupled, KeywordadvertisemappingidsviewRow.unapply)

    /** Database column KeywordId SqlType(INTEGER) */
    val keywordid: Rep[Option[Int]] = column[Option[Int]]("KeywordId")
    /** Database column Keyword SqlType(TEXT) */
    val keyword: Rep[Option[String]] = column[Option[String]]("Keyword")
    /** Database column KeywordAdvertiseMappingId SqlType(INTEGER) */
    val keywordadvertisemappingid: Rep[Option[Int]] = column[Option[Int]]("KeywordAdvertiseMappingId")
    /** Database column AdvertiseId SqlType(INTEGER) */
    val advertiseid: Rep[Option[Int]] = column[Option[Int]]("AdvertiseId")
    /** Database column CampaignId SqlType(INTEGER) */
    val campaignid: Rep[Option[Int]] = column[Option[Int]]("CampaignId")
    /** Database column BannerAdvertiseTypeId SqlType(INTEGER) */
    val banneradvertisetypeid: Rep[Option[Int]] = column[Option[Int]]("BannerAdvertiseTypeId")
    /** Database column IsVideo SqlType(INTEGER), Length(1,false) */
    val isvideo: Rep[Option[Int]] = column[Option[Int]]("IsVideo", O.Length(1,varying=false))
    /** Database column IsBanner SqlType(INTEGER), Length(1,false) */
    val isbanner: Rep[Option[Int]] = column[Option[Int]]("IsBanner", O.Length(1,varying=false))
    /** Database column ContentContextId SqlType(INTEGER) */
    val contentcontextid: Rep[Option[Int]] = column[Option[Int]]("ContentContextId")
  }
  /** Collection-like TableQuery object for table Keywordadvertisemappingidsview */
  lazy val Keywordadvertisemappingidsview = new TableQuery(tag => new Keywordadvertisemappingidsview(tag))

  /** Entity class storing rows of table Logtrace
   *  @param logtraceid Database column LogTraceId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param methodname Database column MethodName SqlType(TEXT)
   *  @param classname Database column ClassName SqlType(TEXT)
   *  @param request Database column Request SqlType(TEXT)
   *  @param message Database column Message SqlType(TEXT)
   *  @param entitydata Database column EntityData SqlType(TEXT)
   *  @param databasetransactiontypeid Database column DatabaseTransactionTypeId SqlType(INTEGER)
   *  @param createddatetimestamp Database column CreatedDateTimestamp SqlType(TIMESTAMP) */
  case class LogtraceRow(logtraceid: Int, methodname: Option[String], classname: Option[String], request: Option[String], message: Option[String], entitydata: Option[String], databasetransactiontypeid: Option[Int], createddatetimestamp: Option[java.time.Instant])
  /** GetResult implicit for fetching LogtraceRow objects using plain SQL queries */
  implicit def GetResultLogtraceRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Int]], e3: GR[Option[java.time.Instant]]): GR[LogtraceRow] = GR{
    prs => import prs._
    LogtraceRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[Int], <<?[java.time.Instant]))
  }
  /** Table description of table LogTrace. Objects of this class serve as prototypes for rows in queries. */
  class Logtrace(_tableTag: Tag) extends profile.api.Table[LogtraceRow](_tableTag, "LogTrace") {
    def * = (logtraceid, methodname, classname, request, message, entitydata, databasetransactiontypeid, createddatetimestamp) <> (LogtraceRow.tupled, LogtraceRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(logtraceid), methodname, classname, request, message, entitydata, databasetransactiontypeid, createddatetimestamp)).shaped.<>({r=>import r._; _1.map(_=> LogtraceRow.tupled((_1.get, _2, _3, _4, _5, _6, _7, _8)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column LogTraceId SqlType(INTEGER), AutoInc, PrimaryKey */
    val logtraceid: Rep[Int] = column[Int]("LogTraceId", O.AutoInc, O.PrimaryKey)
    /** Database column MethodName SqlType(TEXT) */
    val methodname: Rep[Option[String]] = column[Option[String]]("MethodName")
    /** Database column ClassName SqlType(TEXT) */
    val classname: Rep[Option[String]] = column[Option[String]]("ClassName")
    /** Database column Request SqlType(TEXT) */
    val request: Rep[Option[String]] = column[Option[String]]("Request")
    /** Database column Message SqlType(TEXT) */
    val message: Rep[Option[String]] = column[Option[String]]("Message")
    /** Database column EntityData SqlType(TEXT) */
    val entitydata: Rep[Option[String]] = column[Option[String]]("EntityData")
    /** Database column DatabaseTransactionTypeId SqlType(INTEGER) */
    val databasetransactiontypeid: Rep[Option[Int]] = column[Option[Int]]("DatabaseTransactionTypeId")
    /** Database column CreatedDateTimestamp SqlType(TIMESTAMP) */
    val createddatetimestamp: Rep[Option[java.time.Instant]] = column[Option[java.time.Instant]]("CreatedDateTimestamp")
  }
  /** Collection-like TableQuery object for table Logtrace */
  lazy val Logtrace = new TableQuery(tag => new Logtrace(tag))

  /** Entity class storing rows of table Lostbid
   *  @param lostbidid Database column LostBidId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param bidid Database column BidId SqlType(INTEGER)
   *  @param reason Database column Reason SqlType(TEXT)
   *  @param losingprice Database column LosingPrice SqlType(REAL)
   *  @param currency Database column Currency SqlType(TEXT), Length(5,false), Default(Some(USD))
   *  @param demandsideplatformid Database column DemandSidePlatformId SqlType(INTEGER)
   *  @param createddatetimestamp Database column CreatedDateTimestamp SqlType(TIMESTAMP) */
  case class LostbidRow(lostbidid: Int, bidid: Option[Int], reason: Option[String], losingprice: Option[Double], currency: Option[String] = Some("USD"), demandsideplatformid: Int, createddatetimestamp: java.time.Instant)
  /** GetResult implicit for fetching LostbidRow objects using plain SQL queries */
  implicit def GetResultLostbidRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[String]], e3: GR[Option[Double]], e4: GR[java.time.Instant]): GR[LostbidRow] = GR{
    prs => import prs._
    LostbidRow.tupled((<<[Int], <<?[Int], <<?[String], <<?[Double], <<?[String], <<[Int], <<[java.time.Instant]))
  }
  /** Table description of table LostBid. Objects of this class serve as prototypes for rows in queries. */
  class Lostbid(_tableTag: Tag) extends profile.api.Table[LostbidRow](_tableTag, "LostBid") {
    def * = (lostbidid, bidid, reason, losingprice, currency, demandsideplatformid, createddatetimestamp) <> (LostbidRow.tupled, LostbidRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(lostbidid), bidid, reason, losingprice, currency, Rep.Some(demandsideplatformid), Rep.Some(createddatetimestamp))).shaped.<>({r=>import r._; _1.map(_=> LostbidRow.tupled((_1.get, _2, _3, _4, _5, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column LostBidId SqlType(INTEGER), AutoInc, PrimaryKey */
    val lostbidid: Rep[Int] = column[Int]("LostBidId", O.AutoInc, O.PrimaryKey)
    /** Database column BidId SqlType(INTEGER) */
    val bidid: Rep[Option[Int]] = column[Option[Int]]("BidId")
    /** Database column Reason SqlType(TEXT) */
    val reason: Rep[Option[String]] = column[Option[String]]("Reason")
    /** Database column LosingPrice SqlType(REAL) */
    val losingprice: Rep[Option[Double]] = column[Option[Double]]("LosingPrice")
    /** Database column Currency SqlType(TEXT), Length(5,false), Default(Some(USD)) */
    val currency: Rep[Option[String]] = column[Option[String]]("Currency", O.Length(5,varying=false), O.Default(Some("USD")))
    /** Database column DemandSidePlatformId SqlType(INTEGER) */
    val demandsideplatformid: Rep[Int] = column[Int]("DemandSidePlatformId")
    /** Database column CreatedDateTimestamp SqlType(TIMESTAMP) */
    val createddatetimestamp: Rep[java.time.Instant] = column[java.time.Instant]("CreatedDateTimestamp")

    /** Foreign key referencing Bid (database name Bid_FK_1) */
    lazy val bidFk = foreignKey("Bid_FK_1", bidid, Bid)(r => Rep.Some(r.bidid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Demandsideplatform (database name DemandSidePlatform_FK_2) */
    lazy val demandsideplatformFk = foreignKey("DemandSidePlatform_FK_2", demandsideplatformid, Demandsideplatform)(r => r.demandsideplatformid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Lostbid */
  lazy val Lostbid = new TableQuery(tag => new Lostbid(tag))

  /** Entity class storing rows of table Nobidresponsetype
   *  @param nobidresponsetypeid Database column NoBidResponseTypeId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param nobidresponsetype Database column NoBidResponseType SqlType(TEXT) */
  case class NobidresponsetypeRow(nobidresponsetypeid: Int, nobidresponsetype: Option[String])
  /** GetResult implicit for fetching NobidresponsetypeRow objects using plain SQL queries */
  implicit def GetResultNobidresponsetypeRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[NobidresponsetypeRow] = GR{
    prs => import prs._
    NobidresponsetypeRow.tupled((<<[Int], <<?[String]))
  }
  /** Table description of table NoBidResponseType. Objects of this class serve as prototypes for rows in queries. */
  class Nobidresponsetype(_tableTag: Tag) extends profile.api.Table[NobidresponsetypeRow](_tableTag, "NoBidResponseType") {
    def * = (nobidresponsetypeid, nobidresponsetype) <> (NobidresponsetypeRow.tupled, NobidresponsetypeRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(nobidresponsetypeid), nobidresponsetype)).shaped.<>({r=>import r._; _1.map(_=> NobidresponsetypeRow.tupled((_1.get, _2)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column NoBidResponseTypeId SqlType(INTEGER), AutoInc, PrimaryKey */
    val nobidresponsetypeid: Rep[Int] = column[Int]("NoBidResponseTypeId", O.AutoInc, O.PrimaryKey)
    /** Database column NoBidResponseType SqlType(TEXT) */
    val nobidresponsetype: Rep[Option[String]] = column[Option[String]]("NoBidResponseType")
  }
  /** Collection-like TableQuery object for table Nobidresponsetype */
  lazy val Nobidresponsetype = new TableQuery(tag => new Nobidresponsetype(tag))

  /** Entity class storing rows of table Privatemarketplacedeal
   *  @param privatemarketplacedealid Database column PrivateMarketPlaceDealId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param impressionid Database column ImpressionId SqlType(INTEGER), Default(None)
   *  @param bidrequestid Database column BidRequestId SqlType(INTEGER), Default(None)
   *  @param bidfloor Database column BidFloor SqlType(REAL), Default(Some(0.0))
   *  @param bidfloorcurrency Database column BidFloorCurrency SqlType(TEXT), Length(5,false) */
  case class PrivatemarketplacedealRow(privatemarketplacedealid: Int, impressionid: Option[Int] = None, bidrequestid: Option[Int] = None, bidfloor: Option[Double] = Some(0.0), bidfloorcurrency: Option[String])
  /** GetResult implicit for fetching PrivatemarketplacedealRow objects using plain SQL queries */
  implicit def GetResultPrivatemarketplacedealRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[Double]], e3: GR[Option[String]]): GR[PrivatemarketplacedealRow] = GR{
    prs => import prs._
    PrivatemarketplacedealRow.tupled((<<[Int], <<?[Int], <<?[Int], <<?[Double], <<?[String]))
  }
  /** Table description of table PrivateMarketPlaceDeal. Objects of this class serve as prototypes for rows in queries. */
  class Privatemarketplacedeal(_tableTag: Tag) extends profile.api.Table[PrivatemarketplacedealRow](_tableTag, "PrivateMarketPlaceDeal") {
    def * = (privatemarketplacedealid, impressionid, bidrequestid, bidfloor, bidfloorcurrency) <> (PrivatemarketplacedealRow.tupled, PrivatemarketplacedealRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(privatemarketplacedealid), impressionid, bidrequestid, bidfloor, bidfloorcurrency)).shaped.<>({r=>import r._; _1.map(_=> PrivatemarketplacedealRow.tupled((_1.get, _2, _3, _4, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column PrivateMarketPlaceDealId SqlType(INTEGER), AutoInc, PrimaryKey */
    val privatemarketplacedealid: Rep[Int] = column[Int]("PrivateMarketPlaceDealId", O.AutoInc, O.PrimaryKey)
    /** Database column ImpressionId SqlType(INTEGER), Default(None) */
    val impressionid: Rep[Option[Int]] = column[Option[Int]]("ImpressionId", O.Default(None))
    /** Database column BidRequestId SqlType(INTEGER), Default(None) */
    val bidrequestid: Rep[Option[Int]] = column[Option[Int]]("BidRequestId", O.Default(None))
    /** Database column BidFloor SqlType(REAL), Default(Some(0.0)) */
    val bidfloor: Rep[Option[Double]] = column[Option[Double]]("BidFloor", O.Default(Some(0.0)))
    /** Database column BidFloorCurrency SqlType(TEXT), Length(5,false) */
    val bidfloorcurrency: Rep[Option[String]] = column[Option[String]]("BidFloorCurrency", O.Length(5,varying=false))
  }
  /** Collection-like TableQuery object for table Privatemarketplacedeal */
  lazy val Privatemarketplacedeal = new TableQuery(tag => new Privatemarketplacedeal(tag))

  /** Entity class storing rows of table Publisher
   *  @param publisherid Database column PublisherId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param publishername Database column PublisherName SqlType(TEXT)
   *  @param publisherwebsite Database column PublisherWebsite SqlType(TEXT)
   *  @param publisheraddress Database column PublisherAddress SqlType(TEXT)
   *  @param demandsideplatformid Database column DemandSidePlatformId SqlType(INTEGER) */
  case class PublisherRow(publisherid: Int, publishername: String, publisherwebsite: String, publisheraddress: String, demandsideplatformid: Int)
  /** GetResult implicit for fetching PublisherRow objects using plain SQL queries */
  implicit def GetResultPublisherRow(implicit e0: GR[Int], e1: GR[String]): GR[PublisherRow] = GR{
    prs => import prs._
    PublisherRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[Int]))
  }
  /** Table description of table Publisher. Objects of this class serve as prototypes for rows in queries. */
  class Publisher(_tableTag: Tag) extends profile.api.Table[PublisherRow](_tableTag, "Publisher") {
    def * = (publisherid, publishername, publisherwebsite, publisheraddress, demandsideplatformid) <> (PublisherRow.tupled, PublisherRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(publisherid), Rep.Some(publishername), Rep.Some(publisherwebsite), Rep.Some(publisheraddress), Rep.Some(demandsideplatformid))).shaped.<>({r=>import r._; _1.map(_=> PublisherRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column PublisherId SqlType(INTEGER), AutoInc, PrimaryKey */
    val publisherid: Rep[Int] = column[Int]("PublisherId", O.AutoInc, O.PrimaryKey)
    /** Database column PublisherName SqlType(TEXT) */
    val publishername: Rep[String] = column[String]("PublisherName")
    /** Database column PublisherWebsite SqlType(TEXT) */
    val publisherwebsite: Rep[String] = column[String]("PublisherWebsite")
    /** Database column PublisherAddress SqlType(TEXT) */
    val publisheraddress: Rep[String] = column[String]("PublisherAddress")
    /** Database column DemandSidePlatformId SqlType(INTEGER) */
    val demandsideplatformid: Rep[Int] = column[Int]("DemandSidePlatformId")

    /** Foreign key referencing Demandsideplatform (database name DemandSidePlatform_FK_1) */
    lazy val demandsideplatformFk = foreignKey("DemandSidePlatform_FK_1", demandsideplatformid, Demandsideplatform)(r => r.demandsideplatformid, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Publisher */
  lazy val Publisher = new TableQuery(tag => new Publisher(tag))

  /** Entity class storing rows of table Seatbid
   *  @param seatbidid Database column SeatBidId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param bidrequestid Database column BidRequestId SqlType(INTEGER), Default(None)
   *  @param bidresponseid Database column BidResponseId SqlType(INTEGER), Default(None)
   *  @param auctionid Database column AuctionId SqlType(INTEGER), Default(None)
   *  @param demandsideplatformid Database column DemandSidePlatformId SqlType(INTEGER), Default(None)
   *  @param isgroupbid Database column IsGroupBid SqlType(INTEGER), Length(1,false), Default(Some(0))
   *  @param seatbidrawjson Database column SeatBidRawJson SqlType(TEXT), Default(None)
   *  @param createddatetimestamp Database column CreatedDateTimestamp SqlType(TIMESTAMP) */
  case class SeatbidRow(seatbidid: Int, bidrequestid: Option[Int] = None, bidresponseid: Option[Int] = None, auctionid: Option[Int] = None, demandsideplatformid: Option[Int] = None, isgroupbid: Option[Int] = Some(0), seatbidrawjson: Option[String] = None, createddatetimestamp: java.time.Instant)
  /** GetResult implicit for fetching SeatbidRow objects using plain SQL queries */
  implicit def GetResultSeatbidRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[String]], e3: GR[java.time.Instant]): GR[SeatbidRow] = GR{
    prs => import prs._
    SeatbidRow.tupled((<<[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[String], <<[java.time.Instant]))
  }
  /** Table description of table SeatBid. Objects of this class serve as prototypes for rows in queries. */
  class Seatbid(_tableTag: Tag) extends profile.api.Table[SeatbidRow](_tableTag, "SeatBid") {
    def * = (seatbidid, bidrequestid, bidresponseid, auctionid, demandsideplatformid, isgroupbid, seatbidrawjson, createddatetimestamp) <> (SeatbidRow.tupled, SeatbidRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(seatbidid), bidrequestid, bidresponseid, auctionid, demandsideplatformid, isgroupbid, seatbidrawjson, Rep.Some(createddatetimestamp))).shaped.<>({r=>import r._; _1.map(_=> SeatbidRow.tupled((_1.get, _2, _3, _4, _5, _6, _7, _8.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column SeatBidId SqlType(INTEGER), AutoInc, PrimaryKey */
    val seatbidid: Rep[Int] = column[Int]("SeatBidId", O.AutoInc, O.PrimaryKey)
    /** Database column BidRequestId SqlType(INTEGER), Default(None) */
    val bidrequestid: Rep[Option[Int]] = column[Option[Int]]("BidRequestId", O.Default(None))
    /** Database column BidResponseId SqlType(INTEGER), Default(None) */
    val bidresponseid: Rep[Option[Int]] = column[Option[Int]]("BidResponseId", O.Default(None))
    /** Database column AuctionId SqlType(INTEGER), Default(None) */
    val auctionid: Rep[Option[Int]] = column[Option[Int]]("AuctionId", O.Default(None))
    /** Database column DemandSidePlatformId SqlType(INTEGER), Default(None) */
    val demandsideplatformid: Rep[Option[Int]] = column[Option[Int]]("DemandSidePlatformId", O.Default(None))
    /** Database column IsGroupBid SqlType(INTEGER), Length(1,false), Default(Some(0)) */
    val isgroupbid: Rep[Option[Int]] = column[Option[Int]]("IsGroupBid", O.Length(1,varying=false), O.Default(Some(0)))
    /** Database column SeatBidRawJson SqlType(TEXT), Default(None) */
    val seatbidrawjson: Rep[Option[String]] = column[Option[String]]("SeatBidRawJson", O.Default(None))
    /** Database column CreatedDateTimestamp SqlType(TIMESTAMP) */
    val createddatetimestamp: Rep[java.time.Instant] = column[java.time.Instant]("CreatedDateTimestamp")

    /** Foreign key referencing Auction (database name Auction_FK_1) */
    lazy val auctionFk = foreignKey("Auction_FK_1", auctionid, Auction)(r => Rep.Some(r.auctionid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Bidrequest (database name BidRequest_FK_2) */
    lazy val bidrequestFk = foreignKey("BidRequest_FK_2", bidrequestid, Bidrequest)(r => Rep.Some(r.bidrequestid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Bidresponse (database name BidResponse_FK_3) */
    lazy val bidresponseFk = foreignKey("BidResponse_FK_3", bidresponseid, Bidresponse)(r => Rep.Some(r.bidresponseid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Demandsideplatform (database name DemandSidePlatform_FK_4) */
    lazy val demandsideplatformFk = foreignKey("DemandSidePlatform_FK_4", demandsideplatformid, Demandsideplatform)(r => Rep.Some(r.demandsideplatformid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Seatbid */
  lazy val Seatbid = new TableQuery(tag => new Seatbid(tag))

  /** Entity class storing rows of table Transaction
   *  @param transactionid Database column TransactionId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param advertiseid Database column AdvertiseId SqlType(INTEGER)
   *  @param spend Database column Spend SqlType(REAL)
   *  @param impressionid Database column ImpressionId SqlType(INTEGER)
   *  @param currency Database column Currency SqlType(TEXT), Length(5,false), Default(Some(USD))
   *  @param createddatetimestamp Database column CreatedDateTimestamp SqlType(TIMESTAMP) */
  case class TransactionRow(transactionid: Int, advertiseid: Int, spend: Double, impressionid: Int, currency: Option[String] = Some("USD"), createddatetimestamp: java.time.Instant)
  /** GetResult implicit for fetching TransactionRow objects using plain SQL queries */
  implicit def GetResultTransactionRow(implicit e0: GR[Int], e1: GR[Double], e2: GR[Option[String]], e3: GR[java.time.Instant]): GR[TransactionRow] = GR{
    prs => import prs._
    TransactionRow.tupled((<<[Int], <<[Int], <<[Double], <<[Int], <<?[String], <<[java.time.Instant]))
  }
  /** Table description of table Transaction. Objects of this class serve as prototypes for rows in queries. */
  class Transaction(_tableTag: Tag) extends profile.api.Table[TransactionRow](_tableTag, "Transaction") {
    def * = (transactionid, advertiseid, spend, impressionid, currency, createddatetimestamp) <> (TransactionRow.tupled, TransactionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(transactionid), Rep.Some(advertiseid), Rep.Some(spend), Rep.Some(impressionid), currency, Rep.Some(createddatetimestamp))).shaped.<>({r=>import r._; _1.map(_=> TransactionRow.tupled((_1.get, _2.get, _3.get, _4.get, _5, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column TransactionId SqlType(INTEGER), AutoInc, PrimaryKey */
    val transactionid: Rep[Int] = column[Int]("TransactionId", O.AutoInc, O.PrimaryKey)
    /** Database column AdvertiseId SqlType(INTEGER) */
    val advertiseid: Rep[Int] = column[Int]("AdvertiseId")
    /** Database column Spend SqlType(REAL) */
    val spend: Rep[Double] = column[Double]("Spend")
    /** Database column ImpressionId SqlType(INTEGER) */
    val impressionid: Rep[Int] = column[Int]("ImpressionId")
    /** Database column Currency SqlType(TEXT), Length(5,false), Default(Some(USD)) */
    val currency: Rep[Option[String]] = column[Option[String]]("Currency", O.Length(5,varying=false), O.Default(Some("USD")))
    /** Database column CreatedDateTimestamp SqlType(TIMESTAMP) */
    val createddatetimestamp: Rep[java.time.Instant] = column[java.time.Instant]("CreatedDateTimestamp")

    /** Foreign key referencing Advertise (database name Advertise_FK_1) */
    lazy val advertiseFk = foreignKey("Advertise_FK_1", advertiseid, Advertise)(r => r.advertiseid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Impression (database name Impression_FK_2) */
    lazy val impressionFk = foreignKey("Impression_FK_2", impressionid, Impression)(r => r.impressionid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Transaction */
  lazy val Transaction = new TableQuery(tag => new Transaction(tag))

  /** Entity class storing rows of table Userclassification
   *  @param userclassificationid Database column UserClassificationId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param userkeywordscontains Database column UserKeywordsContains SqlType(TEXT), Default(None)
   *  @param internetprotocol Database column InternetProtocol SqlType(TEXT), Default(None)
   *  @param country Database column Country SqlType(TEXT), Default(None)
   *  @param lat Database column lat SqlType(REAL), Default(None)
   *  @param lon Database column lon SqlType(REAL), Default(None)
   *  @param iswhitelist Database column IsWhiteList SqlType(INTEGER), Length(1,false), Default(Some(0))
   *  @param isblacklist Database column IsBlackList SqlType(INTEGER), Length(1,false), Default(Some(0)) */
  case class UserclassificationRow(userclassificationid: Int, userkeywordscontains: Option[String] = None, internetprotocol: Option[String] = None, country: Option[String] = None, lat: Option[Double] = None, lon: Option[Double] = None, iswhitelist: Option[Int] = Some(0), isblacklist: Option[Int] = Some(0))
  /** GetResult implicit for fetching UserclassificationRow objects using plain SQL queries */
  implicit def GetResultUserclassificationRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Double]], e3: GR[Option[Int]]): GR[UserclassificationRow] = GR{
    prs => import prs._
    UserclassificationRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[Double], <<?[Double], <<?[Int], <<?[Int]))
  }
  /** Table description of table UserClassification. Objects of this class serve as prototypes for rows in queries. */
  class Userclassification(_tableTag: Tag) extends profile.api.Table[UserclassificationRow](_tableTag, "UserClassification") {
    def * = (userclassificationid, userkeywordscontains, internetprotocol, country, lat, lon, iswhitelist, isblacklist) <> (UserclassificationRow.tupled, UserclassificationRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(userclassificationid), userkeywordscontains, internetprotocol, country, lat, lon, iswhitelist, isblacklist)).shaped.<>({r=>import r._; _1.map(_=> UserclassificationRow.tupled((_1.get, _2, _3, _4, _5, _6, _7, _8)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column UserClassificationId SqlType(INTEGER), AutoInc, PrimaryKey */
    val userclassificationid: Rep[Int] = column[Int]("UserClassificationId", O.AutoInc, O.PrimaryKey)
    /** Database column UserKeywordsContains SqlType(TEXT), Default(None) */
    val userkeywordscontains: Rep[Option[String]] = column[Option[String]]("UserKeywordsContains", O.Default(None))
    /** Database column InternetProtocol SqlType(TEXT), Default(None) */
    val internetprotocol: Rep[Option[String]] = column[Option[String]]("InternetProtocol", O.Default(None))
    /** Database column Country SqlType(TEXT), Default(None) */
    val country: Rep[Option[String]] = column[Option[String]]("Country", O.Default(None))
    /** Database column lat SqlType(REAL), Default(None) */
    val lat: Rep[Option[Double]] = column[Option[Double]]("lat", O.Default(None))
    /** Database column lon SqlType(REAL), Default(None) */
    val lon: Rep[Option[Double]] = column[Option[Double]]("lon", O.Default(None))
    /** Database column IsWhiteList SqlType(INTEGER), Length(1,false), Default(Some(0)) */
    val iswhitelist: Rep[Option[Int]] = column[Option[Int]]("IsWhiteList", O.Length(1,varying=false), O.Default(Some(0)))
    /** Database column IsBlackList SqlType(INTEGER), Length(1,false), Default(Some(0)) */
    val isblacklist: Rep[Option[Int]] = column[Option[Int]]("IsBlackList", O.Length(1,varying=false), O.Default(Some(0)))
  }
  /** Collection-like TableQuery object for table Userclassification */
  lazy val Userclassification = new TableQuery(tag => new Userclassification(tag))

  /** Entity class storing rows of table Videoplaybackmethod
   *  @param videoplaybackmethodid Database column VideoPlaybackMethodId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param videoplaybackmethoddescription Database column VideoPlaybackMethodDescription SqlType(TEXT) */
  case class VideoplaybackmethodRow(videoplaybackmethodid: Int, videoplaybackmethoddescription: String)
  /** GetResult implicit for fetching VideoplaybackmethodRow objects using plain SQL queries */
  implicit def GetResultVideoplaybackmethodRow(implicit e0: GR[Int], e1: GR[String]): GR[VideoplaybackmethodRow] = GR{
    prs => import prs._
    VideoplaybackmethodRow.tupled((<<[Int], <<[String]))
  }
  /** Table description of table VideoPlaybackMethod. Objects of this class serve as prototypes for rows in queries. */
  class Videoplaybackmethod(_tableTag: Tag) extends profile.api.Table[VideoplaybackmethodRow](_tableTag, "VideoPlaybackMethod") {
    def * = (videoplaybackmethodid, videoplaybackmethoddescription) <> (VideoplaybackmethodRow.tupled, VideoplaybackmethodRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(videoplaybackmethodid), Rep.Some(videoplaybackmethoddescription))).shaped.<>({r=>import r._; _1.map(_=> VideoplaybackmethodRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column VideoPlaybackMethodId SqlType(INTEGER), AutoInc, PrimaryKey */
    val videoplaybackmethodid: Rep[Int] = column[Int]("VideoPlaybackMethodId", O.AutoInc, O.PrimaryKey)
    /** Database column VideoPlaybackMethodDescription SqlType(TEXT) */
    val videoplaybackmethoddescription: Rep[String] = column[String]("VideoPlaybackMethodDescription")
  }
  /** Collection-like TableQuery object for table Videoplaybackmethod */
  lazy val Videoplaybackmethod = new TableQuery(tag => new Videoplaybackmethod(tag))

  /** Entity class storing rows of table Videoresponseprotocol
   *  @param videoresponseprotocolid Database column VideoResponseProtocolId SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param videoresponseprotocoldescription Database column VideoResponseProtocolDescription SqlType(TEXT) */
  case class VideoresponseprotocolRow(videoresponseprotocolid: Int, videoresponseprotocoldescription: String)
  /** GetResult implicit for fetching VideoresponseprotocolRow objects using plain SQL queries */
  implicit def GetResultVideoresponseprotocolRow(implicit e0: GR[Int], e1: GR[String]): GR[VideoresponseprotocolRow] = GR{
    prs => import prs._
    VideoresponseprotocolRow.tupled((<<[Int], <<[String]))
  }
  /** Table description of table VideoResponseProtocol. Objects of this class serve as prototypes for rows in queries. */
  class Videoresponseprotocol(_tableTag: Tag) extends profile.api.Table[VideoresponseprotocolRow](_tableTag, "VideoResponseProtocol") {
    def * = (videoresponseprotocolid, videoresponseprotocoldescription) <> (VideoresponseprotocolRow.tupled, VideoresponseprotocolRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(videoresponseprotocolid), Rep.Some(videoresponseprotocoldescription))).shaped.<>({r=>import r._; _1.map(_=> VideoresponseprotocolRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column VideoResponseProtocolId SqlType(INTEGER), AutoInc, PrimaryKey */
    val videoresponseprotocolid: Rep[Int] = column[Int]("VideoResponseProtocolId", O.AutoInc, O.PrimaryKey)
    /** Database column VideoResponseProtocolDescription SqlType(TEXT) */
    val videoresponseprotocoldescription: Rep[String] = column[String]("VideoResponseProtocolDescription")
  }
  /** Collection-like TableQuery object for table Videoresponseprotocol */
  lazy val Videoresponseprotocol = new TableQuery(tag => new Videoresponseprotocol(tag))

  /** Entity class storing rows of table Winningpriceinfoview
   *  @param bidid Database column BidId SqlType(INTEGER)
   *  @param impressionid Database column ImpressionId SqlType(INTEGER)
   *  @param seatbidid Database column SeatBidId SqlType(INTEGER)
   *  @param campaignid Database column CampaignId SqlType(INTEGER)
   *  @param auctionid Database column AuctionId SqlType(INTEGER)
   *  @param bidrequestid Database column BidRequestId SqlType(INTEGER)
   *  @param bidresponseid Database column BidResponseId SqlType(INTEGER)
   *  @param advertiseid Database column AdvertiseId SqlType(INTEGER)
   *  @param demandsideplatformid Database column DemandSidePlatformId SqlType(INTEGER)
   *  @param auctionwinningprice Database column AuctionWinningPrice SqlType(REAL)
   *  @param dealbiddingprice Database column DealBiddingPrice SqlType(REAL)
   *  @param actualwiningprice Database column ActualWiningPrice SqlType(REAL)
   *  @param auctioncreateddatetimestamp Database column AuctionCreatedDateTimestamp SqlType(TIMESTAMP)
   *  @param biddingcreateddatetimestamp Database column BiddingCreatedDateTimestamp SqlType(TIMESTAMP)
   *  @param impressioncreateddatetimestamp Database column ImpressionCreatedDateTimestamp SqlType(TIMESTAMP)
   *  @param iswon Database column IsWon SqlType(INTEGER), Length(1,false)
   *  @param isgroupbid Database column IsGroupBid SqlType(INTEGER), Length(1,false) */
  case class WinningpriceinfoviewRow(bidid: Option[Int], impressionid: Option[Int], seatbidid: Option[Int], campaignid: Option[Int], auctionid: Option[Int], bidrequestid: Option[Int], bidresponseid: Option[Int], advertiseid: Option[Int], demandsideplatformid: Option[Int], auctionwinningprice: Option[Double], dealbiddingprice: Option[Double], actualwiningprice: Option[Double], auctioncreateddatetimestamp: Option[java.time.Instant], biddingcreateddatetimestamp: Option[java.time.Instant], impressioncreateddatetimestamp: Option[java.time.Instant], iswon: Option[Int], isgroupbid: Option[Int])
  /** GetResult implicit for fetching WinningpriceinfoviewRow objects using plain SQL queries */
  implicit def GetResultWinningpriceinfoviewRow(implicit e0: GR[Option[Int]], e1: GR[Option[Double]], e2: GR[Option[java.time.Instant]]): GR[WinningpriceinfoviewRow] = GR{
    prs => import prs._
    WinningpriceinfoviewRow.tupled((<<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Double], <<?[Double], <<?[Double], <<?[java.time.Instant], <<?[java.time.Instant], <<?[java.time.Instant], <<?[Int], <<?[Int]))
  }
  /** Table description of table WinningPriceInfoView. Objects of this class serve as prototypes for rows in queries. */
  class Winningpriceinfoview(_tableTag: Tag) extends profile.api.Table[WinningpriceinfoviewRow](_tableTag, "WinningPriceInfoView") {
    def * = (bidid, impressionid, seatbidid, campaignid, auctionid, bidrequestid, bidresponseid, advertiseid, demandsideplatformid, auctionwinningprice, dealbiddingprice, actualwiningprice, auctioncreateddatetimestamp, biddingcreateddatetimestamp, impressioncreateddatetimestamp, iswon, isgroupbid) <> (WinningpriceinfoviewRow.tupled, WinningpriceinfoviewRow.unapply)

    /** Database column BidId SqlType(INTEGER) */
    val bidid: Rep[Option[Int]] = column[Option[Int]]("BidId")
    /** Database column ImpressionId SqlType(INTEGER) */
    val impressionid: Rep[Option[Int]] = column[Option[Int]]("ImpressionId")
    /** Database column SeatBidId SqlType(INTEGER) */
    val seatbidid: Rep[Option[Int]] = column[Option[Int]]("SeatBidId")
    /** Database column CampaignId SqlType(INTEGER) */
    val campaignid: Rep[Option[Int]] = column[Option[Int]]("CampaignId")
    /** Database column AuctionId SqlType(INTEGER) */
    val auctionid: Rep[Option[Int]] = column[Option[Int]]("AuctionId")
    /** Database column BidRequestId SqlType(INTEGER) */
    val bidrequestid: Rep[Option[Int]] = column[Option[Int]]("BidRequestId")
    /** Database column BidResponseId SqlType(INTEGER) */
    val bidresponseid: Rep[Option[Int]] = column[Option[Int]]("BidResponseId")
    /** Database column AdvertiseId SqlType(INTEGER) */
    val advertiseid: Rep[Option[Int]] = column[Option[Int]]("AdvertiseId")
    /** Database column DemandSidePlatformId SqlType(INTEGER) */
    val demandsideplatformid: Rep[Option[Int]] = column[Option[Int]]("DemandSidePlatformId")
    /** Database column AuctionWinningPrice SqlType(REAL) */
    val auctionwinningprice: Rep[Option[Double]] = column[Option[Double]]("AuctionWinningPrice")
    /** Database column DealBiddingPrice SqlType(REAL) */
    val dealbiddingprice: Rep[Option[Double]] = column[Option[Double]]("DealBiddingPrice")
    /** Database column ActualWiningPrice SqlType(REAL) */
    val actualwiningprice: Rep[Option[Double]] = column[Option[Double]]("ActualWiningPrice")
    /** Database column AuctionCreatedDateTimestamp SqlType(TIMESTAMP) */
    val auctioncreateddatetimestamp: Rep[Option[java.time.Instant]] = column[Option[java.time.Instant]]("AuctionCreatedDateTimestamp")
    /** Database column BiddingCreatedDateTimestamp SqlType(TIMESTAMP) */
    val biddingcreateddatetimestamp: Rep[Option[java.time.Instant]] = column[Option[java.time.Instant]]("BiddingCreatedDateTimestamp")
    /** Database column ImpressionCreatedDateTimestamp SqlType(TIMESTAMP) */
    val impressioncreateddatetimestamp: Rep[Option[java.time.Instant]] = column[Option[java.time.Instant]]("ImpressionCreatedDateTimestamp")
    /** Database column IsWon SqlType(INTEGER), Length(1,false) */
    val iswon: Rep[Option[Int]] = column[Option[Int]]("IsWon", O.Length(1,varying=false))
    /** Database column IsGroupBid SqlType(INTEGER), Length(1,false) */
    val isgroupbid: Rep[Option[Int]] = column[Option[Int]]("IsGroupBid", O.Length(1,varying=false))
  }
  /** Collection-like TableQuery object for table Winningpriceinfoview */
  lazy val Winningpriceinfoview = new TableQuery(tag => new Winningpriceinfoview(tag))
}
