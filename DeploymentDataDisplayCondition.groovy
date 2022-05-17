package sdlc2common.webpanels.others

import com.atlassian.jira.issue.Issue
import com.atlassian.jira.plugin.webfragment.conditions.AbstractWebCondition
import com.atlassian.jira.plugin.webfragment.model.JiraHelper
import com.atlassian.jira.user.ApplicationUser
import core.dtos.JiraIssueContext
import core.lookups.ConfigProvider
import core.lookups.IssueContextConfigurationProvider
import groovy.transform.InheritConstructors

//import core.utils.SDLCLogger

/**
 * DeploymentDataDisplayCondition
 * Author: pmarella.
 */
@InheritConstructors
class DeploymentDataDisplayCondition extends AbstractWebCondition {

    private Properties sdlcProperties = ConfigProvider.SDLCSubsystemProperties
//    private SDLCLogger sdlcLogger = SDLCLogger.getInstance(this.class)


    @Override
    boolean shouldDisplay(ApplicationUser applicationUser, JiraHelper jiraHelper) {

        Issue currentIssue = (Issue) jiraHelper.getContextParams().get("issue")
        String projectKey = currentIssue.projectObject.key
        String issueType = currentIssue.issueType.getName()

        // ==============================================================================
        // TO REMAIN COMMENTED FOR PRODUCTION USE
        // if (projectKey.trim().toLowerCase() != "POC".toLowerCase()) return false
        // ==============================================================================

        JiraIssueContext requiredContext = new JiraIssueContext(projectKey, issueType)
        if (!IssueContextConfigurationProvider.getInstance().issueSpec(requiredContext)) {
            return false
        }

        String releaseIssueType = this.sdlcProperties.getProperty("jira.release.issue.type.name")
        if (currentIssue.issueType.getName().toLowerCase() == releaseIssueType.toLowerCase()) {
            return false
        }
        // this.sdlcLogger.logDebug(String.format("DeploymentDataDisplayCondition::shouldDisplay: Issue Context is Valid for %s which is of Issue type %s", currentIssue.key, issueType))
        return true
    }
} // Class
