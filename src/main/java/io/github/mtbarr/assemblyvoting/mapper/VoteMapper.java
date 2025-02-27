package io.github.mtbarr.assemblyvoting.mapper;

import io.github.mtbarr.assemblyvoting.data.entity.VoteEntity;
import io.github.mtbarr.assemblyvoting.domain.Vote;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoteMapper {

  Vote toVote(VoteEntity entity);

  VoteEntity toEntity(Vote vote);
}
