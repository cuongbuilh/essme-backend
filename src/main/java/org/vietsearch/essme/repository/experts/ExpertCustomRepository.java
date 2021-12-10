package org.vietsearch.essme.repository.experts;

import java.util.Map;

public interface ExpertCustomRepository {
    Map<String, Integer> getNumberOfExpertsInEachField();
}
