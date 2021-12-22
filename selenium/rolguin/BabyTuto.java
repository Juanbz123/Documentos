package selenium.rolguin;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class BabyTuto {

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

    }

    @Test
    public void act06_filtrarProductosPorMarca() {

        ////div[@id="newsletter"]/button
        driver.get("https://www.babytuto.com/");
        WebDriverWait d = new WebDriverWait(driver,10);
        d.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id=\"newsletter\"]/button")));
        driver.findElement(By.xpath("//div[@id=\"newsletter\"]/button")).click();

        int size = driver.findElements(By.cssSelector("div.bar-2-products ul li a")).size();
        System.out.println(size);
        for (int i=0; i<size;i++){
            String coche = driver.findElements(By.cssSelector("div.bar-2-products ul li a")).get(i).getText();
            if (coche.equals("COCHES")){
                driver.findElements(By.cssSelector("div.bar-2-products ul li a")).get(i).click();
                break;
            }
        }

        int ca = driver.findElements(By.cssSelector("ul.tree li a")).size();
        d.until(ExpectedConditions.visibilityOfElementLocated((By.cssSelector("ul.tree li a"))));
        for (int k=0; k<ca;k++){
            String cocheAccesorios = driver.findElements(By.cssSelector("ul.tree li a")).get(k).getText();
            if (cocheAccesorios.equals("Accesorios para coches")){
                driver.findElements(By.cssSelector("ul.tree li a")).get(k).click();
                break;
            }
        }


        d.until(ExpectedConditions.visibilityOfElementLocated((By.cssSelector("a.f span"))));
        int a = driver.findElements(By.cssSelector("a.f span")).size();
        for (int o=0; o<a;o++){
            String bbpro =  driver.findElements(By.cssSelector("a.f span")).get(o).getText();
            if (bbpro.equals("BBpro")){
                driver.findElements(By.cssSelector("a.f span")).get(o).click();
                break;
            }
        }

        d.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[@class=\"items lst-vertical\"]/div"))));
        //cuanto la cantidad de productos desplegados en la pantalla
        int contador = driver.findElements(By.xpath("//div[@class=\"items lst-vertical\"]/div")).size();
        //utilizo un contador para obtener la catidad productos bbpro desplegados
        int bbcantidad = 0;
        for (int o=0; o<contador;o++){
            String listabbpro =  driver.findElements(By.xpath("//div[@class=\"merchant-name\"]/a")).get(o).getText();
            System.out.println(listabbpro);
            if (listabbpro.equals("BBPRO")){
                bbcantidad++;
            }
        }
        //comparo la cantidad de productos desplegados con la cantidad de productos bbpro encontrados
        assertEquals(contador,bbcantidad);



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
