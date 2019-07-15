package net.marklogic.report;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.LogStatus;

import net.marklogic.selenium.core.BaseTest;
import net.marklogic.utilities.Constants;
import net.marklogic.utilities.DateCalendar;
import net.marklogic.utilities.Utilities;

public class ScreenRecorder extends Constants {

	// private static WebDriver driver;

	public static String imgPath;

	/**
	 * Taking screen shot through webdriver
	 * 
	 * @return
	 */
	public static String takeScreenShot(WebDriver driver) {
		String fileName = System.getProperty("className");
		String screenshotName = Utilities.getFileName(fileName);
		String path = BaseTest.resultPath + "/" + fileName;
		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			new File(path).mkdirs();
			imgPath = path + "/" + screenshotName + ".png";
			File screenshotLocation = new File(imgPath);
			FileUtils.copyFile(screenshot, screenshotLocation);
			Thread.sleep(2000);

			// ==========================================================================================
			// To embed screen shot in HTML report
			// ==========================================================================================

			/*
			 * String screen = null; InputStream is = new
			 * FileInputStream(screenshotLocation); byte[] imageBytes =
			 * IOUtils.toByteArray(is); Thread.sleep(2000); String base64 =
			 * Base64.getEncoder().encodeToString(imageBytes); test.log(LogStatus.PASS,
			 * fileName + "_" + "\n Snapshot below: " +
			 * test.addBase64ScreenShot("data:image/png;base64," + base64));
			 * test.addScreenCapture(screen); Reporter.log( "<a href= '" + screen +
			 * "'target='_blank' ><img src='" + screen + "'>" + screenshotName + "</a>");
			 * imageBytes = null; Runtime.getRuntime().gc(); System.gc(); base64 = null;
			 */
			// FileUtils.cleanDirectory(screenshotLocation);

			// ==========================================================================================

			BaseTest.test.log(LogStatus.PASS, fileName + "_" + "\n Snapshot below: "
					+ BaseTest.test.addScreenCapture(fileName + "/" + screenshotName + ".png"));
			// return imgPath;
		} catch (Exception ex) {
		}
		return imgPath;
	}

	/**
	 * Taking screen shot with alert
	 * 
	 * @return
	 */
	public static String alertScreenRecorder() {
		String fileName = System.getProperty("className");
		String screenshotName = Utilities.getFileName(fileName);
		String path = BaseTest.resultPath + "/" + fileName;

		new File(path).mkdirs();

		try {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			imgPath = path + "/" + DateCalendar.dateTimeFileName() + ".png";
			new Rectangle(screenSize);
			Robot robot = null;
			robot = new Robot();
			BufferedImage img = robot.createScreenCapture(new Rectangle(1366, 768));
			ImageIO.write(img, "png", new File(imgPath));
			BaseTest.test.log(LogStatus.PASS,
					fileName + "_" + "\n Snapshot below: " + BaseTest.test.addScreenCapture(imgPath));
			return imgPath;
		} catch (Exception ex) {
		}
		return imgPath;
	}

	/**
	 * Taking screen shot of complete window screen along with task bar
	 * 
	 * @return
	 */
	public static String captureScreenCasts() {
		String fileName = System.getProperty("className");
		String screenshotName = Utilities.getFileName(fileName);
		String path = BaseTest.resultPath + "/" + fileName;
		new File(path).mkdirs();
		try {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			imgPath = path + "/" + screenshotName + ".png";
			Robot robot = null;
			robot = new Robot();
			BufferedImage img = robot.createScreenCapture(new Rectangle(screenSize)); // 1366, 768
			ImageIO.write(img, "png", new File(imgPath));
			BaseTest.test.log(LogStatus.PASS, fileName + "_" + "\n Snapshot below: "
					+ BaseTest.test.addScreenCapture(fileName + "/" + screenshotName + ".png"));
			return imgPath;
		} catch (Exception ex) {
		}
		return imgPath;
	}

}
