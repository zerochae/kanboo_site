package com.kanboo.www.service.impl.project;

import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.domain.entity.member.ProjectMember;
import com.kanboo.www.domain.entity.project.*;
import com.kanboo.www.domain.entity.project.Calendar;
import com.kanboo.www.domain.entity.project.Compiler;
import com.kanboo.www.domain.repository.member.MemberRepository;
import com.kanboo.www.domain.repository.project.*;
import com.kanboo.www.dto.member.ProjectMemberDTO;
import com.kanboo.www.dto.project.CalendarDTO;
import com.kanboo.www.dto.project.IssueDTO;
import com.kanboo.www.dto.project.ProjectDTO;
import com.kanboo.www.security.JwtSecurityService;
import com.kanboo.www.service.inter.project.ProjectService;
import com.kanboo.www.util.FileSystemUtil;
import com.kanboo.www.util.SaveCompileFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final FileSystemUtil fileSystemUtil;
    private final CompilerRepository compilerRepository;
    private final SaveCompileFile saveCompileFile;
    private final JwtSecurityService jwtSecurityService;
    private final ProjectMemberRepository projectMemberRepository;
    private final MemberRepository memberRepository;
    private final CompilerContentRepository compilerContentRepository;
    private final ChattingRepository chattingRepository;
    private final DemandRepository demandRepository;
    private final KanbanRepository kanbanRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Project save(ProjectDTO project) {
        return projectRepository.save(project.dtoToEntity());
    }

    @Override
    @Transactional
    public void updateReadMe(ProjectDTO project) {
        Project prjtc = projectRepository.findById(project.getPrjctIdx()).get();
        prjtc.updateReadMe(project.getPrjctReadMe());
    }

    @Override
    public ProjectDTO getProject(Long projectIdx) {
        Project project = projectRepository.findByPrjctIdx(projectIdx);


        List<IssueDTO> issue = new ArrayList<>();
        if(project.getIssueList() != null && !project.getIssueList().isEmpty()) {
            project.getIssueList().sort(ProjectServiceImpl::compare);
            int length = project.getIssueList().size();
            if(project.getIssueList().size() >= 2) {
                length = 2;
            }
            for(int i = 0; i < length; i++) {
                IssueDTO issueDTO = project.getIssueList().get(i).entityToDto();
                issueDTO.setProject(ProjectDTO.builder().prjctIdx(projectIdx).build());
                issue.add(issueDTO);
            }
        }

        List<CalendarDTO> calendar = new ArrayList<>();
        if(project.getCalendarList() != null && !project.getCalendarList().isEmpty()) {
            project.getCalendarList().sort(ProjectServiceImpl::compare);
            int length = project.getCalendarList().size();
            if(project.getCalendarList().size() >= 3) {
                length = 3;
            }
            for(int i = 0; i < length; i++) {
                CalendarDTO calendarDTO = project.getCalendarList().get(i).entityToDto();
                calendarDTO.setProject(ProjectDTO.builder().prjctIdx(projectIdx).build());
                calendar.add(calendarDTO);
            }
        }

        ProjectDTO projectDTO = ProjectDTO.builder()
                .prjctIdx(project.getPrjctIdx())
                .prjctNm(project.getPrjctNm())
                .prjctStartDate(project.getPrjctStartDate())
                .prjctEndDate(project.getPrjctEndDate())
                .prjctProgress(project.getPrjctProgress())
                .prjctDelAt(project.getPrjctDelAt())
                .prjctComplAt(project.getPrjctComplAt())
                .prjctReadMe(project.getPrjctReadMe())
                .issueList(issue)
                .calendarList(calendar)
                .build();

        project.getProjectMembers().forEach(item -> {
            projectDTO.getProjectMembers().add(item.entityToDto());
        });

        return projectDTO;
    }

    @Override
    public void addDirOrFile(Map<String, Object> map) {

        String type = map.get("type") + "";
        Long projectIdx = Long.parseLong(map.get("projectIdx") + "");

        Project project = projectRepository.findByPrjctIdx(projectIdx);

        String name = map.get("name") + "";
        String path = map.get("path") + "";
        String classification = map.get("classification") + "";
        Long comIdx = Long.parseLong(map.get("comIdx") + "");

        boolean result = false;
        String pkg = "";

        if(type.equals("Directory")) {
            Map<String, String> rootPath = new HashMap<>();
            rootPath.put("rootPath", "/compileFiles/member");
            rootPath.put("project", project.getPrjctIdx() + project.getPrjctNm());
            rootPath.put("path", "project/src");
            rootPath.put("name", path + "/" + name);
            rootPath.put("type", "dir");
            result = fileSystemUtil.createFileOrDir(rootPath);
        } else {
            Map<String, String> rootPath = new HashMap<>();
            StringBuilder code = new StringBuilder();

            String[] split = path.split("/");

            for (String s : split) {
                pkg += s + ".";
            }
            pkg = pkg.substring(0, pkg.length() - 1);

            code.append("package " + pkg + ";\n");
            code.append("\n");
            code.append("public class " + name + " {\n");
            code.append("\n");
            code.append("}");
            rootPath.put("filePath", "src/" + path);
            rootPath.put("fileName", name);
            rootPath.put("fileExtension", classification.equals("JAVA") ? ".java" : ".html");
            rootPath.put("fileDetail", code.toString());
            rootPath.put("project", "/member/" + project.getPrjctIdx() + project.getPrjctNm() + "/project");
            rootPath.put("type", "file");
            result = saveCompileFile.saveFile(rootPath);
        }

        if(result) {
            Compiler parentCompiler = compilerRepository.findByComIdx(comIdx);

            Compiler compiler = Compiler.builder()
                    .project(project)
                    .parentComIdx(parentCompiler.getComIdx())
                    .comSe(type.equals("Directory") ? "d" : "f")
                    .comNm(type.equals("Directory") ? name : name + ".java")
                    .build();

            Compiler save = compilerRepository.save(compiler);


            if(compiler.getComSe().equals("f")) {
                StringBuilder code = new StringBuilder();
                code.append("package " + pkg + ";\n");
                code.append("\n");
                code.append("public class " + name + " {\n");
                code.append("\n");
                code.append("}");

                CompilerFile compilerFile = CompilerFile.builder()
                                            .comFileCn(code.toString())
                                            .compiler(save)
                                            .build();
                compilerContentRepository.save(compilerFile);
            }

            map.put("result", save.entityToDto());
        }
    }

    @Override
    public Map<String, Object> getAllList(String token) {
        String exeToken = jwtSecurityService.getToken(token);
        if(!exeToken.isEmpty()) {
            Map<String, Object> resultMap = new HashMap<>();

            List<ProjectMember> allList = projectMemberRepository.getAllList(exeToken);
            List<ProjectMemberDTO> list = new ArrayList<>();
            allList.forEach(item -> {
                list.add(item.entityToDto());
            });

            resultMap.put("projectMemberDtoList", list);

            return resultMap;
        }
        return null;
    }

    @Override
    public void saveProject(Map<String, Object> param) {
        /** 파라미터 조회 */
        ProjectDTO project = (ProjectDTO) param.get("project");
        String tag = param.get("tag") + "";

        /** 저장 */
        Member member = memberRepository.findByMemTag(tag);
        project.setPrjctManager(member.getMemId());
        Project saveProject = projectRepository.save(project.dtoToEntity());

        Compiler compiler = Compiler.builder()
                .comNm("src")
                .comSe("d")
                .project(saveProject)
                .build();
        Compiler savedCompiler = compilerRepository.save(compiler);

        Compiler mainFile = Compiler.builder()
                .comNm("Main.java")
                .comSe("f")
                .project(saveProject)
                .parentComIdx(savedCompiler.getComIdx())
                .build();
        Compiler savedMainFile = compilerRepository.save(mainFile);

        StringBuilder source = new StringBuilder();
        source.append("public class Main {\n");
        source.append("\tpublic static void main(String[] args) {\n");
        source.append("\t\tSystem.out.println(\"Hello World!\")\n");
        source.append("\t}\n");
        source.append("}");

        CompilerFile mainSource = CompilerFile.builder()
                .comFileCn(source.toString())
                .compiler(savedMainFile)
                .build();

        compilerContentRepository.save(mainSource);

        ProjectMember pm = ProjectMember.builder()
                .project(saveProject)
                .member(member)
                .prjctMemRole("PM")
                .build();
        ProjectMember save = projectMemberRepository.save(pm);

        Chat chat = Chat.builder()
                .member(member)
                .project(saveProject)
                .build();
        chattingRepository.save(chat);

        Demand demand = Demand.builder()
                .project(saveProject)
                .demandReviseDate(LocalDateTime.now())
                .build();
        demandRepository.save(demand);

        Kanban kanban = Kanban.builder()
                .project(saveProject)
                .build();
        kanbanRepository.save(kanban);

        String successYn = "N";
        if(save != null) {
            Map<String, String> creatDirPath = new HashMap<>();
            creatDirPath.put("rootPath", "/compileFiles/member");
            creatDirPath.put("project", saveProject.getPrjctIdx() + saveProject.getPrjctNm());
            creatDirPath.put("path", "project");
            creatDirPath.put("name", "src");
            creatDirPath.put("type", "dir");
            fileSystemUtil.createFileOrDir(creatDirPath);

            creatDirPath.put("name", "class");
            fileSystemUtil.createFileOrDir(creatDirPath);

            creatDirPath.put("name", "META-INF");
            fileSystemUtil.createFileOrDir(creatDirPath);

            creatDirPath.put("name", "lib");
            fileSystemUtil.createFileOrDir(creatDirPath);

            Map<String, String> createFile = new HashMap<>();
            StringBuilder manifest = new StringBuilder();
            manifest.append("Class-Path: ../class/\n");
            manifest.append("Main-Class: Main\n");

            createFile.put("filePath", "/META-INF/");
            createFile.put("fileName", "Manifest");
            createFile.put("fileExtension", ".txt");
            createFile.put("fileDetail", manifest.toString());
            createFile.put("project", "/member/" + saveProject.getPrjctIdx() + saveProject.getPrjctNm() + "/project");
            saveCompileFile.saveFile(createFile);

            StringBuilder main = new StringBuilder();
            main.append("public class Main {\n");
            main.append("   public static void main(String[] args) {\n");
            main.append("       \n");
            main.append("   }\n");
            main.append("}");

            createFile.put("filePath", "/src/");
            createFile.put("fileName", "Main");
            createFile.put("fileExtension", ".java");
            createFile.put("fileDetail", main.toString());
            
            boolean isSave = saveCompileFile.saveFile(createFile);
            
            
            
            if(isSave) {
                param.put("project", saveProject.entityToDto());
                successYn = "Y";
            }
        }
        param.put("successYn",successYn);
    }

    @Override
    public ProjectDTO getDashBoardData(String memTag, Long projectIdx) {
        return projectRepository.getDashBoard(memTag, projectIdx);
    }

    @Override
    @Transactional
    public ProjectDTO updateProjectState(ProjectDTO projectDTO) {

        Project project = projectRepository.findByPrjctIdx(projectDTO.getPrjctIdx());

        project.changePrjctComplAt(projectDTO.getPrjctComplAt());
        project.changePrjctDelAt(projectDTO.getPrjctDelAt());

        return project.entityToDto();
    }

    @Override
    public Long getMaxIndexOfProject(String selected, String key) {
        return projectRepository.getMaxIndexOfProject(selected,key);
    }


    // ======================== sort
    private static int compare(Issue o1, Issue o2) {
        return o1.getIssueIdx() <= o2.getIssueIdx() ? 1 : -1;
    }

    private static int compare(com.kanboo.www.domain.entity.project.Calendar o1, Calendar o2) {
        return o1.getCalIdx() <= o2.getCalIdx() ? 1 : -1;
    }

}
