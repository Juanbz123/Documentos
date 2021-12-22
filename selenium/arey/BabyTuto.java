package selenium.arey;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BabyTuto {
    private WebDriver driver;

    @BeforeClass
    public static void setup()
    {
        System.out.println("Setup necesario antes de Instanciar");
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void init()
    {
        System.out.println("init para instanciar");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);      // MANERA IMPLICITA DE INTRODUCIR TIEMPO MAXIMO QUE LE DA AL TEST PARA CARGAR EL ATRIBUTO
        driver.get("https://www.babytuto.com/");
    }

    @Test
    public void filtrarProductosPorMarca()
    {
        if(driverElement("//*[@id=\"newsletter\"]").isDisplayed())
        {
            driverElement("//*[@id=\"newsletter\"]/button").click();
        }
            findElementList("div.bar-2-products ul li a","COCHES");
            WebDriverWait w = new WebDriverWait(driver,15);
            w.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Accesorios para coches')]")));
            driverElement("//a[contains(text(),'Accesorios para coches')]").click();
            Boolean result = w.until(ExpectedConditions.urlMatches("https://www.babytuto.com/categoria/accesorios-para-coches"));
            Assert.assertTrue(result);
            driverElement("/html/body/div[2]/div[6]/div/div[2]/div[1]/div[6]/ul/li[3]").click();

    }

    @After
    public void close() throws InterruptedException {
        if (driver != null){
            Thread.sleep(2000);
            driver.close();
        }
    }

    @AfterClass
    public static void closeAll()
    {
        System.out.println("closeAll :: Cerrar otras conexiones que fueron utilizados en el test");

    }

    public WebElement driverElement(String path)
    {
        WebElement driverelement = driver.findElement(By.xpath(path));
        return driverelement;
    }

    public void findElementList(String path,String element)
    {
        List<WebElement> options_list = driver.findElements(By.cssSelector(path));
        for (WebElement l: options_list)
        {
//se recorre la lista hasta encontrar la opción requerida
            if (l.getText().contains(element))
            {
                l.click();
                break;
            }
        }

    }
}
