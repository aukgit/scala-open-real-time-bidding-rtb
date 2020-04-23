package shared.com.ortb.enumeration

object HttpActionWrapperType extends Enumeration {
  type HttpActionWrapperType = Value
  val GetOk,
  PutOk,
  PostAddOk,
  DeleteOk,
  PatchOk,
  GetFailed,
  PutUpdateFailed,
  PostAddFailed,
  DeleteFailed,
  GenericFailed,
  GenericOkay,
  PatchFailed = Value
}
