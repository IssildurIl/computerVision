package ru.sfedu.computervision.api;

import org.opencv.core.Mat;

public interface ImageService {

    Mat imgToMat(int numberOfChannel, Mat image);

    Mat imgToMatByPath(int numberOfChannel, String pathName, String imageName);

    Mat convertSobel(Mat image, int dx, int dy);

    Mat convertLaplace(Mat image, int kSize);

    Mat mirrorImage(Mat image, int flipCode);

    Mat unionImage(Mat mat, Mat dst);

    Mat repeatImage(Mat image, int ny, int nx);

    Mat resizeImage(Mat image, int width, int height);

    Mat geometryChangeImage(Mat enterImage, Mat outImage, int angle, boolean isCut);

    Mat warpImage(Mat image,int x,int y);
}
