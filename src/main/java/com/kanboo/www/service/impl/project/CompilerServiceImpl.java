package com.kanboo.www.service.impl.project;

import com.kanboo.www.domain.entity.project.Compiler;
import com.kanboo.www.domain.entity.project.CompilerFile;
import com.kanboo.www.domain.entity.project.Project;
import com.kanboo.www.domain.repository.project.CompilerContentRepository;
import com.kanboo.www.domain.repository.project.CompilerRepository;
import com.kanboo.www.dto.project.CompilerDTO;
import com.kanboo.www.dto.project.ProjectDTO;
import com.kanboo.www.service.inter.project.CompilerContentService;
import com.kanboo.www.service.inter.project.CompilerService;
import com.kanboo.www.util.CompilerUtil;
import com.kanboo.www.util.SaveCompileFile;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CompilerServiceImpl implements CompilerService {

    private final SaveCompileFile saveCompileFile;
    private final CompilerUtil compilerUtil;
    private static final String rootPath = "./compileFiles/";
    private final CompilerRepository compilerRepository;
    private final CompilerContentRepository compilerContentRepository;
    private final Logger logger = LoggerFactory.getLogger(CompilerServiceImpl.class);

    @Override
    public Map<String, String> runDemo(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("filePath", "/src/");
        map.put("fileName", "Main");
        map.put("fileExtension", ".java");
        map.put("fileDetail", code);
        map.put("project", "demo/project");
        boolean saveResult = saveCompileFile.saveFile(map);

        if(!saveResult) {
            return null;
        }

        Map<String, String> pathList = new HashMap<>();
        pathList.put("classPath", rootPath + "demo/project/class");
        pathList.put("srcPath", rootPath + "demo/project/src");
        pathList.put("jarNameAndPath", rootPath + "demo/project/lib/result.jar");
        pathList.put("manifestNameAndPath", rootPath + "demo/project/META-INF/Manifest.txt");
        pathList.put("topPath", rootPath + "demo/project");

        Map<String, String[]> cmdList = compilerUtil.createCmd(pathList);

        Map<String, String> compileCmd = compilerUtil.terminalCompile(cmdList.get("compileCmd"));
        if(compileCmd.get("isSuccess").equals("false")) {
            compileCmd.put("detail", compileCmd.get("detail").substring(32, compileCmd.get("detail").length()));
            return compileCmd;
        }

        compilerUtil.terminalCompile(cmdList.get("createManifestCmd"));

        compilerUtil.terminalCompile(cmdList.get("setJarCmd"));

        compilerUtil.terminalCompile(cmdList.get("setManifestCmd"));

        return compilerUtil.terminalCompile(cmdList.get("runJarCmd"));
    }

    @Override
    public List<CompilerDTO> getList(ProjectDTO projectDTO) {
        List<Compiler> projectEntity = compilerRepository.findByProjectPrjctIdx(projectDTO.getPrjctIdx());
        if(projectEntity.isEmpty()) {
            return null;
        }
        List<CompilerDTO> projectList = new ArrayList<>();
        projectEntity.forEach(item -> {
            projectList.add(CompilerDTO.builder()
                    .comIdx(item.getComIdx())
                    .comNm(item.getComNm())
                    .comSe(item.getComSe())
                    .parentComIdx(item.getParentComIdx())
                    .build());
        });
        return projectList;
    }

    @Override
    public Map<String, String> runMemberProject(ProjectDTO projectDTO) {

        List<CompilerFile> compileFileList = compilerContentRepository.findAllByCompiler_Project_PrjctIdx(projectDTO.getPrjctIdx());
        File root = new File("");
        String absolutePath = root.getAbsolutePath();

        Long prjctIdx = compileFileList.get(0).getCompiler().getProject().getPrjctIdx();
        String prjctNm = compileFileList.get(0).getCompiler().getProject().getPrjctNm();

        String p = absolutePath + "/compileFiles/member/" + prjctIdx + prjctNm + "/project";
        showFilesInDir(p, compileFileList, p);
        Map<String, String> resultMap = runCompile(p);
        return resultMap;
    }

    @Override
    public List<CompilerDTO> getHtmlList(ProjectDTO projectDTO) {
        List<Compiler> compilerList = compilerRepository.findByProjectPrjctIdxAndComSe(projectDTO.getPrjctIdx(), "h");
        List<CompilerDTO> result = new ArrayList<>();
        compilerList.forEach(item -> {
            result.add(item.entityToDto());
        });

        return result;
    }

    private Map<String, String> runCompile(String path) {
        Map<String, String> pathList = new HashMap<>();
        pathList.put("classPath", path + "/class");
        pathList.put("srcPath", path + "/src");
        pathList.put("jarNameAndPath", path + "/lib/result.jar");
        pathList.put("manifestNameAndPath", path + "/META-INF/Manifest.txt");
        pathList.put("topPath", path);

        Map<String, String[]> cmdList = compilerUtil.createCmd(pathList);

        Map<String, String> compileCmd = compilerUtil.terminalCompile(cmdList.get("compileCmd"));
        if(compileCmd.get("isSuccess").equals("false")) {
            compileCmd.put("detail", compileCmd.get("detail").substring(compileCmd.get("detail").indexOf("/src"), compileCmd.get("detail").length()));
            return compileCmd;
        }

        compilerUtil.terminalCompile(cmdList.get("createManifestCmd"));

        compilerUtil.terminalCompile(cmdList.get("setJarCmd"));

        compilerUtil.terminalCompile(cmdList.get("setManifestCmd"));

        return compilerUtil.terminalCompile(cmdList.get("runJarCmd"));
    }

    private void showFilesInDir(String rootPath, List<CompilerFile> compileFileList, String changePath) {
        File dir = new File(changePath);
        File files[] = dir.listFiles();

        for(File f : files) {
            if(f.isDirectory()) {
                showFilesInDir(rootPath, compileFileList, f.getPath());
            } else {
                // file에 정보 작성
                if(f.getName().contains(".java")) {
                    int src = f.getPath().indexOf("src") + 4;
                    int slash = f.getPath().lastIndexOf("/");
                    List<String> dirs = new ArrayList<>();
                    try{
                        String pathList = f.getPath().substring(src, slash);

                        String[] split = pathList.split("/");
                        dirs.add("src");
                        dirs.addAll(Arrays.asList(split));
                    } catch (StringIndexOutOfBoundsException e) {
                        dirs.add("src");
                    }
                    dirs.add(f.getName());

                    List<Compiler> filePath = compilerRepository.getFilePath(dirs, compileFileList.get(0).getCompiler().getProject().getPrjctIdx());
                    String path = "";
                    for(Compiler c : filePath) {
                        path += c.getComNm() + "/";
                    }
                    path = path.substring(0, path.length() - 1);
                    Compiler compiler = filePath.get(filePath.size() - 1);
                    CompilerFile detail = compilerContentRepository.findByCompiler_ComIdx(compiler.getComIdx());
                    String cn = detail.getComFileCn();

                    File savedFile = new File(rootPath + "/" + path);

                    try(OutputStream os = new FileOutputStream(savedFile)) {
                        byte[] c = cn.getBytes();

                        os.write(c);
                        os.flush();

                    } catch (Exception e) {
                        logger.info(e.getMessage());
                    }

                }
            }
        }
    }
}
