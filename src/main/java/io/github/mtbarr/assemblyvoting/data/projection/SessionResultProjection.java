package io.github.mtbarr.assemblyvoting.data.projection;


import java.util.UUID;


public interface SessionResultProjection {

  UUID getSubjectId();

  String getSubjectTitle();

  String getSubjectDescription();

  Long getYesVotes();

  Long getNoVotes();

}
