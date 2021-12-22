package selenium.arey;

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

public class AutomationFrankLuzon{


        private WebDriver driver;

        @BeforeClass
        public static void setup()
        {
            System.out.println("Setup necesario antes de Instanciar");
            WebDriverManager.chromedriver().setup();
        }

        @Before
        public void init()
        {
            System.out.println("init para instanciar");
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);      // MANERA IMPLICITA DE INTRODUCIR TIEMPO MAXIMO QUE LE DA AL TEST PARA CARGAR EL ATRIBUTO
            driver.get("http://automation.frankluzon.com//");
        }

        @Test
        public void EnviarResena()
        {
            driverElement("//*[@id=\"post-27\"]/div/div[3]/ul/li[10]/a/div[1]").click();
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.urlMatches("http://automation.frankluzon.com/product/cap/"));
            Assert.assertEquals("http://automation.frankluzon.com/product/cap/",driver.getCurrentUrl());
            driverElement("//*[@id=\"tab-title-reviews\"]").click();
            driverElement("//*[@id=\"commentform\"]/div/p/span/a[5]").click();
            driverElement("//*[@id=\"comment\"]").sendKeys("Very good cap");
            driverElement("//*[@id=\"author\"]").sendKeys("Alejandro");
            driverElement("//*[@id=\"email\"]").sendKeys("ale@gmail.com");
            driverElement("//*[@id=\"submit\"]").click();
            if(driver.getCurrentUrl().equals("http://automation.frankluzon.com/wp-comments-post.php")){

            } else{
                Assert.assertEquals("Your review is awaiting approval",driver.findElement(By.className("meta")).getText());
            }
        }

        @After
        public void close() throws InterruptedException {
            if (driver != null){
                Thread.sleep(4000);
                driver.close();
            }
        }

        @AfterClass
        public static void closeAll()
        {
            System.out.println("closeAll :: Cerrar otras conexiones que fueron utilizados en el test");

        }

        public WebElement driverElement(String path)
        {
            WebElement driverelement = driver.findElement(By.xpath(path));
            return driverelement;
        }

        public void findElementList(String path,String element)
        {
            List<WebElement> options_list = driver.findElements(By.cssSelector(path));
            for (WebElement l: options_list)
            {
//se recorre la lista hasta encontrar la opción requerida
                if (l.getText().contains(element))
                {
                    l.click();
                    break;
                }
            }


    }

}
