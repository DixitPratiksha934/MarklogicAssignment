package net.marklogic.testScripts;

import java.util.Properties;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import net.marklogic.selenium.core.BaseTest;
import net.marklogic.selenium.core.Configuration;

public class QueryConsoleTest extends BaseTest {

	@BeforeMethod
	public void getTestData() throws Exception {
		System.setProperty("className", getClass().getSimpleName());
		Properties prop = Configuration.readTestData("RegressionTestData");
		query1 = prop.getProperty("query1");
		query2 = prop.getProperty("query2");

	}

	@Test(description = "Query Console Automation Flow")
	public void queryConsoleAutomationFlow() {

		reportLog("1.1:Open Query Console and execute a query to create a database");
		qConsolePage.sendCommand(query1);

		reportLog("1.2:Execute a query");
		qConsolePage.clickOnRunButton();

		reportLog("2:Verify the output shown when the query is been executed.");

		reportLog("3.1:Open Query Console and execute a query to create a forest");

		reportLog("3.2:Execute a query");
		qConsolePage.clickOnRunButton();

		reportLog("4:Verify the output shown when the query is been executed.");

		reportLog("5:Insert some data in the database created, using a query in Query Console itself");

		reportLog("6:Now move to admin page");

		reportLog("7: open the database created");

		reportLog("8: Move to status page");

		reportLog("9:check the count of the documents which are inserted on the status page.");

	}

}
