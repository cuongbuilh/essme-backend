package org.vietsearch.essme.repository.academic_disciplines;

import org.vietsearch.essme.model.academic_disciplines.Discipline;

import java.util.List;

public interface DisciplineCustomRepo {
    List<Discipline> findByParentId(String name);

    List<Object> getNumberOfDisciplinesInEachParent();
}
