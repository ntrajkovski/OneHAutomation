package ui;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
//@CucumberOptions(plugin = {"pretty", "html:target/cucumber"})
@CucumberOptions(format={"pretty","json:target/Reports/UI.json","junit:target/surefire-reports/cucumber-junit.xml"})
public class UiRunnerTest {
    //tst
}