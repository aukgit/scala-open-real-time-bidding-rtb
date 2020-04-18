package shared.com.ortb.enumeration

object HttpActionWrapperType extends Enumeration {
  type HttpActionWrapperType = Value
  val GetOk,
  PutOk,
  PostOk,
  DeleteOk,
  PatchOk,
  GetFailed,
  PutFailed,
  PostFailed,
  DeleteFailed,
  GenericFailed,
  GenericOkay,
  PatchFailed = Value
}
