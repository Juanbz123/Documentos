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

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ViajesFalabella {

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
        driver.get("https://www.viajesfalabella.cl/");

    }



    @Test
    public void atc03(){
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // espera implicito para toda la pagina

        driver.findElement(By.xpath("//*[contains(concat( \" \", @class, \" \" ), concat( \" \", \"button-circle-label\", \" \" ))]")).click();
        WebElement busqueda = driver.findElement(By.xpath("//*[@id=\"searchbox\"]/div/div/div/div[3]/div[2]/div[1]/div/div/div/div/div/input"));
        busqueda.sendKeys("pinchas");

        String b= "No se encontraron resultados que coincidan con  ";
        String selected = driver.findElement(By.cssSelector(".ac-group-hint-error")).getText();
        Assert.assertEquals("No se encontraron resultados que coincidan con  " + "pinchas",b+selected);


    }

    @Test
    public void atc01() {

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // espera implicito para toda la pagina

        driver.findElement(By.xpath("//*[contains(concat( \" \", @class, \" \" ), concat( \" \", \"button-circle-label\", \" \" ))]")).click();
        WebElement busqueda = driver.findElement(By.xpath("//*[@id=\"searchbox\"]/div/div/div/div[3]/div[2]/div[1]/div/div/div/div/div/input"));
        busqueda.sendKeys("miami");
        WebDriverWait wait =  new WebDriverWait(driver,15); // tiempo de espera explicito
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[7]/div/div/ul")));
        busqueda.sendKeys(Keys.ARROW_DOWN);
        busqueda.sendKeys(Keys.ENTER);

//primeras fechas

        //Fecha
        driver.findElement(By.xpath("//input[@placeholder='Entrada']")).click();
        asignarFechaDisponible(10);
        driver.findElement(By.xpath("//input[@placeholder='Salida']")).click();
        driver.findElement(By.xpath("//input[@placeholder='Salida']")).click();
        asignarFechaDisponible(12);
//boton buscar
        driver.findElement(By.xpath("//*[@id=\"searchbox\"]/div/div/div/div[3]/div[2]/div[4]/div/a/em")).click();


//filtros
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("/html/body/aloha-app-root/aloha-results/div/div/div/div[1]/aloha-filter-list/div/ul/li[5]/aloha-filter/aloha-checkbox-filter/ul/li[1]/span/span[1]/aloha-checkbox/span/label")).click();
        //4estrwellas
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("/html/body/aloha-app-root/aloha-results/div/div/div/div[1]/aloha-filter-list/div/ul/li[6]/aloha-filter/aloha-checkbox-filter/ul/li[3]/span/span[1]/aloha-checkbox/span/label")).click();

        String selected = driver.findElement(By.cssSelector("body > aloha-app-root > aloha-results > div > div > div > div.filters-column > aloha-filter-list > div > ul > li:nth-child(5) > aloha-filter > aloha-checkbox-filter > ul > li:nth-child(1) > span > span.filters-checkbox > aloha-checkbox > span > label > em > span")).getText();
        Assert.assertEquals("Todas las ofertas",selected);


    }


    @Test
    public void atc02() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // espera implicito para toda la pagina

        driver.findElement(By.xpath("//*[contains(concat( \" \", @class, \" \" ), concat( \" \", \"button-circle-label\", \" \" ))]")).click();
        WebElement busqueda = driver.findElement(By.xpath("//*[@id=\"searchbox\"]/div/div/div/div[3]/div[2]/div[1]/div/div/div/div/div/input"));
        busqueda.sendKeys("bue");
        WebDriverWait wait =  new WebDriverWait(driver,5); // tiempo de espera explicito
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[7]/div/div[1]/ul/li[1]/span")));
        busqueda.sendKeys(Keys.ENTER);

        //Fecha
        driver.findElement(By.xpath("//input[@placeholder='Entrada']")).click();
        asignarFechaDisponible(10);
        driver.findElement(By.xpath("//input[@placeholder='Salida']")).click();
        driver.findElement(By.xpath("//input[@placeholder='Salida']")).click();
        asignarFechaDisponible(12);

//adultos y ni;os
        driver.findElement(By.xpath("//*[@id=\"searchbox\"]/div/div/div/div[3]/div[2]/div[3]/div/div/div[2]/div/div")).click();
        driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[1]/div[2]/div[2]/div[2]/div/a[2]")).click();
        driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[1]/div[2]/div[2]/div[2]/div/a[2]")).click();
        driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[1]/div[2]/div[3]/div[1]/div[2]/div/div/select/option[9]")).click();
        driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[1]/div[2]/div[3]/div[2]/div[2]/div/div/select/option[14]")).click();



//buscar
        driver.findElement(By.xpath("//*[@id=\"searchbox\"]/div/div/div/div[3]/div[2]/div[4]/div/a/em")).click();

        driver.findElement(By.xpath("/html/body/aloha-app-root/aloha-results/div/div/div/div[1]/aloha-filter-list/div/ul/li[6]/aloha-filter/aloha-checkbox-filter/ul/li[3]/span/span[1]/aloha-checkbox/span/label/i")).click();
        //Filtro todas las ofertas
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("/html/body/aloha-app-root/aloha-results/div/div/div/div[1]/aloha-filter-list/div/ul/li[5]/aloha-filter/aloha-checkbox-filter/ul/li[1]/span/span[1]/aloha-checkbox/span/label")).click();
        //4estrwellas
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("/html/body/aloha-app-root/aloha-results/div/div/div/div[1]/aloha-filter-list/div/ul/li[6]/aloha-filter/aloha-checkbox-filter/ul/li[3]/span/span[1]/aloha-checkbox/span/label")).click();
        Thread.sleep(4000);
//ordenar mayor a menor
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector(" div[class='select-container'] > select[class='select-tag'] > option[value='total_price_ascending']")).click();
        Thread.sleep(4000);

        //elegir hotel
        driver.findElement(By.xpath("/html/body/aloha-app-root/aloha-results/div/div/div/div[2]/div[2]/aloha-list-view-container/div[2]/div[1]/aloha-cluster-container/div/div/div[2]/aloha-cluster-pricebox-container/div/div[2]/div[2]/aloha-button")).click();
        //reservar
        //driver.findElement(By.xpath("//*[@id=\"roompacks-container-wrapper\"]/aloha-roompacks-container/aloha-roompacks-grid-container/div[2]/div[2]/aloha-reservation-summary-container/div/aloha-next-step-button/aloha-button/button")).click();

    }

    public void asignarFechaDisponible(int dias){
        List<WebElement> fechas = driver.findElements(By.cssSelector("._dpmg2--show ._dpmg2--month-active ._dpmg2--available span._dpmg2--date-number"));
        fechas.get(dias).click();
    }

    @After
    public void close(){
        if(driver != null){
            driver.quit();
        }
    }

    @AfterClass
    public static void closeAll(){
        System.out.println("closeAll :: Cerrar otras conexiones que fueron utilizadas en el test");

    }

}





