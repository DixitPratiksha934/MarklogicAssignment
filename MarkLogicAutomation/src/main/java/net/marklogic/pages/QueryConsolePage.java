package net.marklogic.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import net.marklogic.selenium.core.BasePage;

public class QueryConsolePage extends BasePage {

	public QueryConsolePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//*[contains(@class,'CodeMirror cm-s-default')]")
	private WebElement commandExecutorDiv;

	@FindBy(xpath = "//*[contains(@id,'run-btn')]")
	private WebElement runButton;

	@FindBy(xpath = "//select[@id='source-databases']")
	private WebElement dataBaseDropDown;

	@FindBy(xpath = "//*[@id='query-type']")
	private WebElement queryTypeDropDown;

	@FindBy(xpath = "//*[@id='query-view-content']")
	private WebElement queryResultDiv;

	@FindBy(xpath = "//*[@id='tab-space']//*[contains(@title,'Add query to workspace (alt =)')]")
	private WebElement addQueryButton;

	@FindBy(xpath = "//span[@class='cm-text']")
	private WebElement spanQueryElement;

	@FindBy(xpath = "//*[@class='resultItem']//*[text()='1.0-ml']")
	private WebElement resultOfQuery;

	@FindBy(xpath = "//span[text()='Admin']//parent::a")
	private WebElement adminTab;

	@FindBy(xpath = "//img[@alt='Status']/parent::a")
	private WebElement statusTab;

	@FindBy(xpath = "//select[@id='source-databases']/option")
	private List<WebElement> elementOptionsList;

	@FindBy(xpath = "//select[@id='query-type']/option")
	private List<WebElement> queryOptionsList;

	@FindBy(xpath = "//select[@id='query-type']")
	private WebElement queryTypeSelect;

	@FindBy(xpath = "//i[contains(text(),'your query')]")
	private WebElement queryDocInsertResult;

	@FindBy(xpath = "//div[contains(@class,'CodeMirror')]")
	private WebElement queryInput;

	@FindBy(xpath = "//*[@class='type']//span")
	private WebElement resultViewDropDown;

	/* Click On Add Query Button */
	public void clickOnAddQuery()  {

		waitForElementToBecomeVisible(By.xpath("//*[contains(@title,'Run query ')]"), driver);
		javascriptButtonClick(addQueryButton);
		waitForElementToBecomeInvisible(By.xpath("//*[@id='server-side-spinner-container']/img[@class='shown']"));
	}

	/* Send Command To create forest */
	public void insertForestSendCommand(String forestName, String query) {

		waitForElementPresent(queryInput, DEFAULT_WAIT_4_PAGE);
		String forestQuery = "let $spec := admin:forest-create(admin:get-configuration()," + "\"" + forestName + "\""
				+ ", xdmp:host(), ()) return admin:save-configuration-without-restart($spec)";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].CodeMirror.setValue('" + query + " " + forestQuery + "');", queryInput);

	}

	/* Send Command to create database */

	public void insertDataBaseSendCommand(String dataBaseName, String query) {

		waitForElementPresent(queryInput, DEFAULT_WAIT_4_PAGE);
		String dataBaseQuery = "let $spec := admin:database-create(admin:get-configuration()," + "\"" + dataBaseName
				+ "\"" + ", xdmp:database(" + "\"Security\"" + "), xdmp:database(" + "\"Schemas\""
				+ ")) return admin:save-configuration-without-restart($spec)";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].CodeMirror.setValue('" + query + " " + dataBaseQuery + "');", queryInput);
	}

	/* Attach Forest to the database */
	public void attachForestToDataBase(String dataBaseName, String forestName, String query) {

		waitForElementPresent(queryInput, DEFAULT_WAIT_4_PAGE);
		String attachQuery = "let $spec := admin:database-attach-forest(admin:get-configuration(), xdmp:database("
				+ "\"" + dataBaseName + "\"" + "),xdmp:forest(" + "\"" + forestName + "\""
				+ ") ) return admin:save-configuration-without-restart($spec)";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].CodeMirror.setValue('" + query + " " + attachQuery + "');", queryInput);

	}

	/* Select database from DropDown */
	public void selectDataBase(String dataBaseToSelect){

		refreshPage();
		boolean flag = true;
		waitForElementPresent(dataBaseDropDown, DEFAULT_WAIT_4_ELEMENT);
		waitForElement(dataBaseDropDown);
		Select select = new Select(dataBaseDropDown);
		javascriptButtonClick(dataBaseDropDown);
		for (int optionVar = 0; optionVar < elementOptionsList.size(); optionVar++) {
			if (getText(elementOptionsList.get(optionVar)).trim().contains(dataBaseToSelect)) {
				select.selectByIndex(optionVar);
				flag = true;
				break;
			}
		}
		Assert.assertTrue(flag);

	}

	/* Select Query Type from DropDown */
	public void selectQueryType(String querySelect){

		boolean flag = true;
		moveToElement(queryTypeSelect);
		waitForElementPresent(queryTypeSelect, DEFAULT_WAIT_4_PAGE);
		Select select = new Select(queryTypeSelect);
		for (int optionVar = 0; optionVar < queryOptionsList.size(); optionVar++) {
			if (getText(queryOptionsList.get(optionVar)).trim().contains(querySelect)) {
				int columnIndex = optionVar;
				select.selectByIndex(columnIndex);
				flag = true;
				break;
			}
		}
		Assert.assertTrue(flag);

	}

	/* Select run button for executing query */
	public void clickOnRunButton() {
		
		waitForElementToBecomeVisible(By.xpath("//pre[@class=' CodeMirror-line ']"), driver);
		waitForElementPresent(runButton, DEFAULT_WAIT_4_PAGE);
		javascriptButtonClick(runButton);
		waitForElementToBecomeInvisible(By.xpath("//*[contains(@title,'stop query ')]"));
		setImplicitWait(DEFAULT_WAIT_4_PAGE);
	}

	/* Verify Result of Query */
	public void verifyResultOfQuery()  {

		waitForElementPresent(resultOfQuery, DEFAULT_WAIT_4_ELEMENT);
		moveToElement(queryResultDiv);
		Assert.assertTrue(resultOfQuery.isDisplayed());
		rightResultdropDown();
	}

	/* Insert Document */

	public void insertDocument(String documentInsertQuery) {

		waitForElementPresent(queryInput, DEFAULT_WAIT_4_PAGE);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].CodeMirror.setValue('" + documentInsertQuery + "');", queryInput);

	}

	/* Verify Result of Insert document */
	public void verifyResultofInsertedDocument() {

		waitForElement(queryDocInsertResult);
		moveToElement(queryDocInsertResult);
		Assert.assertTrue(queryDocInsertResult.isDisplayed());

	}

	/* Verify right result DropDown */
	public void rightResultdropDown() {
		waitForElementToVisible("//*[@class='type']//span", 10);
		Assert.assertTrue(getText(resultViewDropDown).trim().contains("string"));
	}
}
