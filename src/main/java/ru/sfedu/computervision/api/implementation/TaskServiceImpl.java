package ru.sfedu.computervision.api.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import ru.sfedu.computervision.Constants;
import ru.sfedu.computervision.Constants.OSType;
import ru.sfedu.computervision.api.ConversionService;
import ru.sfedu.computervision.api.ImageService;
import ru.sfedu.computervision.api.TaskService;
import ru.sfedu.computervision.utils.ConfigurationUtil;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Locale;

public class TaskServiceImpl implements TaskService {

    private static final Logger log = LogManager.getLogger(TaskServiceImpl.class);
    private final String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
    private final ImageService imageService = new ImageServiceImpl();
    private final ConversionService conversionService = new ConversionServiceImpl();


    public TaskServiceImpl() {
        try {
            log.info("Checking OS.....");
            switch (OSType.getOperatingSystemType(OS)) {
                case LINUX -> loadProp(Constants.PATH_TO_NATIVE_LIB_LINUX);
                case WINDOWS -> loadProp(Constants.PATH_TO_NATIVE_LIB_WIN);
                case MACOS -> throw new Exception("Mac OS does not support!!!!!!!!");
                case OTHER -> throw new Exception("Current OS does not support!!!!!");
            }
        } catch (Exception e) {
            log.debug(e);
        }
    }

    private void loadProp(String path) throws IOException {
        String pathLin = ConfigurationUtil.getConfigurationEntry(path);
        System.load(Paths.get(pathLin).toAbsolutePath().toString());
        log.debug("Properties are loaded \n" + "OS Version: " + OS + "\n" + "Open CV version - " + Core.getVersionString());
    }

    @Override
    public Mat task2(int numberOfChannel, String pathName, String imageName) {
        imageService.showImageByPath(pathName + imageName);
        Mat mat = imageService.imgToMatByPath(numberOfChannel, pathName, imageName);
        imageService.showImageByBufferedImage(conversionService.matToBufferedImage(mat));
        conversionService.saveMatToFile(pathName, mat);
        return mat;
    }
}