package selenium.jspizarro;

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

import static org.junit.Assert.assertTrue;

public class AutomationPractice {
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
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @Test
    public void atc01_BusquedaPalabrasClaves(){
        driver.findElement(By.xpath("//*[@id='search_query_top']")).sendKeys("dress");
        driver.findElement(By.xpath("//*[@id='searchbox']/button")).click();

        WebElement resultados = driver.findElement(By.xpath("//*[@id='center_column']/h1/span[2]"));
        Assert.assertTrue(resultados.getText().equals("7 results have been found."));
    }
    @Test
    public void atc02_busquedaDirectaProductoExistente(){
        driver.findElement(By.cssSelector("#search_query_top")).sendKeys("printed chiffon dress");
        driver.findElement(By.cssSelector("#searchbox > button")).click();

        WebElement primerElemento = driver.findElement(By.cssSelector("a.product-name"));
        Assert.assertTrue(primerElemento.getText().equals("Printed Chiffon Dress"));
    }
    @Test
    public void atc03_tiendaEmiteMensajeDeProductoNoEncontrado(){
        WebElement cajaBusqueda = driver.findElement(By.xpath("//*[@id='search_query_top']"));
        cajaBusqueda.sendKeys("liquido matapulgas");
        cajaBusqueda.sendKeys(Keys.ENTER);

        WebElement mensajeError = driver.findElement(By.xpath("//*[@id='center_column']/p"));
        Assert.assertTrue(mensajeError.getText().equals("No results were found for your search \"liquido matapulgas\""));
    }
    @Test
    public void atc04_encontrarProductoDeListaDinamica(){
        WebDriverWait wait = new WebDriverWait(driver,15);
        WebElement cajaBusqueda = driver.findElement(By.xpath("//*[@id='search_query_top']"));
        cajaBusqueda.sendKeys("blo");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#index .ac_results")));

        cajaBusqueda.sendKeys(Keys.ARROW_DOWN);
        cajaBusqueda.sendKeys(Keys.ENTER);

        String resultadoEsperado = "Model demo_2";
        WebElement resultadoBusqueda = driver.findElement(By.id("product_reference"));
        Assert.assertTrue(resultadoBusqueda.getText().equals(resultadoEsperado));
    }
    @Test
    public void atc05_agregarProductoCambiandoTallaYColor(){
        WebDriverWait wait = new WebDriverWait(driver,15);
        WebElement cajaBusqueda = driver.findElement(By.xpath("//*[@id='search_query_top']"));
        cajaBusqueda.sendKeys("blo");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#index .ac_results")));

        cajaBusqueda.sendKeys(Keys.ARROW_DOWN);
        cajaBusqueda.sendKeys(Keys.ENTER);

        Select s = new Select(driver.findElement(By.id("group_1")));
        s.selectByVisibleText("L");

        int count = driver.findElements(By.cssSelector("#color_to_pick_list .color_pick")).size();

        for (int i = 0; i < count; i++) {
           boolean colorSelect = driver.findElements(By.cssSelector("#color_to_pick_list .color_pick")).get(i).isSelected();
            if(colorSelect == false){
                driver.findElements(By.cssSelector("#color_to_pick_list .color_pick")).get(i).click();
                break;
            }
        }

        driver.findElement(By.cssSelector("#add_to_cart .exclusive")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#layer_cart")));
        WebElement checkAddProduct = driver.findElement(By.cssSelector("#layer_cart .layer_cart_product h2"));
        WebElement productName = driver.findElement(By.cssSelector("#layer_cart_product_title"));
        WebElement productAttributes = driver.findElement(By.cssSelector("#layer_cart_product_attributes"));

        String checkTextAddProduct = "Product successfully added to your shopping cart";
        String checkTextProductName = "Blouse";
        String checkTextProductAttributes = "White, L";

        Assert.assertEquals(checkTextAddProduct,checkAddProduct.getText());
        Assert.assertEquals(checkTextProductName,productName.getText());
        Assert.assertEquals(checkTextProductAttributes,productAttributes.getText());
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
