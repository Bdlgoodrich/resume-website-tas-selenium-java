import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;

import static java.lang.Math.floor;

public class Utilities {
	WebDriver driver;

	public Utilities(WebDriver driver) {
		this.driver = driver;
	}

	//private JavascriptExecutor js = (JavascriptExecutor) driver;
	//private Actions a = new Actions(driver);
	//private Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5));

	public boolean verifyTitle(String title) {
		return driver.getTitle().contentEquals(title);
	}

	public void actionClick(WebElement element) {
		scrollToElement(element);
		Actions a = new Actions(driver);
		a.moveToElement(element).click(element).build().perform();
	}

	public void acceptAlert() {
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.accept();
	}

	public void scrollToElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	public void scrollToTop() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTop");
	}
	public void scrollToBottom() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	public void scrollDown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, 1000)");
	}

	public double fetchCurrentPosition() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String position = js.executeScript("return window.pageYOffset;").toString();
		return parseAndRound(position);
	}

	public double fetchElementPosition (WebElement element) {
		scrollToElement(element);
		return fetchCurrentPosition();
	}

	public double parseAndRound (String number) {
		return round(Double.valueOf(number));
	}

	public double round(double number) {
		int roundTo = 2;
		if (number < 50) return floor(number);
		else if (number < 1000) return Math.round(number/10)*10;
		else return Math.round(number/100)*100;
	}


	public void waitForElementVisible(WebElement element) {
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	//returns String of broken link names and response codes OR "No broken Links."

	public String fetchAllBrokenLinks () throws URISyntaxException, IOException {
		return linksUnbroken(driver.findElements(By.tagName("a")));
	}

	public String linksUnbroken (List<WebElement> links) throws URISyntaxException, IOException {
		boolean linksUnbroken = true;
		StringBuilder brokenLinks = new StringBuilder();
		for(WebElement link : links) {
			String url = link.getAttribute("href");
			HttpURLConnection conn = (HttpURLConnection) new URI(url).toURL().openConnection();
			conn.setRequestMethod("HEAD");
			conn.connect();
			int respCode = conn.getResponseCode();
			if (conn.getResponseCode()<400) {
				linksUnbroken = false;
				brokenLinks.append("The link with Text").append(link.getText()).append(" is broken with code").append(respCode).append(". ");
			}
		}
		if (linksUnbroken) brokenLinks.append("No broken links.");
        return brokenLinks.toString();
	}
}