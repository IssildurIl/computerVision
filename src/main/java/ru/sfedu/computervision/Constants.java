package ru.sfedu.computervision;

public class Constants {
    public static final String CONFIG_PATH = "config.path";

    public enum OSType {
        MACOS,
        WINDOWS,
        LINUX,
        OTHER;

        public static OSType getOperatingSystemType(String type){
            if (type.contains("mac") || type.contains("darwin")) {
                return OSType.MACOS;
            } else if (type.contains("win")) {
                return OSType.WINDOWS;
            } else if (type.contains("nux")) {
                return OSType.LINUX;
            } else {
                return OSType.OTHER;
            }
        }
    }

    public static final String PATH_TO_NATIVE_LIB_LINUX = "lin_path";
    public static final String PATH_TO_NATIVE_LIB_WIN = "win_path";
    public static final String IMAGE_PATH = "D:/computerVision/src/main/resources/images";
}
