package selenium.fluzon;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ManejoPopup {

    //atributos
    static WebDriver driver;

    @BeforeClass
    public static void init(){
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies(); //borrar cookies
        driver.manage().window().maximize();

        //espera implicita -> se define 1 sola vez y sirve para toda la ejecucion
        // findelement -> findelements
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //no se encuentra el elemento Exception: No
    }

    @Test
    public void popupJsAlert(){
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        WebElement btnJsAlert = driver.findElement(By.xpath("//button[@onclick='jsAlert()']"));
        btnJsAlert.click();
        //funcion switchTo
        Alert popup1 = driver.switchTo().alert();
        String text = popup1.getText();
        if (text.contains("JS Alert")){
            popup1.accept();
        }

    }
    @Test
    public void popupJsConfirm(){
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        WebElement btnJsConfirm = driver.findElement(By.xpath("//button[@onclick='jsConfirm()']"));
        btnJsConfirm.click();
        //funcion switchTo
        Alert popup2 = driver.switchTo().alert();
        String text = popup2.getText();
        if (text.contains("JS Confirm")){
            popup2.dismiss();
            btnJsConfirm.click();
            popup2.accept();
        }

    }

    @Test
    public void popupJsPrompt() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        WebElement btnJsPrompt = driver.findElement(By.xpath("//button[@onclick='jsPrompt()']"));
        btnJsPrompt.click();
        //funcion switchTo
        Alert popup3 = driver.switchTo().alert();
        String text = popup3.getText();
        if (text.contains("JS prompt")){
            popup3.sendKeys("Hola Bootcamp");
            Thread.sleep(2000);
            popup3.accept();
        }

    }

    @Test
    public void popupAuth() throws InterruptedException {
        String user="admin";
        String pass="admin";
        //autorizacion basica
        // https://user:pass@url.com -> https://admin:admin@the-internet.herokuapp.com/basic_auth
        driver.get("https://"+user+":"+pass+"@the-internet.herokuapp.com/basic_auth");
        Thread.sleep(2000);
        WebElement ingresoExitoso = driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credenti')]"));
        Assert.assertEquals("Congratulations! You must have the proper credentials.",ingresoExitoso.getText());
    }

    @Test
    public void popupWindows() throws InterruptedException {
        //generalmente este popup aparece cuando queremos subir un archivo
        driver.get("https://the-internet.herokuapp.com/upload");
        WebElement upload = driver.findElement(By.xpath("//input[@id='file-upload']"));
        WebElement btnupload = driver.findElement(By.xpath("//input[@id='file-submit']"));

        //subir archivo
        upload.sendKeys("D:\\subirSelenium.txt"); //ruta del archivo a subir
        btnupload.click();
        Thread.sleep(2000);
        WebElement validacionUpload = driver.findElement(By.xpath("//h3[contains(text(),'File Uploaded!')]"));
        Assert.assertEquals("File Uploaded!", validacionUpload.getText());
    }




    @After
    public void close(){
        if(driver != null){
            driver.close();
        }
    }


}
