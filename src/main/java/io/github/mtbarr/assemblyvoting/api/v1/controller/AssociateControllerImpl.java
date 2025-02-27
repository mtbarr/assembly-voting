package io.github.mtbarr.assemblyvoting.api.v1.controller;


import io.github.mtbarr.assemblyvoting.api.v1.request.CreateAssociateRequest;
import io.github.mtbarr.assemblyvoting.api.v1.response.AssociateResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/associates")
public class AssociateControllerImpl implements AssociateController {

  @Override
  public Page<AssociateResponse> listAllAssociates() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public AssociateResponse registerNewAssociate(CreateAssociateRequest request) {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
