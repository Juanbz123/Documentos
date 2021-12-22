package selenium.acabrera;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import static org.junit.Assert.assertTrue;

public class BabyTuto {

    private WebDriver driver;
    private WebDriverWait espera;

    @BeforeClass
    public static void Setup(){

        //Inicialización del WebDriver con Chrome
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void init() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();//Maximiza la pagina
        String pagina = "https://www.babytuto.com/";
        driver.get(pagina);//Carga la pagina
        espera = new WebDriverWait(driver,15);
    }

    @Test
    public void atc06filtrarProductos() throws InterruptedException {

        //se pregunta si aparece el popup, si es asi se cierra
        if (driver.findElement(By.cssSelector("#newsletter > div")).isDisplayed()){
            driver.findElement(By.cssSelector("#newsletter > button")).click();
        }

        //se crea una lista y se recorre los elementos con tagname li
        List<WebElement> options_list = driver.findElements(By.tagName("li"));

        for (WebElement l: options_list) {

            //si la lista contiene un elemento con el texto "COCHES" que realice un clic y salga del ciclo
            if (l.getText().contains("COCHES"))
            {
                l.click();
                break;
            }
        }

        //se realiza una espera para que cargue los elementos correspondientes a la seccion coches
        espera.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Accesorios para coches')]")));

        //Como la primera opcion es "Accesorios para coches" se realiza el clic
        driver.findElement(By.xpath("//a[contains(text(),'Accesorios para coches')]")).click();

        //Se realiza una busqueda en el span que contenga texto "BBpro" y se da clic
        driver.findElement(By.xpath("//span[contains(text(),'BBpro')]")).click();

        //Se crea una lista de los detalles de la marca
        List<WebElement> listaProductos = driver.findElements(By.cssSelector("div.merchant-name"));

        //Se crea una variable donde se asigna el tamaño de la lista de productos
        int totalProductos = listaProductos.size();

        //Se crea una variable inicializada en 0
        int contador = 0;

        //Se recorre la lista y se pregunta cuales son iguales a "BBpro"
        for (WebElement lp : listaProductos ){
            if (lp.getText().equalsIgnoreCase("BBpro"));
            //a la variable contador se suman todas las coincidencias con "BBpro"
            contador +=1;
        }


        assertTrue(totalProductos==contador);

    }

    @After
    public void cerrar() {

        if (driver != null) {
            driver.close();
        }
    }
}