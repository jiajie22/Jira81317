package util.configuration

import com.atlassian.jira.component.ComponentAccessor

import groovy.util.logging.Log4j

@Log4j
enum Environment {
    TEST, PROD

    static Environment current() {
        String baseUrl = ComponentAccessor.getApplicationProperties().getString("jira.baseurl")
        log.warn "baseUrl === ${baseUrl}"

        if (baseUrl == 'http://localhost:9093') {
            return Environment.PROD
        } else if (baseUrl == 'http://localhost:9093' || baseUrl == 'http://localhost:9093') {
            return Environment.TEST
        } else {
            throw new Exception("No environment for $baseUrl")
        }
    }
}