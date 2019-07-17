package net.marklogic.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import net.marklogic.selenium.core.BasePage;

public class AdminPage extends BasePage {

	public AdminPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[@class='nav-text']//following-sibling::span")
	private List<WebElement> configureTabsList;

	@FindBy(xpath = "//img[@alt='Status']/parent::a")
	private WebElement statusTab;

	@FindBy(xpath = "(//table[@class='statustable'])[1]//tr[@class='statusrowtitle']/th[@class='statustitle']/a")
	private List<WebElement> forestDocumentTitle;

	@FindBy(xpath = "//span[@class='nav-text']//following-sibling::span/span//span")
	private List<WebElement> subTabList;

	/* Click On Admin Configure Links */

	public void clickOnAdminConfigureLinks(String linkToChoose, String subTabToChoose) {
		boolean flag = false;
		waitForPageLoaded();
		outerloop: for (WebElement tabs : configureTabsList) {
			if (getText(tabs.findElement(By.xpath("./a/span"))).trim().equalsIgnoreCase(linkToChoose)) {
				javascriptButtonClick(tabs.findElement(By.xpath("./a")));
				for (WebElement subTabs : subTabList) {
					if (getText(subTabs).trim().equalsIgnoreCase(subTabToChoose)) {
						scrollPageThroughWebElement(subTabs);
						clickOn(subTabs.findElement(By.xpath(".//parent::a")));
						flag = true;
						break outerloop;
					}

				}
			}
		}
		Assert.assertTrue(flag);
	}

	/* Click to Status Tab */
	public void clickToStatusTab() {
		waitForElement(statusTab);
		moveToElement(statusTab);
		javascriptButtonClick(statusTab);

	}

	/* Verify Total Documents Count */
	public void documentsTotalCount(String totalDocuments) {
		boolean flag = false;
		for (int columnIndex = 0; columnIndex < forestDocumentTitle.size(); columnIndex++) {
			if (getText(forestDocumentTitle.get(columnIndex)).trim().equalsIgnoreCase("Documents")) {
				int colIndex = columnIndex + 1;
				WebElement documentsTd = driver.findElement(
						By.xpath("(//table[@class='statustable'])[1]//tr[@valign='top']/td[" + colIndex + "]"));
				scrollPageThroughWebElement(documentsTd);
				if (getText(documentsTd).trim().equalsIgnoreCase(totalDocuments)) {
					flag = true;
					break;
				}
			}
		}
		Assert.assertTrue(flag);
	}
}
