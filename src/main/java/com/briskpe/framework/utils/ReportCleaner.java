package com.briskpe.framework.utils;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class to clean up report directories by deleting specified folders recursively.
 */
public class ReportCleaner {

    private static final Logger logger = Logger.getLogger(ReportCleaner.class.getName());

    /**
     * Deletes specified report folders recursively from the project root directory.
     */
    public static void deleteAllReports() {
        String[] foldersToDelete = {
                "screenshots",
                "allure-results",
                "test-output",
                "reports"
        };

        String projectRoot = System.getProperty("user.dir");
        for (String folderName : foldersToDelete) {
            File folder = new File(projectRoot + File.separator + folderName);
            if (folder.exists()) {
                try {
                    deleteFolderRecursively(folder);
                    if (!folder.exists()) {
                        logger.info("Deleted folder: " + folder.getAbsolutePath());
                    } else {
                        logger.warning("Failed to completely delete folder: " + folder.getAbsolutePath());
                    }
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Error deleting folder: " + folder.getAbsolutePath(), e);
                }
            } else {
                logger.info("Folder not found (skipped): " + folder.getAbsolutePath());
            }
        }
    }

    /**
     * Recursively deletes a folder and its contents.
     *
     * @param folder File object pointing to file or directory
     * @return true if deletion successful, false otherwise
     */
    private static boolean deleteFolderRecursively(File folder) {
        if (folder.isDirectory()) {
            File[] allContents = folder.listFiles();
            if (allContents != null) {
                for (File file : allContents) {
                    if (!deleteFolderRecursively(file)) {
                        logger.warning("Failed to delete: " + file.getAbsolutePath());
                        return false;
                    }
                }
            }
        }
        boolean deleted = folder.delete();
        if (!deleted) {
            logger.warning("Failed to delete: " + folder.getAbsolutePath());
        }
        return deleted;
    }
}
