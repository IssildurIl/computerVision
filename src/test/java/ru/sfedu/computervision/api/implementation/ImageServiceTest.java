package ru.sfedu.computervision.api.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import ru.sfedu.computervision.api.ConversionService;
import ru.sfedu.computervision.api.ImageService;

class ImageServiceTest {

    private static final Logger log = LogManager.getLogger(ImageServiceTest.class);
    TaskServiceImpl taskService = new TaskServiceImpl();
    ImageService imageService = new ImageServiceImpl();
    ConversionService conversionService = new ConversionServiceImpl();
    private static final String TEST_IMAGE_PATH = "D:/computerVision/images/";
    private static final String TASK2_TEST_IMAGE_NAME = "D:/computerVision/images/task2testimage.jpg";
    private static final String TASK2_TEST_IMAGE_NAME1 = "D:/computerVision/images/banan.jpg";


    @Test
    void task1Success() {
        String TEST_IMAGE_NAME = "testimage2.jpg";
        taskService.task2(60, TEST_IMAGE_PATH, TEST_IMAGE_NAME);
    }

    @Test
    void task2SobelSuccess() {
        Mat defaultMat = Imgcodecs.imread(TASK2_TEST_IMAGE_NAME);
        conversionService.saveMatToFile(TEST_IMAGE_PATH, imageService.convertSobel(defaultMat, 1, 1));
    }

    @Test
    void task2LaplaceSuccess() {
        Mat defaultMat = Imgcodecs.imread(TASK2_TEST_IMAGE_NAME);
        conversionService.saveMatToFile(TEST_IMAGE_PATH, imageService.convertLaplace(defaultMat, 10));
    }

    @Test
    void task2MirrorSuccess() {
        Mat defaultMat = Imgcodecs.imread(TASK2_TEST_IMAGE_NAME);
        conversionService.saveMatToFile(TEST_IMAGE_PATH, imageService.mirrorImage(defaultMat, -10));
    }

    @Test
    void task2RepeatSuccess() {
        Mat defaultMat = Imgcodecs.imread(TASK2_TEST_IMAGE_NAME);
        conversionService.saveMatToFile(TEST_IMAGE_PATH, imageService.repeatImage(defaultMat, 20, 20));
    }

    @Test
    void task2UnionSuccess() {
        Mat defaultMatWaterlemon = Imgcodecs.imread(TASK2_TEST_IMAGE_NAME);
        Mat defaultMatBanan = imageService.resizeImage(Imgcodecs.imread(TASK2_TEST_IMAGE_NAME1), 350, 296);
        conversionService.saveMatToFile(TEST_IMAGE_PATH, imageService.unionImage(defaultMatBanan, defaultMatWaterlemon));
    }

    @Test
    void task2ResizeSuccess() {
        conversionService.saveMatToFile(TEST_IMAGE_PATH, imageService.resizeImage(Imgcodecs.imread(TASK2_TEST_IMAGE_NAME1), 350, 296));
    }

    @Test
    void task2GeometryChangeImageSuccess() {
        Mat defaultMatWaterlemon = Imgcodecs.imread(TASK2_TEST_IMAGE_NAME);
        Mat outMat = new Mat();
        conversionService.saveMatToFile(TEST_IMAGE_PATH, imageService.geometryChangeImage(defaultMatWaterlemon,outMat,30,true));
    }

    @Test
    void task2WarpImageSuccess() {
        Mat defaultMatWaterlemon = Imgcodecs.imread(TASK2_TEST_IMAGE_NAME);
        conversionService.saveMatToFile(TEST_IMAGE_PATH, imageService.warpImage(defaultMatWaterlemon,90,90));
    }

}