package sberbank.mortgage.autotests;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.ru.Когда;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import sberbankMortgage.MortgagePage;
import sberbankMortgage.SberbankMainPage;

public class Steps extends BaseTest {

    @Before
    public void init() {
        initialize();
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

    @Когда("^перейти на страницу \"(.+)\"$")
    public void openUrl(String url) {
        System.out.println(url);
    }

    @Test
    public void checkSberMortgage() {
        WebDriverWait wait = new WebDriverWait(driver,10);
        // 1перейти на сайт сбера
        driver.navigate().to(URL);

        //2.1 В верхнем меню "навестись" на Ипотека
        SberbankMainPage main = new SberbankMainPage(driver);
        String mortgage = main.getMenuElementXpath("Ипотека");
        new Actions(driver).
                moveToElement(driver.findElement(By.xpath(mortgage))).perform();

        //2.2 дождаться открытия выпадающего меню
        String subMenuPanel = main.getSubMenuPanel(1);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(subMenuPanel)));

        //2.3 выбрать "Ипотека на готовое жилье"
        String subMenu = main.getSubMenuCategoryXpath("Ипотека на готовое жильё");
        new Actions(driver).
                moveToElement(driver.findElement(By.xpath(subMenu))).perform();
        driver.findElement(By.xpath(subMenu)).click();

        //скролл
        MortgagePage mortgagePage = new MortgagePage(driver);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", mortgagePage.countMortgageTitle);

        //Заполнить поля

        //Стоимость недвижимости 5 180 000 ₽
        driver.switchTo().frame(mortgagePage.iframe);
        driver.findElement(By.xpath("//label[@for='estateCost']")).click();
        mortgagePage.realEstateCost.click();
        mortgagePage.realEstateCost.clear();
        mortgagePage.realEstateCost.sendKeys("5180000");
        new Actions(driver)
                .sendKeys(Keys.ESCAPE)
                .perform();

        //Первоначальный взнос 3 058 000 ₽
        mortgagePage.firstFee.click();
        mortgagePage.firstFee.clear();
        mortgagePage.firstFee.sendKeys("3058000");
        new Actions(driver)
                .sendKeys(Keys.ESCAPE)
                .perform();

        mortgagePage.period.click();
        mortgagePage.period.clear();
        mortgagePage.period.sendKeys("30");
        new Actions(driver)
                .sendKeys(Keys.ESCAPE)
                .perform();

        //Снять галочку - есть зарплатная карта сбербанка
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", mortgagePage.titleForScroll);
        new Actions(driver)
                .sendKeys(Keys.ESCAPE)
                .perform();
        wait.until(ExpectedConditions.elementSelectionStateToBe(mortgagePage.sberCardOwningInput, true));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mortgagePage.sberCardOwning.click();

        //дождаться появления "есть возможность подтвердить доход справкой"
        wait.until(ExpectedConditions.visibilityOf(mortgagePage.abilityToValidateIncome));
        //поставить галочку "молодая семья"
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", mortgagePage.abilityToValidateIncome);
        mortgagePage.youngFamily.click();

        //Проверить значение полей
        //Сумма кредита
        //2 122 000 ₽

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", mortgagePage.mortgageSum);
        Assert.assertEquals(2122000, mortgagePage.getSumAsInt(mortgagePage.mortgageSum));
        //Ежемесячный платеж
        //17 535 ₽
        Assert.assertEquals(17535, mortgagePage.getSumAsInt(mortgagePage.monthlyPayment));

        //Необходимый доход
        //29224 ₽
        Assert.assertEquals(29224, mortgagePage.getSumAsInt(mortgagePage.necessaryIncome));

        //Процентная ставка
        //11% - тут ошибка (специально)
        Assert.assertTrue(11.0 == mortgagePage.getPerCentAsDouble(mortgagePage.interestRate));
    }
}
