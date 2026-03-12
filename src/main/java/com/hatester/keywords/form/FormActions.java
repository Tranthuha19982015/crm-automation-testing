package com.hatester.keywords.form;

import com.hatester.enums.MatchType;
import com.hatester.keywords.context.ContextActions;
import com.hatester.keywords.element.ClickActions;
import com.hatester.keywords.element.ElementActions;
import com.hatester.keywords.element.InputActions;
import com.hatester.keywords.interaction.MouseActions;
import com.hatester.keywords.scroll.ScrollActions;
import com.hatester.keywords.wait.WaitActions;
import com.hatester.reports.AllureManager;
import com.hatester.utils.DataUtil;
import com.hatester.utils.LogUtils;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FormActions {
    public static void setTextIfChanged(By by, String expected, String attribute) {
        // Excel không có cột thì bỏ qua trường này
        if (expected == null) {
            return;
        }

        String actual = ElementActions.getElementAttribute(by, attribute);

        // Excel có cột nhưng ô để trống → clear
        if (expected.isEmpty()) {
            if (!actual.isEmpty()) {
                InputActions.clearElementText(by);
            }
            return;
        }

        if (expected.equals(actual)) {
            return;
        }

        InputActions.clearElementText(by);
        InputActions.setText(by, expected);
    }

    public static void selectDropdownIfChanged(By dropdown, String expected, String attribute, By optionDropdown) {
        if (StringUtils.isBlank(expected)) {
            return;
        }

        String actual = ElementActions.getElementAttribute(dropdown, attribute);
        if (expected.equals(actual)) {
            return;
        }

        ClickActions.clickElement(dropdown);
        ClickActions.clickElement(optionDropdown);
    }

    public static void selectSearchableDropdownIfChanged(By dropdown, String expected, String attribute, By inputSearchDropdown, By optionDropdown) {
        if (StringUtils.isBlank(expected)) {
            return;
        }

        String actual = ElementActions.getElementAttribute(dropdown, attribute);
        if (expected.equals(actual)) {
            return;
        }

        ClickActions.clickElement(dropdown);
        InputActions.setText(inputSearchDropdown, expected);
        WaitActions.sleep(0.3);
        MouseActions.sendTextByAction(inputSearchDropdown, " ");
        WaitActions.waitForElementToBeClickable(optionDropdown, 20);
        ClickActions.clickElement(optionDropdown);
    }

    //Function<String, By>: là Java Function, Nhận vào 1 giá trị kiểu String → Trả ra 1 giá trị kiểu By
    public static void selectMultiDropdownToggleIfChanged(By dropdown, List<String> expected, String attribute, Function<String, By> optionByText) {
        //Không truyền dữ liệu → không làm gì
        if (expected == null || expected.isEmpty()) {
            return;
        }

        //Lấy giá trị đang selected trên UI
        List<String> current;

        String raw = ElementActions.getElementAttribute(dropdown, attribute);

        if (raw == null || raw.isBlank() || raw.equalsIgnoreCase("Nothing selected")) {
            current = Collections.emptyList();
        } else {
            current = DataUtil.parseList(raw);
        }

        //chuyển list thành set để bỏ qua thứ tự khi duyệt for
        Set<String> expectedSet = expected.stream().map(String::trim).collect(Collectors.toSet());
        Set<String> currentSet = current.stream().map(String::trim).collect(Collectors.toSet());

        //Mở dropdown
        ClickActions.clickElement(dropdown);

        //Xóa giá trị trên UI nếu không có trong excel
        for (String value : currentSet) {
            if (!expectedSet.contains(value)) {
                ClickActions.clickElement(optionByText.apply(value));
            }
        }

        //Add giá trị thiếu (giữ thứ tự Excel)
        for (String value : expected) {
            if (!currentSet.contains(value)) {
                ClickActions.clickElement(optionByText.apply(value));
            }
        }

        //Đóng dropdown
        ClickActions.clickElement(dropdown);
    }

    //Multi-value input dạng chip / token / pill  (VD: tags, labels,...)
    public static void setMultiChipInput(By input, List<String> expected, By chipRemoveIcon, By blurTarget) {
        // 1. Excel không có cột cần thao tác → bỏ qua
        if (expected == null) {
            return;
        }

        // 2. Xoá toàn bộ thẻ hiện tại trên UI (nếu có)
        List<WebElement> listRemoveIcon = ElementActions.getWebElements(chipRemoveIcon);
        for (int i = listRemoveIcon.size() - 1; i >= 0; i--) {
            listRemoveIcon.get(0).click();
            listRemoveIcon = ElementActions.getWebElements(chipRemoveIcon);
        }

        // 3. Excel có cột cần thao tác nhưng ô để trống → chỉ xoá, không add
        if (expected.isEmpty()) {
            return;
        }

        // 4. Add lại thẻ theo Excel
        for (String member : expected) {
            InputActions.setTextAndKey(input, member, Keys.ENTER);
        }
        ClickActions.clickElement(blurTarget);
    }

    public static void setTextInIframeIfChanged(By iframe, By iframeEditor, String expected) {
        if (expected == null) {
            return;
        }

        ContextActions.switchToFrame(iframe);

        try {
            String actual = ElementActions.getElementText(iframeEditor);

            if (expected.isEmpty()) {
                if (!actual.isEmpty()) {
                    InputActions.clearElementText(iframeEditor);
                }
                return;
            }

            if (expected.equals(actual)) {
                return;
            }

            InputActions.clearElementText(iframeEditor);
            InputActions.setText(iframeEditor, expected);
        } finally {
            ContextActions.switchToDefaultContent();
        }
    }

    public static void setCheckboxIfChanged(By checkbox, boolean expected, By label) {
        boolean actual = ElementActions.isCheckboxSelected(checkbox);
        if (actual == expected) {
            return;
        }
        ClickActions.clickElement(label);
    }

    public static void uploadMultiFileBySendkeys(By openAttachLocator, Function<Integer, By> addMoreIconLocator,
                                                 Function<Integer, By> fileInputLocator, List<String> files, String baseUploadPath) {
        if (files == null || files.isEmpty()) {
            return;
        }

        if (openAttachLocator != null) {
            ClickActions.clickElement(openAttachLocator);
        }

        // Click icon Add more files nếu có nhiều hơn 1 file
        if (files.size() > 1 && addMoreIconLocator != null) {
            for (int i = 0; i < files.size() - 1; i++) {
                ClickActions.clickElement(addMoreIconLocator.apply(0));
            }
        }

        // Set file path
        for (int i = 0; i < files.size(); i++) {
            String fullPath = baseUploadPath + files.get(i);
            InputActions.setText(fileInputLocator.apply(i), fullPath);
        }
    }

    public static void setSlider(By inputHidden, By slider, String expected) {
        if (StringUtils.isBlank(expected)) {
            return;
        }

        try {
            int percent = Integer.parseInt(expected.trim());
            ElementActions.setSliderValue(inputHidden, slider, percent);
        } catch (NumberFormatException e) {
            LogUtils.error("Invalid slider value: " + expected);
            throw e;
        }
    }

    //table
    @Step("Verify column [{4}] with expected value '{3}' using condition '{5}'")
    public static void verifyTableColumnValues(By rowLocator, By cellLocatorTemplate, int columnIndex,
                                               String expectedValue, String columnName, MatchType matchType) {
        LogUtils.info("Verify column [" + columnName + "] with expected value '" + expectedValue + "' using condition '" + matchType + "'");

        //Xác định số dòng của table sau khi search
        List<WebElement> rows = ElementActions.getWebElements(rowLocator);
        int totalRows = rows.size();

        LogUtils.info("Total rows found in table: " + totalRows);
        Assert.assertTrue(totalRows > 0, "Table has no data");

        //Duyệt từng dòng
        for (int i = 1; i <= totalRows; i++) {
            String rawXpath = cellLocatorTemplate.toString().replace("By.xpath: ", "");
            By cellLocator = By.xpath(String.format(rawXpath, i, columnIndex));

            WebElement cell = ElementActions.getWebElement(cellLocator);
            ScrollActions.scrollToElementAtBottom(cell);

            String actualValue = ElementActions.getElementText(cellLocator).trim();

            LogUtils.info("Row " + i + " | Expected: " + expectedValue + " | Actual: " + actualValue);
            AllureManager.saveTextLog("Row " + i + " | Expected: " + expectedValue + " | Actual: " + actualValue);

            //Switch expression (Java 14+) /// Dấu -> thay thế hoàn toàn break
            //Trả về một giá trị và gán trực tiếp vào result
            boolean result = switch (matchType) {
                case CONTAINS -> actualValue.toUpperCase().contains(expectedValue.toUpperCase());
                case EQUALS -> actualValue.equalsIgnoreCase(expectedValue);
            };

            Assert.assertTrue(result, "Row " + i + " | Column [" + columnName + "] | Expected [" + expectedValue + "] not matched.");
        }
    }

    @Step("Verify all rows contain search value '{1}'")
    public static void verifyAllRowsContainSearchValue(By rowLocator, String searchValue) {
        List<WebElement> rows = ElementActions.getWebElements(rowLocator);
        int totalRows = rows.size();

        LogUtils.info("Total rows found: " + totalRows);
        Assert.assertTrue(totalRows > 0, "Table has no data after search");

        for (int i = 0; i < totalRows; i++) {
            WebElement row = rows.get(i);
            ScrollActions.scrollToElementAtBottom(row);

            String rowText = row.getText().trim();

            LogUtils.info("Row " + (i + 1) + " | Row Text: " + rowText);
            AllureManager.saveTextLog("Row " + (i + 1) + " | Row Text: " + rowText);

            boolean result = rowText.toUpperCase().contains(searchValue.toUpperCase());
            Assert.assertTrue(result, "Row " + (i + 1) + " does not contain search value: " + searchValue);
        }
    }
}
