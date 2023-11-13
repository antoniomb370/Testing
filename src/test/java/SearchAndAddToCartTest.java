import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import extentReports.ExtentFactory;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SearchAndAddToCartTest {

    private WebDriver driver;

    static ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark/Spark.html");
    static ExtentReports extent;
    ExtentTest test;

    @BeforeAll
    public static void init() {
        extent = ExtentFactory.getInstance();
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://opencart.abstracta.us/index.php?route=common/home");
        driver.manage().window().maximize();
        test = extent.createTest(" Buscar y añadir a la cesta");
        test.log(Status.PASS, "Se ingreso correctamente a la pagina");
        pause(5000);

    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        extent.flush();
    }

    @Test
    public void testSearchAndAddToCart() {
        searchProduct("iPhone");
        verifySearchResults();
        addToCart();

        pause(5000);
        driver.quit();
    }

    private void searchProduct(String productName) {
        WebElement searchField = driver.findElement(By.xpath("//*[@id=\"search\"]/input"));
        searchField.sendKeys(productName);
        Assertions.assertEquals(productName, searchField.getAttribute("value"), "Search field is not filled with '" + productName + "'");
        test.log(Status.PASS, "El campo de búsqueda se rellena correctamente");
        WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"search\"]/span/button"));
        searchButton.click();
        test.log(Status.PASS, "Se hace clic en el botón de búsqueda");
        pause(5000);
        Assertions.assertTrue(driver.getCurrentUrl().contains("search=" + productName), "User is not redirected to the search results page");
        test.log(Status.PASS, "El usuario es redirigido a la página de resultados de búsqueda");
    }

    private void verifySearchResults() {
        WebElement searchResults = driver.findElement(By.cssSelector(".product-thumb"));
        Assertions.assertTrue(searchResults.isDisplayed(), "Search results are not displayed");
        test.log(Status.PASS, "Los resultados de la búsqueda se muestran correctamente");
    }

    private void addToCart() {
        WebElement addToCartButton = driver.findElement(By.xpath("//*[@id=\"content\"]/div[3]/div/div/div[2]/div[2]/button[1]"));
        addToCartButton.click();
        test.log(Status.PASS, "El usuario hace clic en el botón 'Añadir al carrito");
        pause(5000);

        WebElement successMessage = driver.findElement(By.cssSelector(".alert.alert-success.alert-dismissible"));
        String expectedMessage = "Success: You have added iPhone to your shopping cart!";
        Assertions.assertTrue(successMessage.getText().contains(expectedMessage), "Success message does not contain the expected text");
        test.log(Status.PASS, "El mensaje de éxito contiene el texto esperado");
    }

    private void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
