package pageobject;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class HomePage {
    private static By userInfoButtonSvg = By.className("hi-user-circle");
    private static By userLogOutButtonDiv = By.className("hippo-logout");

    public static void LogOut() {
        $(userInfoButtonSvg).click();
        $(userLogOutButtonDiv).click();
        LoginPage.IsAt();
    }
}