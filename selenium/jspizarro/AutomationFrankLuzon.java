package selenium.jspizarro;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


import java.util.concurrent.TimeUnit;

public class AutomationFrankLuzon {

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
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @Test
    public void atc01_AgregarReview(){
        WebElement cajaBusqueda = driver.findElement(By.id("woocommerce-product-search-field-0"));
        cajaBusqueda.click();
        cajaBusqueda.sendKeys("CAP");
        cajaBusqueda.sendKeys(Keys.ENTER);

        driver.findElement(By.xpath("//a[contains(text(),'Reviews')]")).click();

        int estrellaAleatorio = (int) (Math.random() * 5) + 1;
        driver.findElement(By.cssSelector("#commentform .stars .star-"+ estrellaAleatorio)).click();

        driver.findElement(By.xpath("//textarea[@id='comment']")).sendKeys("Review9");
        driver.findElement(By.xpath("//textarea[@id='comment']")).sendKeys(Keys.TAB);

        driver.findElement(By.xpath("//input[@id='author']")).sendKeys("Sebastian");
        driver.findElement(By.xpath("//input[@id='author']")).sendKeys(Keys.TAB);

        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("jpizarrosebastian@gmail.com");

        driver.findElement(By.xpath("//input[@id='submit']")).click();

        String productName = driver.findElement(By.cssSelector("#main .product_title")).getText();
        String checkReview = driver.findElement(By.cssSelector("#reviews .woocommerce-Reviews-title")).getText();
        String checkApprovalReview = driver.findElement(By.cssSelector("#comments .woocommerce-review__awaiting-approval")).getText();
        String descriptionReview = driver.findElement(By.cssSelector("#comments .description")).getText();
        //String estrellas = driver.findElement(By.cssSelector("#comments .rating")).getText();

        String checkProductName = "Cap";
        String checkTextReview = "Reviews";
        String checkTextApprovalReview = "Your review is awaiting approval";
        String checkTextDescriptionReview = "Review9";
        //String checkEstrellas = String.valueOf(estrellaAleatorio);

        Assert.assertEquals(checkProductName,productName);
        Assert.assertEquals(checkTextReview,checkReview);
        Assert.assertEquals(checkTextApprovalReview,checkApprovalReview);
        Assert.assertEquals(checkTextDescriptionReview,descriptionReview);
        //Assert.assertEquals(checkEstrellas,estrellas);

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
