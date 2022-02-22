package ru.sfedu.computervision.api.implementation;

import org.opencv.core.Point;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import ru.sfedu.computervision.api.ConversionService;
import ru.sfedu.computervision.api.ImageService;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageServiceImpl implements ImageService {

    private static final ConversionService convertionService = new ConversionServiceImpl();

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
        Point center = new Point(enterImage.width() / 2.0, enterImage.height() / 2.0);
        double scale = 1;
        if (isCut) {
            double size = Math.sqrt(enterImage.width() * enterImage.width() + enterImage.height() * enterImage.height());
            double scaleX = enterImage.width() / size;
            double scaleY = enterImage.height() / size;
            scale = Math.min(scaleX, scaleY);
        }
        Mat rotationMat = Imgproc.getRotationMatrix2D(
                center,
                angle,
                scale
        );
        Imgproc.warpAffine(enterImage, outImage, rotationMat, new Size(enterImage.width(), enterImage.height()),
                Imgproc.INTER_LINEAR, Core.BORDER_TRANSPARENT, new Scalar(0, 0, 0, 255));
        return outImage;
    }

    @Override
    public Mat warpImage(Mat image, int x, int y) {
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

    @Override
    public void showImageByPath(String path) {
        ImageIcon icon = new ImageIcon(path);
        frame(icon);
    }

    @Override
    public void showImageByBufferedImage(BufferedImage bufferedImage) {
        ImageIcon icon = new ImageIcon(bufferedImage);
        frame(icon);
    }

    @Override
    public Mat baseBlur(Mat src, Mat dst, Size ksize) {
        Imgproc.blur(src, dst, ksize, new Point(-1, -1));
        return src;
    }

    @Override
    public Mat gaussianBlur(Mat src, Mat dst, Size ksize, double sigmaX, double sigmaY, int borderType) {
        Imgproc.GaussianBlur(src, dst, ksize, sigmaX, sigmaY, borderType);
        return src;
    }

    @Override
    public Mat medianBlur(Mat src, Mat dst, int ksize) {
        Imgproc.medianBlur(src, dst, ksize);
        return src;
    }

    @Override
    public Mat bilateralFilter(Mat src, Mat dst, int d, double sigmaColor, double sigmaSpace, int borderType) {
        Imgproc.bilateralFilter(src, dst, d, sigmaColor, sigmaSpace, borderType);
        return dst;
    }

    @Override
    public void morphingRect(Mat defaultMat) {
        double[] sizes = {3, 5, 7, 9, 13, 15};
        for (double size : sizes) {
            Mat morphEllipse = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(size, size));

            Mat dst1 = defaultMat.clone();
            Imgproc.dilate(defaultMat, dst1, morphEllipse);
            convertionService.saveMatToFile("mrf_ellipse_rect"+size, dst1);

            Mat dst1_1 = defaultMat.clone();
            Imgproc.morphologyEx(defaultMat, dst1_1, Imgproc.MORPH_GRADIENT, morphEllipse);
            convertionService.saveMatToFile("mrf_gradient_rect"+size, dst1_1);

            Mat dst1_2 = defaultMat.clone();
            Imgproc.morphologyEx(defaultMat, dst1_2, Imgproc.MORPH_BLACKHAT, morphEllipse);
            convertionService.saveMatToFile("mrf_blackhat_rect"+size, dst1_2);
        }
    }

    @Override
    public void morphingEllipse(Mat defaultMat) {
        double[] sizes = {3, 5, 7, 9, 13, 15};
        for (double size : sizes) {
            Mat morphEllipse = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(size, size));

            Mat dst1 = defaultMat.clone();
            Imgproc.dilate(defaultMat, dst1, morphEllipse);
            convertionService.saveMatToFile("mrf_ellipse_el_"+size, dst1);

            Mat dst1_1 = defaultMat.clone();
            Imgproc.morphologyEx(defaultMat, dst1_1, Imgproc.MORPH_GRADIENT, morphEllipse);
            convertionService.saveMatToFile("mrf_gradient_el"+size, dst1_1);

            Mat dst1_2 = defaultMat.clone();
            Imgproc.morphologyEx(defaultMat, dst1_2, Imgproc.MORPH_BLACKHAT, morphEllipse);
            convertionService.saveMatToFile("mrf_blackhat_el"+size, dst1_2);
        }
    }

    private void frame(ImageIcon icon) {
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(frame.getToolkit().getScreenSize().width - 300, frame.getToolkit().getScreenSize().height - 350);
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Mat imgToMatByPath(int numberOfChannel, String pathName, String imageName) {
        Mat image = Imgcodecs.imread(pathName + imageName);
        return imgToMat(numberOfChannel, image);
    }

}
