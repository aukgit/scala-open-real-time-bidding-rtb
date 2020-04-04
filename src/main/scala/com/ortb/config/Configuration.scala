//package com.ortb.config
//
//import com.ortb.model.config.ConfigModel
//import io.JsonParser
//
//class Configuration {
//  def getConfig(path: String) : ConfigModel = {
//    val result = JsonParser.toObjectFromJSONPath(path);
//
//    if(result.isEmpty){
//      return null;
//    }
//
//    return result.get.asInstanceOf[ConfigModel];
//  }
//}
