package selenium.clopez;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class babytuto {

    private WebDriver driver;

    private static void limit_Is_Reached(WebDriver driver){
        while (Objects.equals(driver.getTitle(),"Resource Limit Is Reached")){
            driver.navigate().refresh();
        }
    }

    @BeforeClass
    public static void Setup(){
        System.out.println("Setup necesario antes de Instanciar");
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void init() throws InterruptedException {
        System.out.println("init para instanciar");
        driver = new ChromeDriver();
        // driver.manage().deleteAllCookies(); //borrar cookies
        driver.manage().window().maximize();
        driver.get("https://www.babytuto.com/");
        limit_Is_Reached(driver);
    }

    @Test
    public void atc06_FiltrarProductosPorMarca() {
        // 1.- CARGAR HOME
        // 2.- CERRAR PANTALLA EMERGENTE
        // APARECE PANTALLA MODAL?
        if (driver.findElement(By.cssSelector(".modal-backdrop")).isDisplayed()){
            System.out.println("Aparece ventana emergente");
            driver.findElement(By.cssSelector("#newsletter > button:nth-child(1)")).click();
            System.out.println("Ventana emergente cerrada");
        }
        // ESPERA
        WebDriverWait espera = new WebDriverWait(driver,10);
        // 3.- SELECCIONAR CATEGORIA COCHES
        // CREAMOS LISTA
        List<WebElement> categorias = driver.findElements(By.cssSelector("div.bar-2-products ul li a"));
        // CICLO FOR PARA SELECCIONAR LA CATEGORIA COCHES
        for (WebElement l: categorias){
            // RECORREMOS LA LISTA HASTA ENCONTRAR LA PALABRA REQUERIDA
            if (l.getText().contains("COCHES")){
                System.out.println("Categoria COCHES encontrada");
                // HACEMOS CLICK EN LA CATEGORIA SELECCIONADA
                l.click();
                System.out.println("Esperamos que se despliegue la categoria");
                // ESPERAMOS A QUE SE DESPLIEGUE EL CONTENIDO DE LA CATEGORIA
                espera.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Accesorios para coches')]")));
                break;
            }
        }
        // ELEMENTO SEÑALADO HACEMOS CLICK
        System.out.println("Click en la categoria");
        // LE DAMOS CLICK A LA CATEGORIA
        driver.findElement(By.xpath("//a[contains(text(),'Accesorios para coches')]")).click();
        //ESPERAMOS QUE CARGE LA SIGUIENTE PAGINA
        // LE DAMOS CLICK EN BBPRO
        System.out.println("Nueva pagina");
        driver.findElement(By.cssSelector("div.filter:nth-child(6) > ul:nth-child(2) > li:nth-child(3) > a:nth-child(1) > span:nth-child(2)")).click();
        List<WebElement> productos = driver.findElements(By.cssSelector("div.items div.item.sq div.merchant-name"));
        System.out.println("Cantidad de elementos: "+productos.size());
        int contador = 0;
        // EN LA LISTA TODOS SON BBPRO?
        for (WebElement l: productos){
            if (l.getText().contains("BBPRO")){
                contador++;
            }
        }
        // SI SE ENCUENTRAN PRODUCTOS LUEGO DEL FILTRO
        Assert.assertTrue(productos.size() > 0);
        // TODOS LOS PRODUCTOS SON BBPRO?
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
