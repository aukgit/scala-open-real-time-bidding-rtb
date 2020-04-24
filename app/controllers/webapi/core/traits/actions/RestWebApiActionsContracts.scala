package controllers.webapi.core.traits.actions

trait RestWebApiActionsContracts[TTable, TRow, TKey]
  extends RestWebApiAddAction[TTable, TRow, TKey]
    with RestWebApiAddOrUpdateAction[TTable, TRow, TKey]
    with RestWebApiDeleteAction[TTable, TRow, TKey]
    with RestWebApiUpdateAction[TTable, TRow, TKey]
    with RestWebApiAddEntitiesAction[TTable, TRow, TKey]
    with RestWebApiGetByIdAction[TTable, TRow, TKey]
    with RestWebApiGetAllAction[TTable, TRow, TKey]
