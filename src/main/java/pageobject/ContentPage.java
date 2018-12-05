package pageobject;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import utils.Operations;

import java.util.UUID;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;
import static pageobject.DashboardPage.newsTitle;

public class ContentPage {
    //TODO: Change these not to depend on name, because cards:n is changing ba
    private static By documentTitleInput =
            By.name("cards:0:panel:editor:extension.editor:form:template:extension.left:view:1:item:view:1:item:value:widget");
    private static By documentSummaryTextArea =
            By.name("cards:0:panel:editor:extension.editor:form:template:extension.left:view:2:item:view:1:item:value:widget");
    private static By saveCloseButtonSpan = By.className("hi-floppy-times-circle");
    private static By documentDescriptionAreaDiv = By.xpath("//div[contains(@role,'textbox')]");
    private static By documentOKButtonInput = By.name("buttons:1:button");
    private static By documentSchedulerMinutesInput = By.name("bottom-left:value:minutes");
    private static By documentSchedulerHoursInput = By.name("bottom-left:value:hours");


    public static void CreateNewDocumentAndSaveCloseIt(String title, String summary)
    {
        $(documentTitleInput).val(title);
        $(documentSummaryTextArea).val(summary);
    }
    public static void SaveCloseDocument()
    {
        // TODO: Close all open documents in the document tab.
        //  This is to avoid that multiple documents are open, and the Save&Close button is not unique
        // TODO: Make a list of all the elements with the save close classname, and select the last.
        $(saveCloseButtonSpan).click();
    }

    public static void openPublicationToolbar()
    {
        //The first element is the Publication toolbar
        // TODO: Close all the other open documents, otherwise there will be conflict
        // Or find another way to open the Publication menu
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        $$(By.className("menu-item")).get(1).click();
    }
    public static void clickOnPublishRequest()
    {
        // TODO: Find a better way to click on publish, in case of working with different languages
        $(By.xpath("//span[contains(@title, 'Publish (request)')]")).waitUntil(visible,3000).click();
    }

    public static void RequestToPublishCurrentDocument() {
        openPublicationToolbar();
        clickOnPublishRequest();
    }

    public static void AcceptRequest() {
        OpenRequestsToolbar();
        ClickOnAcceptRequest();
    }

    private static void OpenRequestsToolbar() {

        //TODO: How to find a better way to open the requests. I cannot rely on the below code,
        // since the structure is different for user and editor. Do it with span
        $$(By.className("menu-item")).get(0).click();
    }
    public static void ClickOnAcceptRequest()
    {
        // TODO: Find a better way to click on publish, in case of working with different languages
        $(By.xpath("//span[contains(@title, 'Accept request')]")).click();
    }

    public static boolean isDocumentLive() {
        //ContentPage.openNewsWithPath(newsTitle);
        //TODO: Find a better way to find the element to help for the localization
        return $(By.xpath("//span[contains(@title,'Core document (live)')]")).waitUntil(visible,3000).is(exist);
    }

    public static boolean DocumentAppearedOnSite() {
        String currentUrl = url();
        String tempResultAfterSplit = currentUrl.split("hippogreen/")[0] + ".html";
        //TODO: See if there is a better way to check for if URL exists
        open("https://www.demo.onehippo.com/"+tempResultAfterSplit);
        //If the link does not exist, it will not be opened and the test case will fail
        return true;
    }

    public static void openNewsWithPath(String newsPath) {
        //Put the news title of the document, so when the editor checks, the user checks by title if the request arrived
        newsTitle="request_for_change";
        open("https://cms.demo.onehippo.com/?1&path=/content/documents/hippogogreen/news/" + newsPath);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void makeSomeChangesInOpenDocumentDescription() {
        clickOnEditButton();
        $(documentDescriptionAreaDiv).scrollTo().val("Some random text");
    }

    private static void clickOnEditButton() {
        $$(By.className("menu-item")).get(0).click();
    }

    public static boolean ChangedDocumentAppearedOnSite() {
        //TODO: Move this into another page object
        String currentUrl = "";
        open("https://www.demo.onehippo.com/news/2018/12/request_for_change.html");
        //If the link does not exist, it will not be opened and the test case will fail
        $(By.xpath("//div[contains(@class,'blog-post-body')]/p")).has(text("Some random text"));
        return true;
    }
    public static boolean DocumentRemainedTheSameOnSite() {
        String currentUrl = "";
        open("https://www.demo.onehippo.com/news/2018/12/request_for_change.html");
        //If the link does not exist, it will not be opened and the test case will fail
        $(By.xpath("//div[contains(@class,'blog-post-body')]/p")).has(text(""));
        return true;
    }

    public static void RejectRequest() {
        OpenRequestsToolbar();
        RejectRequestWithNoFeedback();
    }

    private static void RejectRequestWithNoFeedback() {
        // TODO: Find a better way to click on publish, in case of working with different languages
        $(By.xpath("//span[contains(@title, 'Reject request')]")).click();
        $(documentOKButtonInput).click();
    }

    public static void DocumentNotAppearedOnSite() {
        //TODO: Check how the stattus 404 can be obtained
        String currentUrl = url();
        String tempResultAfterSplit = currentUrl.split("hippogreen/")[0] + ".html";
        //TODO: See if there is a better way to check for if URL exists
        Operations.GetStatusCode("https://www.demo.onehippo.com/"+tempResultAfterSplit);
    }

    public static void InputUUIDForDocumentTitle() {
        $(documentTitleInput).val(newsTitle);
    }

    public static void RequestToPublishCurrentDocumentIn5Minutes() {
        openPublicationToolbar();
        clickOnPublishScheduleRequest();
        PublishDocumentIn5Minutes();
    }

    private static void clickOnPublishScheduleRequest() {
        $(By.xpath("//span[contains(@title, 'Schedule publication (request)')]")).waitUntil(visible,3000).click();
    }

    private static void PublishDocumentIn5Minutes() {
        int minutes;
        int hours;
        int newHours;
        minutes = Integer.parseInt($(documentSchedulerMinutesInput).val());
        //Logic if the added minutes enter into a new hour
        int  newMinutes = (minutes + 5)%60;
        if(newMinutes<5)
        {
            hours = Integer.parseInt($(documentSchedulerHoursInput).val());
            newHours = hours % 24;
            $(documentSchedulerHoursInput).val(String.valueOf(newHours));
            //TODO: Logic when the hours pass into a new day
        }
        $(documentSchedulerMinutesInput).val(String.valueOf(newMinutes));
        $(documentOKButtonInput).click();
    }

    public static void verifyDocumentAppearedOnSiteAfter5Mins() {
        try {
            Thread.sleep(300000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        open("https://www.demo.onehippo.com/news/2018/12/"+ newsTitle+".html");
    }
}
