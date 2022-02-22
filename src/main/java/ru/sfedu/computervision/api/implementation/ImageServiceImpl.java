package ru.sfedu.computervision.api.implementation;

import org.opencv.core.Point;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;
import ru.sfedu.computervision.api.ConversionService;
import ru.sfedu.computervision.api.ImageService;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.opencv.core.CvType.CV_8UC3;

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

    @Override
    public void toFill(Integer initVal, Mat defaultMat){
        Point seedPoint = new Point(0,0);
        Scalar newVal = new Scalar(255,255,0);
        Scalar loDiff = new Scalar(initVal,initVal,initVal);
        Scalar upDiff = new Scalar(initVal,initVal,initVal);
        Mat mask = new Mat();
        Imgproc.floodFill(defaultMat, mask, seedPoint, newVal, new Rect(), loDiff, upDiff,
                Imgproc.FLOODFILL_FIXED_RANGE + 8);
        convertionService.saveMatToFile("floodFill", defaultMat);
    }

    public void toPyr()  {
        Mat defaultMat = noiseMat(350, 350);

        Mat mask = new Mat();
        Imgproc.pyrDown(defaultMat, mask);
        convertionService.saveMatToFile("mask_pyrDown", mask);

        Imgproc.pyrUp(mask, mask);
        convertionService.saveMatToFile("mask_pyrUp", mask);

        Core.subtract(defaultMat, mask, mask);
        convertionService.saveMatToFile("mask_subtract", mask);
    }

    public void toSquare(Mat defaultMat){
        Mat grayImage = new Mat();
        Imgproc.cvtColor(defaultMat, grayImage, Imgproc.COLOR_BGR2GRAY);
        convertionService.saveMatToFile("toSquare_grey_image", grayImage);

        Mat denoisingImage = new Mat();
        Photo.fastNlMeansDenoising(grayImage, denoisingImage);
        convertionService.saveMatToFile("toSquare_denoising_image", denoisingImage);

        Mat histogramEqualizationImage = new Mat();
        Imgproc.equalizeHist(denoisingImage, histogramEqualizationImage);
        convertionService.saveMatToFile("toSquare_histogramEqualization_image", histogramEqualizationImage);

        Mat morphologicalOpeningImage = new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
        Imgproc.morphologyEx(histogramEqualizationImage, morphologicalOpeningImage, Imgproc.MORPH_RECT, kernel);
        convertionService.saveMatToFile("toSquare_morphologicalOpening_image", morphologicalOpeningImage);

        Mat subtractImage = new Mat();
        Core.subtract(histogramEqualizationImage, morphologicalOpeningImage, subtractImage);
        convertionService.saveMatToFile("toSquare_subtract_image", subtractImage);

        Mat thresholdImage = new Mat();
        double threshold = Imgproc.threshold(subtractImage, thresholdImage, 50, 255, Imgproc.THRESH_OTSU);
        convertionService.saveMatToFile("toSquare_threshold_image", thresholdImage);
        thresholdImage.convertTo(thresholdImage, CvType.CV_16SC1);

        Mat edgeImage = new Mat();
        thresholdImage.convertTo(thresholdImage, CvType.CV_8U);
        Imgproc.Canny(thresholdImage, edgeImage, threshold, threshold * 3, 3, true);
        convertionService.saveMatToFile("toSquare_edge_image", edgeImage);

        Mat dilatedImage = new Mat();
        Imgproc.dilate(thresholdImage, dilatedImage, kernel);
        convertionService.saveMatToFile("toSquare_dilated_image", dilatedImage);

        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(dilatedImage, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        contours.sort(Collections.reverseOrder(Comparator.comparing(Imgproc::contourArea)));
        for (MatOfPoint contour : contours.subList(0, 1)) {
            MatOfPoint2f point2f = new MatOfPoint2f();
            MatOfPoint2f approxContour2f = new MatOfPoint2f();
            MatOfPoint approxContour = new MatOfPoint();
            contour.convertTo(point2f, CvType.CV_32FC2);
            double arcLength = Imgproc.arcLength(point2f, true);
            Imgproc.approxPolyDP(point2f, approxContour2f, 0.03 * arcLength, true);
            approxContour2f.convertTo(approxContour, CvType.CV_32S);
            Rect rect = Imgproc.boundingRect(approxContour);
            double ratio = (double) rect.height / rect.width;
            if (Math.abs(0.3 - ratio) > 0.15) {
                continue;
            }
            Mat submat = defaultMat.submat(rect);
            Imgproc.resize(submat, submat, new Size(400, 400 * ratio));
            convertionService.saveMatToFile("toSquare_submat_image", submat);
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

    protected Mat noiseMat(int height, int width) {
        Mat noiseMat = new Mat(new Size(width, height), CV_8UC3, new Scalar(0, 0, 0));
        Core.randn(noiseMat, 20, 50);
        Core.add(noiseMat, noiseMat, noiseMat);
        return noiseMat;
    }
}
