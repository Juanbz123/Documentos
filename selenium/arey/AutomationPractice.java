package selenium.arey;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AutomationPractice {

    private WebDriver driver;

        //Inicialización del WebDriver con Chrome
    @BeforeClass
    public static void setup()
    {
        System.out.println("Setup necesario antes de Instanciar");
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void init()
    {
        System.out.println("init para instanciar");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
      driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);      // MANERA IMPLICITA DE INTRODUCIR TIEMPO MAXIMO QUE LE DA AL TEST PARA CARGAR EL ATRIBUTO
        driver.get("http://automationpractice.com");
        while(driver.getTitle()=="508 Resource Limit Is Reached")
        {
            driver.navigate().refresh();
        }
    }

    @Test   //TODO TEST 05
    public void atc05CambiarProductoYAgregarAlCarrito() {
        String strBusqueda = "blo";
        driverElement("//*[@id=\"search_query_top\"]").sendKeys(strBusqueda);
        WebElement search = driverElement("//*[@id=\"index\"]/div[2]");
        Assert.assertTrue(search.isDisplayed() && search.isEnabled());
        Assert.assertEquals("Blouses > Blouse", search.getText());
        search.click();
        Select s = new Select(driverElement("//*[@id=\"group_1\"]"));
        s.selectByVisibleText("L");
        Assert.assertNotNull(s.getFirstSelectedOption());

        List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"color_to_pick_list\"]/li"));
        Collections.shuffle(elements);
        Random randomcolor = new Random();
        int cantcolores= elements.size();
        System.out.println("elementos "+elements);
        for (WebElement element : elements){
            if (element.getAttribute("class").equals("selected"))
            {
                elements.remove(element);
                cantcolores--;
                randomcolor.ints(cantcolores);
                elements.get(cantcolores-1).click();
//                Assert.assertTrue(element.getAttribute("class").equals("selected"));
                break;
            }

        }
        WebElement alertCompraInicio = driverElement("//*[@id=\"layer_cart\"]");
        alertCompraInicio.getCssValue("display");
        WebElement cartButton = driverElement("//*[@id=\"add_to_cart\"]/button");
        cartButton.click();
        Assert.assertNotEquals(alertCompraInicio,driverElement("//*[@id=\"layer_cart\"]").getCssValue("display"));
        if (isClicked(cartButton))
        {
            alertCompraInicio.getCssValue("display");
            Assert.assertNotEquals(alertCompraInicio,driverElement("//*[@id=\"layer_cart\"]").getCssValue("display"));
        }

    }

     /*@Test //TODO TEST 04
    public void atc04BusquedaProductoLista() {
        String strBusqueda = "blo";
        driverElement("//*[@id=\"search_query_top\"]").sendKeys(strBusqueda);
        WebDriverWait w = new WebDriverWait(driver,15); // SEGUNDA FORMA DE HACERLO - MANERA EXPLICITA (TIEMPO MAXIMO QUE LE DA AL TEST PARA TERMINAR O
        //                                                                                                            TERMINA ANTES SI SE CUMPLE LA CONDICION SOLICITADA)

//        WebElement result = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"index\"]/div[2]"))); // SEGUNDA/TERCERA FORMA DE HACERLO
        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver) // TERCERA FORMA DE HACERLO - MANERA FLUIDA (MARCA TIEMPO MAXIMO QUE LE DA AL WEBDRIVER PARA ESPERAR
                //     A QUE UNA CONDICION, O SEA UN WEBELEMENT, SE VUELVA VISIBLE. TAMBIEN CON CUANTA FRECUENCIA CHEQUEA
                //   SI LA CONDICION APARECE ANTES DE TIRAR ALGUN ERROR/EXCEPCION))
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(200))
                .ignoring(NoSuchElementException.class);
        w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"index\"]/div[2]")));

        WebElement search = driverElement("//*[@id=\"index\"]/div[2]");
        Assert.assertTrue(search.isDisplayed() && search.isEnabled());
        Assert.assertEquals("Blouses > Blouse", search.getText());
        search.click();
        Assert.assertEquals("Model demo_2", driverElement("//*[@id=\"product_reference\"]").getText());
    }*/

 /*@Test //TODO TEST 03
    public void atc03EmitirMensaje(){

        String strBusqueda = "liquido matapulgas";
        editBox().sendKeys(strBusqueda);
        Assert.assertEquals(strBusqueda,editBox().getText());
        Assert.assertFalse(editBox().getText().isEmpty());
        editBox().sendKeys(Keys.ENTER);
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"center_column\"]/p")).isDisplayed());
    }*/

     /*@Test //TODO TEST 02
    public void atc02SearchChiffonDress() throws InterruptedException {
        String strBuscado = "#search_query_top";
        WebElement objetobuscado = driver.findElement(By.cssSelector(strBuscado));
        Thread.sleep(2000);
        objetobuscado.sendKeys("printed chiffon dress");
        if (objetobuscado.getText() == "printed chiffon dress")
        {
            Thread.sleep(500);
            driver.findElement(By.cssSelector("#searchbox > button")).click();

        }else {
            System.out.println("No se busca el string solicitado");
        }
    }*/

   /*@Test //TODO TEST 01
    public void atc01SearchDress()
    {
        driver.findElement(By.xpath("//*[@id=\"search_query_top\"]")).sendKeys("dress");
        driver.findElement(By.xpath("//*[@id=\"searchbox\"]/button")).click();
        List<WebElement> products = driver.findElements(By.xpath("//*[@id=\"center_column\"]/ul/li"));

        Assert.assertTrue(products.size() > 2);
    }*/

    @After
    public void close() throws InterruptedException {
        if (driver != null){
            Thread.sleep(2000);
            driver.close();
        }
    }

    @AfterClass
    public static void closeAll()
    {
        System.out.println("closeAll :: Cerrar otras conexiones que fueron utilizados en el test");

    }

    public WebElement driverElement(String path)
    {
        WebElement driverelement = driver.findElement(By.xpath(path));
        return driverelement;
    }

    public WebElement editBox()
    {
        WebElement d =  driver.findElement(By.xpath("//*[@id=\"search_query_top\"]"));
        return d;
    }

    public boolean isClicked(WebElement element)
    {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            return true;
        } catch(Exception e){
            return false;
        }
    }

}
