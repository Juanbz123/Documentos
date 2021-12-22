package selenium.mrivera;

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

import static org.junit.Assert.*;

public class AutomationPractice {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public static void Setup(){
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void init(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 30);

        //Navegar a página
        driver.get("http://automationpractice.com");

        //En caso de que la página se cuelgue
        while(driver.getTitle().equals("508 Resource Limit Is Reached")){
            driver.navigate().refresh();
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

    @Test
    public void etc01_BusquedaPalabrasClave() {
        //Busqueda
        driver.findElement(By.xpath("//*[@id=\'search_query_top\']")).sendKeys("dress");
        driver.findElement(By.xpath("//*[@id=\'searchbox\']/button")).click();

        //Obtiene los resultados
        List<WebElement> productos = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id=\'center_column\']/ul/li")));

        //Comprobacion
        int cantidadDeProductos = productos.size();
        assertTrue(" La búsqueda debe obtener al menos 2 resultados.", cantidadDeProductos >= 2);
    }

    @Test
    public void etc02_BusquedaDirectaProductoExistente(){
        String elementoABuscar = "printed chiffon dress";

        //Busqueda
        driver.findElement(By.cssSelector("#search_query_top")).sendKeys(elementoABuscar);
        driver.findElement(By.cssSelector("#searchbox > button")).click();

        //Obtiene el primer resultado
        WebElement elemento = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h5 > a.product-name")));

        //Comprobacion
        String resultado = elemento.getText().toLowerCase();
        assertEquals(elementoABuscar, resultado);
    }

    @Test
    public void atc03_MensajeProductoNoEncontrado() {
        String valorABuscar = "liquido matapulgas";
        String valorEsperado = "No results were found for your search " + "\"" + valorABuscar + "\"";

        //Busqueda
        WebElement input =  driver.findElement(By.cssSelector("#search_query_top"));
        input.sendKeys(valorABuscar);
        input.sendKeys(Keys.ENTER);

        //Espera mensaje en pantalla
        WebElement elemento = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#center_column > p.alert")));
        String resultado = elemento.getText();

        //Comprobacion
        assertEquals(valorEsperado, resultado);
    }

    @Test
    public void atc04_EncontrarProductoListaDinamica(){
        buscarProducto("blo");

        //Obtiene el resultado
        WebElement resultado = driver.findElement(By.cssSelector("#product_reference"));

        //Comprobacion
        String modeloABuscar = "Model demo_2";
        assertEquals(modeloABuscar,resultado.getText());
    }


    @Test
    public void atc05_AgregarProductoAlCarrito() {
        buscarProducto("Blouse");

        //Cambia la talla
        Select select = new Select(driver.findElement(By.cssSelector("select#group_1")));
        select.selectByVisibleText("L");

        //Elige otro color que no sea el seleccionado
        WebElement colorPredefinido = driver.findElement(By.cssSelector("a.color_pick.selected"));
        List<WebElement> colores = driver.findElements(By.cssSelector("a.color_pick"));
        for(WebElement color: colores){
            if(color.getAttribute("title") != colorPredefinido.getAttribute("title")){
                color.click();
                break;
            }
        }

        //Boton Agregar al Carro
        driver.findElement(By.cssSelector("#add_to_cart > button")).click();

        //Espera el Popup y obtiene el texto de confirmacion
        WebElement resultado = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#layer_cart div.layer_cart_product > h2")));

        //Comprobacion
        String valorEsperado = "Product successfully added to your shopping cart";
        assertEquals(valorEsperado, resultado.getText());
    }

    public void buscarProducto(String valorABuscar){
        //Pone valores al buscador
        driver.findElement(By.cssSelector("#search_query_top")).sendKeys(valorABuscar);

        //Espera resultados
        List<WebElement> items =  wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".ac_results > ul > li")));

        //Elige el primer resultado que concuerde con el valor a buscar
        for(WebElement item: items){
            if(item.getText().toLowerCase().contains(valorABuscar.toLowerCase())){
                item.click();
                break;
            }
        }

        //Espera que se cargue la página
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#product_reference")));
    }
}
