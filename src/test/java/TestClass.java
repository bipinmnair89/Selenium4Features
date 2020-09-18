import org.openqa.selenium.*;
import org.testng.annotations.*;

import javax.management.monitor.CounterMonitor;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TestClass extends BaseConfiguration {

    public TestClass()
    {
        super();
    }

    @BeforeSuite
    public void initialSetup()
    {
        initializationMethod();
    }

    @Test(priority=0)
    public void verify_LoginPage_Title()
    {
        CommonMethods.assertWaitForElementToBeClickable(driver, By.xpath("//input[@name='email']"), 50);
        CommonMethods.assertPageTitle(driver, "Cogmento CRM");
    }

    @Test(priority=1)
    public void verify_Application_Login() {
        CommonMethods.assertWaitForElementToBeClickable(driver, By.xpath("//input[@name='email']"), 50);
        CommonMethods.clearAndEnterText(driver, By.xpath("//input[@name='email']"), prop.getProperty("username"));
        CommonMethods.assertWaitForElementToBeClickable(driver, By.xpath("//input[@name='password']"), 50);
        CommonMethods.clearAndEnterText(driver, By.xpath("//input[@name='password']"), prop.getProperty("password"));
        CommonMethods.clickWebElement(driver, By.xpath("//div[text()='Login']"), 50);
        CommonMethods.assertWebElementText(driver, By.xpath("//div[@id='main-nav']/descendant::span[text()='Home']"), "Home");
    }

    @Test(priority=2)
    public void verify_NavigationMenu_Links() throws IOException {
        List<WebElement> listItem=driver.findElements(By.xpath("//div[@id='main-nav']/descendant::span"));
        for(WebElement element : listItem)
        {
            String elementTxt=element.getText();
            System.out.println(elementTxt);
            CommonMethods.assertWebElementText(driver, By.xpath("//div[@id='main-nav']/descendant::span[text()='"+elementTxt+"']"), elementTxt);
        }
            //taking screenshot of the whole page
            CommonMethods.take_Page_Screenshot(driver,"HomePage");

    }

    @Test(priority=3)
    public void get_ElementScreenshot() throws IOException {
        CommonMethods.take_element_Screenshot(driver, By.xpath("//div[@id='main-nav']/descendant::span[text()='Home']/ancestor::a"), "HomeBtn");
        CommonMethods.take_element_Screenshot(driver, By.xpath("//div[@id='main-nav']/descendant::span[text()='Calendar']/ancestor::a"), "CalendarBtn");
        CommonMethods.take_element_Screenshot(driver, By.xpath("//div[@id='main-nav']/descendant::span[text()='Contacts']/ancestor::a"), "ContactsBtn");

        CommonMethods.take_element_Screenshot(driver, By.xpath("//div[@id='main-nav']"), "NavigationBar");

        CommonMethods.take_element_Screenshot_alternate(driver, By.xpath("//div[@id='main-nav']/descendant::span[text()='Companies']/ancestor::a"), "CompaniesBtn");
        CommonMethods.take_element_Screenshot_alternate(driver, By.xpath("//div[@id='main-nav']/descendant::span[text()='Deals']/ancestor::a"), "DealsBtn");
        CommonMethods.take_element_Screenshot_alternate(driver, By.xpath("//div[@id='main-nav']/descendant::span[text()='Tasks']/ancestor::a"), "TasksBtn");

    }

    @Test(priority=4)
    public void get_ElementPosition() {
        WebElement pageLogo=driver.findElement(By.xpath("//div[@class='ui navbar fixed main menu']/div[contains(@style,'background-image')]"));

        //Methods in Selenium 3 and 4 to get elements height, width and position
        System.out.println("Methods in Selenium 3 and 4 to get elements height, width and position");
        Dimension pageLogoDim=pageLogo.getSize();
        System.out.println("Element height ="+pageLogoDim.getHeight());
        System.out.println("Element width ="+pageLogoDim.getWidth());

        Point pageLogoPoint=pageLogo.getLocation();
        System.out.println("Element xValue ="+pageLogoPoint.getX());
        System.out.println("Element yValue ="+pageLogoPoint.getY());

        //Methods in Selenium 4 to get elements height, width and position
        System.out.println("Methods in Selenium 4 to get elements height, width and position");
        Rectangle pageLogoRect=pageLogo.getRect();
        System.out.println("Element height ="+pageLogoRect.getHeight());
        System.out.println("Element width ="+pageLogoRect.getWidth());
        System.out.println("Element xValue ="+pageLogoRect.getX());
        System.out.println("Element yValue ="+pageLogoRect.getY());
        System.out.println("Element pointValue ="+pageLogoRect.getPoint());
        System.out.println("Element dimension ="+pageLogoRect.getDimension());
    }

    @Test(priority=5)
    public void get_NewTaborWindow() {
        driver.switchTo().newWindow(WindowType.TAB);  //newWindow method of Selenium4
        driver.get("https://www.google.com");
        System.out.println(driver.getTitle());
        driver.close();
        Set<String> tabSet=driver.getWindowHandles();
        Iterator<String> it=tabSet.iterator();
        String parentTab=it.next();
        driver.switchTo().window(parentTab);
        System.out.println(driver.getTitle());

        driver.switchTo().newWindow(WindowType.WINDOW); //newWindow method of Selenium4
        driver.get("https://www.twitter.com");
        System.out.println(driver.getTitle());
        driver.close();
        Set<String> windowSet=driver.getWindowHandles();
        Iterator<String> it1=windowSet.iterator();
        String parentWindow=it1.next();
        driver.switchTo().window(parentWindow);
        System.out.println(driver.getTitle());
 }

    @AfterSuite
    public void tearDown()
    {
        driver.close();
    }


}