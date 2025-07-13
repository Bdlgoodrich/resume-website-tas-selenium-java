import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.floor;

public class Utilities {
    WebDriver driver;

    public Utilities(WebDriver driver) {
        this.driver = driver;
    }

    public boolean verifyTitle(String title) {
        return Objects.requireNonNull(driver.getTitle()).contentEquals(title);
    }

    public void scrollToElement(WebElement element) throws InterruptedException {
        var js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(500);
        waitForElementVisible(element);
    }

    public void scrollDown() {
        var js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 1000)");
    }

    public double fetchCurrentPosition() {
        var js = (JavascriptExecutor) driver;
        String position = js.executeScript("return window.pageYOffset;").toString();
        return parseAndRound(position);
    }

    public double fetchElementPosition(WebElement element) throws InterruptedException {
        scrollToElement(element);
        return fetchCurrentPosition();
    }

    public double parseAndRound(String number) {
        return round(Double.parseDouble(number));
    }

    public double round(double number) {
        if (number < 50) return floor(number);
        else if (number < 1000) return Math.round(number / 10) * 10;
        else return Math.round(number / 100) * 100;
    }


    public void waitForElementVisible(WebElement element) {
        var wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementToBeClickable(WebElement element) {
        var wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public String fetchAllBrokenLinks() throws URISyntaxException, IOException {
        return linksUnbroken(driver.findElements(By.tagName("a")));
    }

    public String linksUnbroken(List<WebElement> links) throws URISyntaxException, IOException {
        boolean linksUnbroken = true;
        StringBuilder brokenLinks = new StringBuilder();
        for (WebElement link : links) {
            String url = link.getAttribute("href");
            HttpURLConnection conn = (HttpURLConnection) new URI(url).toURL().openConnection();
            conn.setRequestMethod("HEAD");
            conn.connect();
            int respCode = conn.getResponseCode();
            if (conn.getResponseCode() < 400) {
                linksUnbroken = false;
                brokenLinks.append("The link with Text").append(link.getText()).append(" is broken with code").append(respCode).append(". ");
            }
        }
        if (linksUnbroken) brokenLinks.append("No broken links.");
        return brokenLinks.toString();
    }
}