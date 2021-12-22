package selenium.bfuentes;

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
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
public class AutomationPractice {

    private WebDriver driver;

    @BeforeClass
    public static void Setup(){
        System.out.println("Setup necesario antes de Instanciar");
        WebDriverManager.chromedriver().setup();

    }
    @Before
    public void init() throws InterruptedException {
        System.out.println("init para instanciar");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://automationpractice.com/");
        // Esperamos

        while (Objects.equals(driver.getTitle(), "508 Error - Resource Limit Reached")){
            driver.navigate().refresh();
            // Esperamos
            Thread.sleep(1000);
        }

    }

    @Test
    public void atc01() {

        driver.findElement(By.xpath("//*[@id=\"search_query_top\"]")).sendKeys("dress");
        driver.findElement(By.xpath("//*[@id=\"searchbox\"]/button")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // espera implicito para toda la pagina
        List<WebElement> dress = driver.findElements(By.xpath("/html/body/div/div[2]/div/div[3]/div[2]/ul/li"));
        Assert.assertTrue(dress.size() > 2);


    }

    @Test
    public void act02() {

        driver.findElement(By.xpath("//*[@id=\"search_query_top\"]")).sendKeys("printed chiffon dress");
        driver.findElement(By.xpath("//*[@id=\"searchbox\"]/button")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // espera implicito para toda la pagina

        String resultado= "Printed Chiffon Dress";
        WebElement resultadoBusqueda1= driver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li[1]/div/div[2]/h5/a"));
        Assert.assertEquals(resultadoBusqueda1.getText(), resultado);



    }

    @Test
    public void act03() {
        WebElement buscar =  driver.findElement(By.xpath("//*[@id=\"search_query_top\"]"));
        buscar.sendKeys("liquido matapulgas");
        buscar.sendKeys(Keys.ENTER); // presionar enter en el cuadro de busqueda
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // espera implicito para toda la pagina
        WebElement resultadoBusqueda= driver.findElement(By.xpath("//*[@id=\"center_column\"]/p"));
        Assert.assertEquals("No results were found for your search \"liquido matapulgas\"", resultadoBusqueda.getText());
        //resultado esperado para la busqueda:  No results were found for your search "liquido matapulgas"




    }

    @Test
    public void act04() {
        WebElement buscar=  driver.findElement(By.xpath("//*[@id=\"search_query_top\"]"));
        buscar.sendKeys("blo");
        WebDriverWait wait =  new WebDriverWait(driver,15); // tiempo de espera explicito
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"index\"]/div[2]/ul/li")));

        buscar.sendKeys(Keys.ARROW_DOWN);
        buscar.sendKeys(Keys.ENTER);


        String resultado= "Model demo_2";
        WebElement resultadoBusqueda= driver.findElement(By.xpath("//*[@id=\"product_reference\"]"));
        Assert.assertEquals(resultadoBusqueda.getText(), resultado);
/*

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // espera implicito para toda la pagina

  */// explicito
        //WebDriverWait  ewait = new WebDriverWait(driver,10);
        // ewait.until(ExpectedConditions.elementToBeSelected(By.xpath("//*[@id=\"index\"]/div[2]/ul/li")));



    }

    @Test
    public void act05() {

        WebElement buscar=  driver.findElement(By.xpath("//*[@id=\"search_query_top\"]"));
        buscar.sendKeys("blo");
        WebDriverWait wait =  new WebDriverWait(driver,15); // tiempo de espera explicito
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"index\"]/div[2]/ul/li")));

        buscar.sendKeys(Keys.ARROW_DOWN);
        buscar.sendKeys(Keys.ENTER);
//seleccionar talla L
        Select s = new Select( (driver.findElement(By.xpath("//*[@id=\"group_1\"]"))));
        s.selectByVisibleText("L");
        driver.findElement(By.xpath("//*[@id=\"color_8\"]")).click();
        //seleccionamos color blanco
        driver.findElement(By.xpath("//*[@id=\"add_to_cart\"]/button/span")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[1]/h2")));


        String resultado= "Product successfully added to your shopping cart";
        WebElement resultadoBusqueda= driver.findElement(By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[1]/h2"));
        Assert.assertEquals(resultadoBusqueda.getText(), resultado);



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