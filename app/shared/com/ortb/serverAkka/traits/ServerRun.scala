package shared.com.ortb.serverAkka.traits

trait ServerRun {
  def serverRunAt(port : Int = 0) : Unit
}
