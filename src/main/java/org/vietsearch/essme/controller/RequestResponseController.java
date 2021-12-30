package org.vietsearch.essme.controller;

import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.vietsearch.essme.filter.AuthenticatedRequest;
import org.vietsearch.essme.repository.RequestResponseRepository;
import org.vietsearch.essme.model.request_response.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/requests")
public class RequestResponseController {
    @Autowired
    private RequestResponseRepository requestRepository;

    @DeleteMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public String testPrincipal(AuthenticatedRequest request) throws FirebaseAuthException {
        return request.getUserId();
    }

    @GetMapping
    public Page<Request> getRequests(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "20") int size, @RequestParam(value = "sort", defaultValue = "createdAt") String sortAttr, @RequestParam(value = "desc", defaultValue = "false") boolean desc) {
        Sort sort = Sort.by(sortAttr);
        if (desc)
            sort = sort.descending();

        Page<Request> requestPage = requestRepository.findAll(PageRequest.of(page, size, sort));
        return requestPage;
    }


    @GetMapping("/{id}")
    public Request getRequestById(@PathVariable("id") String id) {
        return requestRepository.findById(id).get();
    }

    @GetMapping("/search")
    public List<Request> searchRequests(@RequestParam("text") String text) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingPhrase(text);
        return requestRepository.findBy(criteria);
    }

    @GetMapping("/topic/{topic}")
    public Page<Request> getRequestByTopic(@PathVariable("topic") String topic, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "20") int size, @RequestParam(value = "sort", defaultValue = "createdAt") String sortAttr, @RequestParam(value = "desc", defaultValue = "false") boolean desc) {
        Sort sort = Sort.by(sortAttr);
        if (desc)
            sort = sort.descending();

        Page<Request> requestPage = requestRepository.findByTopic(topic, PageRequest.of(page, size, sort));
        return requestPage;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Request updateRequest(AuthenticatedRequest authenticatedRequest,@PathVariable("id") String id, @Valid @RequestBody Request request) {
        String uuid = authenticatedRequest.getUserId();
        if (requestRepository.existsById(id)) {
            // check
            if(!matchUserRequest(uuid, id)){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Permission denied", null);
            }

            // update
            request.set_id(id);
            requestRepository.save(request);
            return request;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found", null);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteRequest(AuthenticatedRequest authenticatedRequest,@PathVariable("id") String id) {
        String uuid = authenticatedRequest.getUserId();
        if (requestRepository.existsById(id)) {
            // check
            if(!matchUserRequest(uuid, id)){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Permission denied", null);
            }

            // delete
            requestRepository.deleteById(id);
            return "Deleted";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found", null);
        }
    }


    @PostMapping("/{requestId}/responses")
    @ResponseStatus(HttpStatus.CREATED)
    public Request addResponse(AuthenticatedRequest authenticatedRequest, @PathVariable("requestId") String requestId, @Valid @RequestBody Response response) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found", null));
        if (request.getResponses() == null)
            request.setResponses(new ArrayList<>());
        response.setUid(authenticatedRequest.getUserId());
        request.getResponses().add(response);
        return requestRepository.save(request);
    }

    @GetMapping("/{requestId}/responses")
    public List<Response> getResponse(@PathVariable("requestId") String requestId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found", null));
        return request.getResponses();
    }

    @PutMapping("/{requestId}/responses/{responsesId}")
    @ResponseStatus(HttpStatus.OK)
    public Response updateResponse(AuthenticatedRequest authenticatedRequest,@PathVariable("requestId") String requestId, @PathVariable("responsesId") String responsesId, @Valid @RequestBody Response response) {
        String uuid = authenticatedRequest.getUserId();
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found", null));
        if (request.getResponses() != null) {
            for (Response res : request.getResponses()) {
                if (matchUserResponse(uuid, responsesId, res)) {
                    res.setExpertId(response.getExpertId());
                    res.setContent(response.getContent());
                    res.setUpdatedAt(new Date());
                    requestRepository.save(request);
                    return res;
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Response not found", null);
    }

    @DeleteMapping("/{requestId}/responses/{responsesId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteResponse(AuthenticatedRequest authenticatedRequest, @PathVariable("requestId") String requestId, @PathVariable("responsesId") String responseId) {
        String uuid = authenticatedRequest.getUserId();
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found", null));
        if (request.getResponses() != null) {
            for (Response res : request.getResponses()) {
                if (matchUserResponse(uuid, responseId, res)) {
                    request.getResponses().remove(res);
                    requestRepository.save(request);
                    return "Deleted";
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Response not found", null);
    }

    private boolean matchUserRequest(String uuid, String requestId){
        // return true if uuid created request
        Optional<Request> optional = requestRepository.findById(requestId);
        return optional.map(res -> res.getCustomerId().equals(uuid)).orElse(false);
    }

    private boolean matchUserResponse(String uuid, String responseChangedId, Response response){
        // return true if uuid created response
        if(!response.get_id().equals(responseChangedId))
            return false;
        return response.getExpertId().equals(uuid);
    }

}
