package itsv.dokablage

import groovy.util.logging.Log4j
import util.configuration.ConfigurationFactory

@Log4j
class Configuration {

  // enter relative path to application.json from scriptRoot
  private static final Configuration INSTANCE = ConfigurationFactory.current('itsv/dokablage/applications.json') as Configuration

  String restBaseUrl
  String restBaseUrlItsv 
  String restBaseUrlItd
  String restEvaCoreUrl
  String restEvaCore2Url
  String liveLinkBaseUrl
  String liveLinkMainFolderId
  String liveLinkTrashId
  
  // private to avoid initialization
  private Configuration() {}

  static Configuration getInstance() {
    return INSTANCE
  }
  
}
