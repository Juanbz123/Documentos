package selenium.jbrizuela;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.assertEquals;


public class EjerciciosAutomatizacion2 {


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
        driver.get("http://automation.frankluzon.com/");

    }


    @Test
    public void ejercicioAgregarReview(){


        WebElement buscador= driver.findElement(By.xpath("//*[@id=\"woocommerce-product-search-field-0\"]"));
        buscador.click();
        buscador.sendKeys("CAP");
        buscador.sendKeys(Keys.ENTER);

        driver.findElement(By.xpath("//*[@id=\"tab-title-reviews\"]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"commentform\"]/div/p/span/a[4]")).click();

       WebElement mensajeReviewUsuario = driver.findElement(By.xpath("//*[@id=\"comment\"]"));
       mensajeReviewUsuario.sendKeys("Muy buenaa!!!");


        driver.findElement(By.xpath("//*[@id=\"author\"]")).sendKeys("Juan");
        driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("juanbrizuelazarate@gmail.com");
        driver.findElement(By.xpath("//*[@id=\"submit\"]")).click();


        WebDriverWait espera = new WebDriverWait(driver, 10);
        espera.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("dfn, cite, em, i")));
        WebElement vericarComent = driver.findElement(By.cssSelector("dfn, cite, em, i"));

        assertEquals("Your review is awaiting approval", vericarComent.getText());
    }

    @After
    public void close(){
        if(driver != null){
            driver.close();
        }

    }

    @AfterClass
    public static void closeAll() {
        System.out.println("closeAll :: Cerrar otras conexiones que fueron utilizadas en el test");

    }

}

