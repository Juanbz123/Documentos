package selenium.landres;

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

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class Tests {

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
        driver.get("http://automationpractice.com/");

    }

    @Test
    public void atc01(){
        driver.findElement(By.id("search_query_top")).sendKeys("dress");
        driver.findElement(By.xpath("//*[@id='searchbox']/button")).click();
        int Listado;
        Listado = driver.findElements(By.xpath("//*[@id='center_column']/ul/li")).size();
        System.out.println(Listado);
        if (Listado > 2) {

            System.out.println("Se mostro al menos 2 resultados");
        }
        Assert.assertNotNull(Listado);
    }

    @Test
    public void atc02(){
        driver.findElement(By.cssSelector("#search_query_top")).sendKeys("printed chiffon dress");
        driver.findElement(By.xpath("//*[@id='searchbox']/button")).click();
        WebElement prenda = driver.findElement(By.cssSelector("#center_column > ul > li.ajax_block_product.col-xs-12.col-sm-6.col-md-4.first-in-line.last-line.first-item-of-tablet-line.first-item-of-mobile-line.last-mobile-line > div > div.left-block > div > a.product_img_link > img"));
        String guardar = prenda.getAttribute("title");
        Assert.assertEquals("Printed Chiffon Dress", guardar);

    }
    @Test
    public void atc03(){
        driver.findElement(By.id("search_query_top")).sendKeys("liquido matapulga");
        driver.findElement(By.xpath("//*[@id='searchbox']/button")).sendKeys(Keys.ENTER);
        String error=driver.findElement(By.cssSelector("#center_column > p")).getText();

        assertEquals("No results were found for your search \"liquido matapulga\"",error);

    }

    @Test
    public void atc04() {
        driver.findElement(By.id("search_query_top")).sendKeys("blo");
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#index > div.ac_results > ul > li")));
        WebElement busqueda = driver.findElement(By.xpath("//*[@id='search_query_top']"));
        // WebElement busqueda = driver.findElement(By.id("'search_query_top'"));
        String selected = driver.findElement(By.cssSelector("#index > div.ac_results > ul > li")).getText();
        busqueda.sendKeys(Keys.ARROW_DOWN);
        Assert.assertEquals("Blouses > Blouse",selected);
        busqueda.sendKeys(Keys.ENTER);


        String blousa = driver.findElement(By.xpath("//*[@id='product_reference']")).getText();
        Assert.assertEquals("Model demo_2", blousa);
    }

    @Test
    public void atc05() {
        driver.findElement(By.xpath("//*[@id=\"search_query_top\"]")).sendKeys("blouses");
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='index']/div[2]/ul/li")));
        WebElement b = driver.findElement(By.xpath("//*[@id='search_query_top']"));

        String selected = driver.findElement(By.cssSelector("#index > div.ac_results > ul > li")).getText();
        b.sendKeys(Keys.ARROW_DOWN);
        Assert.assertEquals("Blouses > Blouse",selected);
        b.sendKeys(Keys.ENTER);


        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        driver.findElement(By.xpath("//*[@id='group_1']")).click();
        Select Talla = new Select(driver.findElement(By.id("group_1")));
        Talla.selectByValue("3");


        driver.findElement(By.xpath("//*[@id='color_8']")).click();

        driver.findElement(By.xpath("//*[@id='add_to_cart']/button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='layer_cart']/div[1]/div[1]/h2")));
        String compra = driver.findElement(By.xpath("//*[@id='layer_cart']/div[1]/div[1]/h2")).getText();
        Assert.assertEquals("Product successfully added to your shopping cart",compra);
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