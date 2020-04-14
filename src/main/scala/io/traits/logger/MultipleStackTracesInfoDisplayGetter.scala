package io.traits.logger

import com.ortb.constants.AppConstants

trait MultipleStackTracesInfoDisplayGetter extends
                                           StackTraceInfoDisplayGetter {
  def getStackTraceInfoUpToIndex : String = {
    val stacks = Thread.currentThread().getStackTrace

    stacks.map(stack => getStackTraceInfo(stack))
          .mkString(AppConstants.HyphenRightAngel)
  }
}
