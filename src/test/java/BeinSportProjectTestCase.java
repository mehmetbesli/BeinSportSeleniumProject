/**
 * created by MehmetBesli063 on 04.2019
 */

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.junit.After;
import org.junit.Assert;

import java.sql.Driver;

public class BeinSportProjectTestCase {

    BeinSportProject project;

    @Before
    public void openChromeBrowser() {
        project = new BeinSportProject();
        project.openChromeBrowser();
    }

    @After
    public void closeBrowser() {
        project.closeBrowser();
    }

    @Test
    public void gotoAmazonWebPageTest() throws Exception {
        project.navigateToWebsite();
        Assert.assertTrue("You are not in amazon web site", project.getDriver().getCurrentUrl().contains("https://connect-th.beinsports.com/en"));
    }

    @Test
    public void SubscribeButton() throws Exception {
        project.navigateToWebsite();
        project.SubscribeButton();
        Assert.assertTrue("Clicked Subscribe button", project.getDriver().getCurrentUrl().contains("subscribe"));
    }

    @Test
    public void getPrice() throws Exception {
        project.navigateToWebsite();
        project.SubscribeButton();
        project.getCorrectPrice();
        Assert.assertEquals("Correct Prices is chosen", true, project.getPriceBeforeClick());
    }

    @Test
    public void goToOneMonth() throws Exception {
        project.navigateToWebsite();
        project.SubscribeButton();
        project.getCorrectPrice();
        project.goToOneMonth();
        String text=project.getDriver().findElement(By.xpath("//div[@class='layout-wide']//div[4]/h1")).getText();
        Assert.assertEquals("Successfully clicked OneMonth Part", true, text.equalsIgnoreCase("Select Your Package"));
    }

    @Test
    public void comparePrice() throws Exception {
        project.navigateToWebsite();
        project.SubscribeButton();
        project.getCorrectPrice();
        project.goToOneMonth();
        project.comparePrice();
        Assert.assertEquals("Prices are same", project.getPriceBeforeClick(), project.getLastTakenPrice());
    }

    @Test
    public void MonthlyPasswithOneWeek() throws Exception {
        project.navigateToWebsite();
        project.SubscribeButton();
        project.getCorrectPrice();
        project.goToOneMonth();
        project.comparePrice();
        project.MonthlyPasswithOneWeek();
        //Assert.assertTrue("You are in Monthly Pass with One Week page", project.getLastTakenPrice()!=null);
        Assert.assertTrue("You are in Monthly Pass with One Week page", project.getLastTakenPrice().contains("199"));

    }

    @Test
    public void createAccount() throws Exception {
        project.navigateToWebsite();
        project.SubscribeButton();
        project.getCorrectPrice();
        project.goToOneMonth();
        project.comparePrice();
        project.MonthlyPasswithOneWeek();
        project.createAccount();
        Assert.assertTrue("You are not in amazon web site", project.getDriver().getCurrentUrl().contains("account/register"));
    }

    @Test
    public void IgnoreEmailVerification() throws Exception {
        project.navigateToWebsite();
        project.SubscribeButton();
        project.getCorrectPrice();
        project.goToOneMonth();
        project.comparePrice();
        project.MonthlyPasswithOneWeek();
        project.createAccount();
        project.IgnoreEmailVerification();
        String ignoreVerificationAlert  = project.getDriver().findElement(By.id("close")).getText();
        Assert.assertTrue("We saw error message", ignoreVerificationAlert.contains("X"));
    }

    @Test
    public void makePaymentWıthCard() throws Exception {
        project.navigateToWebsite();
        project.SubscribeButton();
        project.getCorrectPrice();
        project.goToOneMonth();
        project.comparePrice();
        project.MonthlyPasswithOneWeek();
        project.createAccount();
        project.IgnoreEmailVerification();
        project.makePaymentWıthCard();
        String textWritten = project.getDriver().findElement(By.xpath("//p[@class='payment-cards-description']")).getText();
        Assert.assertTrue("We are in Make Payment With Car Pages", textWritten.contains("Pay with Credit/Debit Card"));
        //Assert.assertTrue("We are in Make Payment With Car Pages", textWritten.equalsIgnoreCase("Pay with Credit/Debit Card"));
    }

    @Test
    public void expectTotalChargeContol() throws Exception {
        project.navigateToWebsite();
        project.SubscribeButton();
        project.getCorrectPrice();
        project.goToOneMonth();
        project.comparePrice();
        project.MonthlyPasswithOneWeek();
        project.createAccount();
        project.IgnoreEmailVerification();
        project.makePaymentWıthCard();
        project.expectTotalChargeContol();
        //Assert.assertTrue("1.00 total charge exist", project.errorMessage.contains("1.00"));
        Assert.assertTrue("1.00 total charge exist", project.getDriver().getCurrentUrl().contains("ncol/prod"));
    }

    @Test
    public void writeCardDetails() throws Exception {
        project.navigateToWebsite();
        project.SubscribeButton();
        project.getCorrectPrice();
        project.goToOneMonth();
        project.comparePrice();
        project.MonthlyPasswithOneWeek();
        project.createAccount();
        project.IgnoreEmailVerification();
        project.makePaymentWıthCard();
        project.expectTotalChargeContol();
        project.writeCardDetails();
        //Assert.assertTrue("1.00 total charge exist", project.getDriver().getCurrentUrl().contains("ncol/prod"));
        Assert.assertTrue("You are in payment page", project.getDriver().getCurrentUrl().contains("ncol/prod"));
    }

    @Test
    public void expectErrorTestAndFinish() throws Exception {
        project.navigateToWebsite();
        project.SubscribeButton();
        project.getCorrectPrice();
        project.goToOneMonth();
        project.comparePrice();
        project.MonthlyPasswithOneWeek();
        project.createAccount();
        project.IgnoreEmailVerification();
        project.makePaymentWıthCard();
        project.expectTotalChargeContol();
        project.writeCardDetails();
        project.expectErrorTestAndFinish();
        String writtenText = project.getDriver().findElement(By.xpath("//td[@class='ncoltxtc']/h3")).getText();
        Assert.assertTrue("Error message got", writtenText.equalsIgnoreCase("The transaction has been denied"));
    }
}