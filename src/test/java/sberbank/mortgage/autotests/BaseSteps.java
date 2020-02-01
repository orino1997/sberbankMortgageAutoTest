package sberbank.mortgage.autotests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BaseSteps{
    WebDriver driver;
    public WebElement getWebElement(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    public static void moveSlider() {

    }
}
