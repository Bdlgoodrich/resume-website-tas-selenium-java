import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Test_PageFormat extends Base_Test{

    @BeforeMethod
    public void InitializeAndGoToUrl() {
        WebDriver driver = initializeDriver();
        ResumePage resumePage = new ResumePage(driver);
        resumePage.goToUrl();
    }

    @Test
    public void VerifyTitle(){
        ResumePage resumePage = new ResumePage(driver);
        Assert.assertTrue(resumePage.verifyTitle(resumePage.title),"Incorrect page title");
    }


}
