package ru.sfedu.computervision.api.implementation;

import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import ru.sfedu.computervision.api.ImageService;

public class Task5Test {
    TaskServiceImpl taskService = new TaskServiceImpl();
    ImageService imageService = new ImageServiceImpl();
    Mat mat = Imgcodecs.imread("D:/computerVision/images/banan.jpg");

    @Test
    void task5toFill() {
        imageService.toFill(3, mat);
    }

    @Test
    void task5toPyr() {
        imageService.toPyr();
    }

    @Test
    void task5toSquare(){
        imageService.toSquare(mat);
    }
}
