package ru.sfedu.computervision.api;

import org.opencv.core.Mat;

import java.awt.image.BufferedImage;

public interface ConversionService {

    void saveMatToFile(String filePath, Mat img);

    BufferedImage matToBufferedImage(Mat image);

    void showImage(String path);
}
