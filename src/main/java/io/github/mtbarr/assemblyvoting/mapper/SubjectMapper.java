package io.github.mtbarr.assemblyvoting.mapper;

import io.github.mtbarr.assemblyvoting.data.entity.SubjectEntity;
import io.github.mtbarr.assemblyvoting.domain.Subject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

  Subject toSubject(SubjectEntity entity);

  SubjectEntity toEntity(Subject subject);
}
