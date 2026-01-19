package com.hatester.helpers;

import com.hatester.drivers.DriverManager;
import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static com.hatester.constants.FrameworkConstant.*;
import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class CaptureHelper extends ScreenRecorder {
    public static ScreenRecorder screenRecorder;
    public String name;

    public CaptureHelper(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat,
                         Format mouseFormat, Format audioFormat, File movieFolder, String name) throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        this.name = name;
    }

    @Override
    protected File createMovieFile(Format fileFormat) throws IOException {
        if (!movieFolder.exists()) {
            movieFolder.mkdirs();
        } else if (!movieFolder.isDirectory()) {
            throw new IOException("\"" + movieFolder + "\" is not a directory.");
        }
        return new File(movieFolder, name + "_" + SystemHelper.getDateTimeNow() + "."
                + Registry.getInstance().getExtension(fileFormat));  //Tạo File mới
    }

    public static void startRecord(String videoRecordName) {
        //Tạo thư mục để lưu file video vào
        File file = new File(SystemHelper.getCurrentDir() + VIDEO_RECORD_PATH);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        Rectangle captureSize = new Rectangle(0, 0, width, height);

        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        try {
            screenRecorder = new CaptureHelper(gc, captureSize, new Format(MediaTypeKey, MediaType.FILE,
                    MimeTypeKey, MIME_AVI), new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,
                    ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                    DepthKey, 24, FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                    null, file, videoRecordName);

            screenRecorder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public static void stopRecord() {
        try {
            if (screenRecorder != null) {
                screenRecorder.stop();
                screenRecorder = null;
                System.out.println("Video recording stopped successfully.");
            } else {
                System.out.println("screenRecorder is null — skipping stopRecord.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void takeScreenshot(String screenshotName) {
        TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);

        File theDir = new File(SCREENSHOT_PATH);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }

        try {
            FileHandler.copy(source, new File(SCREENSHOT_PATH + "/" + screenshotName + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
