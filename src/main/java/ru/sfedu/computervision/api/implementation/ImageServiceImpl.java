package ru.sfedu.computervision.api.implementation;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import ru.sfedu.computervision.api.ImageService;

public class ImageServiceImpl implements ImageService {

    @Override
    public Mat imgToMat(int numberOfChannel, Mat image) {
        int totalBytes = (int) (image.total() * image.elemSize());
        byte[] buffer = new byte[totalBytes];
        image.get(0, 0, buffer);
        for (int i = 0; i < totalBytes; i++) {
            if (i % numberOfChannel == 0) {
                buffer[i] = 0;
            }
        }
        image.put(0, 0, buffer);
        return image;
    }

    @Override
    public Mat convertSobel(Mat image, int dx, int dy) {
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
        Mat dstSobel = new Mat();
        Imgproc.Sobel(grayImage, dstSobel, CvType.CV_32F, dx, dy);
        Core.convertScaleAbs(dstSobel, dstSobel);
        return dstSobel;
    }

    @Override
    public Mat convertLaplace(Mat image, int kSize) {
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
        Mat dstLaplace = new Mat();
        Imgproc.Laplacian(grayImage, dstLaplace, kSize);
        Core.convertScaleAbs(dstLaplace, dstLaplace);
        return dstLaplace;
    }

    @Override
    public Mat mirrorImage(Mat image, int flipCode) {
        Mat dstV = new Mat();
        Core.flip(image, dstV, flipCode);
        return dstV;
    }

    @Override
    public Mat unionImage(Mat mat, Mat dst) {
        Core.addWeighted(mat, 0.5, dst, 0.5, 0.0, mat);
        return mat;
    }

    @Override
    public Mat repeatImage(Mat image, int ny, int nx) {
        Mat rotationImage = new Mat();
        Core.repeat(image, ny, nx, rotationImage);
        return rotationImage;
    }

    @Override
    public Mat resizeImage(Mat image, int width, int height) {
        Mat resizeImage = new Mat();
        Imgproc.resize(image, resizeImage, new Size(width, height));
        return resizeImage;
    }

    @Override
    public Mat geometryChangeImage(Mat enterImage, Mat outImage, int angle, boolean isCut) {
        Point center = new Point(enterImage.width() >> 1, enterImage.height() >> 1);
        Mat rotationMat = Imgproc.getRotationMatrix2D(center, angle, 1);
        Imgproc.warpAffine(enterImage, outImage, rotationMat,new Size(enterImage.width(), enterImage.height()),
                Imgproc.INTER_LINEAR, Core.BORDER_TRANSPARENT,new Scalar(0,0,0,255));
        return outImage;
    }

    @Override
    public Mat warpImage(Mat image,int x,int y) {
        MatOfPoint2f src = new MatOfPoint2f(
                new Point(0, 0),
                new Point(image.cols(), 0),
                new Point(0, image.rows()),
                new Point(image.cols(), image.rows())
        );
        MatOfPoint2f target = new MatOfPoint2f(
                new Point(x, y),
                new Point(image.cols() - x, 0),
                new Point(0, image.rows() - y),
                new Point(image.cols() - x, image.rows() - y)
        );
        Mat matWarp = Imgproc.getPerspectiveTransform(src, target);
        Mat res = new Mat();
        Imgproc.warpPerspective(image, res, matWarp, image.size());
        return res;
    }

    public Mat imgToMatByPath(int numberOfChannel, String pathName, String imageName) {
        Mat image = Imgcodecs.imread(pathName + imageName);
        return imgToMat(numberOfChannel, image);
    }



}
