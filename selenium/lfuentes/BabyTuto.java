package selenium.lfuentes;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class BabyTuto {

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
        driver.get("https://www.babytuto.com/");

    }

    @Test
    public void atc01() {


        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.manage().deleteAllCookies();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#newsletter")));
        driver.findElement(By.xpath("//*[@id='newsletter']/button")).click();
        List<WebElement> options_list = driver.findElements(By.cssSelector("div.bar-2-products ul li a"));
        for (WebElement l : options_list) {
            //se recorre la lista hasta encontrar la opciÃ³n requerida
            if (l.getText().contains("COCHES")) {
                l.click();
                break;
            }
        }
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Accesorios para coches')]")));
        driver.findElement(By.xpath("//a[contains(text(),'Accesorios para coches')]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'BBpro')]")));
        driver.findElement(By.xpath("//span[contains(text(),'BBpro')]")).click();

        List<WebElement> listaMarca = driver.findElements(By.xpath("//a[@itemprop='brand']"));

        int tamañoLista = listaMarca.size();
        int contador = 0;

        for (WebElement l : listaMarca) {
            //se recorre la lista hasta encontrar la opcion requerida
            if (l.getText().equalsIgnoreCase("BBpro")) {
                contador = contador + 1;
            }
        }

        Assert.assertTrue(tamañoLista == contador);
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