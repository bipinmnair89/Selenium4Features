import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseConfiguration {

    public static Properties prop;
    public static RemoteWebDriver driver;
    public WebDriverWait wait;

    //constructor of base class with the code for properties file
    public BaseConfiguration()
    {
        try {
            prop=new Properties();
            FileInputStream ip = new FileInputStream("./src/main/resources/config.properties");
            prop.load(ip);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    //initialization method to initialize browser, load the url
    public static void initializationMethod()
    {
        String browser=prop.getProperty("browser");
        if(browser.equalsIgnoreCase("chrome"))
        {
            System.setProperty("webdriver.chrome.driver", "./src/main/resources/Drivers/chromedriver.exe");
            if(prop.getProperty("headless").equalsIgnoreCase("yes"))
            {
                ChromeOptions options=new ChromeOptions();
                options.setHeadless(true);
                driver=new ChromeDriver(options);
            }
            else
            {
                driver=new ChromeDriver();
            }

        }else if(browser.equalsIgnoreCase("firefox"))
        {
            System.setProperty("webdriver.gecko.driver", "./src/main/resources/Drivers/geckodriver.exe");
            if(prop.getProperty("headless").equalsIgnoreCase("yes"))
            {
                FirefoxOptions options=new FirefoxOptions();
                options.setHeadless(true);
                driver=new FirefoxDriver(options);
            }
            else
            {
                driver=new FirefoxDriver();
            }

        }
        else if(browser.equalsIgnoreCase("Edge"))
        {
            System.setProperty("webdriver.edge.driver", "./src/main/resources/Drivers/msedgedriver85.0.564.51.exe");
            driver=new EdgeDriver();
        }
        else if(browser.equalsIgnoreCase("IE"))
        {
            System.setProperty("webdriver.ie.driver","./src/main/resources/Drivers/IEDriverServer.exe");
            driver=new InternetExplorerDriver();
        }
        else{
            System.out.print("Missing browser selection in properties file");
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get(prop.getProperty("url"));


    }

}
