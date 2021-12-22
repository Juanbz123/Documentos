package selenium.mcarrillo;

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
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class automationPracticeTest {

    private WebDriver driver;


    @BeforeClass
    public static void Setup(){
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void init(){

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://automationpractice.com/");


    }




    @Test
    public void atc01(){
        driver.findElement(By.xpath("//*[@id=\"search_query_top\"]")).sendKeys("dress");
        driver.findElement(By.xpath("//*[@id=\"searchbox\"]/button")).click();

        List<WebElement> dress = driver.findElements(By.xpath("/html/body/div/div[2]/div/div[3]/div[2]/ul/li"));
        Assert.assertTrue(dress.size() > 2); //lo valido si me entrega mas de un resultado
    }

    @Test
    public void atc02(){
        String resultadoEsperado1= "Printed Chiffon Dress";
        driver.findElement(By.cssSelector("#search_query_top")).sendKeys("printed chiffon dress");
        driver.findElement(By.cssSelector("#searchbox > button")).click();
        driver.manage().timeouts().implicitlyWait(5 , TimeUnit.SECONDS);
        List <WebElement> options_list = driver.findElements(By.cssSelector("h5[itemprop=name] > a[class=product-name]"));

        Assert.assertEquals(options_list.get(0).getText(),resultadoEsperado1);

    }
    @Test
    public void atc03(){
        driver.findElement(By.xpath("//*[@id=\"search_query_top\"]")).sendKeys("liquido matapulgas");
        driver.findElement(By.xpath("//*[@id=\"search_query_top\"]")).sendKeys(Keys.ENTER);
        driver.manage().timeouts().implicitlyWait(5 , TimeUnit.SECONDS);
        WebElement resultado = driver.findElement(By.xpath("//*[@id=\"center_column\"]/p"));
        String resultadoMensaje = resultado.getText();
        String resultadoEsperado = "No results were found for your search \"liquido matapulgas\"";
        assertEquals(resultadoEsperado, resultadoMensaje);
    }

    @Test
    public void atc04(){
        WebDriverWait espera =  new WebDriverWait(driver,20);
        driver.findElement(By.xpath("//*[@id=\"search_query_top\"]")).sendKeys("blo");


        espera.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"index\"]/div[2]/ul/li")));
        driver.findElement(By.xpath("//*[@id=\"search_query_top\"]")).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(By.xpath("//*[@id=\"search_query_top\"]")).sendKeys(Keys.ENTER);
        WebElement resultadoBusqueda= driver.findElement(By.xpath("//*[@id=\"product_reference\"]"));

        String resultadoEsperado= "Model demo_2";
        String resultadoTest = resultadoBusqueda.getText();

        Assert.assertEquals(resultadoEsperado,resultadoTest);



    }

    @Test
    public void atc05() throws InterruptedException {
        WebDriverWait espera =  new WebDriverWait(driver,20);
        //busco el campo de busqueda y le ingreso blouse
        driver.findElement(By.xpath("//*[@id=\"search_query_top\"]")).sendKeys("blouse");
        //le doy enter
        driver.findElement(By.xpath("//*[@id=\"search_query_top\"]")).sendKeys(Keys.ENTER);
        //creo un elemento webelement el cual almacena el resultado
        WebElement resultado = driver.findElement(By.cssSelector("a[title=Blouse]"));
        //le doy click al resultado
        resultado.click();
        //almaceno el color
        WebElement colorN = driver.findElement(By.xpath("//*[@id=\"color_11\"]"));
        //almaceno el boton de carrito
        WebElement botonAgregarCarrito = driver.findElement(By.xpath("//*[@id=\"add_to_cart\"]/button"));


        //Thread.sleep(6000);
        //almaceno la talla del select y seleciono la L , la cual tiene el valor 3
        Select Talla = new Select(driver.findElement(By.xpath("//*[@id=\"group_1\"]")));
        Talla.selectByValue("3");
        //selecciono el color negro
        colorN.click();
        //le doy click para agregar al carro
        botonAgregarCarrito.click();
        espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2 > i[class=icon-ok]")));
        WebElement resultadoTest= driver.findElement(By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[1]/h2"));
        String resultadoEsperado= "Product successfully added to your shopping cart";
        String resultadoEsperadoTexto = resultadoTest.getText();
        Assert.assertEquals(resultadoEsperadoTexto,resultadoEsperado);







    }
    @After
    public void close(){
        if(driver != null){
            driver.close();
        }

    }

}
