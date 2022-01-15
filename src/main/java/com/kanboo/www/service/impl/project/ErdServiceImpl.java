package com.kanboo.www.service.impl.project;

import com.kanboo.www.domain.entity.project.Erd;
import com.kanboo.www.domain.entity.project.ErdColumn;
import com.kanboo.www.domain.entity.project.Project;
import com.kanboo.www.domain.repository.project.ErdColumnRepository;
import com.kanboo.www.domain.repository.project.ErdRepository;
import com.kanboo.www.domain.repository.project.ProjectRepository;
import com.kanboo.www.dto.project.ErdDTO;
import com.kanboo.www.service.inter.project.ErdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ErdServiceImpl implements ErdService {

    private final ErdRepository erdRepository;
    private final ProjectRepository projectRepository;
    private final ErdColumnRepository erdColumnRepository;

    @Override
    public List<ErdDTO> getAllErd(Long prjctIdx) {
        List<Erd> erdEntity = erdRepository.findAllByProject_PrjctIdx(prjctIdx);
        List<ErdDTO> list = new ArrayList<>();
        erdEntity.forEach(item -> {
            list.add(item.entityToDto());
        });

        return list;
    }

    @Override
    public ErdDTO createTable(ErdDTO erdDTO) {
        Project project = projectRepository.findById(erdDTO.getProject().getPrjctIdx()).get();

        Erd erd = Erd.builder()
                .project(project)
                .erdName(erdDTO.getErdName())
                .build();

        Erd savedErd = erdRepository.save(erd);

        erdDTO.getColumns().forEach(column -> {
            ErdColumn erdColumn = ErdColumn.builder()
                    .erd(savedErd)
                    .erdColumnName(column.getErdColumnName())
                    .erdColumnType(column.getErdColumnType())
                    .erdColumnConstraint(column.getErdColumnConstraint())
                    .erdColumnReferences(column.getErdColumnReferences())
                    .build();
            erdColumnRepository.save(erdColumn);
        });

        return erdRepository.findById(savedErd.getErdIdx()).get().entityToDto();

    }

    @Override
    @Transactional
    public void deleteTable(ErdDTO erdDTO) {
        erdColumnRepository.deleteByErd_ErdIdx(erdDTO.getErdIdx());
        erdRepository.deleteById(erdDTO.getErdIdx());
    }

    @Override
    public void updateTable(ErdDTO erdDTO) {
        Project project = projectRepository.findById(erdDTO.getProject().getPrjctIdx()).get();
        Erd erd = Erd.builder()
                .erdIdx(erdDTO.getErdIdx())
                .project(project)
                .erdName(erdDTO.getErdName())
                .erdOrder(erdDTO.getErdOrder())
                .build();

        Erd savedErd = erdRepository.save(erd);

        erdDTO.getColumns().forEach(col -> {
            ErdColumn column = ErdColumn.builder()
                    .erdColumnIdx(col.getErdColumnIdx())
                    .erd(savedErd)
                    .erdColumnName(col.getErdColumnName())
                    .erdColumnType(col.getErdColumnType())
                    .erdColumnConstraint(col.getErdColumnConstraint())
                    .erdColumnReferences(col.getErdColumnReferences())
                    .build();
            erdColumnRepository.save(column);
        });
    }
}
