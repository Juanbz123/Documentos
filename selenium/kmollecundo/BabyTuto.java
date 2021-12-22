package selenium.kmollecundo;

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

public class BabyTuto {
    private WebDriver driver;
    @BeforeClass
    public static void Setup(){
        //Initialization del web driver con chrome
        WebDriverManager.chromedriver().setup();
    }
    @Before
    public void Init(){
        //Instanciamos init
        driver = new ChromeDriver();

        //Metodo para iniciar siempre con la pantalla maximizada
        driver.manage().window().maximize();

        //Cargar la página
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://www.babytuto.com/");
    }
    @Test
    public void atc01_FiltrarProductosPorMarca(){
        //Cierro pantalla de subscripcion
        driver.findElement(By.cssSelector("#newsletter > button")).click();

        //Selecciono categiria Coches
        List<WebElement> option_list = driver.findElements(By.cssSelector("div.bar-2-products ul li a"));
        for (WebElement l: option_list
             ) {
            if (l.getText().contains("COCHES")){
                l.click();
                break;
            }
        }

        //Selecciono Accesorios de coches
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.bar-2-products .sub-cats .sub-cat ul.tree li a")));
        List<WebElement> option_listsubmenu = driver.findElements(By.cssSelector("div.bar-2-products .sub-cats .sub-cat ul.tree li a"));
        for (WebElement s: option_listsubmenu
             ) {
            if (s.getText().contains("Accesorios para coches")){
                s.click();
                break;
            }
        }

        //Filtro por marca BBPRO
        WebDriverWait wait1 = new WebDriverWait(driver,15);
        wait1.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.main-container .container .main-big .categories .span2 .filter ul li a span.lbl")));
        List<WebElement> filter = driver.findElements(By.cssSelector("div.main-container .container .main-big .categories .span2 .filter ul li a span.lbl"));
        for (WebElement f: filter
             ) {
            if (f.getText().contains("BBpro")){
                f.click();
                break;
            }
        }

        //Verifico que solo encuentre productos BBPRO
        WebElement marca = driver.findElement(By.cssSelector("div.span10 .items .item .info .merchant-name"));
        Assert.assertEquals("BBPRO",marca.getText());

    }
        @After
        public void Close(){
            if (driver != null) {
                //
                driver.close();
            }
        }

}
