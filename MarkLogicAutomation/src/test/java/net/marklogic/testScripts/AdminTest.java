package net.marklogic.testScripts;

import java.util.Properties;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import net.marklogic.selenium.core.BaseTest;
import net.marklogic.selenium.core.Configuration;

public class AdminTest extends BaseTest {

	@BeforeMethod
	public void getTestData() throws Exception {
		System.setProperty("className", getClass().getSimpleName());
		Properties prop = Configuration.readTestData("RegressionTestData");
		dataBaseName = prop.getProperty("dataBaseName");
		documentCount=prop.getProperty("documentCount");
	}

	
	
	@Test(description="Verify Admin Page")
	public void verifyAdminPage()  {

		reportLog("1: open the database created");
		adminPage.clickOnAdminConfigureLinks(configurationDataBaseLink, dataBaseName);

		reportLog("2: Move to status page");
		adminPage.clickToStatusTab();

		reportLog("3:check the count of the documents which are inserted on the status page.");
		adminPage.documentsTotalCount(documentCount);

	}
}
