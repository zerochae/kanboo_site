package com.kanboo.www.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

@Service
public class FileSystemUtil {

    private final Logger logger = LoggerFactory.getLogger(FileSystemUtil.class);

    public boolean createFileOrDir(Map<String, String> map) {
        String rootPath = "/Users/andaegeun/Desktop/kanboo" + map.get("rootPath") + "/";
        String projectName = map.get("project") + "/";
        String projectPath = map.get("path") + "/";
        String target = map.get("name");

        if(map.get("type").equals("dir")) {
            return addDir(rootPath + projectName + projectPath + target);
        }
        return addFile(rootPath + projectName, projectPath, target);
    }

    private boolean addDir(String path) {
        return new File(path).mkdirs();
    }

    private boolean addFile(String rootPath, String parentPath, String fileName) {
        if(!fileName.contains(".java")) {
            fileName += ".java";
        }

        int data = 0;
        File file = new File(rootPath + parentPath + fileName);
        try(FileOutputStream fos = new FileOutputStream(file)) {
            while((data = System.in.read()) != -1) {
                fos.write(data);
            }
        }catch (Exception e) {
            logger.info(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean delDir(String path) {
        boolean result = false;
        File folder = new File(path);

        if(folder.exists()) {
            File[] fileList = folder.listFiles();

            for(File file : fileList) {
                if(file.isFile()) {
                    file.delete();
                } else {
                    delDir(file.getPath());
                }
                file.delete();
            }
            result = folder.delete();
        }
        return result;
    }

    public boolean delFile(Map<String, String> map) {
        File file = new File(map.get("rootPath") + map.get("delName") + ".java");

        if(file.exists() && file.delete()) {
            return true;
        }
        return false;
    }
}
