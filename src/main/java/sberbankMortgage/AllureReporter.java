package sberbankMortgage;

import cucumber.api.event.EventPublisher;
import cucumber.api.event.TestCaseFinished;
import cucumber.api.formatter.Formatter;


public class AllureReporter implements Formatter {

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestCaseFinished.class, this::handleTestFinished);
    }
    private void handleTestFinished(TestCaseFinished event) {
        if(!event.result.isOk(true)) {
               }
    }
}

