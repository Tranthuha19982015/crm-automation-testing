package com.hatester.keywords.interaction;

import com.hatester.keywords.wait.WaitActions;
import com.hatester.utils.LogUtils;
import org.testng.Assert;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class RobotActions {
    private static Robot getRobot() {
        try {
            return new Robot();
        } catch (AWTException e) {
            LogUtils.error("Failed to initialize AWT Robot: " + e.getMessage());
            Assert.fail("Failed to initialize AWT Robot.");
            return null;
        }
    }

    public static void pressENTER() {
        try {
            Robot robot = getRobot();
            if (robot != null) {
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                LogUtils.info("Pressed ENTER key");
            }
        } catch (Exception e) {
            Assert.fail("FAILED. Cannot press ENTER: " + e.getMessage());
        }
    }

    public static void pressESC() {
        try {
            Robot robot = getRobot();
            if (robot != null) {
                robot.keyPress(KeyEvent.VK_ESCAPE);
                robot.keyRelease(KeyEvent.VK_ESCAPE);
                LogUtils.info("Pressed ESC key");
            }
        } catch (Exception e) {
            Assert.fail("FAILED. Cannot press ESC: " + e.getMessage());
        }
    }

    public static void pressF11() {
        try {
            Robot robot = getRobot();
            if (robot != null) {
                robot.keyPress(KeyEvent.VK_F11);
                robot.keyRelease(KeyEvent.VK_F11);
                LogUtils.info("Pressed F11 key");
            }
        } catch (Exception e) {
            Assert.fail("FAILED. Cannot press F11: " + e.getMessage());
        }
    }

    public static void copyFilePathToClipboard(String filePath) {
        try {
            StringSelection str = new StringSelection(filePath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
            LogUtils.info("Copied to clipboard: " + filePath);
        } catch (Exception e) {
            LogUtils.error("FAILED to copy to clipboard: " + e.getMessage());
            Assert.fail("FAILED to copy to clipboard: " + e.getMessage());
        }
    }

    public static void pasteFromClipboard(int seconds, int delayTime) {
        Robot robot = getRobot();
        if (robot == null) return;
        try {
            WaitActions.sleep(seconds);
            robot.setAutoDelay(delayTime);

            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);

            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

            LogUtils.info("Pasted from clipboard and pressed ENTER");
        } catch (Exception e) {
            // Đảm bảo thả phím Control nếu chẳng may bị lỗi lúc đang đè phím
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            LogUtils.error("FAILED. Cannot paste from clipboard: " + e.getMessage());
            Assert.fail("FAILED. Cannot paste from clipboard: " + e.getMessage());
        }
    }
}
