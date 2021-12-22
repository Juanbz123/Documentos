package selenium.clopez;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static java.lang.Object.*;
import static org.junit.Assert.assertTrue;

public class automationpractice {

    private WebDriver driver;

    private static void limit_Is_Reached(WebDriver driver){
        while (Objects.equals(driver.getTitle(),"Resource Limit Is Reached")){
            driver.navigate().refresh();
        }
    }

    @BeforeClass
    public static void Setup(){
        System.out.println("Setup necesario antes de Instanciar");
        WebDriverManager.firefoxdriver().setup();
    }

    @Before
    public void init() throws InterruptedException {
        System.out.println("init para instanciar");
        driver = new FirefoxDriver();
        // driver.manage().deleteAllCookies(); //borrar cookies
        driver.manage().window().maximize();
        driver.get("http://automationpractice.com//");
        // Esperamos
        Thread.sleep(2000);
        limit_Is_Reached(driver);
    }

    //@Ignore
    @Test
    public void atc01_BusquedaPalabrasClaves() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\'search_query_top\']")).sendKeys("dress");
        driver.findElement(By.xpath("/html/body/div/div[1]/header/div[3]/div/div/div[2]/form/button")).click();
        // Esperamos /html/body/div/div[1]/header/div[3]/div/div/div[2]/form/button
        Thread.sleep(2000);
        // se crea una lista de elementos web
        // List<WebElement> dress = driver.findElements(By.xpath("//*[@id=\"center_column\"]/ul/li"));
        // carga bien?
        limit_Is_Reached(driver);
        List<WebElement> dress = driver.findElements(By.xpath("/html/body/div/div[2]/div/div[3]/div[2]/ul/li"));
        // Tamaño obtenido = 7 en ambas listas anteriores
        // System.out.println("Tamaño = "+dress.size());
        Assert.assertTrue(dress.size() > 2);
    }

    //@Ignore
    @Test
    public void atc02_busquedaDirectaProductoExistente() throws InterruptedException {
        driver.findElement(By.cssSelector("#search_query_top")).sendKeys("printed chiffon dress");
        driver.findElement(By.cssSelector("button.btn:nth-child(5)")).click();
        // Espera
        Thread.sleep(2000);
        // carga bien?
        limit_Is_Reached(driver);
        // Tomamos el nombre del primer elemento
        Assert.assertEquals( "Printed Chiffon Dress", driver.findElement(By.cssSelector("a.product-name")).getText());
    }

    // @Ignore
    @Test
    public void atc03_tiendaEmiteMensajeProductoNoEncontrado() throws InterruptedException {
        // 2.- Introdrucir "liquido matapulgas"
        // 3.- Hacer la búsqueda introduciendo Enter en el campo de búsqueda
        driver.findElement(By.cssSelector("#search_query_top")).sendKeys("liquido matapulgas"+ Keys.ENTER);
        // 4.- Esperar a que cargue la página de Resultados.
        Thread.sleep(2000);
        limit_Is_Reached(driver);
        // Tomamos la frase encontrada y la pasamos a un String
        String frase = driver.findElement(By.cssSelector("p.alert.alert-warning")).getText();
        // System.out.println("frase: "+frase);
        // Reemplazamos comillas dobles por simples de la frase para luego poder comparar
        frase = frase.replaceAll("\"", "\'");
        // 5.- Si muestra el mensaje el test es positivo
        Assert.assertEquals( "No results were found for your search 'liquido matapulgas'", frase);
    }

    // @Ignore
    @Test
    public void atc04_encontrarProductoListaDinamica() throws InterruptedException {
        WebElement busqueda = driver.findElement(By.cssSelector("#search_query_top"));
        // 2.- Introdrucir "blo" en el campo de busqueda
        busqueda.sendKeys("blo");
        // 3.- Esperar a que liste los resultados
        WebDriverWait blo = new WebDriverWait(driver,30);
        blo.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#index > div.ac_results")));
        // 4.- Seleccionar la opción "Blouses > Blouse"
        driver.findElement(By.cssSelector("#index > div.ac_results")).click();
        limit_Is_Reached(driver);
        // 5.- Debería haber cargado el detalle del producto "Blouse Model demo_2"
        String h1 = driver.findElement(By.cssSelector(".pb-center-column > h1")).getText();
        String frase = driver.findElement(By.id("product_reference")).getText();
        // Blouse + " " + Model demo_2
        frase = h1 + " " + frase;
        Assert.assertEquals( "Blouse Model demo_2", frase);
    }

    @Test
    public void atc05_AgregarProductoCambiandoTallaYColor() throws InterruptedException {
        WebElement busqueda = driver.findElement(By.cssSelector("#search_query_top"));
        // 2.- Introdrucir "blo" en el campo de busqueda
        busqueda.sendKeys("blo");
        // 3.- Esperar a que liste los resultados
        WebDriverWait blo = new WebDriverWait(driver,10);
        blo.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#index > div.ac_results")));
        // 4.- Seleccionar la opción "Blouses > Blouse"
        driver.findElement(By.cssSelector("#index > div.ac_results")).click();
        limit_Is_Reached(driver);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        String h1 = driver.findElement(By.cssSelector(".pb-center-column > h1")).getText();
        String frase = driver.findElement(By.id("product_reference")).getText();
        // Blouse + " " + Model demo_2
        frase = h1 + " " + frase;
        System.out.println("Valores encontrados: "+frase);
        Select s = new Select(driver.findElement(By.id("group_1")));
        s.selectByVisibleText("L");
        System.out.println("Select L");
        driver.findElement(By.id("color_8")).click();
        System.out.println("Color");
        driver.findElement(By.cssSelector("button.exclusive")).click();
        System.out.println("Comprar");
        // WebDriverWait wait = new WebDriverWait(driver,10);
        blo.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#layer_cart > div:nth-child(1)")));
        System.out.println("Comparar");
        String h2 = driver.findElement(By.cssSelector(".layer_cart_product > h2")).getText();
        String num = driver.findElement(By.cssSelector("#layer_cart_product_quantity")).getText();
        System.out.println("CANT: "+num);
        String attr = driver.findElement(By.cssSelector("#layer_cart_product_attributes")).getText();
        System.out.println("Valores encontrados: "+frase+" "+num+" "+attr);
        Assert.assertEquals( "Blouse Model demo_2", frase);
        Assert.assertEquals( "1", num);
        Assert.assertEquals( "White, L", attr);
        Assert.assertEquals( "Product successfully added to your shopping cart", h2);
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
