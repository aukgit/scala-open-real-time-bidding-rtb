package shared.io.loggers

import com.google.inject.Inject
import shared.com.ortb.manager.AppManager

class DatabaseLogTracerImplementation @Inject()(val appManager: AppManager) extends DatabaseLogTracer
