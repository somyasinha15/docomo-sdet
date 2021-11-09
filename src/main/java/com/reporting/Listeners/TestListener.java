package com.reporting.Listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.core.DriverManager;
import com.reporting.ExtentReports.ExtentManager;
import com.reporting.ExtentReports.ExtentTestManager;
import io.qameta.allure.Attachment;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

@Slf4j
public class TestListener extends DriverManager implements ITestListener {

    private TestStatus testStatus;
    // private ResultSender rs= new ResultSender();

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        iTestContext.setAttribute("WebDriver", this.driverThread);
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        ExtentTestManager.endTest();
        ExtentManager.getReporter().flush();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        this.testStatus = new TestStatus();
        ExtentTestManager.startTest(iTestResult.getMethod().getMethodName(), "");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        // this.sendStatus(iTestResult,"PASS");
        ExtentTestManager.getTest().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        try {
            if (this.driverThread != null) {
                // this.sendStatus(iTestResult,"FAIL");
                saveScreenshotPNG();
                Object testClass = iTestResult.getInstance();
                this.driverThread = ((DriverManager) testClass).getDriver();
                String base64Screenshot = "data:image/png;base64,"
                        + ((TakesScreenshot) driverThread).getScreenshotAs(OutputType.BASE64);
                ExtentTestManager.getTest().log(Status.FAIL, "Test Failed",
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        // this.sendStatus(iTestResult,"SKIP");
        ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshotPNG() {
        return ((TakesScreenshot) this.driverThread).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "0", type = "text/plain")
    public static String saveTextLogs(String message) {
        return message;
    }
    // private void sendStatus(ITestResult iTestResult, String status){
    // this.testStatus.setTestClass(iTestResult.getTestClass().getName());
    // this.testStatus.setDescription(iTestResult.getMethod().getDescription());
    // this.testStatus.setStatus(status);
    // this.testStatus.setExecutionDate(LocalDateTime.now().toString());
    // rs.send(this.testStatus);
    // }
}
