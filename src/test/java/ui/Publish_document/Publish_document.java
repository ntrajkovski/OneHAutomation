package ui.Publish_document;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import pageobject.ContentPage;
import pageobject.DashboardPage;
import pageobject.HomePage;
import pageobject.LoginPage;

import static com.codeborne.selenide.Selenide.$;

public class Publish_document {
    @Given("^editor was logged in$")
    public void editorWasLoggedIn() {
        LoginPage.GoTo();
        LoginPage.LoginAsEditor();
    }

    @Given("^author was logged in$")
    public void authorWasLoggedIn() {
        LoginPage.GoTo();
        LoginPage.LoginAsAuthor();
    }

    @Given("^publish request existed$")
    public void publishRequestExisted() {
        LoginPage.GoTo();
        LoginPage.LoginAsAuthor();
        DashboardPage.GoTo();
        DashboardPage.CreateNewsDocument();
        ContentPage.InputUUIDForDocumentTitle();
        ContentPage.SaveCloseDocument();
        ContentPage.RequestToPublishCurrentDocument();
        HomePage.LogOut();
    }

    @When("^the user accepts the request$")
    public void theUserAcceptsTheRequest() {
        DashboardPage.GoTo();
        DashboardPage.acceptRequestGeneratedDuringTheTest();
    }

    @Then("^the status of the document is live$")
    public void theStatusOfTheDocumentIsLive() {
        Assert.assertTrue("The document is not live", ContentPage.isDocumentLive());
    }

    @And("^the document appears on the website$")
    public void theDocumentAppearsOnTheWebsite() {
        ContentPage.DocumentAppearedOnSite();
    }

    @And("^publish request for a changed document existed$")
    public void publishRequestForAChangedDocumentExisted() {
        LoginPage.GoTo();
        LoginPage.LoginAsAuthor();
        //TODO: The below link should be always on the site, so we have a published document
        ContentPage.openNewsWithPath("2018/12/request_for_change");
        ContentPage.makeSomeChangesInOpenDocumentDescription();
        ContentPage.SaveCloseDocument();
        ContentPage.RequestToPublishCurrentDocument();
        HomePage.LogOut();
    }

    @And("^the changed document appears on the website$")
    public void theChangedDocumentAppearsOnTheWebsite() {
        ContentPage.ChangedDocumentAppearedOnSite();
    }

    @When("^the user rejects the request$")
    public void theUserRejectsTheRequest() {
        DashboardPage.GoTo();
        DashboardPage.rejectRequestGeneratedDuringTheTest();
    }

    @Then("^the document does not appear on the website$")
    public void theDocumentDoesNotAppearOnTheWebsite() {
        ContentPage.DocumentNotAppearedOnSite();
    }

    @And("^the author receives a notification that the request was rejected$")
    public void theAuthorReceivesANotificationThatTheRequestWasRejected() {
        HomePage.LogOut();
        LoginPage.LoginAsAuthor();
        DashboardPage.verifyRequestRejected();
    }

    @Then("^the document remains the same on the website$")
    public void theDocumentRemainsTheSameOnTheWebsite() {

    }

    @Given("^scheduled publish request existed$")
    public void scheduledPublishRequestExisted() {
        LoginPage.GoTo();
        LoginPage.LoginAsAuthor();
        DashboardPage.GoTo();
        DashboardPage.CreateNewsDocument();
        ContentPage.InputUUIDForDocumentTitle();
        ContentPage.SaveCloseDocument();
        ContentPage.RequestToPublishCurrentDocumentIn5Minutes();
        HomePage.LogOut();
    }

    @Then("^the document appears on the website only when it is scheduled$")
    public void theDocumentAppearsOnTheWebsiteOnlyWhenItIsScheduled() {
        //TODO: First verify if the document does not exist
        ContentPage.verifyDocumentAppearedOnSiteAfter5Mins();
    }
}