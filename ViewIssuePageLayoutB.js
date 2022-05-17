/**
 * Author: pmarella
 */
 const p = new Date();
 console.log(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ")
 console.log("=====================================TEST for restart=========================================== ")
console.log("start > " + p.getTime())

;(function ($, window, document, undefined) {

    var jiraHost = JIRA;
    // var detailsPanel = "details-module";
    var descriptionPanel = "descriptionmodule";
    var uiStalker = "stalker";
    var triggerLayouting = false;
    jiraHost.bind(JIRA.Events.NEW_CONTENT_ADDED, function (e, context, reason) {
        // console.log(" ********** ViewIssuePageLayout-JS:: Jira Event Binding - REASON: " + reason);
        if (reason == JIRA.CONTENT_ADDED_REASON.pageLoad) {
            //console.log(" ViewIssuePageLayout-JS:: Jira Event Binding - Page Load");
            updateGroupSignOffFieldLayouts();
            return;
        }


        if (reason == JIRA.CONTENT_ADDED_REASON.panelRefreshed) {
            // console.log(" ViewIssuePageLayout-JS:: Jira Event Binding - Panel Refreshed: " + context.attr('id'));
            if (context.attr('id') == descriptionPanel || context.attr('id') == uiStalker || context.attr('id') == undefined) {
                triggerLayouting = true;
                // console.log(" ViewIssuePageLayout-JS:: Jira Event Binding - Panel Refreshed: " + context.attr('id') + " | Value of Trigger #0: " + triggerLayouting);
            }

        }

        if (reason == JIRA.CONTENT_ADDED_REASON.inlineEditStarted) {
            console.log(" ViewIssuePageLayout-JS:: Jira Event Binding - Inline Edit Started: " + context.attr('id'));
            triggerLayouting = true;
        }

        if (reason == JIRA.CONTENT_ADDED_REASON.dialogReady) {
            console.log(" ViewIssuePageLayout-JS:: Jira Event Binding - Dialog Ready: " + context.attr('id'));
            triggerLayouting = true;
        }

        //console.log(" ViewIssuePageLayout-JS:: Jira Event Binding - Value of Trigger #1: " + triggerLayouting);

        if (triggerLayouting) {
            console.log(" ViewIssuePageLayout-JS:: Jira Event Binding - Triggering re-layout");
            updateGroupSignOffFieldLayouts();
            triggerLayouting = false;
            moveAcceptanceCriteria();
        }

        const gg = new Date();
        console.log("final > " + gg.getTime())

        // console.log(" ViewIssuePageLayout-JS:: Jira Event Binding - Value of Trigger #2: " + triggerLayouting);


    });


    function initiateApprovalStates() {

const d = new Date();
        

        if (AJS.$('strong:contains("Approval List/PO")').length > 0) {
            AJS.$('strong:contains("Approval List/PO")').css({"width": "250px"});
            AJS.$('strong:contains("Approval Req")').css({"width": "250px"});
            AJS.$('strong:contains("Approval List/PO")').parent().css({"margin-bottom": "30px"});

            var newLink = document.createElement("a");
            newLink.innerHTML = "Show Approvers List";
            newLink.style.color = "#CD5555";
            AJS.$('strong:contains("List/PO")').append("<br>").append(newLink);
            AJS.$('a:contains("Show Approvers List")').click(function () {
                var html = this.innerHTML;
                console.log("Show Hide Approvers Link Click: HTML: " + html);
                console.log("Show Hide Approvers Link Click: ParentNode Title 1: " + this.parentNode.title);
                // console.log("Show Hide Approvers Link Click: ParentNode Title 2: " + AJS.$(this).parentNode.title);
                var parentTable = AJS.$('strong:contains(' + this.parentNode.title + ')').parent().children('div').children('table');
                var thead = parentTable.children('thead');
                var tableRows = parentTable.children('tbody').children('tr').children('td:contains("pending")').parent();
                if (html == "Show Approvers List") {
                    tableRows.show();
                    this.innerHTML = "Hide Approvers List";
                }
                else {
                    tableRows.hide();
                    this.innerHTML = "Show Approvers List";
                }
            });
            var tables = AJS.$('strong:contains("List/PO")').parent().children('div').children('table');
            tables.children('tbody').children('tr').children('td:contains("pending")').parent().hide();
            var notApprovedRow = document.createElement("tr");
            notApprovedRow.style.color = "#CD5C5C";
            notApprovedRow.className = "PendingApproval";
            tables.children('tbody').prepend(notApprovedRow);
            var notApprovedTextColumn = document.createElement("td");
            notApprovedTextColumn.innerHTML = "<b>Pending Approval</b>";
            AJS.$(".PendingApproval").append(document.createElement("td"));
            AJS.$(".PendingApproval").append(notApprovedTextColumn);
            AJS.$(".PendingApproval").append(document.createElement("td"));
            var childrenToHide = tables.children('tbody').children('tr').children('td').children('img[title="has been signed-off"]');
            if (childrenToHide.length > 0) {
                childrenToHide.parent().parent().parent().children(".PendingApproval").hide();
            }

            var approvalsContainer = document.createElement("div");
            approvalsContainer.id = "approvalsmodule";
            approvalsContainer.className = "module toggle-wrap";

            AJS.$('#descriptionmodule').css({"margin-bottom": "25px"});
            AJS.$('#details-module').after(approvalsContainer);

            var approvalsContainerHeading = document.createElement("div");
            approvalsContainerHeading.id = "approvalsmodule_heading";
            approvalsContainerHeading.className = "mod-header";

            AJS.$('#approvalsmodule').append(approvalsContainerHeading);

            var approvalsHeadingList = document.createElement("ul");
            approvalsHeadingList.className = "ops";

            AJS.$('#approvalsmodule_heading').append(approvalsHeadingList);

            var approvalsTitle = document.createElement("h2");
            approvalsTitle.className = "toggle-title";
            approvalsTitle.innerHTML = "Approvals";

            AJS.$('#approvalsmodule_heading').append(approvalsTitle);

            var approvalsContent = document.createElement("div");
            approvalsContent.className = "mod-content";

            AJS.$('#approvalsmodule').append(approvalsContent);

            var approvalsFieldModule = document.createElement("div");
            approvalsFieldModule.id = "approvalscustomfieldmodule";

            AJS.$('#approvalsmodule .mod-content').append(approvalsFieldModule);

            var approvalsFieldTabs = document.createElement("div");
            approvalsFieldTabs.className = "aui-tabs horizontal-tabs";
            approvalsFieldTabs.id = "approvalsfield-tabs";

            AJS.$('#approvalsmodule .mod-content #approvalscustomfieldmodule').append(approvalsFieldTabs);

            var approvalsCustomPane = document.createElement("div");
            approvalsCustomPane.className = "tabs-pane active-pane";
            approvalsCustomPane.id = "approvals-panel-1";

            AJS.$('#approvalsmodule .mod-content #approvalscustomfieldmodule #approvalsfield-tabs').append(approvalsCustomPane);

            var approvalsCustomList = document.createElement("ul");
            approvalsCustomList.className = "property-list";

            AJS.$('#approvalsmodule .mod-content #approvalscustomfieldmodule #approvalsfield-tabs #approvals-panel-1').append(approvalsCustomList);
            var listMap = new Object();
            var sortArray = [];
            AJS.$('#details-module strong:contains("Approval List/PO")').each(function (key, value) {
                listMap[value.innerHTML] = value.parentNode.parentNode;
                sortArray.push(value.innerHTML);
            });
            AJS.$('#details-module strong:contains("Approval Req")').each(function (key, value) {
                listMap[value.innerHTML] = value.parentNode.parentNode;
                sortArray.push(value.innerHTML);
            });
            sortArray.sort();
            if (AJS.$('strong:contains("Approval Req")').length > 0) {
                for (var i = sortArray.length - 1; i > 0; i = i - 2) {
                    AJS.$('#approvalsmodule .mod-content #approvalscustomfieldmodule #approvalsfield-tabs #approvals-panel-1 .property-list').prepend(listMap[sortArray[i - 1]]);
                    AJS.$('#approvalsmodule .mod-content #approvalscustomfieldmodule #approvalsfield-tabs #approvals-panel-1 .property-list').prepend(listMap[sortArray[i]]);
                }
            }
            else {
                for (var j = sortArray.length - 1; j > 0; j = j - 1) {
                    AJS.$('#approvalsmodule .mod-content #approvalscustomfieldmodule #approvalsfield-tabs #approvals-panel-1 .property-list').prepend(listMap[sortArray[j]]);
                }
            }

        }
        console.log("final > " + d.getTime())
    } // Function


    function moveAcceptanceCriteria() {
        if (AJS.$('#details-module strong:contains("Acceptance Criteria")').length > 0 && AJS.$('#descriptionmodule .mod-content').length > 0) {
            var old_listItem = AJS.$('#details-module strong:contains("Acceptance Criteria")')[0].parentNode.parentNode;
            var new_element = document.createElement('div');
            new_element.className = "item";
            new_element.id = old_listItem.id;
            new_element.style.marginTop = "20px";
            new_element.appendChild(AJS.$('strong:contains("Acceptance Criteria")')[0].parentNode);
            AJS.$('#descriptionmodule .mod-content').append(new_element);
            AJS.$('#details-module').after(AJS.$('#descriptionmodule'));
            old_listItem.style.display = "none";

const c = new Date();
        console.log("moveAcceptanceCriteria > " + c.getTime())
        }
    } // Function

    function hideGroupSignOffFieldsWhenEmpty() {
const a = new Date();

        AJS.$('#details-module strong:contains("Approval List/PO")').parent().parent().hide();
        AJS.$('#details-module strong:contains("Approval Req")').parent().parent().hide();
        
        console.log("hideGroupSignOffFieldsWhenEmpty > " + a.getTime())
    } // Function


    function updateGroupSignOffFieldLayouts() {
        const b = new Date();

        var currentIssue = JIRA.Issue.getIssueKey();
        var executing = false;
        var approvalsModule = AJS.$('#approvalsmodule');

        if (!executing) {
            //executing = true;
            if (currentIssue != null) {
                approvalsModule = AJS.$('#approvalsmodule');
                var legacyApprovalList = AJS.$('#details-module strong:contains("Approval List/PO")');
                var legacyApprovalReq = AJS.$('#details-module strong:contains("Approval Req")');
                if (approvalsModule.length == 0) {
                    console.log("ViewIssuePageLayout-JS:: updateGroupSignOffFieldLayouts : 1");
                    if (legacyApprovalList.length == 0 && legacyApprovalReq.length > 0) {
                        console.log("ViewIssuePageLayout-JS:: updateGroupSignOffFieldLayouts : 3");
                        hideGroupSignOffFieldsWhenEmpty();
                    }
                    initiateApprovalStates();
                    moveAcceptanceCriteria();
                }
                else if (legacyApprovalList.length > 0 || legacyApprovalReq.length > 0) {
                    console.log("ViewIssuePageLayout-JS:: updateGroupSignOffFieldLayouts : 2");
                    hideGroupSignOffFieldsWhenEmpty();

                }

            }
            // executing = false;
        }
        
        console.log("updateGroupSignOffFieldLayouts > " + b.getTime())
    } // Function


})(AJS.$, window, document);
