package com.kanboo.www.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class SaveCompileFile {
    private static final String rootPath = "/Users/andaegeun/Desktop/kanboo/compileFiles/";
    private final Logger logger = LoggerFactory.getLogger(SaveCompileFile.class);

    public boolean saveFile(Map<String, String> map) {

        String path = map.get("filePath");
        String fileName = map.get("fileName") + map.get("fileExtension");
        String content = map.get("fileDetail");
        String projectName = map.get("project") + "/";

        File file = new File(rootPath + projectName + path, fileName);

        try(OutputStream os = new FileOutputStream(file)) {
            byte[] c = content.getBytes();

            os.write(c);
            os.flush();

        } catch (Exception e) {
            logger.info(e.getMessage());
            return false;
        }
        return true;
    }
}
