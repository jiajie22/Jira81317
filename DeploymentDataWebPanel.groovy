package sdlc2common.webpanels.others

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.plugin.webfragment.model.JiraHelper
import com.atlassian.jira.user.ApplicationUser
import com.atlassian.plugin.web.model.WebPanel
import com.atlassian.velocity.VelocityManager
import core.dtos.SDLCToolEventData
import core.jiramanagers.SDLCStorageParser
import core.utils.PathHelper
import core.utils.SDLCLogger

/**
 * DeploymentDataWebPanel
 * Author: pmarella.
 */
class DeploymentDataWebPanel implements WebPanel {

    private final VelocityManager velocityManager
    private SDLCLogger sdlcLogger
    private static final String templateFilePath = PathHelper.parsePath(PathHelper.Path_Sdlc2WebPanelsFolder, "others", "DeploymentDataPanel.vm")

    DeploymentDataWebPanel() {
        this.velocityManager = ComponentAccessor.velocityManager
        this.sdlcLogger = SDLCLogger.getInstance(this.class)
    }


    @Override
    String getHtml(Map<String, Object> map) {
        StringWriter writer = new StringWriter()
        this.writeHtml(writer, map)
        return writer.toString()
    }

    @Override
    void writeHtml(Writer writer, Map<String, Object> map) throws IOException {
        try {

            JiraHelper jiraHelper = (JiraHelper) map.get('helper')
            Issue currentIssue = (Issue) jiraHelper.getContextParams().get("issue")
            ApplicationUser currentUser = ComponentAccessor.jiraAuthenticationContext.loggedInUser


            String templateContent = (new File(templateFilePath)).text
            SDLCStorageParser sdlcStorageParser = new SDLCStorageParser()
            SDLCToolEventData sdlcToolEventData = sdlcStorageParser.retrieveToolEventDataFromStorage(currentIssue)
            String formattedContent = String.format("<div>%s<div>", "No Deployment Information available for this Issue")
            if (sdlcToolEventData) {
                DeploymentDataDisplayFormatter displayFormatter = new DeploymentDataDisplayFormatter(sdlcToolEventData, currentIssue, currentUser)
                map.put("displayHelper", displayFormatter)
                formattedContent = this.velocityManager.getEncodedBodyForContent(templateContent, displayFormatter.jiraBaseURL(), map)
            }

            writer.write(formattedContent)
            writer.flush()
        }
        catch (IOException ex) {
            this.sdlcLogger.logError(String.format("IO Exception. Details: '%s' ", ex.toString()))
        }
    }


} // Class
