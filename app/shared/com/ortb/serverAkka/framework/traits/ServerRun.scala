package shared.com.ortb.serverAkka.framework.traits

trait ServerRun {
  def serverRunAt(port : Int = 0) : Unit
}
