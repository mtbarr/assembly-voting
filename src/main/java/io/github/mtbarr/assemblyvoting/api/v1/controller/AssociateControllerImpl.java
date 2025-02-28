package io.github.mtbarr.assemblyvoting.api.v1.controller;


import io.github.mtbarr.assemblyvoting.api.v1.request.CreateAssociateRequest;
import io.github.mtbarr.assemblyvoting.api.v1.response.AssociateResponse;
import io.github.mtbarr.assemblyvoting.service.AssociateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/associates")
@RequiredArgsConstructor
public class AssociateControllerImpl implements AssociateController {

  private final AssociateService associateService;

  @Override
  public Page<AssociateResponse> listAllAssociates(@PageableDefault Pageable pageable) {
    return associateService.getAllAssociates(pageable)
      .map(AssociateResponse::new);
  }

  @Override
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public AssociateResponse registerNewAssociate(@RequestBody CreateAssociateRequest request) {
    return new AssociateResponse(associateService.registerNewAssociate(request.cpf()));
  }
}
