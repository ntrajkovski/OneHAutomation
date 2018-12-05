package pageobject;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.Condition.*;

public class LoginPage {

    private static By loginInput = By.name("username");
    private static By passwordInput = By.name("password");
    private static By loginButtonInput = By.name("p::submit");
    private static By userIconDiv=By.className("hippo-user-icon");
    private static By logOutButtonDiv = By.className("hippo-logout");

    public static void GoTo()
    {
        //clearBrowserCache();
        open("/");
        getWebDriver().manage().window().maximize();
        // If another user is logged in, log out the user
        // I assume that the reason the LoginPage was not displayed
        // is because another user is logged in
        if(!IsAt()){
            logOut();
        }
    }

    private static void logOut() {
        $(userIconDiv).click();
        $(logOutButtonDiv).click();
    }

    public static boolean IsAt()
    {
        $(loginInput).waitUntil(visible, 20000);
        return $(loginInput).isDisplayed();
    }

    public static void LoginWithUserNameAndPassword(String username, String password)
    {
        $(loginInput).val(username);
        $(passwordInput).val(password);
        $(loginButtonInput).click();
        DashboardPage.IsAt();
    }

    public static void LoginAsEditor()
    {
        //TODO: Check if the user is not logged in already
        String editorUsername="editor01";
        String editorPassword="editor01";
        LoginWithUserNameAndPassword(editorUsername,editorPassword);
    }

    public static void LoginAsAuthor()
    {
        String authorUsername="author01";
        String authorPassword="author01";
        LoginWithUserNameAndPassword(authorUsername,authorPassword);
    }
    public static void LoginAsAdmin()
    {
        String adminUsername="admin01";
        String adminPassword="admin01";
        LoginWithUserNameAndPassword(adminUsername,adminPassword);
    }

}
