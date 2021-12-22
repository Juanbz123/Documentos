package selenium.fluzon;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ManejoDeWaits {
    // Espera implicita, Explicita, FluentWait

    //atributos
    static WebDriver driver;

    @BeforeClass
    public static void init(){
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies(); //borrar cookies
        driver.manage().window().maximize();

        //espera implicita -> se define 1 sola vez y sirve para toda la ejecucion
        // findelement -> findelements
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //no se encuentra el elemento Exception: No
    }

    @Test
    public void explicitWait(){
        //espera explicita -> WebDriverWait
        WebDriverWait exwait = new WebDriverWait(driver,10);
        By localizadorAEsperar = By.xpath("//h4[contains(text(),'Hello World!')]");

        //flujo funcional
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");
        WebElement btnStart = driver.findElement(By.xpath("//button[contains(text(),'Start')]"));
        //click
        btnStart.click();
        // espera explicita -> Condicion Esperada
        exwait.until(ExpectedConditions.elementToBeClickable(localizadorAEsperar)); // se encuentra?? -> 500ms
        //capturar el WebElement
        WebElement hello = driver.findElement(localizadorAEsperar);
        Assert.assertEquals("Hello World!",hello.getText());
    }

    @Test
    public void fluentWait(){
        //fluent Wait : mayor control de configuracion, se usa harto para llamadas asincronas
        FluentWait<WebDriver> fluwait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10)) //Set timeout
                .pollingEvery(Duration.ofMillis(100)) //Set query/check/control interval
                .withMessage("Error de Timeout!") //Set timeout message
                .ignoring(NoSuchElementException.class); //Ignore NoSuchElementException

        By localizadorAEsperar = By.xpath("//h4[contains(text(),'Hello World!')]");
        //flujo funcional
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");
        WebElement btnStart = driver.findElement(By.xpath("//button[contains(text(),'Start')]"));
        //click
        btnStart.click();
        // espera explicita -> Condicion Esperada
        fluwait.until(ExpectedConditions.elementToBeClickable(localizadorAEsperar)); // se encuentra?? -> 100ms
        //capturar el WebElement
        WebElement hello = driver.findElement(localizadorAEsperar);
        Assert.assertEquals("Hello World!",hello.getText());
    }


    @After
    public void close(){
        if(driver != null){
            driver.close();
        }
    }

}
