import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * created by MehmetBesli063 on 04.2019
 */
public class BeinSportProject {

    private Logger logger = Logger.getLogger("BeinSportProject");

    private WebDriver driver;
    private WebDriverWait driverWait;
    private Select select;

    public String priceBeforeClick;
    public String lastTakenPrice;
    public String totalCharge;
    public String errorMessage;


    public void beinsportTestCase() throws Exception {
        openChromeBrowser();
        navigateToWebsite();
        SubscribeButton();
        getCorrectPrice();
        goToOneMonth();
        comparePrice();
        MonthlyPasswithOneWeek();
        createAccount();
        IgnoreEmailVerification();
        makePaymentWıthCard();
        expectTotalChargeContol();
        writeCardDetails();
        expectErrorTestAndFinish();
        closeBrowser();
    }

    public void closeBrowser() {
        try {
            driver.close();
            logger.info("Browser closed");
        } catch (Exception e) {
            logger.info("Exception occured when browser closed");
        }
    }


    public void expectErrorTestAndFinish() throws Exception {
        errorMessage = driver.findElement(By.xpath("//td[@class='ncoltxtc']")).getText();
        if (errorMessage != null && errorMessage.length() > 0) {
            logger.info("Got error message");
        } else {
            logger.info("Did not error message");
            throw new Exception("could not get error message");
        }
    }


    public void writeCardDetails() {
        driver.findElement(By.id("Ecom_Payment_Card_Name")).sendKeys(getRandomString(5));

        driver.findElement(By.id("Ecom_Payment_Card_Number")).sendKeys(getRandomNumber((16)));

        Select dropDown = new Select(driver.findElement(By.id("Ecom_Payment_Card_ExpDate_Month")));
        dropDown.selectByIndex(4);

        Select dropDown2 = new Select(driver.findElement(By.id("Ecom_Payment_Card_ExpDate_Year")));
        dropDown2.selectByIndex(3);

        driver.findElement(By.id("Ecom_Payment_Card_Verification")).sendKeys(getRandomNumber(3));

        driver.findElement(By.id("submit3")).click();
    }


    public void expectTotalChargeContol() throws Exception {
        totalCharge = driver.findElement(By.xpath("//*[@id=\"ncol_ref\"]/tbody/tr[2]/td[2]/small")).getText();
        if (totalCharge.contains("1.00")) {
            logger.info("1.00 total charge exist");
        } else {
            logger.info("1.00 total charge is not exist");
        }
    }


    public void makePaymentWıthCard() {
        driver.findElement(By.xpath("//div[@class='custom-checkbox']")).click();
        driverWait = new WebDriverWait(driver, 10);
        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='form-group position-r show']/input[@class='bc-subscribe border-none show']"))).click();
    }


    public void IgnoreEmailVerification() {
        driver.findElement(By.id("close")).click();
    }


    public void createAccount() {
        driver.findElement(By.name("FirstName")).sendKeys(getRandomString(5));
        driver.findElement(By.name("LastName")).sendKeys(getRandomString(5));
        driver.findElement(By.name("EmailOrPhone")).sendKeys(generateRandomEmail());
        driver.findElement(By.name("Password")).sendKeys(getRandomString(8));
        driver.findElement(By.xpath("//div[@class='custom-checkbox']")).click();
        driver.findElement(By.xpath("//input[@class='border-none bc-subscribe']")).click();
    }


    public void MonthlyPasswithOneWeek() {
        driver.findElement(By.xpath("//div[@class='row subscription-item']//div[@class='subscription-package open'][2]//a[@class='btn-type-1 mt-20-plus add-card']")).click();
    }


    public void comparePrice() {
        lastTakenPrice = driver.findElement(By.xpath("//div[@class='row subscription-item']//div[@class='subscription-package open'][2]//span[@class='price fz-14 fw-bold text-center']")).getAttribute("innerHTML");

        if (lastTakenPrice.equals(priceBeforeClick)) {
            logger.info("Same Price");
        } else {
            logger.info("Different Prices");
        }
    }


    public void goToOneMonth() {
        driver.findElement(By.xpath("//div[@class='container-onboarding step1']/div[3]")).click();
    }


    public void getCorrectPrice() {
        priceBeforeClick = driver.findElement(By.xpath("//div[@class='container-onboarding step1']//div[@class='row subscription-package package-group open clearfix'][3]//span[@class='subscription-price-item price fz-14 fw-bold']")).getAttribute("innerHTML");
    }


    public void SubscribeButton() {
        driver.findElement(By.xpath("//li[@class='subscribe']/a")).click();

    }


    public void navigateToWebsite() throws Exception {
        driver.get("https://connect-th.beinsports.com/en");
        if (driver.getCurrentUrl().contains("https://connect-th.beinsports.com/en")) {
            logger.info("Beinsport page opened");
        } else {
            logger.info("Beinsport did not open");
        }
    }


    public void openChromeBrowser() {
        String path = System.getProperty("user.dir");
        System.out.println("Proje path : " + path);
        System.setProperty("webdriver.chrome.driver", path + "\\lib\\chromedriver.exe");

        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }


    public String generateRandomEmail() {
        return getRandomString(10) + "@" + getRandomString(5) + ".com";
    }


    public String getRandomString(int randomStringSize) {
        String randomSumberString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < randomStringSize) {
            int index = (int) (rnd.nextFloat() * randomSumberString.length());
            salt.append(randomSumberString.charAt(index));
        }
        return salt.toString();
    }


    private String getRandomNumber(int randomStringSize) {
        String randomNumbers = "123456789";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < randomStringSize) {
            int index = (int) (rnd.nextFloat() * randomNumbers.length());
            salt.append(randomNumbers.charAt(index));
        }
        return salt.toString();
    }


    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public String getPriceBeforeClick() {
        return priceBeforeClick;
    }

    public void setPriceBeforeClick(String priceBeforeClick) {
        this.priceBeforeClick = priceBeforeClick;
    }

    public String getLastTakenPrice() {
        return lastTakenPrice;
    }

    public void setLastTakenPrice(String lastTakenPrice) {
        this.lastTakenPrice = lastTakenPrice;
    }

    public String getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(String totalCharge) {
        this.totalCharge = totalCharge;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}