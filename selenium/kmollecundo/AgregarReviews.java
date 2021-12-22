package selenium.kmollecundo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class AgregarReviews {
    private WebDriver driver;
    @BeforeClass
    public static void Setup(){
        //Initialization del web driver con chrome
        WebDriverManager.chromedriver().setup();
    }
    @Before
    public void Init(){
        driver = new ChromeDriver();

        //Metodo para iniciar siempre con la pantalla maximizada
        driver.manage().window().maximize();

        //Cargar la página
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://automation.frankluzon.com/");
    }
    @Test
    public void atc01_AgregarReview() {
        //Buscar producto CAP
        WebElement search = driver.findElement(By.cssSelector("#woocommerce-product-search-field-0"));
        search.sendKeys("CAP");
        search.sendKeys(Keys.ENTER);

        //Selecciono la opcion REviews
        driver.findElement(By.cssSelector("#tab-title-reviews > a")).click();

        //Selecciono estrella 2
        WebDriverWait wait2 = new WebDriverWait(driver, 10);
        wait2.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#commentform > div > p > span > a.star-2")));
        driver.findElement(By.cssSelector("#commentform > div > p > span > a.star-2")).click();

        //Escribo texto en el campo YourReview
        WebElement escribir = driver.findElement(By.cssSelector("#comment"));
        escribir.click();
        escribir.sendKeys("Escribo Texto12");

        //Introduzco nombre de persona
        WebElement name = driver.findElement(By.cssSelector("#author"));
        name.click();
        name.sendKeys("Pepito");

        //Introduzco mail valido
        WebElement mail = driver.findElement(By.cssSelector("#email"));
        mail.click();
        mail.sendKeys("kev.moll93@gmail.com");

        //Le hago click a summit
        driver.findElement(By.cssSelector("#submit")).click();

        //Verifico que la pagina muestre el mensaje especificado de exito.
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("dfn, cite, em, i")));
        WebElement check = driver.findElement(By.cssSelector("dfn, cite, em, i"));
        Assert.assertEquals("Your review is awaiting approval", check.getText());
    }
    @After
    public void Close(){
        if (driver != null) {
            //
            driver.close();
        }
    }
}
