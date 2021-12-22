package selenium.mcarrillo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

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

        driver.get("https://www.babytuto.com/");

    }

    @Test
    public void atc01(){
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebDriverWait espera =  new WebDriverWait(driver,10);
        //busco si el msg se esta mostrando y luego lo cierro
        if (driver.findElement(By.xpath("//*[@id=\"newsletter\"]/div/div")).isDisplayed()){
            driver.findElement(By.xpath("//*[@id=\"newsletter\"]/button")).click();
        }

        List<WebElement>ListaNav = driver.findElements(By.cssSelector("div.bar-2-products > ul > li > a"));
        for (WebElement l: ListaNav){
//recorro con un ciclo for buscando si uno contiene la palabra coches y lo clickeo
            if(l.getText().contains("COCHES")){
                l.click();
                break;
            }
        }

        espera.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Accesorios para coches')]")));
        driver.findElement(By.xpath("//a[contains(text(),'Accesorios para coches')]")).click();


        driver.findElement(By.xpath("//span[contains(text(),'BBpro')]")).click();


        List <WebElement> Productos = driver.findElements(By.cssSelector("div[class=merchant-name] > a[itemprop=brand]"));
        int cantidadProd = Productos.size();



        int ProductosValidados= 0;


        while (ProductosValidados<cantidadProd){


            String Marca = driver.findElements(By.cssSelector("div[class=merchant-name] > a[itemprop=brand]")).get(ProductosValidados).getText();
            ProductosValidados++;
        }



        Assert.assertEquals(cantidadProd,ProductosValidados);


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
