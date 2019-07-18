package net.marklogic.pages;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import net.marklogic.selenium.core.BasePage;
import net.marklogic.utilities.ApplicationVerificationTitles;

public class SearchResultPage extends BasePage {

	public SearchResultPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//*[@id='term']")
	private WebElement searchInputBox;

	@FindBy(xpath = "//*[@id='submitbtn']")
	private WebElement searchBTN;

	@FindBy(xpath = "//*[@class='result-item']//p/span")
	private List<WebElement> resultList;

	@FindBy(xpath = "//*[@class='result-item']//p")
	private List<WebElement> noResults;

	/* Enter Text in Search Input Box */
	public void enterTextInSearchInputBox(String textToBeEnter) {
		waitForElement(searchInputBox);
		inputText(searchInputBox, textToBeEnter);
	}

	/* Click On Search Button */
	public void clickOnSearchButton() {
		waitForElement(searchBTN);
		clickOn(searchBTN);
	}

	/* Verify Search Page Title */
	public void verifySearchPageTitle() {
		Assert.assertEquals(driver.getTitle(), ApplicationVerificationTitles.searchResultPageTitle);
	}

	/* Verify Search Page Attribute Showing Properly */
	public void verifySearchPageElements() {
		Assert.assertTrue(searchInputBox.isDisplayed() && searchBTN.isDisplayed());
	}

	/* Verify Search Term present in displayed results */
	public void verifySearchTermPresentInResults(String searchResultTerm) {
		waitForPageLoaded();
		boolean flag = false;
		for (WebElement searchTerm : resultList) {
			waitForElement(searchTerm);
			moveToElement(searchTerm);
			if (getText(searchTerm).equals(searchResultTerm)) {
				flag = true;
				break;
			}
		}
		Assert.assertTrue(flag);
	}

	/* Verify Searched Term is Highlighted */
	public void verifySearchedTermIsHighlighted(String searchResultTerm, String highlightedColor) {
		boolean flag = false;
		for (WebElement searchTerm : resultList) {
			if (getText(searchTerm).equals(searchResultTerm)) {
				waitForElement(searchTerm);
				String cssValue = searchTerm.getCssValue("background-color");
				if (cssValue.equals(highlightedColor)) {
					flag = true;
					break;
				}
			}
		}
		Assert.assertTrue(flag);
	}

	/* Verify if No Result Found */
	public void verifyNoResultShowIfTermNotPresent() {
		Assert.assertEquals(noResults.size(), 0);

	}
}
