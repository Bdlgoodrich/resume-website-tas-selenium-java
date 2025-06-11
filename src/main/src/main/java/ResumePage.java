import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ResumePage extends Navbar {
    WebDriver driver;

    public ResumePage (WebDriver driver) {
        super(driver);
        this.driver = driver;
     }

    //Expected website text content
    private final String url = "https://resume-website-brianna-goodrichs-projects.vercel.app/";
    public final String title = "Brianna Goodrich Resume";

    //WebElement By locators
    private final By heroTitle = By.tagName("h1");
    private final By aboutEmailLink = By.id("aboutEmailLink");
    private final By aboutLinkedInLink = By.id("aboutLinkedInLink");

    public void goToUrl (){
        driver.get(url);
    }

    public String getTitle (){
        return driver.getTitle();
    }

}
