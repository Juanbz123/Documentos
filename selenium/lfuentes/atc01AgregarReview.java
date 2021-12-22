package selenium.lfuentes;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class atc01AgregarReview {

    private WebDriver driver;

    @BeforeClass
    public static void Setup() {
        System.out.println("Setup necesario antes de Instanciar");
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void init() {
        System.out.println("init para instanciar");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://automation.frankluzon.com");
    }

    @Test
    public void atc01_AgregarReview() {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        driver.findElement(By.cssSelector("#woocommerce-product-search-field-0")).sendKeys("CAP");
        driver.findElement(By.cssSelector("#woocommerce-product-search-field-0")).sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#tab-title-reviews > a"))).click();
        driver.findElement(By.cssSelector("#commentform > div > p > span > a.star-4")).click();
        driver.findElement(By.xpath("//*[@id='comment']")).sendKeys("Muy buen producto en relacion precio calidad!");
        driver.findElement(By.cssSelector("#author")).sendKeys("Luis Fuentes");
        driver.findElement(By.cssSelector("#email")).sendKeys("neluis97@yahoo.com.ar");
        driver.findElement(By.cssSelector("#submit")).click();
        String aprobacion = driver.findElement(By.cssSelector("em.woocommerce-review__awaiting-approval")).getText();
        Assert.assertEquals("Your review is awaiting approval", aprobacion);


    }

    @After
    public void close() {
        if (driver != null) {
            driver.close();
        }

    }

    @AfterClass
    public static void closeAll() {
        System.out.println("closeAll :: Cerrar otras conexiones que fueron utilizadas en el test");

    }

}

