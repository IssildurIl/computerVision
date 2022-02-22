package ru.sfedu.computervision.api.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import ru.sfedu.computervision.api.ConversionService;
import ru.sfedu.computervision.api.ImageService;

public class Task4Test {

    private static final Logger log = LogManager.getLogger(Task2Test.class);
    TaskServiceImpl taskService = new TaskServiceImpl();
    ImageService imageService = new ImageServiceImpl();
    ConversionService conversionService = new ConversionServiceImpl();
    Mat jakalMat = Imgcodecs.imread("D:/computerVision/images/shakali.jpg");
    Mat reclamaMat = Imgcodecs.imread("D:/computerVision/images/orex.jpg");

    @Test
    void task4BaseBlur() {
        String name = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Size size = new Size(3, 3);
        conversionService.saveMatToFile(name, imageService.baseBlur(jakalMat, jakalMat, size));
    }

    @Test
    void task4GaussianBlur() {
        String name = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Size size = new Size(3, 3);
        conversionService.saveMatToFile(name, imageService.gaussianBlur(jakalMat, jakalMat, size, 90, 90, 2));
    }

    @Test
    void task4MedianBlur() {
        String name = new Object() {
        }.getClass().getEnclosingMethod().getName();
        conversionService.saveMatToFile(name, imageService.medianBlur(jakalMat, jakalMat, 11));
    }

    @Test
    void task4BilateralFilter() {
        String name = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Mat mat = new Mat();
        conversionService.saveMatToFile(name, imageService.bilateralFilter(jakalMat, mat, 15, 80, 80, Core.BORDER_DEFAULT));
    }

    @Test
    void task4Blur() {
        taskService.task4("D:/computerVision/images/mops.jpg", 3, 3);
    }

    @Test
    void task5MorfEllipse() {
        String name = new Object() {
        }.getClass().getEnclosingMethod().getName();
        imageService.morphingEllipse(reclamaMat);
    }

    @Test
    void task5MorfRect() {
        String name = new Object() {
        }.getClass().getEnclosingMethod().getName();
        imageService.morphingRect(reclamaMat);
    }
}
