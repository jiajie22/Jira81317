package sdlc2common.webpanels.release

import com.atlassian.jira.issue.Issue
import com.atlassian.jira.plugin.webfragment.conditions.AbstractWebCondition
import com.atlassian.jira.plugin.webfragment.model.JiraHelper
import com.atlassian.jira.user.ApplicationUser
import core.jiramanagers.JiraIssuePropertyManager
import core.lookups.ConfigProvider
import core.utils.SDLCLogger
import groovy.transform.InheritConstructors

/**
 * ReleaseDataDisplayCondition
 * Author: pmarella.
 */

@InheritConstructors
class ReleaseDataDisplayCondition extends AbstractWebCondition {

    private Properties sdlcProperties = ConfigProvider.SDLCSubsystemProperties


    @Override
    boolean shouldDisplay(ApplicationUser applicationUser, JiraHelper jiraHelper) {
        Issue currentIssue = (Issue) jiraHelper.getContextParams().get("issue")
        String currentIssueType = currentIssue.issueType.getName()
        String releaseIssueType = sdlcProperties.getProperty("jira.release.issue.type.name")
        String hotfixIssueType = sdlcProperties.getProperty("jira.hotfix.issue.type.name")
        Boolean mustDisplay = currentIssueType.toLowerCase() == releaseIssueType.toLowerCase()
        SDLCLogger sdlcLogger = SDLCLogger.getInstance(this.class)
        if (currentIssueType.toLowerCase() == hotfixIssueType.toLowerCase()) {
            sdlcLogger.logDebug(String.format("ReleaseDataDisplayCondition::shouldDisplay: Evaluating for - Issue key: %s | Issue Type: %s", currentIssue.key, currentIssueType))
            JiraIssuePropertyManager jiraIssuePropertyManager = new JiraIssuePropertyManager()
            mustDisplay = jiraIssuePropertyManager.issueHasEnteredReleaseStage(currentIssue, applicationUser)
        }
//        sdlcLogger.logDebug(String.format("ReleaseDataDisplayCondition::shouldDisplay: Issue key: %s | Evaluates to: %s", currentIssue.key, mustDisplay))
        return mustDisplay
    }
} // Class
