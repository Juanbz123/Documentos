package selenium.gortego;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Assert;
import org.openqa.selenium.support.ui.Select;
import java.util.Objects;
import static org.junit.Assert.*;

public class AutomationPractice {
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
    public void actividad1() {

        driver.findElement(By.xpath("//*[@id='search_query_top']")).sendKeys("dress");
        driver.findElement(By.xpath("//*[@id='searchbox']/button")).click();
        int catalogo;
        catalogo = driver.findElements(By.xpath("//*[@id='center_column']/ul/li[1]/div/div[1]/div")).size();

        if (catalogo < 2) {

            System.out.println("Se mostro al menos 2 resultados");
        }
        Assert.assertNotNull(catalogo);
    }

    @Test
    public void actividad2(){

        driver.findElement(By.cssSelector("#search_query_top")).sendKeys("printed chiffon dress");
        driver.findElement(By.cssSelector("#searchbox > button")).click();

        WebElement ropa = driver.findElement(By.cssSelector("#center_column > ul > li.ajax_block_product.col-xs-12.col-sm-6.col-md-4.first-in-line.last-line.first-item-of-tablet-line.first-item-of-mobile-line.last-mobile-line > div > div.left-block > div > a.product_img_link > img"));

        String guardar = ropa.getAttribute("title");

        Assert.assertEquals("Printed Chiffon Dress", guardar);


    }

    @Test
    public void act03Test(){
        driver.findElement(By.xpath("//*[@id='search_query_top']")).sendKeys("liquido matapulgas");
        driver.findElement(By.xpath("//*[@id='searchbox']/button")).sendKeys(Keys.ENTER);
        String error = driver.findElement(By.cssSelector("#center_column > p")).getText();

        assertEquals("No results were found for your search \"liquido matapulgas\"", error);


    }


    @Test
    public void atc04() {
        driver.findElement(By.xpath("//*[@id='search_query_top']")).sendKeys("blo");
        WebDriverWait espera = new WebDriverWait(driver, 15);
        espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#index > div.ac_results > ul > li")));
        WebElement barra = driver.findElement(By.xpath("//*[@id='search_query_top']"));
        barra.sendKeys(Keys.ARROW_DOWN);
        barra.sendKeys(Keys.ENTER);


        String tipo = driver.findElement(By.cssSelector("#center_column > div > div > div.pb-center-column.col-xs-12.col-sm-4 > h1")).getText();
        String modelo = driver.findElement(By.cssSelector("#product_reference")).getText();
        Assert.assertEquals("Model demo_2", modelo);
    }

    @Test
    public void atc05(){


        driver.findElement(By.xpath("//*[@id=\"search_query_top\"]")).sendKeys("blouses");
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='index']/div[2]/ul/li")));
        WebElement b = driver.findElement(By.xpath("//*[@id='search_query_top']"));
        b.sendKeys(Keys.ARROW_DOWN);
        b.sendKeys(Keys.ENTER);


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

        }

    }

    @AfterClass
    public static void closeAll(){


    }

}
