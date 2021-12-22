package selenium.mrivera;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;

public class BabyTuto {

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
        driver.get("https://www.babytuto.com/");
    }

    @After
    public void close(){
        if(driver != null){
            driver.close();
        }

    }

    @Test
    public void FiltrarProductosPorMarca() throws InterruptedException{

        //Cierra popup inicial
        cerrarPopup();

        //Busca y clickea la seccion COCHES
        for(WebElement item: driver.findElements(By.cssSelector(".bar-2-products ul li a"))){
            if(item.getText().contains("COCHES")){
                item.click();
                break;
            }
        }

        //Busca y clickea el link de Accesorios para coches
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.sub-cats.loaded")));
        for(WebElement item: driver.findElements(By.cssSelector("ul.tree > li > a"))){
            if(item.getText().contains("Accesorios para coches")){
                item.click();
                break;
            }
        }

        //Espera que se cargue la página
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[contains(text(),'Accesorios para coches')]")));

        //En caso del que popup vuelva a aparecer
        cerrarPopup();

        //Filtra por marca BBpro
        for(WebElement item: driver.findElements(By.cssSelector("a.f > span"))){
            if(item.getText().contains("BBpro")){
                item.click();
                break;
            }
        }

        //Verifica los resultados, si no existe un producto de otra marca
        boolean contieneSoloLaMarca = true;
        for(WebElement item: driver.findElements(By.cssSelector(".merchant-name > a"))){
           if(!item.getText().contains("BBPRO")){
               contieneSoloLaMarca = false;
           }
        }

        //Comprobacion
        assertTrue(contieneSoloLaMarca);
    }

    public void cerrarPopup(){
        WebElement button;
        //Si existe el popup
        if(!driver.findElements(By.cssSelector("div.modal.fade.in")).isEmpty()){
            button = driver.findElement(By.cssSelector("#newsletter > button"));
            button.click();
        }
    }

}

