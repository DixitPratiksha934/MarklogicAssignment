package net.marklogic.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import net.marklogic.selenium.core.BasePage;
import net.marklogic.utilities.ApplicationVerificationTitles;

public class IndexPage extends BasePage {

	public IndexPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//*[@id='ascending']//following-sibling::input")
	private WebElement ascendingRadioBTN;

	@FindBy(xpath = " //*[@id='descending']//following-sibling::input")
	private WebElement descendingRadioBTN;

	@FindBy(xpath = "//*[@value='Search!']")
	private WebElement searchBTN;

	@FindBy(css = "input[id='LoginCtrl_Login']")
	private WebElement login;

	/**
	 * Verify Index page displayed
	 */
	public void verifyIndexPage() {
		waitForPageLoaded();
		Assert.assertTrue(
				ascendingRadioBTN.isDisplayed() && descendingRadioBTN.isDisplayed() && searchBTN.isDisplayed());
	}

	/* verify Page Title */

	public void verifyPageTitle() {
		Assert.assertEquals(driver.getTitle(), ApplicationVerificationTitles.indexPageTitle);
	}

	/* Choose the radio button(Ascending/Descending) */
	public void clickOnRadioButtonandVerifyIsSelected(String buttonTochoose) {
		WebElement button = driver.findElement(By.xpath("//*[@id='" + buttonTochoose + "']//following-sibling::input"));
		clickOn(button);
		Assert.assertTrue(button.isSelected());
	}

	/* Click on search button */
	public ResultPage clickOnSearchButton() {
		clickOn(searchBTN);
		return PageFactory.initElements(driver, ResultPage.class);
	}

}
