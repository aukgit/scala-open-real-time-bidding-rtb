package com.ortb.persistent.schema

import slick.lifted.Tag
import slick.model.Table

class CampaignSchema (tag: Tag) extends
  Table[(Int, String, String, String, String, String)](tag, "CAMPAIGNS") {
  def id = column[Int]("SUP_ID", O.PrimaryKey)
  def name = column[String]("SUP_NAME")
  def street = column[String]("STREET")
  def city = column[String]("CITY")
  def state = column[String]("STATE")
  def zip = column[String]("ZIP")
  // Every table needs a * projection with the same types as the table's type parameter
  def * = (id, name, street, city, state, zip)
}