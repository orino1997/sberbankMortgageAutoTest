package sberbank.mortgage.autotests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    protected static WebDriver driver;
    protected static String URL;
    public static Properties properties = TestProperties.getInstance().getProperties();

    public void initialize() {
        String browser = properties.getProperty("browser");
        if ("chrome".equals(browser)) {
            System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver"));
            driver = new ChromeDriver();
        } else if ("firefox".equals(browser)) {
            System.setProperty("webdriver.gecko.driver", properties.getProperty("webdriver.gecko.driver"));
            driver = new ChromeDriver();
        } else if ("IE".equals(browser)) {
            System.setProperty("webdriver.ie.driver", properties.getProperty("webdriver.ie.driver"));
            driver = new InternetExplorerDriver();

            System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver"));
            driver = new ChromeDriver();
        } else {
            System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver"));
            driver = new ChromeDriver();
        }
        URL = properties.getProperty("appURL");
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }
    public WebElement getWebElement(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    public void moveToElement(String xpath) {
        String elementXpath = xpath;
        new Actions(driver).
                moveToElement(driver
                        .findElement(By.xpath(elementXpath)))
                .perform();
    }
    public void moveSlider() {

    }

    public void tearDown() {
        driver.close();
        driver.quit();
    }
}
