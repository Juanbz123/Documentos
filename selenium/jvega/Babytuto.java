package selenium.jvega;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertTrue;


public class Babytuto {

    private WebDriver driver;

    @BeforeClass
    public static void Setup(){
        //Inicialización del WebDriver con Chrome
        WebDriverManager.chromedriver().setup();

    }

    @Before
    public void init(){
        driver = new ChromeDriver();
        driver.get("https://www.babytuto.com/");   //Cargar la página
        driver.manage().deleteAllCookies(); //borrar cookies
        driver.manage().window().maximize();  // Maximizar
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

     @Test
    public void productoPorMarca() {

        //sale el popup, por lo tanto lo cierro
         driver.findElement(By.xpath(" //*[@id='newsletter']/button")).click();

         /* Como COCHES esta en la primera ubicacion, si le doy click al elmento que obtengo con la lista  va a
          tomar coches, pero suponiendo que en otro caso no sea el primer elmento  mejor utilizar algun ciclo para recorrerlo
            driver.findElement(By.cssSelector("div.bar-2-products.menu-cats ul li a")).click(); */

        //Obtengo una lista  de elementos del Menu
         List <WebElement> listaMenu = driver.findElements(By.cssSelector("div.bar-2-products.menu-cats ul li a"));
        //recorro la lista y cuando encuentro COCHES hago click
         for (WebElement elemento:listaMenu) {
             if(elemento.getText().equals("COCHES")) {
                 elemento.click();
                 break;
             }
         }


         //obtengo una lista con todos los elementos de Menu
         List<WebElement> listaOpciones = driver.findElements(By.cssSelector("div.bar-2-products.menu-cats ul li"));
        //recorro la lista y comparo cual es igual a (accesorios para coche) y en casp que se encuentre hago click
         for (WebElement elemento:listaOpciones) {
             if(elemento.getText().equals("Accesorios para coches")) {
                 elemento.click();
                 break;
             }
         }


         // obtengo una lista con todas las opciones para  filtrar
         List<WebElement> listaFiltro =  driver.findElements(By.cssSelector("div.span2.filters li a span"));
        //recorro la lista y pregunto cual contiene el BBpro para luego hacerle click
         for (WebElement elemento:listaFiltro) {
              if(elemento.getText().contains("BBpro")) {
                 elemento.click();
                 break;
             }
         }

         //obtengo una lista con los detalles de la marca de los productos
        List <WebElement> listaDetalleProducto= driver.findElements(By.cssSelector("div.merchant-name a[itemprop='brand']"));
        int total=listaDetalleProducto.size(); //obtengo la cantidad de productos (sus detalles) y lo asigno  a una variable entera
        int contador=0;

       //recorro la lista y pregunto cuales son igual a bbpro
        for (WebElement elemento: listaDetalleProducto) {
             if(elemento.getText().equalsIgnoreCase("BBpro"))
                 contador+=1; //en caso que sean iguales los cuento
                }

         assertTrue(total == contador);
             }


    @After
    public void close(){
        if(driver != null){
            driver.close();
        }

    }



}
