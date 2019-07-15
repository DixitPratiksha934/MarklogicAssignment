package net.marklogic.testScripts;

import java.util.Properties;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import net.marklogic.selenium.core.BaseTest;
import net.marklogic.selenium.core.Configuration;

public class SearchTextTest extends BaseTest{
	
	@BeforeMethod
	public void getTestData() throws Exception {
		System.setProperty("className", getClass().getSimpleName());
		Properties prop = Configuration.readTestData("RegressionTestData");
		color = prop.getProperty("highlightedColor");
		searchTerm = prop.getProperty("searchTerm");
		searchFalseTerm=prop.getProperty("falseSearchTerm");

	}
	
	/*----------------Search Term Script-----------------------------------------*/
	
	@Test(description = "Search Text and highlight the text in displayed results")
	public void verifySearchText() {
	
	reportLog("1:Check Search Page Title");
	searchPage.verifySearchPageTitle();
		
	reportLog("2:Verify searchTextBox and search button is present in the page");
	searchPage.verifySearchPageElements();
	
	reportLog("3:Enter search term Text In TextBox");
	searchPage.enterTextInSearchInputBox(searchTerm);
	
	reportLog("4:Click On Search Button");
	searchPage.clickOnSearchButton();
	
	reportLog("5:Verify  all results conatins searched term");
	searchPage.verifySearchTermPresentInResults(searchTerm);
	
	reportLog("6:Verify search term highlighted");
	searchPage.verifySearchedTermIsHighlighted(searchTerm,color);
	
	reportLog("7:Send false search term");
	searchPage.enterTextInSearchInputBox(searchFalseTerm);
	searchPage.clickOnSearchButton();
	
	reportLog("8:Verify Blank Page is Showing");
	searchPage.verifyNoResultShowIfTermNotPresent();

	}
}
