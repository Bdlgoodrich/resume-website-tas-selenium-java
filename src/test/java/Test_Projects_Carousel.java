import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Test_Projects_Carousel extends Base_Test{

    WebDriver driver;

    @BeforeMethod
    public void InitializeAndGoToUrl() {
        driver = initializeHeadedDriver();
        ResumePage resumePage = new ResumePage(driver);
        resumePage.goToUrl();
    }

    @AfterMethod
    public void QuitDriver(){
        driver.quit();
    }

    @Test
    public void CarouselShouldContainExpectedNumberOfProjects() throws InterruptedException {
        var resumePage = new ResumePage(driver);
        resumePage.scrollToCarousel();
        Assert.assertEquals(resumePage.getCarouselItemCount(),resumePage.expectedCarouselItemCount);
    }
    @Test
    public void CarouselPagesShouldContainExpectedNumberOfProjects() throws InterruptedException {
        var resumePage = new ResumePage(driver);
        resumePage.scrollToCarousel();
        int pageCount = resumePage.getCarouselPageCount();
        int expectedItemCountPerPage = resumePage.getExpectedItemsPerCarouselPage();
        for (int i = 0; i<pageCount; i++){
            resumePage.clickCarouselPage(pageCount, i);
            Assert.assertEquals(resumePage.getCurrentCarouselItemCount(), expectedItemCountPerPage);
        }
    }

}
