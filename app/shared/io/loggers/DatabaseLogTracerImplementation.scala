package shared.io.loggers

import com.google.inject.Inject
import javax.inject.Singleton
import shared.com.ortb.manager.AppManager

@Singleton
class DatabaseLogTracerImplementation @Inject()
(val appManager : AppManager, val className : String)
  extends DatabaseLogTracer
