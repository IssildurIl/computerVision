package ru.sfedu.computervision.api.implementation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import ru.sfedu.computervision.api.ConversionService;
import ru.sfedu.computervision.api.ImageService;

import java.util.List;

public class Task6Test {
    TaskServiceImpl taskService = new TaskServiceImpl();
    ImageService imageService = new ImageServiceImpl();
    Mat mat = Imgcodecs.imread("D:/computerVision/images/banan.jpg");
    private static final ConversionService convertionService = new ConversionServiceImpl();

    @Test
    void task6convertCanny() {
        imageService.convertCanny(mat);
    }

    @Test
    void task6detect() {
        Mat img = new Mat(1000, 1000, CvType.CV_8UC3, new Scalar(255, 255, 255));
        Imgproc.rectangle(img, new Point(0, 0), new Point(100, 100),
                new Scalar(0,128,0), Core.FILLED);
        Imgproc.rectangle(img, new Point(200, 0), new Point(300, 100),
                new Scalar(0,255,0), Core.FILLED);
        Imgproc.rectangle(img, new Point(100, 150), new Point(500, 500),
                new Scalar(0,128,128), Core.FILLED);
        convertionService.saveMatToFile("rectangleSource", img);
        List<Mat> rectangles = imageService.toSquare(img, 100, 100);
        rectangles.forEach(mat -> convertionService.saveMatToFile( "final_rectangle", mat));
        Assertions.assertEquals(2, rectangles.size());
    }

}
