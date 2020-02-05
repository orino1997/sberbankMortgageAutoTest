package sberbankMortgage;

import cucumber.api.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    protected static WebDriver driver;
    protected static String URL;
    public static Properties properties = TestProperties.getInstance().getProperties();

    public void initialize() throws MalformedURLException {
        String browser = properties.getProperty("browser");
        String isRemote = properties.getProperty("isRemote");
        if (isRemote.equals("true")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(browser);
            capabilities.setVersion("73.0");
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", false);
            capabilities.setCapability("enableLog", false);
            driver = new RemoteWebDriver(URI.create("http://selenoid.aplana.com:4445/wd/hub/").toURL(),
                    capabilities);
        } else {
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
    }

    public void tearDown(Scenario scenario) {
        if(scenario.isFailed()) {
            byte[] screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.getLifecycle().addAttachment(LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("dd-MMM-yy_hh:mm:ss")), "image/png",
                    "png", screenShot);
        }
        driver.close();
        driver.quit();
    }
}
