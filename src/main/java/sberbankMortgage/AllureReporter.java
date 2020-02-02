package sberbankMortgage;

import cucumber.api.event.EventPublisher;
import cucumber.api.event.TestCaseFinished;
import cucumber.api.formatter.Formatter;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class AllureReporter implements Formatter {

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestCaseFinished.class, this::handleTestFinished);
    }
    private void handleTestFinished(TestCaseFinished event) {
        System.out.println(1);
        if(!event.result.isOk(true)) {
            System.out.println(2);
            System.out.println(BaseTest.getDriver());
            byte[] screenShot = ((TakesScreenshot) BaseTest.getDriver()).getScreenshotAs(OutputType.BYTES);
            System.out.println(screenShot.length);
            Allure.getLifecycle().addAttachment(LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd-MMM-yy_hh:mm:ss")), "image/png", "png", screenShot);}

    }
}

