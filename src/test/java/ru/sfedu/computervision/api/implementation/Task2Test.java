package ru.sfedu.computervision.api.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

class Task2Test {
    private static final Logger log = LogManager.getLogger(Task2Test.class);
    TaskServiceImpl taskService = new TaskServiceImpl();
    String TEST_IMAGE_PATH = "D:/computerVision/images/";

    @Test
    void task1() {
        String TEST_IMAGE_NAME = "testimage.jpg";
        taskService.task2(60, TEST_IMAGE_PATH, TEST_IMAGE_NAME);
    }

}