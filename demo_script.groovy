import com.atlassian.jira.component.ComponentAccessor
import org.apache.log4j.Logger
import org.apache.log4j.Level
import com.atlassian.jira.user.ApplicationUser
import com.atlassian.jira.issue.CustomFieldManager
import com.atlassian.jira.issue.Issue
import com.atlassian.applinks.host.spi.InternalHostApplication
import com.onresolve.scriptrunner.runner.util.UserMessageUtil
import com.atlassian.jira.issue.changehistory.ChangeHistoryItem
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.issue.link.RemoteIssueLink
import com.atlassian.jira.issue.link.RemoteIssueLinkManager
import com.atlassian.jira.issue.link.RemoteIssueLinkBuilder
import itsv.dokablage.Configuration


log.setLevel(Level.DEBUG)


Configuration config
try {
    config = Configuration.getInstance()
    log.warn "config here >> ${config}"
} catch (Exception ex) {
   log.warn  "Error in reading config: $ex"
}

// set REST headers
Map restHeaders = [
    'Accept': 'application/json',
    'Content-Type': 'application/json'
]

def restEvaCoreUrl = config.restEvaCoreUrl
if (!restEvaCoreUrl) {
    log.warn "REST EVA Core URL is not set"
} else{
    restEvaCoreUrl
}
