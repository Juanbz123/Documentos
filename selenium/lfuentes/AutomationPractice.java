package selenium.lfuentes;

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

public class AutomationPractice {

    private WebDriver driver;

    @BeforeClass
    public static void Setup() {
        System.out.println("Setup necesario antes de Instanciar");
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void init() {
        System.out.println("init para instanciar");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://automationpractice.com/");
    }

    @Test
    public void atc01() {
        driver.findElement(By.xpath("//*[@id='search_query_top']")).sendKeys("dress");
        driver.findElement(By.xpath("//*[@id='searchbox']/button")).click();
        WebElement lista = driver.findElement(By.xpath("//*[@id='center_column']/h1/span[2]"));
        String[] texto = lista.getAttribute("innerHTML").trim().split(" ");
        int cantidadObjetos = Integer.parseInt(texto[0]);
        Assert.assertTrue(cantidadObjetos > 2);

    }

    @Test
    public void act02() {
        driver.findElement(By.cssSelector("#search_query_top")).sendKeys("Printed Chiffon Dress");
        driver.findElement(By.cssSelector("#searchbox > button")).click();
        WebElement objetoBuscado = driver.findElement(By.cssSelector("#center_column > ul > li.ajax_block_product.col-xs-12.col-sm-6.col-md-4.first-in-line.last-line.first-item-of-tablet-line.first-item-of-mobile-line.last-mobile-line > div > div.left-block > div > a.product_img_link > img"));
        String guardar = objetoBuscado.getAttribute("title");
        Assert.assertEquals("Printed Chiffon Dress", guardar);
    }

    @Test
    public void atc03() {
        driver.findElement(By.xpath("//*[@id='search_query_top']")).sendKeys("liquido matapulgas");
        driver.findElement(By.xpath("//*[@id='searchbox']/button")).sendKeys(Keys.ENTER);
        WebElement guardarresultado = driver.findElement(By.cssSelector("#center_column > p"));
        String resultado = guardarresultado.getAttribute("innerHTML");
    }

    @Test
    public void atc04() {
        WebElement textSearch = driver.findElement(By.xpath("//*[@id='search_query_top']"));
        textSearch.sendKeys("blo");
        // Metodo explicito para esperar que aparezca un elemento en especifico

        // este metodo permite  esperar 30s hasta verificar que aparecio correctamente

        WebDriverWait blo = new WebDriverWait(driver, 30);
        blo.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#index > div.ac_results")));
        driver.findElement(By.cssSelector("#index > div.ac_results")).click();

        WebElement blouseDemo = driver.findElement(By.cssSelector("#center_column > div > div > div.pb-center-column.col-xs-12.col-sm-4"));
        Assert.assertNotNull(blouseDemo);
        System.out.println(blouseDemo.getText());
    }

    @Test
    public void atc05() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.findElement(By.xpath("//*[@id='search_query_top']")).sendKeys("blouse");
        driver.findElement(By.xpath("//*[@id='searchbox']/button")).click();

        WebElement objetoBuscado = driver.findElement(By.xpath("//*[@id=\'center_column\']/ul/li/div/div[2]/h5/a"));
        if (objetoBuscado.getText().trim().contains("Blouse")) {
            objetoBuscado.click();

            Select objetoSelect = new Select(driver.findElement(By.xpath("//*[@id='group_1']")));
            objetoSelect.selectByVisibleText("L");
        }
        driver.findElement(By.xpath("//*[@id='color_8']")).click();
        driver.findElement(By.xpath("//*[@id='add_to_cart']/button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='layer_cart']/div[1]/div[1]/h2")));
        String productoSeleccionado = driver.findElement(By.xpath("//*[@id='layer_cart']/div[1]/div[1]/h2")).getText();
        Assert.assertEquals("Product successfully added to your shopping cart", productoSeleccionado);
    }


    @After
    public void close() {
        if (driver != null) {
            driver.close();
        }

    }

    @AfterClass
    public static void closeAll() {
        System.out.println("closeAll :: Cerrar otras conexiones que fueron utilizadas en el test");

    }

}