package selenium.foliva;

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

import java.util.List;

public class AutomationPractice {
    private WebDriver driver;
    @BeforeClass
    public static void Setup(){
        WebDriverManager.chromedriver().setup();

    }
    @Before
    public void before(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://automationpractice.com/");

        while ( !driver.getTitle().equals("My Store")){
            driver.navigate().refresh();
        }
    }
    @Test
    public void searchDress(){
        driver.findElement(By.xpath("//*[@id=\"search_query_top\"]")).sendKeys("dress");
        driver.findElement(By.xpath("//*[@id=\"searchbox\"]/button")).click();
        List<WebElement> products = driver.findElements(By.xpath("//*[@id=\"center_column\"]/ul/li"));

        Assert.assertTrue(products.size() > 2);
    }
    @Test
    public void searchPrintedDress(){
        driver.findElement(By.cssSelector("#search_query_top")).sendKeys("printed chiffon dress");
        driver.findElement(By.cssSelector("#searchbox > button")).click();
        WebElement product = driver.findElement(By.cssSelector("#center_column > ul > li.ajax_block_product.col-xs-12.col-sm-6.col-md-4.first-in-line.last-line.first-item-of-tablet-line.first-item-of-mobile-line.last-mobile-line > div > div.right-block > h5 > a"));

        Assert.assertEquals("Printed Chiffon Dress", product.getText());
    }
    @Test
    public void resultNotFound(){
        WebElement inputSearch = driver.findElement(By.cssSelector("#search_query_top"));
        inputSearch.sendKeys("liquido matapulgas" + Keys.ENTER);

        WebElement messageElement = driver.findElement(By.xpath("//*[@id=\"center_column\"]/p"));
        String message = messageElement.getText();

        Assert.assertEquals("No results were found for your search \"liquido matapulgas\"", message);
    }
    @Test
    public void autocompleteWorks() throws InterruptedException {
        WebElement inputSearch = driver.findElement(By.xpath("//*[@id=\"search_query_top\"]"));
        inputSearch.sendKeys("blo");

        WebDriverWait driverWait = new WebDriverWait(driver,25);
        driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"index\"]/div[2]/ul/li")));

        WebElement autocompleteItem = driver.findElement(By.xpath("//*[@id=\"index\"]/div[2]/ul/li"));
        autocompleteItem.click();

        WebElement textElement = driver.findElement(By.xpath("//*[@id=\"product_reference\"]"));
        Assert.assertEquals("Model demo_2", textElement.getText());
        Thread.sleep(1000);
    }
    @Test
    public void addToCart() throws InterruptedException {
        WebElement inputSearch = driver.findElement(By.cssSelector("#search_query_top"));
        inputSearch.sendKeys("Blouse" + Keys.ENTER);

        driver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li/div")).click();
        Select sizeSelect = new Select(driver.findElement(By.xpath("//*[@id=\"group_1\"]")));
        sizeSelect.selectByVisibleText("L");

        //Select color
        List<WebElement> listColors = driver.findElements(By.xpath("//*[@id=\"color_to_pick_list\"]/li"));
        String selectedColor = "";
        for ( WebElement color : listColors) {
            String colorClass = color.getAttribute("class");
            if(!colorClass.equals("selected")){
                color.click();
                selectedColor = color.findElement(By.cssSelector("a")).getAttribute("name");
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"add_to_cart\"]/button")).click();

        //check cart
        WebDriverWait driverWait = new WebDriverWait(driver,20);
        WebElement layerCart = driver.findElement(By.xpath("//*[@id=\"layer_cart\"]"));
        driverWait.until(ExpectedConditions.visibilityOf(layerCart));

        String textSuccessfully = driver.findElement(By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[1]/h2")).getText();
        String colorAndSizeText = driver
                .findElement(By.xpath("//*[@id=\"layer_cart_product_attributes\"]"))
                .getText();


        Assert.assertEquals("Product successfully added to your shopping cart", textSuccessfully);
        Assert.assertEquals(selectedColor+", L", colorAndSizeText);
    }



    @After
    public void after(){
        if(driver != null){
            driver.close();
        }
    }
}
