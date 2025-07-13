import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Test_Projects_Carousel extends Base_Test{

    WebDriver driver;

    @BeforeMethod
    public void InitializeAndGoToUrl() {
        driver = initializeDriver();
        ResumePage resumePage = new ResumePage(driver);
        resumePage.goToUrl();
    }

    @AfterMethod
    public void QuitDriver(){
        driver.quit();
    }

    @Test
    public void CarouselShouldContainExpectedNumberOfProjects() {

    }

}
