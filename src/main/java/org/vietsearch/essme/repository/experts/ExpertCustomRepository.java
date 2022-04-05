package org.vietsearch.essme.repository.experts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vietsearch.essme.model.expert.Expert;

import java.util.List;

public interface ExpertCustomRepository {
    List<Object> getNumberOfExpertsInEachField(String lang);

//    @Query("{ $and : [ {$text:{$search : ?0}}, {'location.features.geometry': {$geoWithin: { $centerSphere: [ [ ?1, ?2 ], ?3/6378 ]}}}  ]}")
//    Page<Expert> searchByLocationAndText(String what, String where, double radius);

    Page<Expert> searchByLocationAndText(String what, String where, double radius, Pageable pageable);

    List<Expert> relatedExpertsByField(String field, int limit, int skip);

    List<Expert> relatedExpertsByExpert(Expert expert, int limit, int skip);
}