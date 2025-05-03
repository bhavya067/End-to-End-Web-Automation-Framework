package util;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CustomTestListener implements ITestListener {

    private FileWriter fileWriter;

    @Override
    public void onStart(ITestContext context) {
        try {
            fileWriter = new FileWriter("custom-report.txt");
            fileWriter.write("Test suite started: " + context.getName() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        try {
            fileWriter.write("Test started: " + result.getMethod().getDescription() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        try {
            fileWriter.write("Test succeeded: " + result.getMethod().getDescription() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            fileWriter.write("Test failed: " + result.getMethod().getDescription() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        try {
            fileWriter.write("Test skipped: " + result.getMethod().getDescription() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        try {
            fileWriter.write("Test failed but within success percentage: " + result.getMethod().getDescription() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        try {
            if (fileWriter != null) {
                fileWriter.write("Test suite finished: " + context.getName() + "\n");
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String xmlFilePath = System.getProperty("user.dir") + "/target/surefire-reports/TEST-TestSuite.xml";
        
        File reportFile = new File(xmlFilePath);
        if (reportFile.exists()) {
            System.out.println("JUnit XML report has been generated successfully.");
        } else {
            System.out.println("JUnit XML report has NOT been generated.");
        }
        
        // Path to the JUnit XML report
        System.out.println(xmlFilePath);
        File xmlFile = new File(xmlFilePath);
        System.out.println("File exists in the location  : " + xmlFile.exists());
        // Wait for the file to be created and not empty
        waitForFile(xmlFile, 20, 1000); // Wait up to 60 seconds, checking every 1000 ms
        
        // Process the XML report after all tests are finished
        XMLPostProcessor.processJUnitXML(xmlFilePath);

    }       
    
    private void waitForFile(File file, long timeoutSeconds, long pollingIntervalMillis) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < timeoutSeconds * 1000) {
            if (file.exists() && file.length() > 0) {
                return; // File is present and not empty
            }
            try {
                Thread.sleep(pollingIntervalMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.err.println("File not found or empty after waiting: " + file.getAbsolutePath());
    }
}
