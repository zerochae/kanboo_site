package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.project.Compiler;
import com.kanboo.www.domain.entity.project.QCompiler;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;


@RequiredArgsConstructor
public class CompilerDslRepositoryImpl implements CompilerDslRepository {

    private final EntityManager em;

    public String getPath(Long projectIdx) {

        JPAQueryFactory query = new JPAQueryFactory(em);
        StringBuilder result = new StringBuilder();

        QCompiler compiler = QCompiler.compiler;

        List<Compiler> compilerList = query.selectFrom(compiler)
                .where(
                        compiler.project.prjctIdx.eq(projectIdx)
                                .and(
                                        compiler.parentComIdx.eq(JPAExpressions.select(compiler.comIdx)
                                                .from(compiler)
                                                .where(
                                                        compiler.project.prjctIdx.eq(projectIdx)
                                                                .and(compiler.parentComIdx.isNull())
                                                ))
                                )
                                .and(compiler.comSe.eq("d"))
                                .or(compiler.parentComIdx.isNull())
                )
                .orderBy(compiler.comIdx.asc())
                .fetch();

        compilerList.forEach(path -> {
            result.append(path.getComNm() + "/");
        });

        return result.toString();
    }

    @Override
    public List<Compiler> getFilePath(List<String> list, Long projectIdx) {

        QCompiler compiler = QCompiler.compiler;

        JPAQueryFactory query = new JPAQueryFactory(em);

        return query.selectFrom(compiler)
                .where(
                        compiler.comNm.in(list)
                                .and(compiler.project.prjctIdx.eq(projectIdx))
                )
                .fetch();
    }
}
