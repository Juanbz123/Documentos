package selenium.jbrizuela;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class BabyTutos {

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

        WebDriverWait espera = new WebDriverWait(driver, 15);
        driver.manage().deleteAllCookies();
        espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#newsletter")));
        driver.findElement(By.xpath("//*[@id='newsletter']/button")).click();
        List<WebElement> options_list = driver.findElements(By.cssSelector("div.bar-2-products ul li a"));
        for (WebElement l : options_list) {

        //se recorre la lista hasta encontrar la opcion requerida

            if (l.getText().contains("COCHES")) {
                l.click();
                break;
            }
        }
        espera.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Accesorios para coches')]")));
        driver.findElement(By.xpath("//a[contains(text(),'Accesorios para coches')]")).click();
        driver.findElement(By.cssSelector("div.filter:nth-child(6) > ul:nth-child(2) > li:nth-child(3) > a:nth-child(1) > span:nth-child(2)")).click();

        List<WebElement> productos = driver.findElements(By.cssSelector("div.items div.item.sq div.merchant-name"));
        System.out.println("Cantidad de items: "+productos.size());


        int contador = 0;
        for (WebElement l: productos){
            if (l.getText().contains("BBPRO")){
                contador++;
            }
        }

        Assert.assertTrue(productos.size() > 0);
        Assert.assertEquals(contador,productos.size());


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


