package net.marklogic.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import net.marklogic.selenium.core.BasePage;

public class QueryConsolePage extends BasePage {

	public QueryConsolePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//*[@class='CodeMirror-code']")
	private WebElement commandExecutorDiv;

	@FindBy(xpath = "//*[contains(@id,'run-btn')]")
	private WebElement runButton;

	@FindBy(xpath = "//*[@id='source-databases']")
	private WebElement dataBaseDropDown;

	@FindBy(xpath = "//*[@id='query-type']")
	private WebElement queryTypeDropDown;

	@FindBy(xpath = "//*[@id='query-view-content']")
	private WebElement queryResultDiv;

	/* Send Command To Command console */
	public void sendCommand(String command) {
		waitForElement(commandExecutorDiv);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('value', '" + command + "')", commandExecutorDiv);
	}

	/* Select database from dropdown */
	public void selectDataBase(String dataBaseToSelect) {
		waitForElement(dataBaseDropDown);
		selectDropDownWithOptGroups(dataBaseDropDown, dataBaseToSelect);
	}

	/* Select Query Type */

	public void selectQueryType(String queryToBeSelect) {
		waitForElement(queryTypeDropDown);
		selectDropDownWithOptGroups(queryTypeDropDown, queryToBeSelect);
	}

	/* Verify Output after query execution */

	public void verifyQueryResults(String resultBeVerify) {
		boolean flag = false;
		waitForElement(queryResultDiv);
		if (getText(queryResultDiv).contains(resultBeVerify)) {
			flag = true;
		}
		Assert.assertTrue(flag);
	}

	/* Select run button for executing query */
	public void clickOnRunButton() {
		clickOn(runButton);
	}

}
