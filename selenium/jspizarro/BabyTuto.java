package selenium.jspizarro;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;


public class BabyTuto {

    private WebDriver driver;

    @BeforeClass
    public static void Setup(){
        System.out.println("Setup necesario antes de Instanciar");
        WebDriverManager.chromedriver().setup();

    }
    @Before
    public void init(){
        System.out.println("init para instanciar");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.babytuto.com/");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @Test
    public void atc06_FiltrarProductosPorMarca() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver,15);
        driver.manage().deleteAllCookies();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#newsletter")));
        driver.findElement(By.cssSelector("#newsletter .close")).click();

        int count = driver.findElements(By.cssSelector("div.container > div.bar-2-products ul li a")).size();
        for (int i = 0; i < count; i++) {
            String categoria = driver.findElements(By.cssSelector("div.container > div.bar-2-products ul li a")).get(i).getText();
            if(categoria.equals("COCHES")){
                driver.findElements(By.cssSelector("div.container > div.bar-2-products ul li a")).get(i).click();
                break;
            }
        }

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Accesorios para coches')]")));
        driver.findElement(By.xpath("//a[contains(text(),'Accesorios para coches')]")).click();

        driver.findElement(By.xpath("//span[contains(text(),'BBpro')]")).click();

        int products = driver.findElements(By.xpath("//a[@itemprop='brand']")).size();
        boolean errorMarca = false;
        for (int i = 0; i < products; i++) {
            String marca = driver.findElements(By.xpath("//a[@itemprop='brand']")).get(i).getText();
            if(!marca.equals("BBPRO")){
                errorMarca = true;
                break;
            }
        }

        assertFalse(errorMarca);

    }

    @After
    public void close(){
        if(driver != null){
            driver.close();
        }

    }

    @AfterClass
    public static void closeAll(){
        System.out.println("closeAll :: Cerrar otras conexiones que fueron utilizadas en el test");

    }

}
