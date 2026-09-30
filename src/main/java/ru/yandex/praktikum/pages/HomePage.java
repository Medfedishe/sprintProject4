package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {

    private static final String PAGE_URL =
            "https://qa-scooter.praktikum-services.ru/";

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Кнопка принятия файлов cookie
    private final By cookieButton =
            By.id("rcc-confirm-button");

    // Верхняя кнопка «Заказать»
    private final By topOrderButton =
            By.xpath("(//button[normalize-space()='Заказать'])[1]");

    // Нижняя кнопка «Заказать»
    private final By bottomOrderButton =
            By.xpath("(//button[normalize-space()='Заказать'])[2]");

    // Заголовок раздела «Вопросы о важном»
    private final By importantQuestionsTitle =
            By.xpath("//*[normalize-space()='Вопросы о важном']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    public void open() {
        driver.get(PAGE_URL);
    }

    public void acceptCookiesIfPresent() {
        List<WebElement> cookieButtons =
                driver.findElements(cookieButton);

        if (!cookieButtons.isEmpty()
                && cookieButtons.get(0).isDisplayed()) {
            cookieButtons.get(0).click();
        }
    }

    public void clickOrderButton(
            OrderButtonPosition buttonPosition
    ) {
        By selectedButton;

        if (buttonPosition == OrderButtonPosition.TOP) {
            selectedButton = topOrderButton;
        } else {
            selectedButton = bottomOrderButton;
        }

        WebElement button = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        selectedButton
                )
        );

        scrollToElement(button);

        wait.until(
                ExpectedConditions.elementToBeClickable(button)
        ).click();
    }

    public void scrollToQuestions() {
        WebElement title = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        importantQuestionsTitle
                )
        );

        scrollToElement(title);
    }

    public void clickQuestion(int questionNumber) {
        By questionLocator = By.id(
                "accordion__heading-" + questionNumber
        );

        WebElement question = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        questionLocator
                )
        );

        scrollToElement(question);

        wait.until(
                ExpectedConditions.elementToBeClickable(question)
        ).click();
    }

    public String getAnswerText(int questionNumber) {
        By answerLocator = By.id(
                "accordion__panel-" + questionNumber
        );

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        answerLocator
                )
        ).getText();
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                element
        );
    }

    public enum OrderButtonPosition {
        TOP,
        BOTTOM
    }
}
