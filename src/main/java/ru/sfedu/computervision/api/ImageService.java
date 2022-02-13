package ru.sfedu.computervision.api;

import org.opencv.core.Mat;

public interface ImageService {

    Mat imgToMat(int numberOfChannel, Mat image);

    Mat imgToMatByPath(int numberOfChannel, String pathName, String imageName);
}
