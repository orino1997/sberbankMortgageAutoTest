package sberbank.mortgage.autotests;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/sberbankMortgageTest.feature"},
        glue = {"sberbank.mortgage.autotests"}
)
public class CucumberRunner {

}
