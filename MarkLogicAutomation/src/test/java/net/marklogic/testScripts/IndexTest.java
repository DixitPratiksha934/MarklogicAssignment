package net.marklogic.testScripts;


import org.testng.annotations.Test;

import net.marklogic.selenium.core.BaseTest;
import net.marklogic.utilities.Constants;

public class IndexTest extends BaseTest {
	
	
	/*----------------Sort Functionality Script-----------------------------------------*/
	@Test(description = "Sorting By Ascending and Descending Order and check results")
	public void verifyIndexPage() {

		reportLog("1.Verify index page title");
		indexPage.verifyPageTitle();

		reportLog("2:Verify index page all elements showing properly");
		indexPage.verifyIndexPage();

		reportLog("3.Click on radio button ascending and verify button is selected or not");
		indexPage.clickOnRadioButtonandVerifyIsSelected(Constants.ascendingRadioButton);

		reportLog("4:Click on search");
		resultPage = indexPage.clickOnSearchButton();

		reportLog("5:Verify after selecting search user is redirecting to the result page");
		resultPage.verifyPageTitle();

		reportLog("6:Verify result Page is showing correct result as users choice is ascending");
		resultPage.verifyAscendingHeading();

		reportLog("7.1:Verify document name is showing in ascending order");
		resultPage.verifyDocumnetNameOrderIsAscending();
		
		reportLog("7.2:Verify index page all elements showing properly");
		indexPage.verifyIndexPage();

		reportLog("8.Click on radio button descending and verify button is selected or not");
		indexPage.clickOnRadioButtonandVerifyIsSelected(Constants.descendingRadioButton);

		reportLog("9:Click on search");
		resultPage = indexPage.clickOnSearchButton();

		reportLog("10:Verify after selecting search user is redirecting to the result page");
		resultPage.verifyPageTitle();

		reportLog("11:Verify result Page is showing correct result as users choice is descending");
		resultPage.verifydescendingHeading();

		reportLog("12.1:Verify document name is showing in descending order");
		resultPage.verifyDocumnetNameOrderIsdescending();
		
		reportLog("12.2:Verify index page all elements showing properly");
		indexPage.verifyIndexPage();

		reportLog("13:Verify If User not select any radio button and search it will show message");
		resultPage.refreshPage();
		
		reportLog("13.1:Click on search");
		resultPage = indexPage.clickOnSearchButton();
		resultPage.verifyIfUserClicksOnSearchWithoutSelectionRadio();

	}
}
