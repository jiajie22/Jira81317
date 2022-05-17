package sdlc2common.webpanels.release

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.plugin.webfragment.model.JiraHelper
import com.atlassian.jira.user.ApplicationUser
import com.atlassian.plugin.web.model.WebPanel
import core.dtos.GenericResponse
import core.dtos.SDLCDeploymentSessionStatus
import core.dtos.SDLCReleaseData
import core.jiramanagers.ReleaseParser
import core.jiramanagers.SDLCDeploymentManager
import core.utils.PathHelper
import core.utils.SDLCLogger

import static core.utils.PathHelper.parsePath

/**
 * ReleaseDataWebPanel
 * Author: pmarella.
 */

class ReleaseDataWebPanel implements WebPanel {


    private final SDLCLogger sdlcLogger = SDLCLogger.getInstance(this.getClass())
    private static final String templateFilePath = parsePath(PathHelper.Path_Sdlc2WebPanelsFolder, "release", "ReleaseDataPanel.vm")


    ReleaseDataWebPanel() {}

    @Override
    String getHtml(Map<String, Object> map) {
        this.sdlcLogger.logDebug(" ------------------------ Release Details - Web Panel - getHTML ------------------------ ")
        StringWriter writer = new StringWriter()
        this.writeHtml(writer, map)
        return writer.toString()
    }

    @Override
    void writeHtml(Writer writer, Map<String, Object> map) throws IOException {
        this.sdlcLogger.logDebug(" ------------------- Release Data - Web Panel - writeHTML ------------------- ")
        try {

            JiraHelper jiraHelper = (JiraHelper) map.get('helper')
            Issue currentIssue = (Issue) jiraHelper.getContextParams().get("issue")
            String templateContent = (new File(templateFilePath)).text

            ApplicationUser currentUser = ComponentAccessor.jiraAuthenticationContext.loggedInUser
            String userToken = currentUser.username
            if (currentUser.username != currentUser.key) userToken = currentUser.key


            SDLCDeploymentManager sdlcDeploymentManager = new SDLCDeploymentManager()
            GenericResponse genericResponse = sdlcDeploymentManager.retrieveLatestReleaseAndSessionData(currentIssue, userToken)

            String formattedContent = String.format("<b>%s</b>", genericResponse.message)

            if (genericResponse.isSuccess) {
                SDLCReleaseData releaseData = (SDLCReleaseData) genericResponse.values.get(ReleaseParser.MapKey_ReleaseData)
                Map<String, SDLCDeploymentSessionStatus> sessionStatusMap = (Map<String, SDLCDeploymentSessionStatus>) genericResponse.values.get(SDLCDeploymentManager.EnvironmentId_SessionStatusMap_KeyName)
                ReleaseDataDisplayFormatter displayFormatter = new ReleaseDataDisplayFormatter(releaseData, currentIssue, currentUser)
                if (sessionStatusMap && sessionStatusMap.size() > 0) {
                    displayFormatter.sessionStatusMap = sessionStatusMap
                }
                map.put("displayHelper", displayFormatter)
                formattedContent = ComponentAccessor.velocityManager.getEncodedBodyForContent(templateContent, displayFormatter.jiraBaseURL(), map)
            }

            writer.write(formattedContent)
            writer.flush()
        }
        catch (IOException ex) {
            this.sdlcLogger.logError(String.format("IO Exception. Details: '%s' ", ex.toString()))

        }
    }

} // Class



