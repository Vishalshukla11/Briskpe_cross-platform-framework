package com.briskpe.framework.utils;

import java.io.File;

public class ReportCleaner {

    public static void deleteAllReports() {
        String[] foldersToDelete = {
                "screenshots",
                "allure-results",
                "test-output",
                "reports"
        };

        for (String folderName : foldersToDelete) {
            File folder = new File(System.getProperty("user.dir") + File.separator + folderName);
            if (folder.exists()) {
                deleteFolderRecursively(folder);
                System.out.println("Deleted: " + folder.getAbsolutePath());
            } else {
                System.out.println("Not found: " + folder.getAbsolutePath());
            }
        }
    }

    private static void deleteFolderRecursively(File folder) {
        File[] allContents = folder.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteFolderRecursively(file);
            }
        }
        folder.delete();
    }
}
