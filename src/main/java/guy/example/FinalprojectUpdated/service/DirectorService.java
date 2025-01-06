package guy.example.FinalprojectUpdated.service;


import guy.example.FinalprojectUpdated.dto.directordto.request.AddPostRequestDirectorDto;
import guy.example.FinalprojectUpdated.dto.directordto.request.PutUpdateRequestDirector;
import guy.example.FinalprojectUpdated.dto.directordto.response.DeleteDirectorListDto;
import guy.example.FinalprojectUpdated.dto.directordto.response.DeleteDirectorResponseDto;
import guy.example.FinalprojectUpdated.dto.directordto.response.DirectorResponseDto;
import guy.example.FinalprojectUpdated.entity.Director;

import java.util.ArrayList;
import java.util.List;



public interface DirectorService {

     DirectorResponseDto addDirector(AddPostRequestDirectorDto dto);

 List<DirectorResponseDto> getAll() ;

 List<DirectorResponseDto> getTop(int top) ;


 List<DirectorResponseDto> getLast(int last) ;


    
 List<DirectorResponseDto> get(int top,ArrayList<Director> sorted ) ;


    
 DeleteDirectorResponseDto deleteDirectorById(long id) ;

    
 DirectorResponseDto update(long id, PutUpdateRequestDirector req) ;

    
 DirectorResponseDto getDirectorById( long id);


 DeleteDirectorListDto deleteAll() ;






}
