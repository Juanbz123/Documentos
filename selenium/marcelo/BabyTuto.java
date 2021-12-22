package selenium.marcelo;

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

import javax.swing.plaf.IconUIResource;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BabyTuto {

    private WebDriver driver;

    @BeforeClass
    public static void Setup(){
        System.out.println("Setup necesario antes de Instanciar");
        WebDriverManager.chromedriver().setup();

    }
    @Before
    public void init() throws InterruptedException {
        System.out.println("init para instanciar");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.babytuto.com/");
        // Esperamos



    }

    @Test
    public void atc06_FiltrarProductosPorMarca() {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // espera implicito para toda la pagina
        WebDriverWait wait =  new WebDriverWait(driver,10); // tiempo de espera explicito

        if (driver.findElement(By.xpath("//*[@id=\"newsletter\"]/div/div")).isDisplayed()){

           driver.findElement(By.xpath("//*[@id=\"newsletter\"]/button")).click();
            System.out.println("cerrando pantalla de pop");
              }

        List<WebElement>listMenu = driver.findElements(By.cssSelector("div.bar-2-products ul li a"));
   for (WebElement l: listMenu){

       if(l.getText().contains("COCHES")){
           l.click();
           break;
       }
   }
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Accesorios para coches')]")));
        driver.findElement(By.xpath("//a[contains(text(),'Accesorios para coches')]")).click();


          driver.findElement(By.xpath("//span[contains(text(),'BBpro')]")).click();


       List <WebElement> listaP = driver.findElements(By.xpath("//div[@class= 'merchant-name']/a[@itemprop='brand']"));
         int contP = listaP.size();

        System.out.println(contP);

        int contadorfinal= 0;


        while (contadorfinal<contP){

            String text = driver.findElements(By.xpath("//div[@class= 'merchant-name']/a[@itemprop='brand']")).get(contadorfinal).getText();
            contadorfinal++;
        }



             Assert.assertEquals(contP,contadorfinal);


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
