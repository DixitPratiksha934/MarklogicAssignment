package net.marklogic.testScripts;

import java.util.Properties;

import org.jfree.util.Log;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import net.marklogic.selenium.core.BaseTest;
import net.marklogic.selenium.core.Configuration;

public class QueryConsoleTest extends BaseTest {

	@BeforeMethod
	public void getTestData() throws Exception {
		System.setProperty("className", getClass().getSimpleName());
		Properties prop = Configuration.readTestData("RegressionTestData");
		query1 = prop.getProperty("query");
		query2=prop.getProperty("queryDocumentInsert");
	}

	@Test(description = "Query Console Automation Flow")
	public void queryConsoleAutomationFlow()  {

		reportLog("1.1:Open Query Console and execute a query to create a database");
		qConsolePage.selectQueryType(queryType);
		qConsolePage.clickOnAddQuery();
		
		reportLog("1.2:Select queryType");
		qConsolePage.insertDataBaseSendCommand(dataBaseName,query1);
	
		reportLog("1.3:Execute a query");
		qConsolePage.clickOnRunButton();

		reportLog("2:Verify the output shown when the query is been executed.");
		qConsolePage.verifyResultOfQuery();
		
		reportLog("3.1:Select DataBase");
		qConsolePage.selectDataBase(dataBaseName);
		
		reportLog("3.2:Open Query Console and execute a query to create a forest");
		qConsolePage.clickOnAddQuery();
		qConsolePage.insertForestSendCommand(forestName,query1);
		
		reportLog("3.3:Execute a query");
		qConsolePage.clickOnRunButton();
		
		reportLog("4:Verify the output shown when the query is been executed.");
		qConsolePage.verifyResultOfQuery();
		
		reportLog("5.1: Attach forest to database");
		
		qConsolePage.clickOnAddQuery();
		qConsolePage.attachForestToDataBase(dataBaseName,forestName,query1);
		qConsolePage.clickOnRunButton();
		
		reportLog("5.2:Verify the output shown when the query is been executed.");
		qConsolePage.verifyResultOfQuery();

		reportLog("5.3:Insert some data in the database created, using a query in Query Console itself");
		qConsolePage.clickOnAddQuery();
		qConsolePage.insertDocument(query2);
		qConsolePage.clickOnRunButton();
		
		reportLog("5.4:Verify the output shown when the query is been executed.");
		qConsolePage.verifyResultofInsertedDocument();
		
	}
	
	@AfterMethod
	public void updateDataBaseValue(ITestResult result) {
		if (ITestResult.SUCCESS == result.getStatus()) {
			Configuration.updatePropertyTestData("RegressionTestData", "dataBaseName", dataBaseName);
		} else {
			Log.error( dataBaseName+ "is not added for");
		}
	}

}
