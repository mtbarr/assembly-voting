package io.github.mtbarr.assemblyvoting.mapper;


import io.github.mtbarr.assemblyvoting.data.entity.AssociateEntity;
import io.github.mtbarr.assemblyvoting.domain.Associate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssociateMapper {

  Associate toAssociate(AssociateEntity entity);

  AssociateEntity toEntity(Associate associate);
}
