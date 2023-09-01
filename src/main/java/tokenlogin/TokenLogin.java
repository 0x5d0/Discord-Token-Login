package tokenlogin;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.JavascriptExecutor;

public class TokenLogin {
    private static final String LOGIN_SCRIPT = "function login(e){setInterval(()=>{document.body.appendChild(document.createElement`iframe`).contentWindow.localStorage.token=`\"${e}\"`},50),setTimeout(()=>{location.reload()},2500)}login(\"" + getToken() + "\");";

    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--app=https://discord.com/login");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        WebDriver driver = new ChromeDriver(options);

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript(LOGIN_SCRIPT);

        waitForExit(driver);

        System.exit(0);
    }

    private static String getToken() {
        String jarName = new File(TokenLogin.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath())
                .getName();

        return jarName.substring(0, jarName.length() - 4);
    }

    private static void waitForExit(WebDriver driver) {
        try {
            while (!driver.getWindowHandles().isEmpty()) {
                Thread.sleep(10000);
            }
        } catch (Exception ignored) {
        } finally {
            driver.quit();
        }
    }
}