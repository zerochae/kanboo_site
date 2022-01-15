package com.kanboo.www.service.impl.project;

import com.kanboo.www.domain.entity.project.Compiler;
import com.kanboo.www.domain.entity.project.CompilerFile;
import com.kanboo.www.domain.entity.project.Project;
import com.kanboo.www.domain.entity.project.QCompilerFile;
import com.kanboo.www.domain.repository.project.CompilerContentRepository;
import com.kanboo.www.domain.repository.project.CompilerRepository;
import com.kanboo.www.domain.repository.project.ProjectRepository;
import com.kanboo.www.dto.project.CompilerContentDTO;
import com.kanboo.www.dto.project.CompilerDTO;
import com.kanboo.www.service.inter.project.CompilerContentService;
import com.kanboo.www.service.inter.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompilerContentServiceImpl implements CompilerContentService {

    private final CompilerContentRepository compilerContentRepository;
    private final CompilerRepository compilerRepository;
    private final ProjectRepository projectRepository;

    public CompilerContentDTO getFile(CompilerDTO compilerDTO) {
        return compilerContentRepository.findByCompiler_ComIdx(compilerDTO.getComIdx()).entityToDto();
    }

    public boolean updateFile(CompilerContentDTO compilerContentDTO) {
        Compiler compiler = compilerRepository.findByComIdx(compilerContentDTO.getCompiler().getComIdx());
        CompilerFile file = CompilerFile.builder()
                .comFileIdx(compilerContentDTO.getComFileIdx())
                .compiler(compiler)
                .comFileCn(compilerContentDTO.getComFileCn())
                .build();
        CompilerFile save = compilerContentRepository.save(file);
        if(save.getComFileIdx() != null) {
            return true;
        }
        return false;
    }

    public CompilerContentDTO getHtmlDetail(CompilerDTO compilerDTO) {
        CompilerFile detail = compilerContentRepository.findByCompiler_ComIdx(compilerDTO.getComIdx());
        return detail.entityToDto();
    }

    @Override
    @Transactional
    public void updateHtml(CompilerContentDTO compilerContentDTO) {
        CompilerFile detail = compilerContentRepository.findByCompiler_ComIdx(compilerContentDTO.getCompiler().getComIdx());
        detail.updateContent(compilerContentDTO.getComFileCn());
    }

    @Override
    public CompilerContentDTO createHtml(Map<String, Object> map) {
        Project project = projectRepository.findById(Long.parseLong(map.get("prjctIdx") + "")).get();

        Compiler compiler = Compiler.builder()
                .comNm(map.get("name") + "")
                .comSe("h")
                .project(project)
                .build();
        Compiler save = compilerRepository.save(compiler);
        CompilerFile detail = CompilerFile.builder()
                .compiler(save)
                .comFileCn("")
                .build();
        CompilerFile compilerFile = compilerContentRepository.save(detail);
        return compilerFile.entityToDto();
    }
}
