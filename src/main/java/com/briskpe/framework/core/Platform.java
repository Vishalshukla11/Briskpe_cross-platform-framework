package com.briskpe.framework.core;

public enum Platform {
    WEB, ANDROID, IOS, MOBILE_WEB;

    public static Platform fromString(String platform) {
        if (platform == null) {
            throw new IllegalArgumentException("Platform is null. Set it using -Dplatform or config.properties");
        }
        return Platform.valueOf(platform.toUpperCase());
    }
}
