package ru.sfedu.computervision.api.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

class ImageServiceTest {

    private static final Logger log = LogManager.getLogger(ImageServiceTest.class);
    TaskServiceImpl imageService = new TaskServiceImpl();
    private static final String TEST_IMAGE_PATH = "D:/computerVision/images/";
    private static final String TEST_IMAGE_NAME = "testimage.jpg";

    @Test
    void task1Success() {
        imageService.task2(20, TEST_IMAGE_PATH, TEST_IMAGE_NAME);
    }
}