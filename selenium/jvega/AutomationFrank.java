package selenium.jvega;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.Assert.assertEquals;


public class AutomationFrank {


    private WebDriver driver;
    private String resultado;


    @BeforeClass
    public static void Setup(){
        //Inicialización del WebDriver con Chrome
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void init(){
        driver = new ChromeDriver();
        driver.get("http://automation.frankluzon.com/");   //Cargar la página
        driver.manage().window().maximize();  // Maximizar
        resultado="";

    }

     @Test
    public void agregarReview() {

        // Escribimos la palabra a buscar y luego hacemos click en el elemento especificado
        driver.findElement(By.xpath("//*[@id='woocommerce-product-search-field-0']")).sendKeys("CAP");
        driver.findElement(By.xpath("//*[@id='woocommerce-product-search-field-0']")).sendKeys(Keys.ENTER);


        //hacemos click en review
        driver.findElement(By.xpath("//*[@id='tab-title-reviews']/a")).click();

        //hacemos click en la estrella nro2
        driver.findElement(By.xpath("//span/a[contains(text(),'2')]")).click();
        //driver.findElement(By.cssSelector("span a.star-2")).click();

        //escribimos un texto en el campo especificado
        driver.findElement(By.xpath("//*[@id='comment']")).sendKeys("Tarea para el lunes");
        //escribimos un nombre en el campo especificado
        driver.findElement(By.xpath("//*[@id='author']")).sendKeys("Juan");
        //escribimos el email en el campo especificado
        driver.findElement(By.xpath("//*[@id='email']")).sendKeys("juan.jv.199@gmail.com");

        //hacemos click en submit
        driver.findElement(By.xpath("//*[@id='submit']")).click();
        resultado= driver.findElement(By.cssSelector("em.woocommerce-review__awaiting-approval")).getText();
        assertEquals("Your review is awaiting approval",resultado);

    }


    @After
    public void close(){
        if(driver != null){
           driver.close();
        }

    }

}
