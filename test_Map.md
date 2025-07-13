Page Objects:
BasePage
FAQDropdown
Navbar
ProjectsCarousel
Utilities

Test Classes:
Base_Test
    initializeDriver()
    getScreenShot()

Test_FAQ_Dropdown
    AllDropdownsShouldBeClosedUponPageLoad
    ShouldDisplayCorrectNumberOfDropdowns
    AllDropdownsShouldOpenAndCloseWithClick (DataProvider: Dropdowns)
    OpenDropdownShouldCloseWhenAnotherDropdownOpens

Test_Navbar
    NavbarShouldStartInvisible
    NavbarShouldBeVisibleUponScroll
    NavbarButtonsShouldScrollToCorrespondingSection (DataProvider: NavbarButtons)
    NavbarResumeButtonShouldOpenResumeInNewTab

Test_Page_Format
    VerifyTitle
    ShouldHaveNoBroken

Test_Projects_Carousel
    CarouselShouldContainExpectedNumberOfProjects
    CarouselPagesShouldContainExpectedNumberOfProjects