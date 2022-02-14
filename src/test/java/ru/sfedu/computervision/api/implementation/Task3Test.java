package ru.sfedu.computervision.api.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import ru.sfedu.computervision.api.ConversionService;
import ru.sfedu.computervision.api.ImageService;

public class Task3Test {
    private static final Logger log = LogManager.getLogger(Task2Test.class);
    ImageService imageService = new ImageServiceImpl();
    ConversionService conversionService = new ConversionServiceImpl();
    Mat mopsMat = Imgcodecs.imread("D:/computerVision/images/mops.jpg");
    Mat monkeyMat = Imgcodecs.imread("D:/computerVision/images/moneky.jpg");
    Mat bananaMat = Imgcodecs.imread("D:/computerVision/images/banan.jpg");

    @Test
    void task2Sobel() {
        String name = new Object() {
        }.getClass().getEnclosingMethod().getName();
        conversionService.saveMatToFile(name, imageService.convertSobel(mopsMat, 1, 1));
    }

    @Test
    void task2Laplace() {
        String name = new Object() {
        }.getClass().getEnclosingMethod().getName();
        conversionService.saveMatToFile(name, imageService.convertLaplace(mopsMat, 5));
    }

    @Test
    void task2Mirror() {
        String name = new Object() {
        }.getClass().getEnclosingMethod().getName();
        conversionService.saveMatToFile(name, imageService.mirrorImage(monkeyMat, 0));
    }

    @Test
    void task2Repeat() {
        String name = new Object() {
        }.getClass().getEnclosingMethod().getName();
        conversionService.saveMatToFile(name, imageService.repeatImage(mopsMat, 3, 3));
    }

    @Test
    void task2Union() {
        String name = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Mat defaultMatBanan = imageService.resizeImage(bananaMat, 300, 202);
        conversionService.saveMatToFile(name, imageService.unionImage(defaultMatBanan, mopsMat));
    }

    @Test
    void task2Resize() {
        String name = new Object() {
        }.getClass().getEnclosingMethod().getName();
        conversionService.saveMatToFile(name, imageService.resizeImage(bananaMat, 350, 296));
    }

    @Test
    void task2GeometryChangeImage() {
        String name = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Mat outMat = new Mat();
        conversionService.saveMatToFile(name, imageService.geometryChangeImage(mopsMat, outMat, 90, true));
    }

    @Test
    void task2WarpImage() {
        String name = new Object() {
        }.getClass().getEnclosingMethod().getName();
        conversionService.saveMatToFile(name, imageService.warpImage(mopsMat, 90, 90));
    }
}
