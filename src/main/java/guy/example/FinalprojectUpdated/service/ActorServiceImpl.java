package guy.example.FinalprojectUpdated.service;

import guy.example.FinalprojectUpdated.dto.actorDto.request.AddPostReqActorDto;
import guy.example.FinalprojectUpdated.dto.actorDto.request.PutUpdateRequestActor;
import guy.example.FinalprojectUpdated.dto.actorDto.response.ActorResponseDto;
import guy.example.FinalprojectUpdated.dto.actorDto.response.DeleteActorListDto;
import guy.example.FinalprojectUpdated.dto.actorDto.response.DeleteActorResponseDto;
import guy.example.FinalprojectUpdated.entity.Actor;
import guy.example.FinalprojectUpdated.error.ResourceNotFoundException;
import guy.example.FinalprojectUpdated.repository.ActorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {
    private final ActorRepository actorRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(ActorServiceImpl.class);

    public ActorResponseDto addActor(AddPostReqActorDto dto) {
        //request dto=>Actor
        var actorEntity = modelMapper.map(dto, Actor.class);
        //Actor->saved Actor
        var saved = actorRepository.save(actorEntity);
        //saved Actor->Response Dto
        return modelMapper.map(saved, ActorResponseDto.class);
    }

    public Collection<ActorResponseDto> getAll() {
        var all = actorRepository.findAll();
        return all.stream().map(m -> modelMapper.map(m, ActorResponseDto.class)).toList();
    }
    public List<ActorResponseDto> getTop(int top) {
        ArrayList<Actor> sorted = new ArrayList<>(actorRepository.findAll());
        return get( top, sorted );
    }

    public List<ActorResponseDto> getLast(int last) {
        ArrayList<Actor> sorted = new ArrayList<>(actorRepository.findAll());
        Collections.reverse(sorted);
        return get(last,sorted);
    }


    public List<ActorResponseDto> get(int top,ArrayList<Actor> sorted ) {
        if(top > sorted.size())
            top =sorted.size();
        var sublist =sorted.subList(0, top).stream().map(actor -> modelMapper.map(actor, ActorResponseDto.class));
        return sublist.toList();
    }


    public DeleteActorResponseDto deleteActorById(long id) {
        // Check for existence before deleting:
        var actor = actorRepository.findById(id);

        // Delete:
        try {
            actorRepository.deleteById(id);

            // Return DTO if actor was deleted successfully:
            if (actor.isPresent()) {
                return DeleteActorResponseDto.builder()
                        .deleted(true)
                        .actorResponseDto(modelMapper.map(actor.get(), ActorResponseDto.class))
                        .build();
            } else {
                return DeleteActorResponseDto.builder()
                        .deleted(false)
                        .actorResponseDto(null)
                        .build();
            }
        } catch (DataIntegrityViolationException e) {
            // Handle constraint violation exception
            logger.error("Error deleting actor with ID: " + id, e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete actor with ID: " + id + " due to existing references", e);
        } catch (EmptyResultDataAccessException e) {
            // Handle non-existent actor
            logger.error("Error deleting actor with ID: " + id + ". Actor not found.", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor with ID: " + id + " not found.", e);
        } catch (Exception e) {
            // Handle other exceptions
            logger.error("Error deleting actor with ID: " + id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while deleting actor with ID: " + id, e);
        }
    }





    public ActorResponseDto update(long id, PutUpdateRequestActor req) {

        Actor actor=getActorByIdOrThrow(id);

        //update:
        if(req.actorName()!=null&&   !req.actorName().trim().isEmpty()){
            actor.setActorName(req.actorName());
        }
        if(req.country()!=null&&   !req.country().trim().isEmpty()) {
            actor.setCountry(req.country());
        }
        if(req.city()!=null&&   !req.city().trim().isEmpty()) {
            actor.setCity(req.city());
        }
        if(req.role()!=null&&   !req.role().trim().isEmpty()) {
            actor.setRole(req.role());
        }


        var saved = actorRepository.save(actor);
        return modelMapper.map(saved, ActorResponseDto.class);
    }


    public ActorResponseDto getActorById(long id){
        Optional<Actor> actor = actorRepository.findById(id);
        if (actor.isPresent()) {
            var a = actor.get();
            Actor saved = actorRepository.save(a);
            return modelMapper.map(saved,ActorResponseDto.class);
        }

        throw new ResourceNotFoundException("actor","id",""+id);
    }

    public ActorResponseDto getActorByName(String name ) throws ResourceNotFoundException {
        Optional<Actor> actor = Optional.ofNullable(actorRepository.findActorByActorNameIgnoreCase(name));
        if (actor.isPresent()) {
            var a = actor.get();
            Actor saved = actorRepository.save(a);
            return modelMapper.map(saved,ActorResponseDto.class);
        }

        throw new ResourceNotFoundException("actor","id",""+actor.get().getId());
    }
    public DeleteActorListDto deleteAll() {

        var all = actorRepository.findAll();
        var x = all.stream().map(m -> modelMapper.map(m, Actor.class)).toList();

        x.forEach(actor -> actorRepository.deleteById(actor.getId()));
        return DeleteActorListDto.builder()
                .deleted(!x.isEmpty()).build();
    }

    private Actor getActorByIdOrThrow(long id) {
        return actorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Movie", "id", id)
        );
    }


}
