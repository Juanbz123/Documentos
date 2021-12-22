package selenium.bbarreto;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AutomationTest {
    private WebDriver driver;

    @BeforeClass
    public static void Setup() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void init() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://automation.frankluzon.com/");
    }

    @Test
    public void AutomationTest() {
        WebDriverWait wait = new WebDriverWait(driver, 12);
        driver.findElement(By.cssSelector("#woocommerce-product-search-field-0")).sendKeys("CAP");
        driver.findElement(By.cssSelector("#woocommerce-product-search-field-0")).sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#tab-title-reviews > a"))).click();
        driver.findElement(By.cssSelector("#commentform > div > p > span > a.star-3")).click();
        driver.findElement(By.xpath("//*[@id='comment']")).sendKeys("Buen material, terminación normal");
        driver.findElement(By.cssSelector("#author")).sendKeys("Braian Barreto");
        driver.findElement(By.cssSelector("#email")).sendKeys("braianbarreto@gmail.com");
        driver.findElement(By.cssSelector("#submit")).click();

        String aprobado = driver.findElement(By.cssSelector("em.woocommerce-review__awaiting-approval")).getText();

        Assert.assertEquals("Your review is awaiting approval", aprobado);
    }
    @After
    public void close() {
        if (driver != null) {
            //driver.close();
        }
    }

    @AfterClass
    public static void closeAll() {
    }
}

