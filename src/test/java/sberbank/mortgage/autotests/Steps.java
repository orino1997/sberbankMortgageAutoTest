package sberbank.mortgage.autotests;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import sberbankMortgage.BaseTest;
import sberbankMortgage.MortgagePage;
import sberbankMortgage.SberbankMainPage;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class Steps extends BaseTest {
    private SberbankMainPage main;
    private WebDriverWait wait;
    private Map<String, Integer> submenuNameToIndex;
    private MortgagePage mortgagePage;

    @Before
    public void init() {
        try {
            initialize();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        wait = new WebDriverWait(driver,8);
        main = new SberbankMainPage(driver);
        submenuNameToIndex = new HashMap<String, Integer>();
        submenuNameToIndex.put("Ипотека", 1);
        mortgagePage = new MortgagePage(driver);
    }

    @After
    public void tearDown(Scenario scenario) {
        super.tearDown(scenario);
    }

    @Когда("^перейти на страницу \"(.+)\"$")
    public void openUrl(String url) {
        driver.navigate().to(url);
    }

    @Когда("^В верхнем меню навести на \"(.+)\"$")
    public void pointToTopMenuItem(String topMenuItem) {
        String mortgage = main.getMenuElementXpath(topMenuItem);
        new Actions(driver).
                moveToElement(driver.findElement(By.xpath(mortgage))).perform();
        String subMenuPanel = main.getSubMenuPanel(submenuNameToIndex.get(topMenuItem));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(subMenuPanel)));
    }

    @Когда("^В открывшемся меню выбрать \"(.+)\"$")
    public void openSubMenuCategory(String subMenuCategory) {
        String subMenu = main.getSubMenuCategoryXpath(subMenuCategory);
        new Actions(driver).
                moveToElement(driver.findElement(By.xpath(subMenu))).perform();
        driver.findElement(By.xpath(subMenu)).click();
    }

    @Когда("^Заполнить поле \"Стоимость недвижимости\" \"(.+)\" ₽$")
    public void fillGapEstateCost(String cost) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", mortgagePage.countMortgageTitle);
        driver.switchTo().frame(mortgagePage.iframe);
        String monthlyPaymentBeforeChange = mortgagePage.monthlyPayment.getText();
        driver.findElement(By.xpath("//label[@for='estateCost']")).click();
        mortgagePage.realEstateCost.click();
        mortgagePage.realEstateCost.clear();
        mortgagePage.realEstateCost.sendKeys(cost);
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(mortgagePage.monthlyPayment, monthlyPaymentBeforeChange)));
    }

    @Когда("^Заполнить поле \"Первоначальный взнос\" \"(.+)\" ₽$")
    public void fillGapFirstPayment(String cost) {
        String monthlyPaymentBeforeChange = mortgagePage.monthlyPayment.getText();
        mortgagePage.firstFee.click();
        mortgagePage.firstFee.clear();
        mortgagePage.firstFee.sendKeys(cost);
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(mortgagePage.monthlyPayment, monthlyPaymentBeforeChange)));
    }

    @Когда("^Заполнить поле \"Срок кредита\" \"(.+)\" лет$")
    public void fillGapTerm (String term) {
        String monthlyPaymentBeforeChange = mortgagePage.monthlyPayment.getText();
        mortgagePage.period.click();
        mortgagePage.period.clear();
        mortgagePage.period.sendKeys(term);
        wait.until(ExpectedConditions.not(ExpectedConditions
                .textToBePresentInElement(mortgagePage.monthlyPayment, monthlyPaymentBeforeChange)));
    }

    @Когда("^Снять галочку - \"Есть зарплатная карта сбербанка\"$")
    public void offSwitcherSberCard() {
        String monthlyPaymentBeforeChange = mortgagePage.monthlyPayment.getText();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", mortgagePage.titleForScroll);
        new Actions(driver)
                .sendKeys(Keys.ESCAPE)
                .perform();
        wait.until(ExpectedConditions.elementSelectionStateToBe(mortgagePage.sberCardOwningInput, true));
        mortgagePage.sberCardOwning.click();
        wait.until(ExpectedConditions.not(ExpectedConditions
                .textToBePresentInElement(mortgagePage.monthlyPayment, monthlyPaymentBeforeChange)));
    }

    @Тогда("^Дождаться появления \"Есть возможность подтвердить доход справкой\"")
    public void waitTillHasAbilityToValidateIncome() {
        wait.until(ExpectedConditions.visibilityOf(mortgagePage.abilityToValidateIncome));
    }

    @Когда("^Поставить галочку \"Молодая семья\"")
    public void onSwitcherYoungFamily() {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", mortgagePage.abilityToValidateIncome);
        mortgagePage.youngFamily.click();
    }
    @Тогда("^Проверить, что значение поля \"Сумма кредита\" равно \"(.+)\" ₽$")
    public void assertSumOfMortgage(String sum) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", mortgagePage.mortgageSum);
        Assert.assertEquals(Integer.parseInt(sum), mortgagePage.getSumAsInt(mortgagePage.mortgageSum));
    }

    @Тогда("^Проверить, что значение поля \"Ежемесячный платеж\" равно \"(.+)\" ₽$")
    public void assertSumOfMonthlyPayment(String monthlyPayment) {
        Assert.assertEquals(Integer.parseInt(monthlyPayment), mortgagePage.getSumAsInt(mortgagePage.monthlyPayment));
    }

    @Тогда("^Проверить, что значение поля \"Необходимый доход\" равно \"(.+)\" ₽$")
    public void assertNeededIncome(String neededIncome) {
        Assert.assertEquals(Integer.parseInt(neededIncome), mortgagePage.getSumAsInt(mortgagePage.necessaryIncome));
    }

    @Тогда("^Проверить, что значение поля \"Процентная ставка\" равно \"(.+)\" %$")
    public void assertPercentRate(String rate) {
        Assert.assertEquals(Double.parseDouble(rate), mortgagePage.getPerCentAsDouble(mortgagePage.interestRate));
    }
}
