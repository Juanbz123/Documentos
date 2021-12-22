package selenium.landres;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


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

    }

    @Test
    public void atc01(){


        driver.findElement(By.xpath("//*[@id='newsletter']/button")).click();
        driver.findElement(By.cssSelector("div.bar-2-products.menu-cats ul li a")).click();


        //driver.findElement(By.xpath("body > div.main-container > div.header-3.sup.section-products > div.header-bar > div > div > ul > li:nth-child(1) > a")).click();
        List<WebElement> options_list = driver.findElements(By.cssSelector("div.bar-2-products.menu-cats ul li a"));

        // System.out.println(options_list.size());
        for (WebElement l: options_list)
        {
//se recorre la lista hasta encontrar la opción requerida
            if (l.getText().equals("Accesorios para coches"))
            {

                l.click();
                break;
            }
        }

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a/span[contains(text(),'BBpro')]")));
        driver.findElement(By.xpath("//a/span[contains(text(),'BBpro')]")).click();


        List <WebElement> listaDetalleProducto= driver.findElements(By.cssSelector("div.merchant-name a[itemprop='brand']"));
        int total=listaDetalleProducto.size();
        int contador=0;


        for (WebElement elemento: listaDetalleProducto) {
            if(elemento.getText().equalsIgnoreCase("BBpro"))
                contador+=1;
        }
        Assert.assertTrue(total == contador);
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