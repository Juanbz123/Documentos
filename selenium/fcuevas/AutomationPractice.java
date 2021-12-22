package selenium.fcuevas;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Instant;
import java.util.List;
import static org.junit.Assert.*;

public class AutomationPractice {

    private WebDriver driver;

    @BeforeClass
    public static void Setup(){
        System.out.println("Setup necesario antes de Instanciar");
        WebDriverManager.chromedriver().setup();
    }
    @Before
    public void init(){
        //Inicialización del WebDriver con Chrome
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        // Ventana del navegador maximizado
        driver.manage().window().maximize();
        driver.get("http://automationpractice.com/");
    }

    @Test
    public void atc01_busquedaPalabrasClaves(){
        driver.findElement(By.xpath("//*[@id='search_query_top']")).sendKeys("dress");
        driver.findElement(By.xpath("//*[@id='searchbox']/button")).click();

        WebElement objetoBuscado = driver.findElement(By.xpath("//*[@id='center_column']/h1/span[2]"));
        String [] palabras = objetoBuscado.getAttribute("innerHTML").trim().split(" ");
        int cantidad = Integer.parseInt(palabras[0]);
      Assert.assertTrue(cantidad > 2);
    }

    @Test
    public void atc02_busquedaDirectaProductoExistente(){
        driver.findElement(By.cssSelector("#search_query_top")).sendKeys("Printed Chiffon Dress");
        driver.findElement(By.cssSelector("#searchbox > button")).click();
        WebElement objetoBuscado = driver.findElement(By.cssSelector("#center_column > ul > li.ajax_block_product.col-xs-12.col-sm-6.col-md-4.first-in-line.last-line.first-item-of-tablet-line.first-item-of-mobile-line.last-mobile-line > div > div.right-block > h5 > a"));

        Assert.assertEquals("Printed Chiffon Dress", objetoBuscado.getText());
    }

    @Test
    public void atc03_tienda_emite_msj_producto_no_encontrado(){
        driver.findElement(By.cssSelector("#search_query_top")).sendKeys("liquido matapulgas");
        driver.findElement(By.xpath("//*[@id='searchbox']/button")).sendKeys(Keys.ENTER);

        WebElement objetoBuscado = driver.findElement(By.xpath("//*[@id='center_column']/p"));
        String buscado = objetoBuscado.getAttribute("innerHTML");
        System.out.println(buscado);
    }

    @Test
    public void atc04_encontrar_productodeListaDinamica(){
        WebDriverWait wait = new WebDriverWait(driver, 15);
        driver.findElement(By.cssSelector("#search_query_top")).sendKeys("blo");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='index']/div[2]")));
        List< WebElement> sugerenciasProductos = driver.findElements(By.xpath("//*[@id='index']/div[2]/ul/li"));
        for (int i = 0; i <sugerenciasProductos.size() ; i++) {
            if (sugerenciasProductos.get(i).getText().trim().contains("Blouses > Blouse")){
                sugerenciasProductos.get(i).click();
            }
            break;
        }
    }

    @Test
    public void atc05_AgregarProductoCambiandoTallaYColor (){
        WebDriverWait wait = new WebDriverWait(driver, 15);
        driver.findElement(By.xpath("//*[@id='search_query_top']")).sendKeys("blouse");
        driver.findElement(By.xpath("//*[@id='searchbox']/button")).click();

        WebElement objetoBuscado = driver.findElement(By.xpath("//*[@id=\'center_column\']/ul/li/div/div[2]/h5/a"));
        if (objetoBuscado.getText().trim().contains("Blouse")){
            objetoBuscado.click();

            Select objetoSelect = new Select (driver.findElement(By.xpath("//*[@id=\'group_1\']")));
            objetoSelect.selectByVisibleText("L");
        }
        driver.findElement(By.xpath("//*[@id='color_8']")).click();
        driver.findElement(By.xpath("//*[@id='add_to_cart']/button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='layer_cart']/div[1]/div[1]/h2")));
        String producto = driver.findElement(By.xpath("//*[@id='layer_cart']/div[1]/div[1]/h2")).getText();
        Assert.assertEquals("Product successfully added to your shopping cart",producto);
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
