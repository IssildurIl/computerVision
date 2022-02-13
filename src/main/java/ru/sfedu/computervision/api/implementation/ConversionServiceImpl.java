package ru.sfedu.computervision.api.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import ru.sfedu.computervision.api.ConversionService;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class ConversionServiceImpl implements ConversionService {

    private static final Logger log = LogManager.getLogger(ConversionServiceImpl.class);

    public void saveMatToFile(String filePath, Mat img) {
        try {
            final String modFilePath = buildImageName(filePath);
            Imgcodecs.imwrite(modFilePath, img);
        } catch (Exception e) {
            log.debug("only .JPG and .PNG files are supported");
        }
    }

    @Override
    public BufferedImage matToBufferedImage(Mat matImage) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (matImage.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = matImage.channels() * matImage.cols() * matImage.rows();
        byte[] b = new byte[bufferSize];
        matImage.get(0, 0, b);
        BufferedImage bufferedImage = new BufferedImage(matImage.cols(), matImage.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        ImageIcon icon = new ImageIcon(bufferedImage);
        frame(icon);
        return bufferedImage;
    }

    @Override
    public void showImage(String path) {
        ImageIcon icon = new ImageIcon(path);
        frame(icon);
    }

    private String buildImageName(String base) {
        return String.format("%s%d.jpg", base, System.nanoTime());
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
}
