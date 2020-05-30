package shared.com.ortb.serverAkka.framework

trait ServerRun {
  def serverRunAt(port : Int = 0) : Unit
}
