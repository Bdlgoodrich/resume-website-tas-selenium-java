import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class Test_Page_Format extends Base_Test{

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
    public void VerifyTitle(){
        var resumePage = new ResumePage(driver);
        Assert.assertTrue(resumePage.verifyTitle(resumePage.title),"Incorrect page title");
    }

    @Test
        public void ShouldHaveNoBroken() throws URISyntaxException, IOException {
        var resumePage = new ResumePage(driver);
        String brokenLinks = resumePage.fetchAllBrokenLinks();
        Assert.assertEquals(brokenLinks, "No broken links.", "The following links are broken:" + brokenLinks);
    }

}
