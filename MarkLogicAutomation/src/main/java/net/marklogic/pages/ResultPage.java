package net.marklogic.pages;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import edu.emory.mathcs.backport.java.util.Collections;

import java.util.List;

import net.marklogic.selenium.core.BasePage;

import net.marklogic.utilities.ApplicationVerificationTitles;

public class ResultPage extends BasePage {

	public ResultPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//*[contains(text(),'Ascending')]")
	private WebElement ascendingHeading;

	@FindBy(xpath = "//*[contains(text(),'Descending')]")
	private WebElement descendingHeading;

	@FindBy(xpath = "//b/p")
	private List<WebElement> documentNamesList;

	@FindBy(xpath = "//b/p[contains(text(),'No Order')]")
	private WebElement noOrderSelectedText;

	/* Get Title */
	public void verifyPageTitle() {
		Assert.assertEquals(driver.getTitle(), ApplicationVerificationTitles.resultPageTitle);
	}

	/* Verify Ascending Heading */
	public void verifyAscendingHeading() {
		Assert.assertTrue(ascendingHeading.isDisplayed());
	}

	/* Verify descending Heading */
	public void verifydescendingHeading() {
		Assert.assertTrue(descendingHeading.isDisplayed());
	}

	/* Check Product order is Ascending */

	public void verifyDocumnetNameOrderIsAscending() {
		List<String> listName = new ArrayList<>();
		ArrayList<String> copyListName = new ArrayList<>();
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//b/p)[5]")));

		for (int i = 0; i < documentNamesList.size(); i++) {
			listName.add(getText(documentNamesList.get(i)));
			copyListName.add(getText(documentNamesList.get(i)));

		}
		Collections.sort(listName);
		Assert.assertEquals(copyListName, listName);
		navigateBack();

	}

	/* Check Product order is descending */

	public void verifyDocumnetNameOrderIsdescending() {
		List<String> listName = new ArrayList<>();
		ArrayList<String> copyListName = new ArrayList<>();
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//b/p)[5]")));
		for (int i = 0; i < documentNamesList.size(); i++) 
			{
				listName.add(getText(documentNamesList.get(i)));
				copyListName.add(getText(documentNamesList.get(i)));
			}
			
		Collections.sort(listName, Collections.reverseOrder());
		Assert.assertEquals(copyListName, listName);
		navigateBack();
	}

	/* Verify No Order is Selected Message Is Showing */
	public void verifyIfUserClicksOnSearchWithoutSelectionRadio() {
		Assert.assertTrue(noOrderSelectedText.isDisplayed());
	}

}
