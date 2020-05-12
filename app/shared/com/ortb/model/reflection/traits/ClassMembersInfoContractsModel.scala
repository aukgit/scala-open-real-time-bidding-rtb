package shared.com.ortb.model.reflection.traits

trait ClassMembersInfoContractsModel[T]
  extends ClassMembersDetailedInfo[T]
    with ClassMembersInfoEventualToRegularModel[T]
    with ClassMembersInfoGetMethods[T]
    with ClassMembersInfoToProduct[T]
