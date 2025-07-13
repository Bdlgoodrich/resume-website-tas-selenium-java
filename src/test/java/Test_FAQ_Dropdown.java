import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Test_FAQ_Dropdown extends Base_Test {
    public WebDriver driver;

    @BeforeMethod
    public void InitializeAndGoToUrl() {
        driver = initializeDriver();
        ResumePage resumePage = new ResumePage(driver);
        resumePage.goToUrl();
    }

    @AfterMethod
    public void CloseDriver (){
        driver.quit();
    }

    @Test
    public void AllDropdownsShouldBeClosedUponPageLoad() throws InterruptedException {
        var resumePage = new ResumePage(driver);
        resumePage.scrollToFAQ();
        Assert.assertTrue(resumePage.allDropdownsAreClosed());
    }

    @Test
    public void ShouldDisplayCorrectNumberOfDropdowns() throws InterruptedException {
        var resumePage = new ResumePage(driver);
        resumePage.scrollToFAQ();
        Assert.assertEquals(resumePage.getDropdownCount(), resumePage.expectedDropdownCount);
    }

    @DataProvider
    public Object[] Dropdowns (){
        return new Object[] {1,2,3,4,5};
    }

    @Test (dataProvider="Dropdowns")
    public void AllDropdownsShouldOpenAndCloseWithClick(int number) throws InterruptedException {
        var resumePage = new ResumePage(driver);
        resumePage.scrollToFAQ();
        Assert.assertTrue(resumePage.allDropdownsAreClosed(),"Not all dropdowns started closed.");
        Assert.assertTrue(resumePage.noAnswersAreShown(),"Answers are shown even when dropdowns are closed");
        resumePage.clickDropdown(number);
        Assert.assertFalse(resumePage.dropdownIsClosed(number), "Dropdown number "+number+" did not open when clicked.");
        Assert.assertTrue(resumePage.allOtherDropdownsAreClosed(number), "More than one dropdown is opened when dropdown number "+number+" is clicked.");
        Assert.assertTrue(resumePage.answerIsShown(number),"Answer number "+number+" was not shown when dropdown was clicked.");
        resumePage.clickDropdown(number);
        Assert.assertTrue(resumePage.allDropdownsAreClosed(), "Dropdown number "+number+" did not open when clicked.");
        Assert.assertFalse(resumePage.answerIsShown(number),"Answer number "+number+" was still shown after dropdown was closed");
    }

    @Test
    public void OpenDropdownShouldCloseWhenAnotherDropdownOpens() throws InterruptedException {
        int firstDropdown = 3;
        int secondDropdown = 4;
        var resumePage = new ResumePage(driver);
        resumePage.scrollToFAQ();
        resumePage.clickDropdown(firstDropdown);
        Assert.assertFalse(resumePage.dropdownIsClosed(firstDropdown),"First dropdown did not open when clicked.");
        resumePage.clickDropdown(secondDropdown);
        Assert.assertFalse(resumePage.dropdownIsClosed(secondDropdown), "Second dropdown did not open.");
        Assert.assertTrue(resumePage.dropdownIsClosed(firstDropdown), "First dropdown did not close when second was opened.");
    }
}
