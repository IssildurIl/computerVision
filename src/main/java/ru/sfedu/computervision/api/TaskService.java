package ru.sfedu.computervision.api;

import org.opencv.core.Mat;

public interface TaskService {

    Mat task2(int numberOfChannel, String pathName, String imageName);
}
