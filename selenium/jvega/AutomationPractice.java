package selenium.jvega;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AutomationPractice {

    private WebDriver driver;
    private WebDriverWait espera;
    private WebElement textoBusqueda;
    private WebElement botonBusqueda;
    private String resultado;

    @BeforeClass
    public static void Setup(){
        //Inicialización del WebDriver con Chrome
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void init(){
        resultado ="";

        driver = new ChromeDriver();
        driver.get("http://automationpractice.com/");   //Cargar la página
        driver.manage().window().maximize();  // Maximizar

        espera= new WebDriverWait(driver,10); // defino la espera de forma explicita  hasta 10 segundos

        textoBusqueda= driver.findElement (By.xpath("//*[@id='search_query_top']"));
        botonBusqueda=  driver.findElement (By.xpath("//*[@id='searchbox']/button"));

    }

    //  @Test  // Test 1 con Xpath
    public void resultadoDosProductosAlmenos(){
        textoBusqueda.sendKeys("dress");
        botonBusqueda.click();;

        espera.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='center_column']/ul/li")));
        // con findelements obtengo varios elmentos  y  los asigno a una lista de elementos web
        List<WebElement> elementos  =  driver.findElements(By.xpath("//*[@id='center_column']/ul/li"));
         // Con que la lista sea mayor o igual a 2, significa que va a contener por lo menos 2 elementos
         assertTrue(elementos.size() >= 2);
    }


      // @Test  // test 2 con CSS
    public void busquedaPrimerProductoCoincide(){

       driver.findElement(By.cssSelector("#search_query_top")).sendKeys("printed chiffon dress"); //al elemento seleccionado le paso un valor
       driver.findElement(By.cssSelector("#searchbox > button")).click(); //elemento seleccionado hago click;

        //Espera 6 segundo asi se carga la pagina
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);

        //paso el texto del elemento seleccionado a una variable de tipo string
        resultado = driver.findElement(By.cssSelector("#center_column .product-name[title*='Chiffon']")).getText();
       // System.out.println(resultado);

        //comparo si el texto que saque del elemento es igual al texto esperado
        assertEquals("Printed Chiffon Dress",resultado);

    }

     //  @Test // Test 3   No Usar Thread Sleep
    public void mensajeProductoNoEncontrado(){

        textoBusqueda.sendKeys("liquido matapulgas");
        botonBusqueda.sendKeys(Keys.ENTER);

        // Esperar hasta que sea visible el elemento con su mensaje
        espera.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='center_column']/p")));

        //comparo si el texto de ese elemento es igual al esperado
        assertEquals("No results were found for your search \"liquido matapulgas\"", driver.findElement (By.xpath("//*[@id='center_column']/p")).getText());

    }

     // @Test //test 4  No Usar Thread Sleep
    public void productoListaDinamicaCargarDetalle(){
       buscarPalabra("blo");

        assertEquals("Model demo_2", driver.findElement(By.cssSelector("#product_reference")).getText());

    }

    //  @Test // Test 5 Usando Clase Select
    public void compraExitosa(){
       buscarPalabra("blo");

        assertEquals("Model demo_2", driver.findElement(By.cssSelector("#product_reference")).getText());

        // A partir de aca es lo nuevo que hay que hacer

        //como es una lista estatica trabajo con select
        Select s = new Select (driver.findElement(By.xpath("//*[@id='group_1']")));
        s.selectByVisibleText("L");

        //click al color seleccionado
        driver.findElement (By.xpath("//*[@id='color_8']")).click();
        //click al boton de compra
        driver.findElement(By.xpath("//*[@id='add_to_cart']/button")).click();
        //espero hasta que salga el mensaje  de compra

        espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#layer_cart .layer_cart_product h2")));
         //System.out.println( driver.findElement(By.xpath("//*[@id='layer_cart']/div[1]/div[1]/h2")).getText());
        // Comparo el mensaje que obtuve con el esperado

        assertEquals("Product successfully added to your shopping cart", driver.findElement( By.cssSelector("#layer_cart .layer_cart_product h2")).getText());
    }

    public void buscarPalabra(String palabra){
        textoBusqueda.sendKeys(palabra);
        // espera hasta que se cargue la lista
        espera.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#index .ac_results li")));
        //le paso los elementos a una lista de web Element
        List<WebElement> cargaAutomatica  =  driver.findElements(By.cssSelector("#index .ac_results li"));
       // System.out.println(cargaAutomatica.size());
        for (WebElement elemento: cargaAutomatica) {
            if (elemento.getText().toLowerCase().contains(palabra)){
                elemento.click();
                break;
          }
        }
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
