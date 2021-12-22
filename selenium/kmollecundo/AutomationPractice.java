package selenium.kmollecundo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class AutomationPractice {
    private WebDriver driver;
    @BeforeClass
    public static void Setup(){
        //Initialization del web driver con chrome
        WebDriverManager.chromedriver().setup();
    }
    @Before
    public void Init(){
        //Instanciamos init
        driver = new ChromeDriver();

        //Metodo para iniciar siempre con la pantalla maximizada
        driver.manage().window().maximize();

        //Cargar la página
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get("http://automationpractice.com/");

        //Ciclo para poder refrescar la pagina en caso de tirar error de requests
        while (Objects.equals(driver.getTitle(), " 508 Resource Limit Is Reached")) {
            driver.navigate().refresh();
        }
    }
    @Test
    public void atc01_BusquedaPalabrasClaves(){
        //Busco la palabra indicada y le doy click
        driver.findElement(By.xpath("//*[@id='search_query_top']")).sendKeys("Dress");
        driver.findElement(By.xpath("//*[@id='searchbox']/button")).click();

        // Metodo para obtener la cantidad de elementos de la busqueda
        List<WebElement> dressList = driver.findElements(By.xpath("//*[@id='center_column']/ul/li"));
        Assert.assertTrue(dressList.size()>2);
        System.out.println("Se Cumple con la condicion de busqueda de mayor a dos elementos por lista");
    }
    @Test
    public void atc02_BusquedaDirectaProductoExistente(){
        //Busco la palabra indicada y le doy click
        driver.findElement(By.cssSelector("#search_query_top")).sendKeys("printed chiffon dress");
        driver.findElement(By.cssSelector("#searchbox > button")).click();

        //Metodo para verificar el primero de la lista sea la busqueda especifica realizada
        WebElement primerDress = driver.findElement(By.cssSelector("#center_column > ul > li.ajax_block_product.col-xs-12.col-sm-6.col-md-4.first-in-line.last-line.first-item-of-tablet-line.first-item-of-mobile-line.last-mobile-line > div > div.right-block > h5 > a"));
        Assert.assertEquals("Printed Chiffon Dress",primerDress.getText());
        System.out.println(primerDress.getText());
    }
    @Test
    public void atc03_BusquedaProductoNoEncontrado(){
        //Busco la palabra indicada y le doy enter
        WebElement textEnter = driver.findElement(By.xpath("//*[@id='search_query_top']"));
        textEnter.sendKeys("liquido mata pulgas");
        textEnter.sendKeys(Keys.ENTER);

        //Compruebo que la busqueda erronea sea la indicada
        WebElement text = driver.findElement(By.cssSelector("#center_column > p"));
        Assert.assertEquals("No results were found for your search \"liquido mata pulgas\"", text.getText());
        System.out.println(text.getText());
    }
    @Test
    public void atc04_BusquedaProductoListaDinamica(){
        //Busco la palabra y espero elemento emergente
        WebElement textSearch = driver.findElement(By.xpath("//*[@id='search_query_top']"));
        textSearch.sendKeys("blo");

        // Metodo explicito para esperar que aparezca un elemento en especifico
        // este metodo nos permite  esperar 30s hasta verificar que aparecio correctamente
        WebDriverWait blo = new WebDriverWait(driver,30);
        blo.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#index > div.ac_results")));
        driver.findElement(By.cssSelector("#index > div.ac_results")).click();

        //Compruebo que el elemento buscado sea igual al esperado
        WebElement blouseDemo = driver.findElement(By.cssSelector("#product_reference"));
        Assert.assertEquals("Model demo_2",blouseDemo.getText());
        System.out.println(blouseDemo.getText());
    }
    @Test
    public void atc05_AgregarProductoCambiandoTallaYColor(){
        //Busco la palabra indicada y le doy click
        driver.findElement(By.xpath("//*[@id='search_query_top']")).sendKeys("blouse");
        driver.findElement(By.xpath("//*[@id='searchbox']/button")).click();

        // le indico que seleccione el elemento solicitado
        WebElement element = driver.findElement(By.cssSelector("#center_column > ul > li > div > div.right-block > h5 > a"));
        Assert.assertEquals("Blouse",element.getText());
        element.click();

        //Selecciono Talla L
        WebElement selectElement = driver.findElement(By.xpath("//*[@id='group_1']"));
        Select sel = new Select(selectElement);
        sel.selectByVisibleText("L");

        //Cambio Color del elemento
        driver.findElement(By.cssSelector("#color_8")).click();

        //Agrego al carrito
        driver.findElement(By.cssSelector("#add_to_cart > button")).click();

        //Verifico que fue agregado correctamente al carrito
        WebDriverWait wait = new WebDriverWait(driver, 4);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#layer_cart .layer_cart_product h2")));
        WebElement checkCart = driver.findElement(By.cssSelector("#layer_cart .layer_cart_product h2"));
        Assert.assertEquals("Product successfully added to your shopping cart", checkCart.getText());
        System.out.println(checkCart.getText());
    }
    @After
    public void Close(){
        if (driver != null) {
            //
            driver.close();
        }
    }
}
