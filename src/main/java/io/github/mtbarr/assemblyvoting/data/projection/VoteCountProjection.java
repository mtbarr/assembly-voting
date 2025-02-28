package io.github.mtbarr.assemblyvoting.data.projection;


public interface VoteCountProjection {
  Long getYesVotes();

  Long getNoVotes();
}
