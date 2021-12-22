package selenium.fluzon;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Autos {

    private WebDriver driver;

    @BeforeClass
    public static void Setup(){
        System.out.println("Setup necesario antes de Instanciar");
        WebDriverManager.chromedriver().setup();
        //WebDriverManager.edgedriver().setup();
        //WebDriverManager.firefoxdriver().setup();


    }
    @Before
    public void init(){
        System.out.println("init para instanciar");
        driver = new ChromeDriver();
        //driver = new EdgeDriver();
        //driver = new FirefoxDriver();
        driver.manage().deleteAllCookies(); //borrar cookies

        driver.manage().window().maximize();
        driver.get("https://www.viajesfalabella.cl/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }


    void waitForLoad(WebDriver driver) {
        new WebDriverWait(driver, 30).until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    @Test
    public void CdP11_busquedaAutos() throws InterruptedException {

        List<WebElement> categorias = driver.findElements(By.cssSelector("div.header-products-container ul li a"));
        busqueda(categorias,"Autos");

        seleccionarEntrega("Cali","Aragon, Cali, Colombia");

        seleccionarRetiro(1,"08:00");

        seleccionarDevolucion(6,"08:00");

        driver.findElement(By.cssSelector("div.sbox-button-container a")).submit();

    }

    @Test
    public void CdP12_busquedaAutos() {
        // 1.- CARGAR HOME
        // ESPERA
        WebDriverWait espera = new WebDriverWait(driver,10);
        // 3.- SELECCIONAR CATEGORIA COCHES
        // CREAMOS LISTA
        List<WebElement> categorias = driver.findElements(By.cssSelector("div.header-products-container ul li a"));
        // CICLO FOR PARA SELECCIONAR LA CATEGORIA COCHES
        busqueda(categorias,"Autos");
        // CLICK EN EL BOTON BUSCAR
        driver.findElement(By.cssSelector("div.sbox-button")).click();
        espera.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), 'Ingresa un lugar de arriendo.')]")));
        // APARECEN LAS ALERTAS Y AHORA HAY QUE GUARDARLAS
        // System.out.println("APARECEN LAS ALERTAS Y AHORA HAY QUE GUARDARLAS");
        String entrega = driver.findElement(By.cssSelector("div.sbox-place-container span.validation-msg")).getText();
        String retiro = driver.findElement(By.cssSelector("div.sbox-moment-container span.validation-msg")).getText(); //#searchbox > div > div > div > div.sbox-mobile-body > div.sbox-row.-wrap.-row-bottom > div.sbox-moments.sbox-row.-wrap > div.sbox-moment-container.-ml3-l.-mb5-m.-mb5-s > div > div > div.sbox-moment-input.sbox-bind-disable-start-date > div > div > span ->muesta HORA
        String devolucion = driver.findElement(By.cssSelector("div.sbox-moment-container.sbox-second-moment-container span.validation-msg")).getText();
        Assert.assertEquals("Ingresa un lugar de arriendo.",entrega.toString());
        Assert.assertEquals("Ingresa una fecha de inicio.",retiro.toString());
        Assert.assertEquals("Ingresa una fecha de finalización.",devolucion.toString());
    }


    private void busqueda(List<WebElement> lista,String palabra){
        for (WebElement l: lista){
            // RECORREMOS LA LISTA HASTA ENCONTRAR LA PALABRA REQUERIDA
            if (l.getText().contains(palabra)){
                // HACEMOS CLICK EN LA CATEGORIA SELECCIONADA
                l.click();
                break;
            }
        }
    }


    private void seleccionarEntrega (String palabra, String comparar){
        WebDriverWait d = new WebDriverWait(driver,10);
        driver.findElement(By.cssSelector("div.sbox-place-container input")).sendKeys(palabra);
        d.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ac-container span")));
        int q =driver.findElements(By.cssSelector("div.ac-container span")).size();
        for (int i=0; i<q;i++){
            String origen = driver.findElements(By.cssSelector("div.ac-container span")).get(i).getText();
            if (origen.equals(comparar)){
                driver.findElements(By.cssSelector("div.ac-container span")).get(i).click();
                break;
            }
        }
    }

    private void seleccionarRetiro(int dia1,String hora) {

        WebDriverWait espera = new WebDriverWait(driver,10);

        driver.findElement(By.cssSelector("div.sbox-moment-input-container input")).click();
        int k = driver.findElements(By.cssSelector("div._dpmg2--month-active span")).size();
        Calendar c1 = Calendar.getInstance();
        String dia = Integer.toString(c1.get(Calendar.DATE));
        int o = Integer.parseInt(dia);
        o = o + dia1;
        String fech1 = Integer.toString(o);

        for (int i = 0; i < k; i++) {
            String fecha1 = driver.findElements(By.cssSelector("div._dpmg2--month-active span span._dpmg2--date-number")).get(i).getText();
            if (fecha1.equals(fech1)) {
                driver.findElements(By.cssSelector("div._dpmg2--month-active span span._dpmg2--date-number")).get(i).click();
                break;
            }
        }

        driver.findElement(By.cssSelector("div.sbox-moment-input-container select")).click();
        List <WebElement> options = driver.findElements(By.cssSelector("div.sbox-moment-input.sbox-bind-disable-timein div.select-container select option"));
        espera.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.sbox-moment-input-container select.select-tag.sbox-timein option")));
        for (WebElement l : options) {
            if (l.getText().contains(hora)) {
                l.click();
                break;
            }

        }

    }

    private void seleccionarDevolucion(int dia2,String hora) {

        WebDriverWait espera = new WebDriverWait(driver,10);

        driver.findElement(By.cssSelector("div.sbox-moment-container.sbox-second-moment-container input")).click();
        int k = driver.findElements(By.cssSelector("div._dpmg2--month-active span")).size();
        Calendar c1 = Calendar.getInstance();
        String dia = Integer.toString(c1.get(Calendar.DATE));
        int o = Integer.parseInt(dia);
        o = o + dia2;
        String fech1 = Integer.toString(o);

        for (int i = 0; i < k; i++) {
            String fecha1 = driver.findElements(By.cssSelector("div._dpmg2--month-active span span._dpmg2--date-number")).get(i).getText();
            if (fecha1.equals(fech1)) {
                driver.findElements(By.cssSelector("div._dpmg2--month-active span span._dpmg2--date-number")).get(i).click();
                break;
            }
        }

        driver.findElement(By.cssSelector("div.sbox-moment-input.sbox-timeout-container select")).click();
        List <WebElement> optionsTO = driver.findElements(By.cssSelector("div.sbox-moment-input.sbox-timeout-container div.select-container select option"));
        System.out.println(optionsTO);
        espera.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.sbox-moment-input.sbox-timeout-container div.select-container select option")));
        for (WebElement l : optionsTO) {
            if (l.getText().contains(hora)) {
                l.click();
                break;
            }

        }

    }

  /*  @After
    public void close(){
        if(driver != null){
            driver.close();
        }

    }*/

    @AfterClass
    public static void closeAll(){
        System.out.println("closeAll :: Cerrar otras conexiones que fueron utilizadas en el test");

    }
}
