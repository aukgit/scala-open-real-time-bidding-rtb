package shared.io.helpers.traits

import shared.io.helpers.ClassTagHelperConcreteImplementation

trait ClassTagHelperMembers
  extends ClassTagHelperMemberInfoGetter
    with ClassTagHelperEventualMembers
    with ClassTagHelperEventualMembersMap {
  this : ClassTagHelperConcreteImplementation =>

}
