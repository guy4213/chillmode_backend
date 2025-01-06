package guy.example.FinalprojectUpdated.service;


import guy.example.FinalprojectUpdated.dto.seriesDto.SeriesListDto;
import guy.example.FinalprojectUpdated.dto.seriesDto.request.AddSeriesRequestDto;
import guy.example.FinalprojectUpdated.dto.seriesDto.request.UpdateRequestSeries;
import guy.example.FinalprojectUpdated.dto.seriesDto.response.DeleteSeriesListDto;
import guy.example.FinalprojectUpdated.dto.seriesDto.response.DeleteSeriesResponseDto;
import guy.example.FinalprojectUpdated.dto.seriesDto.response.SeriesResponseDto;
import guy.example.FinalprojectUpdated.entity.Series;

import java.util.ArrayList;
import java.util.List;



 interface SeriesService {
 
    
 SeriesResponseDto addSeries
         (AddSeriesRequestDto dto);
    
 SeriesResponseDto  addCategories(long seriesId, String[] categoryNames);


     //getAllPosts(1, 10, "asc", "title", "author", "releaseDate")
    
    
 SeriesListDto getAll(int pageNo, int pageSize, String sortDir, String... sortBy) ;
    
 List<SeriesResponseDto> getTop(int top) ;

    
 List<SeriesResponseDto> getLast(int last); 


    //get-getting the top||last by number- improves and shorten the code
    
 List<SeriesResponseDto> get(int top,ArrayList<Series> sorted ) ;


    
 DeleteSeriesResponseDto deleteSeriesById(long id) ;

    
 DeleteSeriesListDto deleteAll() ;

 SeriesResponseDto update(long id, UpdateRequestSeries req) ;


    
 SeriesResponseDto getSeriesById( long id);

    
 List<SeriesResponseDto> search( String name);


}
