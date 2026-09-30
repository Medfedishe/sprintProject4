package ru.yandex.praktikum.driver;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    public static WebDriver createDriver() {
        String browser = System.getProperty(
                "browser",
                "chrome"
        );

        boolean headless = Boolean.parseBoolean(
                System.getProperty("headless", "false")
        );

        WebDriver driver;

        if ("chrome".equalsIgnoreCase(browser)) {
            ChromeOptions options = new ChromeOptions();

            options.addArguments(
                    "--no-sandbox",
                    "--disable-dev-shm-usage"
            );

            if (headless) {
                options.addArguments("--headless=new");
            }

            driver = new ChromeDriver(options);

        } else if ("firefox".equalsIgnoreCase(browser)) {
            FirefoxOptions options = new FirefoxOptions();

            if (headless) {
                options.addArguments("-headless");
            }

            driver = new FirefoxDriver(options);

        } else {
            throw new IllegalArgumentException(
                    "Неизвестный браузер: " + browser
                            + ". Используй chrome или firefox."
            );
        }

        driver.manage().window().setSize(
                new Dimension(1920, 1080)
        );

        return driver;
    }
}