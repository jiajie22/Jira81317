;(function ($, window, document, undefined) {

    JIRA.bind(JIRA.Events.NEW_CONTENT_ADDED, function (e, context, reason) {

        if (reason == JIRA.CONTENT_ADDED_REASON.pageLoad || reason == JIRA.CONTENT_ADDED_REASON.panelRefreshed || reason == JIRA.CONTENT_ADDED_REASON.inlineEditStarted || reason == JIRA.CONTENT_ADDED_REASON.dialogReady) {
            document.getElementById("customfield_10400-val").type = "password";
        }
    });


})(AJS.$, window, document);