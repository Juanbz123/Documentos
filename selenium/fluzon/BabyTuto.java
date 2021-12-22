package selenium.fluzon;

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
    public static void Setup(){
        System.out.println("Setup necesario antes de Instanciar");
        WebDriverManager.chromedriver().setup();

    }
    @Before
    public void init(){
        System.out.println("init para instanciar");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void atc01CargarPaginaPrincipal(){
        driver.get("https://www.babytuto.com/");

        //validar si se levanto una popup de suscripcion
        if (driver.findElement(By.cssSelector("div.modal-backdrop.fade.in")).isDisplayed())
        {
            System.out.println("Pantalla de Suscripcion detectada");
            driver.findElement(By.cssSelector("button.close")).click();
            System.out.println("Cerrar pantalla de Suscripcion");
        }

        WebDriverWait explicit_wait = new WebDriverWait(driver, 5);

        //validar si se levanta popup de ofertas
        if (driver.findElements(By.cssSelector("div.pa-subs-box-th.pa-subs-box-th-1")).size() != 0)
        {
            System.out.println("Ofertas screen on");
            driver.findElement(By.cssSelector("a#pa-deny-btn")).click();
            System.out.println("Cerrar pantalla de Ofertas");
        }


        //obtengo la lista de las categorias para seleccionar la opcion Coches
        List<WebElement>  options_list = driver.findElements(By.cssSelector("div.bar-2-products ul li a"));

        for (WebElement l: options_list)
        {
            //se recorre la lista hasta encontrar la opción COCHES
            if (l.getText().contains("COCHES"))
            {
                //seleccionar la categoria pero esto sólo despliega el menú de subcategorías
                l.click();
                break;
            }
        }

        //seleccionar la categoría coches, se espera a que esté visible
        explicit_wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Accesorios para coches')]")));

        //seleccionar el enlace que lleva a la pagina de accesorios para coches
        driver.findElement(By.xpath("//a[contains(text(),'Accesorios para coches')]")).click();

        //filtrar por marca BBPRO
        driver.findElement(By.xpath("//a/span[contains(text(),'BBpro')]")).click();

        //obtengo la lista de productos y valido que sean todos BBpro
        List<WebElement> products = driver.findElements(By.xpath("//div[@class='merchant-name']/a[@itemprop='brand']"));

        int total_products = products.size();
        int total_brand_products = 0;

        for (WebElement p: products)
        {
            //se recorre la lista hasta encontrar la opción COCHES
            if (p.getText().contains("BBPRO"))
            {
                //cuento el total de productos de la marca buscada
                total_brand_products++;
            }
        }
        Assert.assertEquals(total_products,total_brand_products);

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
