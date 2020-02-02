package sberbank.mortgage.autotests;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/sberbankMortgageTest.feature"},
        glue = {"sberbank.mortgage.autotests"},
        plugin = {"io.qameta.allure.cucumber3jvm.AllureCucumber3Jvm", "sberbankMortgage.AllureReporter"}
)
public class CucumberRunner {

}
