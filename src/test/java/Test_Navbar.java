import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class Test_Navbar extends Base_Test {

    public WebDriver driver;

    @BeforeMethod
    public void InitializeAndGoToUrl() {
        driver = initializeDriver();
        ResumePage resumePage = new ResumePage(driver);
        resumePage.goToUrl();
    }

    @Test
    public void NavbarShouldStartInvisible() {
        var navbar = new Navbar(driver);
        Assert.assertFalse(navbar.navbarIsVisible());
        driver.close();
    }

    @Test
    public void NavbarBecomesVisibleUponScroll() {
        var navbar = new Navbar(driver);
        navbar.scrollDown();
        Assert.assertTrue(navbar.navbarIsVisible());
    }

    @DataProvider public String[] NavbarButtons() {
        return new String[]{"Title", "Intro", "AboutMe", "CodeProjects", "FAQ", "Contact"};
    }

    @Test (dataProvider="NavbarButtons")
    public void NavbarButtonsShouldScrollToCorrespondingSection(String button) {
        var navbar = new Navbar(driver);
        navbar.scrollDown();
        navbar.clickButtonByName(button);
        Assert.assertEquals(navbar.fetchCurrentPosition(),navbar.fetchExpectedPosition(button),"The {button} button did not navigate to expected location.");
    }

    @Test
    public void NavbarResumeButtonShouldOpenResumeInNewTab(){
        var navbar = new Navbar(driver);
        navbar.scrollDown();
        navbar.clickNavbarResumeLink();
    }

    @AfterMethod
    public void CloseDriver (){
        driver.close();
    }

}
