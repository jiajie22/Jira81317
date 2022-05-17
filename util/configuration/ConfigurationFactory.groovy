package util.configuration

import groovy.util.logging.Log4j
import util.configuration.Environment
import com.onresolve.scriptrunner.runner.ScriptRunner
import com.onresolve.scriptrunner.runner.ScriptRunnerImpl
import groovy.json.JsonSlurper

@Log4j
class ConfigurationFactory {

    static Map current(String relativePathFromScriptRootToJsonFile) {
        String scriptRoot = ScriptRunnerImpl.getScriptRunner().getHomeScriptDir().absolutePath
        log.warn "Script Root >>>>>> ${scriptRoot}"
        log.warn "relativePathFromScriptRootToJsonFile >>>>>> ${relativePathFromScriptRootToJsonFile}"
        JsonSlurper jsonSlurper = new JsonSlurper()
        File applicationFile = new File("$scriptRoot/$relativePathFromScriptRootToJsonFile")
        log.warn "Parse file $applicationFile"
        def json = jsonSlurper.parse(applicationFile)
        log.warn "json >>>>>>>>> $json"
        Map environment = json["environments"][Environment.current().toString()] as Map
        log.warn "environment > ${environment}"
        return environment
    }
}