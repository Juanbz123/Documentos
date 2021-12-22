package selenium.bbarreto;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AutomationTests {
    private WebDriver driver;

    @BeforeClass
    public static void Setup(){

        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void init(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://automationpractice.com/");
        while(Objects.equals(driver.getTitle(), "Resource Limit Is Reached")){
            driver.navigate().refresh();
        }
    }

    @Test
    public void act01Test(){
        driver.findElement(By.xpath("//*[@id='search_query_top']")).sendKeys("dress"); //Busca el campo selecciona en ID, y luego lo rellena con la palabra escrita
        driver.findElement(By.xpath("//*[@id='searchbox']/button")).click();
        int lista;
        lista = driver.findElements(By.xpath("//*[@id='center_column']/ul/li[1]/div/div[1]/div")).size();
        if(lista < 2){
            System.out.println("Busqueda mostro al menos 2 resultados");
        }
        Assert.assertNotNull(lista);
        //driver.close();
    }

    @Test
    public void act02Test(){
        driver.findElement(By.cssSelector("#search_query_top")).sendKeys("printed chiffon dress");//Busca el campo selecciona con css, y luego lo rellena con la palabra escrita
        driver.findElement(By.cssSelector("#searchbox > button")).click(); //hace click en el boton buscar
        WebElement guardarropa = driver.findElement(By.cssSelector("#center_column > ul > li.ajax_block_product.col-xs-12.col-sm-6.col-md-4.first-in-line.last-line.first-item-of-tablet-line.first-item-of-mobile-line.last-mobile-line > div > div.left-block > div > a.product_img_link > img"));
        String ropa = guardarropa.getAttribute("title");
        System.out.println(ropa);
        Assert.assertEquals("Printed Chiffon Dress", ropa);
       // driver.close();
    }

    @Test
    public void act03Test(){
        driver.findElement(By.xpath("//*[@id='search_query_top']")).sendKeys("liquido matapulgas");
        driver.findElement(By.xpath("//*[@id='searchbox']/button")).sendKeys(Keys.ENTER);
        String error = driver.findElement(By.cssSelector("#center_column > p")).getText();
        assertEquals("No results were found for your search \"liquido matapulgas\"", error);
        //driver.close();
    }

    @Test
    public void act04Test(){
        //Busca el elemento de la barra y escribe la prenda
        driver.findElement(By.xpath("//*[@id='search_query_top']")).sendKeys("blo");
        //Hace una espera hasta que aparezca el elemento sugerido
        WebDriverWait espera = new WebDriverWait(driver, 15);
        espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#index > div.ac_results > ul > li")));
        //Elige el elemento con el sendkeys
        WebElement barra = driver.findElement(By.xpath("//*[@id='search_query_top']"));
        barra.sendKeys(Keys.ARROW_DOWN);
        String seleccion = driver.findElement(By.cssSelector("#index > div.ac_results > ul > li")).getText();
        Assert.assertEquals("Blouses > Blouse", seleccion);
        barra.sendKeys(Keys.ENTER);

        //Guarda los valores del campo de texto y luego los une para compararlos con el assert equals
        String tipo = driver.findElement(By.cssSelector("#center_column > div > div > div.pb-center-column.col-xs-12.col-sm-4 > h1")).getText();
        String modelo = driver.findElement(By.cssSelector("#product_reference")).getText();
        Assert.assertEquals("Blouse Model demo_2", tipo + " " + modelo);
        //driver.close();
    }

    @Test
    public void act05Test(){

        //Busca el elemento de la barra y escribe la prenda
        driver.findElement(By.xpath("//*[@id='search_query_top']")).sendKeys("blouse");
        //Hace una espera hasta que aparezca el elemento sugerido
        WebDriverWait espera = new WebDriverWait(driver, 15);
        espera.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='index']/div[2]/ul/li")));
        //Elige el elemento con el sendkeys, y lo guardamos en una variable asi no repetimos el codigo
        WebElement barra = driver.findElement(By.xpath("//*[@id='search_query_top']"));
        barra.sendKeys(Keys.ARROW_DOWN);
        String seleccion = driver.findElement(By.cssSelector("#index > div.ac_results > ul > li")).getText();
        //Comparacion si es el mismo elemento
        Assert.assertEquals("Blouses > Blouse", seleccion);
        barra.sendKeys(Keys.ENTER);

        //Mstrar cuando elige la talla
        //Elige la talla
        driver.findElement(By.xpath("//*[@id='group_1']")).click();
        //Realizar un select para elegir la casilla de talle
        Select talle = new Select(driver.findElement(By.id("group_1")));
        //Dentro de talle que seleccione la opcion 3, talle L
        talle.selectByValue("3");

        //Hace lo mismo que con la talla, cuando se elige el color blanco
        driver.findElement(By.xpath("//*[@id='color_8']")).click();
        //Hace el click en añadir al carrito
        driver.findElement(By.xpath("//*[@id='add_to_cart']/button")).click();

        //Realiza una espera hasta que aparezca el elemento de compra realizada
        espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#layer_cart > div.clearfix > div.layer_cart_product.col-xs-12.col-md-6")));
        //Guarda la información que seria el texto de la compra realizada en un string con el get Text
        String compralista = driver.findElement(By.cssSelector("#layer_cart > div.clearfix > div.layer_cart_product.col-xs-12.col-md-6 > h2")).getText();
        //Compara el mensaje que de debe obtener con la variable que guardo el resultado compralista
        Assert.assertEquals("Product successfully added to your shopping cart", compralista);
       // driver.close();
    }

    @After
    public void close() throws InterruptedException {
        if(driver != null){
        }
    }

    @AfterClass
    public static void closeAll(){

    }
}
