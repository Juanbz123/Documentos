package selenium.landres;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class automationFrank {



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
        public void atc01() {

            driver.findElement(By.xpath(" //*[@id='woocommerce-product-search-field-0']")).sendKeys("CAP");
            driver.findElement(By.xpath(" //*[@id='woocommerce-product-search-field-0']")).sendKeys(Keys.ENTER);
            driver.findElement(By.cssSelector(" #tab-title-reviews > a")).click();
            driver.findElement(By.xpath("//span/a[contains(text(),'4')]")).click();
            driver.findElement(By.xpath("//*[@id='comment']")).sendKeys("hola");
            driver.findElement(By.xpath("//*[@id='author']")).sendKeys("Carlos Andres ");
            driver.findElement(By.xpath("//*[@id='email']")).sendKeys("andreslunasalas@gmail.com");
            driver.findElement(By.xpath("//*[@id='submit']")).click();
            String palabra= driver.findElement(By.cssSelector("em.woocommerce-review__awaiting-approval")).getText();
            System.out.println(palabra);
            Assert.assertEquals("Your review is awaiting approval",palabra);

        }

        @After
        public void close(){
            if(driver != null){
                //  driver.close();
            }
        }

        @AfterClass
        public static void closeAll(){
            System.out.println("closeAll :: Cerrar otras conexiones que fueron utilizadas en el test");

        }

    }




