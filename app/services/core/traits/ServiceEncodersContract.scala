package services.core.traits

trait ServiceEncodersContract[TTable, TRow, TKey] {
  val serviceEncoders : ServiceEncoders[TTable, TRow, TKey]
}
