package ru.sfedu.computervision.api.implementation;

import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import ru.sfedu.computervision.api.ConversionService;
import ru.sfedu.computervision.api.ImageService;

public class Task5Test {
    TaskServiceImpl taskService = new TaskServiceImpl();
    ImageService imageService = new ImageServiceImpl();
    Mat mat = Imgcodecs.imread("D:/computerVision/images/banan.jpg");
    private static final ConversionService convertionService = new ConversionServiceImpl();

    @Test
    void task5toFill() {
        imageService.toFill(3, mat);
    }

    @Test
    void task5toPyrUp() {
        convertionService.saveMatToFile("task5toPyrUp", imageService.pyramidUp(mat,2));
    }

    @Test
    void task5toPyrDown() {
        convertionService.saveMatToFile("task5toPyrDOwn", imageService.pyramidDown(mat,2));
    }

}
