package selenium.foliva;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BabyTuto {
    private WebDriver driver;
    @BeforeClass
    public static void Setup(){
        WebDriverManager.chromedriver().setup();

    }
    @Before
    public void before(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.babytuto.com/");
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
    }

    @Test
    public void filtros() throws InterruptedException {
        WebDriverWait driverWait = new WebDriverWait(driver,10);
        WebElement initModal = driver.findElement(By.xpath("//*[@id=\"newsletter\"]/button"));
        if(initModal.isDisplayed()){
            initModal.click();
        }


        List<WebElement> categories = driver.findElements(By.cssSelector("div.section-products > div.header-bar > div > div > ul > li"));
        WebElement categoryCoches = null;

        for( WebElement category : categories){
            if( category.getText().contains("COCHES")){
                categoryCoches = category;
                category.click();
                break;
            }
        }
        driverWait.until(ExpectedConditions.visibilityOf(categoryCoches.findElement(By.cssSelector("div.sub-cats"))));

        WebElement category = categoryCoches.findElement(By.linkText("Accesorios para coches"));
        category.click();


        WebElement bbproCat =  driver.findElement(By.partialLinkText("BBpro"));
        Actions actions = new Actions(driver);
        actions.moveToElement(bbproCat);
        driverWait.until(ExpectedConditions.elementToBeClickable(bbproCat));
        bbproCat.click();

        List<WebElement> products = driver.findElements(By.cssSelector("div.products > div.items > div.item "));

        boolean allSameCategory = true;
         for (WebElement product : products) {
             WebElement brandItem = product.findElement(By.cssSelector("div.info > div.merchant-name"));
             if(!brandItem.getText().contains("BBPRO")){
                 allSameCategory = false;
                 break;
             }
        }

         Assert.assertTrue(allSameCategory);
        Thread.sleep(5000);

    }

    @After
    public void after(){
        if(driver != null){
            driver.close();
        }
    }
}
