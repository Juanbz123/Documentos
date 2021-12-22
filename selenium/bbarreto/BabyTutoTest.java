package selenium.bbarreto;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Objects;

public class BabyTutoTest {
    private WebDriver driver;

    @BeforeClass
    public static void Setup(){

        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void init(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.babytuto.com/");
        while(Objects.equals(driver.getTitle(), "Resource Limit Is Reached")){
            driver.navigate().refresh();
        }
    }

    @Test
    public void BabyTest(){
        WebDriverWait espera = new WebDriverWait(driver, 15);
        cerrarVentana();
        List<WebElement> categorias = driver.findElements(By.cssSelector(".menu-cats li a"));
        for (WebElement l : categorias) {
            if (l.getText().contains("COCHES")) {
                l.click();
                break;
            }
        }
        espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sub-cats.loaded a")));
        List<WebElement> subCategorias = driver.findElements(By.cssSelector("body > div.main-container > div.header-3.sup.section-products > div.header-bar > div > div > ul > li:nth-child(1) > div > table > tbody > tr > td:nth-child(1) > ul > li:nth-child(1) > a"));
        for (WebElement l : subCategorias) {
            if (l.getText().contains("Accesorios para coches")) {
                l.click();
                break;
            }
        }
        cerrarVentana();
        espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.main-container > div:nth-child(7) > div > div.categories.row")));
        driver.findElement(By.cssSelector("body > div.main-container > div:nth-child(7) > div > div.categories.row > div.span2.filters > div:nth-child(6) > ul > li:nth-child(3) > a > span")).click();
        cerrarVentana();
        String marca = driver.findElement(By.cssSelector("body > div.main-container > div:nth-child(7) > div > div.categories.row > div.span10.products > div.items.lst-vertical > div > div.info > div.merchant-name > a")).getText();

        Assert.assertEquals("BBPRO", marca);
    }

    @After
    public void close(){
        if(driver != null){
        }
    }

    public void cerrarVentana() {
        WebElement ventanaPop = driver.findElement(By.xpath("//*[@id='newsletter']/button"));
        if (ventanaPop.isDisplayed()) {
            driver.findElement(By.xpath("//*[@id='newsletter']/button")).click();
        }
    }

    @AfterClass
    public static void closeAll(){

    }
}