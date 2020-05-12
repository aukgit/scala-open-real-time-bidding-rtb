package shared.com.ortb.enumeration

/**
 * 1 - AutoPlaySoundOn
 * 2 - AutoPlaySoundOff and so on
 */
object VideoPlaybackMethodsType extends Enumeration {
  type VideoPlaybackMethodsType = Value
  val
  AutoPlaySoundOn,
  AutoPlaySoundOff,
  ClickToPlay,
  MouseOver = Value
}
