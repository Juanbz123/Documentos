package selenium.rolguin;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class automationPractice {

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

    }

    @Test
    public void atc01BusquedaPalabrasClaves(){
        driver.get("http://automationpractice.com/");
        driver.findElement(By.xpath("//*[@id='search_query_top']")).sendKeys("dress");
        driver.findElement(By.xpath("//*[@id='searchbox']/button")).click();
        int tamaño;
        tamaño = driver.findElements(By.xpath("//*[@id='center_column']/ul/li")).size();
        assertTrue("Si es mayor a 2",2< tamaño);
    }

    @Test
    public void atc02BusquedaDirectaProductoExistente(){
        driver.get("http://automationpractice.com/");
        driver.findElement(By.cssSelector("#search_query_top")).sendKeys("printed chiffon dress");
        driver.findElement(By.cssSelector("#searchbox > button")).click();
        WebElement r=driver.findElement(By.cssSelector("#center_column > ul > li.ajax_block_product.col-xs-12.col-sm-6.col-md-4.first-in-line.last-line.first-item-of-tablet-line.first-item-of-mobile-line.last-mobile-line > div > div.right-block > h5 > a"));
        String prenda = r.getAttribute("title");
        System.out.println(prenda);
        assertTrue("Es la prenda buscada",prenda.equals("Printed Chiffon Dress"));
    }

    @Test
    public void atc03ProductoNoEncontrado(){
        String buscar ="liquido matapulgas";
        driver.get("http://automationpractice.com/");
        driver.findElement(By.xpath("//*[@id='search_query_top']")).sendKeys("liquido matapulgas");
        driver.findElement(By.xpath("//*[@id='searchbox']/button")).click();
        WebElement aler = driver.findElement(By.xpath("//*[@id=\"center_column\"]/p"));
        String alerta = aler.getText();
        assertEquals("No results were found for your search"+" "+'"'+buscar+'"',alerta);
        // alert alert-warning

    }

    @Test
    public void atc04listDinamica() {
        driver.get("http://automationpractice.com/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id='search_query_top']")).sendKeys("blo");

        //obtengo el elemento de la lista dinamica
        int cantidad = driver.findElements(By.xpath("//div[@class=\"ac_results\"]/ul/li")).size();
        WebElement blo = driver.findElement(By.xpath("//div[@class=\"ac_results\"]/ul/li"));
        System.out.println(blo);
        boolean blusa= blo.isDisplayed();
        //pregunto si la lista dinamica esta desplegada
        if (blusa == true){
            String palabra;
            for (int i=0;i<cantidad;i++){
                palabra = driver.findElements(By.xpath("//div[@class=\"ac_results\"]/ul/li")).get(i).getText();
                Pattern patron = Pattern.compile("Blouse");
                Matcher m = patron.matcher(palabra);
                boolean e = m.find();
                if (e==true){
                    driver.findElements(By.xpath("//div[@class=\"ac_results\"]/ul/li")).get(i).click();
                }

            }

        }
        //rescato  el titulo del producto desplegado y lo comparo con lo solicitado
        WebElement r = driver.findElement(By.xpath("//*[@id=\"product_reference\"]"));
        String res = r.getText();
        assertEquals("Model demo_2",res);

    }


    @Test
    public void atc05AgregarProductoCambiandoTallaYColor() {
        driver.get("http://automationpractice.com/");
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id='search_query_top']")).sendKeys("blo");
        WebDriverWait d = new WebDriverWait(driver,10);
        d.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"ac_results\"]/ul/li")));
        driver.findElement(By.xpath("//div[@class=\"ac_results\"]/ul/li")).click();

        //selecciona la talla de la prenda
        int size =  driver.findElements(By.xpath("//select[@id=\"group_1\"]/option")).size();
        for (int i=0; i<size;i++){
            String talla = driver.findElements(By.xpath("//select[@id=\"group_1\"]/option")).get(i).getAttribute("title");
            if (talla.equals("L")){
                driver.findElements(By.xpath("//select[@id=\"group_1\"]/option")).get(i).click();
            }
        }

        //selecciona el color de la prenda
        int cant = driver.findElements(By.xpath("//div[@class=\"attribute_list\"]/ul/li")).size();
        for (int k=0;k<cant;k++){
            String color = driver.findElements(By.xpath("//div[@class=\"attribute_list\"]/ul/li/a")).get(k).getAttribute("title");
            if (color.equals("White")){
                driver.findElements(By.xpath("//div[@class=\"attribute_list\"]/ul/li/a")).get(k).click();
            }
        }

        //Click en el boton agregar al carro
        driver.findElement(By.xpath("//p[@id=\"add_to_cart\"]/button")).click();
        d.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id=\"layer_cart\"]/div/div/h2")));
        WebElement agregado = driver.findElement(By.xpath("//div[@id=\"layer_cart\"]/div/div/h2"));
        String texto = agregado.getText();

        //verifica si el producto fue agregado al carro comparando el texto obtenido despues de apretar el boton agregar
        assertEquals("Product successfully added to your shopping cart",texto);


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
