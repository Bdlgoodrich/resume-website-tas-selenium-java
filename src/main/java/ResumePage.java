import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ResumePage extends Navbar {

    private final int expectedCodeProjectCount = 6;
    WebDriver driver;

    public ResumePage (WebDriver driver) {
        super(driver);
        this.driver = driver;
     }

    //Expected website text content
    private final String url = "https://resume-website-brianna-goodrichs-projects.vercel.app/";
    public final String title = "Brianna Goodrich Resume";
    public final int expectedDropdownCount = 5;

    //WebElement By locators
    private final By heroTitle = By.tagName("h1");
    private final By aboutEmailLink = By.id("aboutEmailLink");
    private final By aboutLinkedInLink = By.id("aboutLinkedInLink");
    private final By FAQ = By.id("FAQ");
    private final By allDropdowns = By.cssSelector("div[id*='heading']");
    private final By closedDropdowns = By.cssSelector("button.collapsed");
    private final By shownAnswers = By.className("show");

    private final By carousel = By.id("code-projects-carousel");
    private final By carouselButtons = By.className("owl-dot");
    private final By carouselItems = By.className("owl-item");
    private final By carouselClones = By.className("cloned");

    public void goToUrl (){
        driver.get(url);
    }

    public int getCarouselButtonCount() {
        return driver.findElements(carouselButtons).size();
    }

    public int getCarouselItemCount() {
        return driver.findElements(carouselItems).size()-driver.findElements(carouselClones).size();
    }

    public int getExpectedItemsPerCarouselPage(int buttonCount){
        return (int)Math.ceil((double)getCarouselItemCount()/buttonCount);
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
        Thread.sleep(200);
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

    public boolean noAnswersAreShown(){
        return driver.findElements(shownAnswers).isEmpty();
    }
}
