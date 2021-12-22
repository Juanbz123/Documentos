package selenium.mrivera;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

import static org.junit.Assert.*;

public class AutomationFrankLuzon {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public static void Setup(){
        WebDriverManager.chromedriver().setup();
    }
    @Before
    public void init(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 15);

        //Navegar a página
        driver.get("https://automation.frankluzon.com/");
    }

    @After
    public void close(){
        if(driver != null){
            driver.close();
        }
    }

    @Test
    public void atc01_AgregarReview()  {

        Random rand = new Random();
        int numeroAleatorio = rand.nextInt(1000);

        String comentario = "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto " + numeroAleatorio;
        String nombre = "John Doe";
        String email = "johndoe@example.com";

        //Buscador
        WebElement buscador = driver.findElement(By.cssSelector("input.search-field"));
        buscador.sendKeys("CAP");
        buscador.sendKeys(Keys.ENTER);

        //Espera que la pagina se cargue
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[contains(text(),'Cap')]")));

        //Abre la pestaña de reviews
        driver.findElement(By.cssSelector("li.reviews_tab > a")).click();

        //Escribe la review
        driver.findElement(By.cssSelector("a.star-1")).click();
        driver.findElement(By.cssSelector("textarea#comment")).sendKeys(comentario);
        driver.findElement(By.cssSelector("input#author")).sendKeys(nombre);
        driver.findElement(By.cssSelector("input#email")).sendKeys(email);
        
        //Envía la review
        driver.findElement(By.cssSelector("input#submit")).click();

        //Comprobación
        String resultado = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.comment-text > p"))).getText();
        String resultadoEsperado = "Your review is awaiting approval";
        assertEquals(resultadoEsperado, resultado);
    }

}
