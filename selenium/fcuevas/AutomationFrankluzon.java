package selenium.fcuevas;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Instant;
import java.util.List;

import static org.junit.Assert.*;

public class AutomationFrankluzon {

    private WebDriver driver;

    @BeforeClass
    public static void Setup() {
        System.out.println("Setup necesario antes de Instanciar");
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
    public void atc01_AgregarReview() {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        int numero = (int) ((Math.random() * 1000) + 1);
        driver.findElement(By.cssSelector("#woocommerce-product-search-field-0")).sendKeys("CAP");
        driver.findElement(By.cssSelector("#woocommerce-product-search-field-0")).sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#tab-title-reviews > a"))).click();
        driver.findElement(By.cssSelector("#commentform > div > p > span > a.star-5")).click();
        driver.findElement(By.xpath("//*[@id='comment']")).sendKeys("Muuuy buena calidad, lindo color, recomendable. La usaria" +" "+ numero +" veces");
        driver.findElement(By.cssSelector("#author")).sendKeys("Franco Javier");
        driver.findElement(By.cssSelector("#email")).sendKeys("chuck_ledbetter@hotmail.com");
        driver.findElement(By.cssSelector("#submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("em.woocommerce-review__awaiting-approval")));
        assertEquals("Your review is awaiting approval", driver.findElement(By.cssSelector("em.woocommerce-review__awaiting-approval")).getText());
    }
    @After
    public void close() {
        if (driver != null) {
            driver.close();
        }
    }
    @AfterClass
    public static void closeAll() {
    }
}
