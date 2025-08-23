package com.briskpe.framework.core;

/**
 * Enum representing supported platforms for cross-platform testing.
 * <p>
 * This enum is used to define the supported platforms (WEB, ANDROID, IOS, MOBILE_WEB)
 * on which the automation framework can run tests. It ensures that platform names are
 * standardized and helps avoid invalid or inconsistent platform inputs.
 */
public enum Platform {
    WEB, ANDROID, IOS, MOBILE_WEB;

    /**
     * Converts a given string to the corresponding Platform enum constant.
     * <p>
     * This method ensures:
     * - The input string matches one of the defined platform names.
     * - Validation is case-insensitive, so "web" or "Web" will map to Platform.WEB.
     * - If input is null, empty, or invalid, a clear exception is thrown to prevent runtime issues.
     *
     * @param platform The platform name as a String (e.g., "WEB", "ANDROID", "IOS", "MOBILE_WEB").
     * @return The matching Platform enum constant.
     * @throws IllegalArgumentException If the input string is null, empty, or not a valid platform name.
     *
     * Example usage:
     * <pre>
     * Platform currentPlatform = Platform.fromString("web");
     * // Returns Platform.WEB
     * </pre>
     */
    public static Platform fromString(String platform) {
        if (platform == null || platform.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Platform is null or empty. Please set it using -Dplatform or in config.properties. " +
                            "Supported platforms: WEB, ANDROID, IOS, MOBILE_WEB."
            );
        }
        try {
            return Platform.valueOf(platform.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid platform: '" + platform + "'. Supported platforms are: WEB, ANDROID, IOS, MOBILE_WEB.", e
            );
        }
    }
}
