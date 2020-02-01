package sberbankMortgage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SberbankMainPage {
    WebDriver driver;

    public WebElement subMenuElement;
    public WebElement subMenu;

    public SberbankMainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
    public String getMenuElementXpath(String menuName) {
        return "//span[.= '" + menuName + "']";
    }

    public String getSubMenuCategoryXpath(String subMenuName) {
        return "//li[contains(@class, 'lg-menu')]//a[.='" + subMenuName + "']";
    }

    public String getSubMenuPanel(int indexOfSubMenu) {
        return "//div[@class = 'lg-menu__sub' and @id='submenu-" + indexOfSubMenu + "']";
    }

}
