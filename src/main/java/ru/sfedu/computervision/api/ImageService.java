package ru.sfedu.computervision.api;

import org.opencv.core.Mat;
import org.opencv.core.Size;

import java.awt.image.BufferedImage;

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

    Mat warpImage(Mat image, int x, int y);

    Mat baseBlur(Mat src, Mat dst, Size ksize);

    Mat GaussianBlur(Mat src, Mat dst, Size ksize, double sigmaX, double sigmaY, int borderType);

    Mat medianBlur(Mat src, Mat dst, int ksize);

    Mat bilateralFilter(Mat src, Mat dst, int d, double sigmaColor, double sigmaSpace, int borderType);

    void showImageByPath(String path);

    void showImageByBufferedImage(BufferedImage bufferedImage);


}
