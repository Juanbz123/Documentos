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

public class BabyTuto {

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
        driver.get("https://www.babytuto.com/");
    }

    @Test
    public void atc06_FiltrarProductosPorMarca() {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        cerrarPopup();
        List<WebElement> categorias = driver.findElements(By.cssSelector(".menu-cats li a"));
        for (WebElement l : categorias) {
            if (l.getText().contains("COCHES")) {
                l.click();
                break;
            }
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sub-cats.loaded a")));
        List<WebElement> subCategorias = driver.findElements(By.cssSelector(".sub-cats.loaded .tree a"));

        for (WebElement l : subCategorias) {
            if (l.getText().contains("Accesorios para coches")) {
                l.click();
                break;
            }
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.categories.row")));
        driver.findElement(By.xpath("//span[contains(text(),'BBpro')]")).click();
        cerrarPopup();
        String marcaProducto = driver.findElement(By.cssSelector("div.info .merchant-name a")).getText();
        Assert.assertEquals("BBPRO", marcaProducto);
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

    public void cerrarPopup() {
        WebElement ventanaPop = driver.findElement(By.xpath("//*[@id='newsletter']/button"));
        if (ventanaPop.isDisplayed()) {
            driver.findElement(By.xpath("//*[@id='newsletter']/button")).click();
        }
    }

}
