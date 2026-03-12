package com.hatester.keywords;

import com.hatester.enums.MatchType;
import com.hatester.keywords.browser.BrowserActions;
import com.hatester.keywords.element.ClickActions;
import com.hatester.keywords.element.ElementActions;
import com.hatester.keywords.element.InputActions;
import com.hatester.keywords.context.ContextActions;
import com.hatester.keywords.form.FormActions;
import com.hatester.keywords.interaction.RobotActions;
import com.hatester.keywords.interaction.MouseActions;
import com.hatester.keywords.scroll.ScrollActions;
import com.hatester.keywords.verify.VerifyActions;
import com.hatester.keywords.wait.WaitActions;
import org.openqa.selenium.*;

import java.util.List;
import java.util.function.Function;

public class WebUI {
    //WAIT
    public static void sleep(double second) {
        WaitActions.sleep(second);
    }

    public static WebElement waitForElementVisible(By by) {
        return WaitActions.waitForElementVisible(by);
    }

    public static WebElement waitForElementVisible(By by, int seconds) {
        return WaitActions.waitForElementVisible(by, seconds);
    }

    public static WebElement waitForElementToBeClickable(By by) {
        return WaitActions.waitForElementToBeClickable(by);
    }

    public static WebElement waitForElementToBeClickable(By by, int seconds) {
        return WaitActions.waitForElementToBeClickable(by, seconds);
    }

    public static WebElement waitForElementPresent(By by) {
        return WaitActions.waitForElementPresent(by);
    }

    public static WebElement waitForElementPresent(By by, int seconds) {
        return WaitActions.waitForElementPresent(by, seconds);
    }

    public static void waitForPageLoaded() {
        WaitActions.waitForPageLoaded();
    }

    public static void waitForElementNotVisible(By by) {
        WaitActions.waitForElementNotVisible(by);
    }

    public static void waitForElementNotVisible(By by, int seconds) {
        WaitActions.waitForElementNotVisible(by, seconds);
    }

    //BROWSER
    public static void openURL(String url) {
        BrowserActions.openURL(url);
    }

    public static String getCurrentURL() {
        return BrowserActions.getCurrentURL();
    }

    public static void refreshPage() {
        BrowserActions.refreshPage();
    }

    //CONTEXT: IFRAME, ALERT, POPUP-WINDOW
    public static void switchToFrame(By by) {
        ContextActions.switchToFrame(by);
    }

    public static void switchToFrame(By by, int seconds) {
        ContextActions.switchToFrame(by, seconds);
    }

    public static void switchToDefaultContent() {
        ContextActions.switchToDefaultContent();
    }

    public static void switchToParentFrame() {
        ContextActions.switchToParentFrame();
    }

    public static void acceptAlert() {
        ContextActions.acceptAlert();
    }

    public static void dismissAlert() {
        ContextActions.dismissAlert();
    }

    //ELEMENT
    public static void highlightElement(By by) {
        ElementActions.highlightElement(by);
    }

    public static void highlightElement(By by, String color) {
        ElementActions.highlightElement(by, color);
    }

    public static WebElement getWebElement(By by) {
        return ElementActions.getWebElement(by);
    }

    public static List<WebElement> getWebElements(By by) {
        return ElementActions.getWebElements(by);
    }

    public static Boolean checkElementExist(By by) {
        return ElementActions.checkElementExist(by);
    }

    public static boolean checkElementExist(By by, int maxRetries, int waitTimeMillis) {
        return ElementActions.checkElementExist(by, maxRetries, waitTimeMillis);
    }

    public static boolean isCheckboxSelected(By by) {
        return ElementActions.isCheckboxSelected(by);
    }

    public static void clickElement(By by) {
        ClickActions.clickElement(by);
    }

    public static void clickElement(By by, int seconds) {
        ClickActions.clickElement(by, seconds);
    }

    public static void clearElementText(By by) {
        InputActions.clearElementText(by);
    }

    public static void clearElementText(By by, int seconds) {
        InputActions.clearElementText(by, seconds);
    }

    public static void setText(By by, String text) {
        InputActions.setText(by, text);
    }

    public static void setText(By by, String text, int seconds) {
        InputActions.setText(by, text, seconds);
    }

    public static void setKey(By by, Keys key) {
        InputActions.setKey(by, key);
    }

    public static void setKey(By by, Keys key, int seconds) {
        InputActions.setKey(by, key, seconds);
    }

    public static void setTextAndKey(By by, String text, Keys key) {
        InputActions.setTextAndKey(by, text, key);
    }

    public static String getElementText(By by) {
        return ElementActions.getElementText(by);
    }

    public static String getElementAttribute(By by, String attributeName) {
        return ElementActions.getElementAttribute(by, attributeName);
    }

    public static String getElementCssValue(By by, String cssPropertyName) {
        return ElementActions.getElementCssValue(by, cssPropertyName);
    }

    //SCROLL
    public static void scrollToElement(By by) {
        ScrollActions.scrollToElement(by);
    }

    public static void scrollToElementAtTop(By by) {
        ScrollActions.scrollToElementAtTop(by);
    }

    public static void scrollToElementAtBottom(By by) {
        ScrollActions.scrollToElementAtBottom(by);
    }

    public static void scrollToElementAtTop(WebElement element) {
        ScrollActions.scrollToElementAtTop(element);
    }

    public static void scrollToElementAtBottom(WebElement element) {
        ScrollActions.scrollToElementAtBottom(element);
    }

    public static void scrollToPosition(int X, int Y) {
        ScrollActions.scrollToPosition(X, Y);
    }

    public static void setSliderValue(By hiddenInputBy, By sliderHandleBy, int percent) {
        ElementActions.setSliderValue(hiddenInputBy, sliderHandleBy, percent);
    }

    public static void clickJS(By by) {
        ClickActions.clickJS(by);
    }

    //ACTIONS CLASS
    public static void hoverElement(By by) {
        MouseActions.hoverElement(by);
    }

    public static void moveToOffset(int X, int Y) {
        MouseActions.moveToOffset(X, Y);
    }

    public static void dragAndDrop(By fromElement, By toElement) {
        MouseActions.dragAndDrop(fromElement, toElement);
    }

    public static void dragAndDropElement(By fromElement, By toElement) {
        MouseActions.dragAndDropElement(fromElement, toElement);
    }

    public static void dragAndDropOffset(By fromElement, int X, int Y) {
        MouseActions.dragAndDropOffset(fromElement, X, Y);
    }

    public static void clickByActions(By by) {
        MouseActions.clickByActions(by);
    }

    public static void sendTextByAction(By by, String text) {
        MouseActions.sendTextByAction(by, text);
    }

    //ROBOT CLASS
    public static void pressENTER() {
        RobotActions.pressENTER();
    }

    public static void pressESC() {
        RobotActions.pressESC();
    }

    public static void pressF11() {
        RobotActions.pressF11();
    }

    public static void copyFilePathToClipboard(String file) {
        RobotActions.copyFilePathToClipboard(file);
    }

    public static void pasteFromClipboard(int seconds, int delayTime) {
        RobotActions.pasteFromClipboard(seconds, delayTime);
    }

    //VERIFY
    public static boolean verifyEquals(Object actual, Object expected) {
        return VerifyActions.verifyEquals(actual, expected);
    }

    public static void assertEquals(Object actual, Object expected, String message) {
        VerifyActions.assertEquals(actual, expected, message);
    }

    public static boolean verifyContains(String actual, String expected) {
        return VerifyActions.verifyContains(actual, expected);
    }

    public static void assertContains(String actual, String expected, String message) {
        VerifyActions.assertContains(actual, expected, message);
    }

    //FORM
    public static void setTextIfChanged(By by, String expected, String attribute) {
        FormActions.setTextIfChanged(by, expected, attribute);
    }

    public static void selectDropdownIfChanged(By dropdown, String expected, String attribute, By optionDropdown) {
        FormActions.selectDropdownIfChanged(dropdown, expected, attribute, optionDropdown);
    }

    public static void selectSearchableDropdownIfChanged(By dropdown, String expected, String attribute, By inputSearchDropdown, By optionDropdown) {
        FormActions.selectSearchableDropdownIfChanged(dropdown, expected, attribute, inputSearchDropdown, optionDropdown);
    }

    public static void selectMultiDropdownToggleIfChanged(By dropdown, List<String> expected, String attribute, Function<String, By> optionByText) {
        FormActions.selectMultiDropdownToggleIfChanged(dropdown, expected, attribute, optionByText);
    }

    public static void setMultiChipInput(By input, List<String> expected, By chipRemoveIcon, By blurTarget) {
        FormActions.setMultiChipInput(input, expected, chipRemoveIcon, blurTarget);
    }

    public static void setTextInIframeIfChanged(By iframe, By iframeEditor, String expected) {
        FormActions.setTextInIframeIfChanged(iframe, iframeEditor, expected);
    }

    public static void setCheckboxIfChanged(By checkbox, boolean expected, By label) {
        FormActions.setCheckboxIfChanged(checkbox, expected, label);
    }

    public static void uploadMultiFileBySendkeys(By openAttachLocator, Function<Integer, By> addMoreIconLocator,
                                                 Function<Integer, By> fileInputLocator, List<String> files, String baseUploadPath) {
        FormActions.uploadMultiFileBySendkeys(openAttachLocator, addMoreIconLocator, fileInputLocator, files, baseUploadPath);
    }

    public static void setSlider(By inputHidden, By slider, String expected) {
        FormActions.setSlider(inputHidden, slider, expected);
    }

    public static void verifyTableColumnValues(By rowLocator, By cellLocatorTemplate, int columnIndex,
                                               String expectedValue, String columnName, MatchType matchType) {
        FormActions.verifyTableColumnValues(rowLocator, cellLocatorTemplate, columnIndex, expectedValue, columnName, matchType);
    }

    public static void verifyAllRowsContainSearchValue(By rowLocator, String searchValue) {
        FormActions.verifyAllRowsContainSearchValue(rowLocator, searchValue);
    }
}