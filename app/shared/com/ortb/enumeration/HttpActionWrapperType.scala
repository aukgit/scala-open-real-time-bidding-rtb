package shared.com.ortb.enumeration

object HttpActionWrapperType extends Enumeration {
  type HttpActionWrapperType = Value
  val
  GetOk,
  PutUpdateOk,
  PostAddOk,
  DeleteOk,
  PatchUpdatePartialOk,
  GetFailed,
  PutUpdateFailed,
  PostAddFailed,
  DeleteFailed,
  GenericFailed,
  GenericOkay,
  PatchUpdatePartialFailed = Value
}

