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


public class TestYourStore {

    private WebDriver driver;
    private User testUser;
    private int randomNumber = (int) (Math.random() * 1000 + 1);
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
        test = extent.createTest(" Registro de usuario");
        test.log(Status.PASS, "Se ingreso correctamente a la pagina");
        System.out.println(" Se ingreso correctamnete a: " + driver.getTitle());
        pause(5000);
        testUser = new User("John", "Doe", "jhdirose" + randomNumber + "@gmail.com", "1234567890", "123456", "123456");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        extent.flush();
    }

    @Test
    public void testNavigateToPage() throws Exception {
        clickOnMyAccount();
        clickOnRegister();
        clickOnSubscribe();
        fillOutRegistrationForm(testUser);
        clickOnPrivacyPolicy();
        clickOnContinue();
        pause(5000);

    }


    private void clickOnMyAccount() throws Exception {
        WebElement myAccountLink = driver.findElement(By.cssSelector("a[title='My Account']"));
        myAccountLink.click();
        test.log(Status.PASS, "Se hace click correctamente en el link de My Account");

        // Verificar que aparece la ventana emergente con las opciones "Register" e "Login"
        WebElement registerOption = driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/ul/li[1]/a"));
        WebElement loginOption = driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/ul/li[2]/a"));

        if (registerOption.isDisplayed() && loginOption.isDisplayed()) {
            test.log(Status.PASS, "La ventana emergente se muestra correctamente");
        } else {
            test.log(Status.FAIL, "La ventana emergente no se muestra correctamente");
        }


    }

    private void clickOnRegister() throws Exception {


        WebElement registerLink = driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/ul/li[1]/a"));
        registerLink.click();
        test.log(Status.PASS, "Se hace click correctamente en el link de Register");
        pause(5000);
        WebElement registerForm = driver.findElement(By.xpath("//*[@id=\"content\"]/form"));

        if (registerForm.isDisplayed()) {
            test.log(Status.PASS, "El formulario de registro se muestra correctamente");
        } else {
            test.log(Status.FAIL, "El formulario de registro no se muestra correctamente");
        }

    }

    private void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void fillOutRegistrationForm(User user) throws Exception {
        WebElement firstName = driver.findElement(By.xpath("//*[@id=\"input-firstname\"]"));
        firstName.sendKeys(user.getFirstName());
        WebElement lastName = driver.findElement(By.xpath("//*[@id=\"input-lastname\"]"));
        lastName.sendKeys(user.getLastName());
        WebElement email = driver.findElement(By.xpath("//*[@id=\"input-email\"]"));
        email.sendKeys(user.getEmail());
        WebElement telephone = driver.findElement(By.xpath("//*[@id=\"input-telephone\"]"));
        telephone.sendKeys(user.getTelephone());
        WebElement password = driver.findElement(By.xpath("//*[@id=\"input-password\"]"));
        password.sendKeys(user.getPassword());
        WebElement passwordConfirm = driver.findElement(By.xpath("//*[@id=\"input-confirm\"]"));
        passwordConfirm.sendKeys(user.getPasswordConfirm());
        test.log(Status.PASS, "El formulario de registro se llena correctamente");
    }

    private void clickOnSubscribe() throws Exception {

        WebElement subscribe = driver.findElement(By.xpath("//input[@value='0']"));
        subscribe.click();
        if (subscribe.isSelected()) {
            test.log(Status.PASS, "Se selecciona correctamente el botón de suscribirse");
        } else {
            test.log(Status.FAIL, "No se selecciona correctamente el botón de suscribirse");
        }

    }

    private void clickOnPrivacyPolicy() throws Exception {

        WebElement privacyPolicy = driver.findElement(By.xpath("//input[@name='agree']"));
        privacyPolicy.click();
        if (privacyPolicy.isSelected()) {
            test.log(Status.PASS, "Se selecciona correctamente el botón de política de privacidad");
        } else {
            test.log(Status.FAIL, "No se selecciona correctamente el botón de política de privacidad");
        }


    }

    private void clickOnContinue() throws Exception {

        WebElement continueButton = driver.findElement(By.cssSelector("input[value='Continue']"));
        continueButton.click();
        pause(5000);

        String expectedUrl = "http://opencart.abstracta.us/index.php?route=account/success";
        WebElement successMessage = driver.findElement(By.xpath("//p[contains(text(), 'Congratulations! Your new account has been successfully created!')]"));
        if (expectedUrl.equals(driver.getCurrentUrl()) && "Congratulations! Your new account has been successfully created!".equals(successMessage.getText())) {
            test.log(Status.PASS, "Se redirecciona correctamente a la página de registro exitoso y se muestra el mensaje de registro exitoso " + successMessage.getText());
        } else {
            test.log(Status.FAIL, "No se redirecciona correctamente a la página de registro exitoso o no se muestra el mensaje de registro exitoso");
        }

    }

}







