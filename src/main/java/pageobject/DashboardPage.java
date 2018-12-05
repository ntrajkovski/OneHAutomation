package pageobject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.Assert;
import org.openqa.selenium.By;
import utils.Operations;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class DashboardPage {

    private static By dashboardButtonInput = By.className("hippo-perspective-dashboardperspective");
    //Assumption is that it is always id16 the Create a news document link
    private static By createNewsDocumentA =
            By.xpath("//div[contains(@class,'hippo-perspective-dashboard-shortcuts')]/div[3]/div/a");
    private static By documentNameInput = By.name("p::name");
    private static By OkButtonCreateDocumentInput = By.name("buttons:1:button");
    public static String newsTitle="";


    public static void GoTo()
    {
        //TODO: see how to do the GoTo Dashboard
        //clearBrowserCache();
        $(dashboardButtonInput).click();
        getWebDriver().manage().window().maximize();
        IsAt();
    }
    public static boolean IsAt()
    {
        $(dashboardButtonInput).waitUntil(visible, 20000);
        return $(dashboardButtonInput).isDisplayed();
    }
    public static void CreateNewsDocument()
    {
        newsTitle= Operations.getUniqueID();
        $(createNewsDocumentA).click();
        $(documentNameInput).val(newsTitle);
        $(OkButtonCreateDocumentInput).click();
    }

    public static void acceptRequestGeneratedDuringTheTest() {
        ClickPendingRequestWithTitle(newsTitle);
        ContentPage.AcceptRequest();
    }

    private static void ClickPendingRequestWithTitle(String newsTitle) {
        SelenideElement toDoTable = $(By.className("hippo-todo"));
        try
        {
            toDoTable.$$(By.xpath("//div[contains(text(),'" + newsTitle + "')]")).get(0).click();
        }
        catch (Exception e)
        {
            Assert.fail("News "+newsTitle+" was not found in the Pending requestes");
        }
    }

    public static void rejectRequestGeneratedDuringTheTest() {
        ClickPendingRequestWithTitle(newsTitle);
        ContentPage.RejectRequest();
    }

    public static void verifyRequestRejected() {
        //TODO: In the future change the xpath
        $(By.xpath("//*[@id=\"id37\"]/div")).has(Condition.matchesText("rejected"));
    }
}