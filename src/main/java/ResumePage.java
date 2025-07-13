import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ResumePage extends Navbar {

    WebDriver driver;

    public ResumePage (WebDriver driver) {
        super(driver);
        this.driver = driver;
     }

    //Expected website text content
    private final String url = "https://resume-website-brianna-goodrichs-projects.vercel.app/";
    public final String title = "Brianna Goodrich Resume";
    public final int expectedDropdownCount = 5;
    public final int expectedCarouselItemCount = 6;

    //WebElement By locators
    private final By heroTitle = By.tagName("h1");
    private final By aboutEmailLink = By.id("aboutEmailLink");
    private final By aboutLinkedInLink = By.id("aboutLinkedInLink");
    private final By FAQ = By.id("FAQ");
    private final By allDropdowns = By.cssSelector("div[id*='heading']");
    private final By closedDropdowns = By.cssSelector("button.collapsed");
    private final By shownAnswers = By.className("show");

    private final By carousel = By.id("code-projects-carousel");
    private final By carouselPageButtons = By.className("owl-dot");
    private final By carouselItems = By.className("owl-item");
    private final By carouselClones = By.className("cloned");
    private final By currentCarouselItems = By.cssSelector(".owl-item.active");

    public void goToUrl (){
        driver.get(url);
    }


    public void scrollToCarousel() throws InterruptedException {
        scrollToElement(driver.findElement(carousel));
    }
    public int getCarouselPageCount() {
        return driver.findElements(carouselPageButtons).size();
    }

    public int getCarouselItemCount() {
        return driver.findElements(carouselItems).size()-driver.findElements(carouselClones).size();
    }

    public int getExpectedItemsPerCarouselPage(){
        return (int)Math.ceil((double)expectedCarouselItemCount/getCarouselPageCount());
    }

    public int getCurrentCarouselItemCount(){
        var activeCarouselItems = driver.findElements(currentCarouselItems);
        int visibleCarouselItems = 0;
        for(WebElement item: activeCarouselItems){
            if(item.isDisplayed()) visibleCarouselItems++;
        }
        return visibleCarouselItems;
    }

    public void clickCarouselPage(int pageCount, int pageIndex){
        driver.findElements(carouselPageButtons).get(pageIndex).click();
        waitForItemsToBeVisible(pageCount, pageIndex);
    }

    private void waitForItemsToBeVisible(int pageCount,int pageIndex){
        int expectedLastIndex;
        if (pageCount == pageIndex) expectedLastIndex = expectedCarouselItemCount;
        else expectedLastIndex = pageIndex*expectedCarouselItemCount/pageCount;
        var lastExpectedItem = driver.findElements(carouselItems).get(expectedLastIndex);
        waitForElementToBeVisible(lastExpectedItem);
    }

    public void scrollToFAQ() throws InterruptedException {
        scrollToElement(driver.findElement(FAQ));
    }

    public boolean allDropdownsAreClosed(){
        return (driver.findElements(closedDropdowns).size() == expectedDropdownCount);
    }

    public int getDropdownCount(){
        return driver.findElements(allDropdowns).size();
    }

    public WebElement getDropdown(int dropdownNumber){
        String id = "heading" + dropdownNumber;
        return driver.findElement(By.id(id));
    }

    public WebElement getDropdownButton(int dropdownNumber){
        String id = "heading" + dropdownNumber;
        WebElement div = driver.findElement(By.id(id));
        return div.findElement(By.tagName("button"));
    }

    public void clickDropdown(int dropdownNumber) throws InterruptedException {
        WebElement dropdown = getDropdown(dropdownNumber);
        dropdown.click();
        waitForAnswerToBeShown(dropdownNumber);
    }

    public boolean dropdownIsClosed(int dropdownNumber){
        var dropdown = getDropdownButton(dropdownNumber);
        var classNames = dropdown.getAttribute("class");
        return classNames.contains("collapsed");
    }

    //returns false if other dropdowns are open OR expected dropdown isn't open
    public boolean allOtherDropdownsAreClosed(int dropdownNumber){
        if (!dropdownIsClosed(dropdownNumber)) {
            return (driver.findElements(closedDropdowns).size() == expectedDropdownCount - 1);
        }
        else return false;
    }

    public WebElement getAnswer (int dropdownNumber){
        String id = "collapse" + dropdownNumber;
        return driver.findElement(By.id(id));
    }

    public boolean answerIsShown(int dropdownNumber) throws InterruptedException {
        if (dropdownNumber>1){
            scrollToElement(getDropdown(dropdownNumber-1));
        }
        else scrollToFAQ();
        String classNames = getAnswer(dropdownNumber).getAttribute("class");
            return classNames.contains("show");
    }

    public void waitForAnswerToBeShown(int dropdownNumber){
        WebElement answer = getAnswer(dropdownNumber);
        var wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.attributeContains(answer,"class","show"));
    }

    public boolean noAnswersAreShown(){
        return driver.findElements(shownAnswers).isEmpty();
    }
}
