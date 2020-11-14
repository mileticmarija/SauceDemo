import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(CustomListener.class)
public class SauceDemoChrome extends Base {
    String URL="https://www.saucedemo.com/";
    String user="standard_user";
    String pass="secret_sauce";

    @BeforeMethod
    public void setUp(){
        setUpChrome(URL);
    }

    @Test
    public void e2e(){
        login(user,pass);
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html");

        String naziv=driver.findElement(By.cssSelector(".inventory_item:nth-child(1)>div>a>div")).getText();
        String cena=driver.findElement(By.cssSelector(".inventory_item:nth-child(1) .inventory_item_price")).getText();

        driver.findElement(By.cssSelector(".inventory_item:nth-child(1) button")).click();
        driver.findElement(By.cssSelector("#shopping_cart_container")).click();

        String nazivKorpa=driver.findElement(By.cssSelector(".inventory_item_name")).getText();
        String cenaKorpa=driver.findElement(By.cssSelector(".inventory_item_price")).getText();

        Assert.assertEquals(naziv,nazivKorpa);
        Assert.assertEquals(cena,"$"+cenaKorpa);

        Assert.assertEquals(Double.valueOf(cena.substring(1,6)),Double.valueOf(cenaKorpa));

        driver.findElement(By.cssSelector(".btn_action.checkout_button")).click();
        driver.findElement(By.cssSelector("#first-name")).sendKeys("nekoIme");
        driver.findElement(By.cssSelector("#last-name")).sendKeys("nekoPrezime");
        driver.findElement(By.cssSelector("#postal-code")).sendKeys("12312");
        driver.findElement(By.cssSelector(".btn_primary.cart_button")).click();

        driver.findElement(By.cssSelector(".btn_action.cart_button")).click();
        Assert.assertTrue(driver.findElement(By.cssSelector(".pony_express")).isDisplayed());



    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}
