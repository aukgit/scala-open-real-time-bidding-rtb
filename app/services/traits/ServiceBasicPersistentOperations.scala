package services.traits

trait ServiceBasicPersistentOperations[TTable, TRow, TKey]
  extends BasicPersistentServiceOperations[TTable, TRow, TKey] with
    MultiplePersistentServiceOperation[TTable, TRow, TKey] {}
