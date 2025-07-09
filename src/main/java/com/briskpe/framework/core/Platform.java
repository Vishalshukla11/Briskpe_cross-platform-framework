package com.briskpe.framework.core;

public enum Platform {
    WEB, ANDROID, IOS;

    public static Platform fromString(String platform) {
        if (platform == null) {
            throw new IllegalArgumentException("Platform is null. Please set it using -Dplatform=WEB or in config.properties");
        }
        return Platform.valueOf(platform.toUpperCase());
    }
}
