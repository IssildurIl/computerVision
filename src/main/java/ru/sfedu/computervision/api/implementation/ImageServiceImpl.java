package ru.sfedu.computervision.api.implementation;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
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

    public Mat imgToMatByPath(int numberOfChannel, String pathName, String imageName) {
        Mat image = Imgcodecs.imread(pathName + imageName);
        return imgToMat(numberOfChannel, image);
    }
}
