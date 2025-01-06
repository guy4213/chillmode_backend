package guy.example.FinalprojectUpdated.service;


import guy.example.FinalprojectUpdated.dto.actorDto.request.AddPostReqActorDto;
import guy.example.FinalprojectUpdated.dto.actorDto.request.PutUpdateRequestActor;
import guy.example.FinalprojectUpdated.dto.actorDto.response.ActorResponseDto;
import guy.example.FinalprojectUpdated.dto.actorDto.response.DeleteActorListDto;
import guy.example.FinalprojectUpdated.dto.actorDto.response.DeleteActorResponseDto;
import guy.example.FinalprojectUpdated.entity.Actor;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public interface ActorService {


    ActorResponseDto addActor(AddPostReqActorDto dto) ;

    Collection<ActorResponseDto> getAll() ;
    List<ActorResponseDto> getTop(int top) ;

    List<ActorResponseDto> getLast(int last);


    List<ActorResponseDto> get(int top, ArrayList<Actor> sorted) ;


    DeleteActorResponseDto deleteActorById(long id)  ;

ActorResponseDto update(long id, PutUpdateRequestActor req)  ;


     ActorResponseDto getActorById(long id) ;
    DeleteActorListDto deleteAll()  ;


}


//@DeleteMapping("/deleteAll")
//public ResponseEntity<DeleteActorListDto> deleteAll(){
//
//    var res = actorService.deleteAll();
//
//    return ResponseEntity.ok(res);
//
//}
//public DeleteActorListDto deleteAll() {
//        var all = actorRepository.findAll();
//        var x = all.stream().map(m -> modelMapper.map(m, Actor.class)).toList();
//
//        x.forEach(actor -> {
//            actorRepository.deleteById(actor.getId());
//
//        });
//        return DeleteActorListDto.builder()
//                .deleted(!x.isEmpty()).build();
//    }