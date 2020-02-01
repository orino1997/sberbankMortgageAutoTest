package sberbankMortgage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MortgagePage extends SberbankMainPage {
    @FindBy(xpath = "//iframe[@title]")
    public WebElement iframe;

    @FindBy(xpath = "//h2[contains(text(),'Рассчитайте ипотеку')]")
    public WebElement countMortgageTitle;

    @FindBy(xpath = "//input[@id='estateCost']")
    public WebElement realEstateCost;

    @FindBy(xpath = "//input[@id='initialFee']")
    public WebElement firstFee;

    @FindBy(xpath = "//input[@id = 'creditTerm']")
    public WebElement period;

    @FindBy(xpath = "//div[@class='dcCalc_input-row-tablet__label' and contains(text(),'Первоначальный взнос')]")
    public WebElement titleForScroll;

    @FindBy(xpath = "//input[@data-test-id = 'paidToCard']")
    public WebElement sberCardOwningInput;

    @FindBy(xpath = "//div[@class = 'dcCalc_switch-tablet__switch' and .//input[@data-test-id = 'paidToCard']]")
    public WebElement sberCardOwning;

    @FindBy(xpath = "//div[contains(text(), 'Есть возможность подтвердить доход справкой')]")
    public WebElement abilityToValidateIncome;

    @FindBy(xpath = "//div[@class = 'dcCalc_switch-tablet__switch' and .//input[@data-test-id = 'youngFamilyDiscount']]")
    public WebElement youngFamily;

    @FindBy(xpath = "//span[@data-test-id ='amountOfCredit']")
    public WebElement mortgageSum;

    @FindBy(xpath = "//span[@data-test-id ='monthlyPayment']")
    public WebElement monthlyPayment;

    @FindBy(xpath = "//span[@data-test-id ='requiredIncome']")
    public WebElement necessaryIncome;

    @FindBy(xpath = "//span[@data-test-id ='rate']")
    public WebElement interestRate;

    public MortgagePage(WebDriver driver) {
        super(driver);
    }

    public int getSumAsInt(WebElement element) {
        return Integer.parseInt(element.getText().replace(" ", "").replace("₽",""));
    }

    public double getPerCentAsDouble(WebElement element) {
        return Double.parseDouble(element.getText().replace(",", ".").replace("%", ""));
    }
}
