package selenium.foliva;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.security.Key;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AutomationFL {
    private WebDriver driver;
    @BeforeClass
    public static void Setup(){
        WebDriverManager.chromedriver().setup();

    }
    @Before
    public void before(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://automation.frankluzon.com/");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void agregarReview() throws InterruptedException {
        WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"woocommerce-product-search-field-0\"]"));
        searchBar.sendKeys("CAP" + Keys.ENTER);

        driver.findElement(By.xpath("//*[@id=\"tab-title-reviews\"]/a")).click();
        List<WebElement> stars = driver.findElements(By.xpath("//*[@id=\"commentform\"]/div/p/span/a"));
        WebElement commentInput = driver.findElement(By.xpath("//*[@id=\"comment\"]"));
        WebElement authorInput = driver.findElement(By.xpath("//*[@id=\"author\"]"));
        WebElement emailInput = driver.findElement(By.xpath("//*[@id=\"email\"]"));
        WebElement submitButton = driver.findElement(By.xpath("//*[@id=\"submit\"]"));
        stars.get(3).click();
        commentInput.sendKeys("Your review");
        authorInput.sendKeys("Fede");
        emailInput.sendKeys("fede@mail.com");
        submitButton.click();

        String message =  driver.findElement(By.xpath("//*[@class=\"comment_container\"]/div/p/em")).getText();
        Assert.assertEquals("Your review is awaiting approval", message);
    }

    @After
    public void after(){
        if(driver != null){
            driver.close();
        }
    }
}
