import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by liy58 on 9/6/17.
 */
public class StockDemoTest {
    public String baseUrl = "https://www.google.com/finance";
    public WebDriver driver;
    Helper helper = new Helper();

    @BeforeTest
    public void beforeTest() {
        System.out.println("launching chrome browser");
        driver = new ChromeDriver();
    }

    @Test(dataProvider = "DemoDataProvider", dataProviderClass = DemoDataProvider.class)
    public void demoStockTest(String c1, String c2) {
        String xPathButton= "//button[@class='gbqfb']";
        String xPathInput = "//input[@class='gbqfif fjfe-searchbox-input']";
        String xPathCurPrice = "//div[@id='price-panel']/div[1]/span/span";
        String xPathcell = "//div[@class='snap-panel']/table[1]/tbody/tr[2]/td[@class='val']";
        String xPathEPS = "//div[@class='snap-panel']/table[2]/tbody/tr[2]/td[@class='val']";
        String xPathName = "//div[@class='appbar-snippet-primary']/span";
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();
        driver.get(baseUrl);
        driver.navigate().refresh();
        WebElement element = driver.findElement(By.xpath(xPathInput));
        element.sendKeys(c1);
        driver.findElement(By.xpath(xPathButton)).sendKeys(Keys.RETURN);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("price-panel")));
            String cur_price = driver.findElement(By.xpath(xPathCurPrice)).getText();
            Reporter.log("Today's price is "+ cur_price);
            String l_h_v = driver.findElement(By.xpath(xPathcell)).getText();
            helper.priceCompare(l_h_v, cur_price);
        } catch (TimeoutException ex) {
            throw new SkipException("invalid input data. aborting...");
        } catch (Exception e) {
            Reporter.log("52 week price is not available");
        }
        try {
            String eps = driver.findElement(By.xpath(xPathEPS)).getText();
            String c_name = driver.findElement(By.xpath(xPathName)).getText();
            WebElement element2 = driver.findElement(By.xpath(xPathInput));
            element2.sendKeys(c2);
            driver.findElement(By.xpath(xPathButton)).sendKeys(Keys.RETURN);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("price-panel")));
            String eps2 = driver.findElement(By.xpath(xPathEPS)).getText();
            String c2_name = driver.findElement(By.xpath(xPathName)).getText();
            helper.compare(eps,eps2,c_name,c2_name);
        } catch (TimeoutException ex) {
            throw new SkipException("invalid input data. aborting...");
        } catch (Exception e){
            Reporter.log("EPS data is not available");
        }
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}
