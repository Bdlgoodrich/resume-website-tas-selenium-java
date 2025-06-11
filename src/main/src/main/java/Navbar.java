import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Navbar extends Utilities{
    WebDriver driver;
    public Navbar(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }
    private final By navbar = By.id("navbar");
    private final By navbarResumeLink = By.id("navbarResumeLink");

    public boolean navbarIsVisible (){
        return driver.findElement(navbar).isDisplayed();
    }

    public void clickButtonByName(String button) {
        driver.findElement(convertToBy(button)).click();
    }

    public void clickNavbarResumeLink () {
        driver.findElement(navbarResumeLink).click();
    }

    public By convertToBy(String button) {
        return By.id("navbar" + button + "Link");
    }

    public double fetchExpectedPosition (String button) {
        if (Objects.equals(button, "Title")) {return (double) 0;}
        return fetchElementPosition(driver.findElement(convertToBy(button)));
    }

}
