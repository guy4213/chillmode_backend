package guy.example.FinalprojectUpdated.controller;


import guy.example.FinalprojectUpdated.dto.seriesDto.SeriesListDto;
import guy.example.FinalprojectUpdated.dto.seriesDto.request.AddSeriesRequestDto;
import guy.example.FinalprojectUpdated.dto.seriesDto.request.UpdateRequestSeries;
import guy.example.FinalprojectUpdated.dto.seriesDto.response.DeleteSeriesListDto;
import guy.example.FinalprojectUpdated.dto.seriesDto.response.DeleteSeriesResponseDto;
import guy.example.FinalprojectUpdated.dto.seriesDto.response.SeriesResponseDto;
import guy.example.FinalprojectUpdated.service.SeriesServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/series")
@CrossOrigin(origins={ "http://localhost:5173", "http://localhost:5174","http://localhost:3000"})
public class SeriesController {
    //props:
    private final SeriesServiceImpl  seriesService;

    @PostMapping("/addRate")
     ResponseEntity<SeriesResponseDto> addRate(@RequestParam(value = "userID", required = true,
            defaultValue = "userID") long userID,
            @RequestParam(value = "seriesID", required = true,
            defaultValue = "id") long seriesID,
                                                       @RequestParam(value = "rate", required = true,
                      defaultValue = "0") float rate) throws Exception {

        var res = seriesService.addRate(userID,seriesID, rate);
        System.out.println("response DTO= " + res.getAverageRate());
        return ResponseEntity.ok(res);

    }
    @PostMapping
    public ResponseEntity<SeriesResponseDto> addSeries(@Valid @RequestBody
                                                           AddSeriesRequestDto dto) {
        var res = seriesService.addSeries(dto);
        var id = res.getId();
        var uri = URI.create("/series/%d".formatted(id));
        return ResponseEntity.created(uri).body(res);
    }

    //add array of categories
    @PostMapping("/addCategoriesByNames")
    public ResponseEntity<SeriesResponseDto> addCategories(
            @RequestParam(value = "SeriesId", required = true,
                    defaultValue = "id") long SeriesId,
            @RequestParam(value = "categoryNames", required = true,
                    defaultValue = "name") String... categoryNames) {
        try {
            var res = seriesService.addCategories(SeriesId, categoryNames);
            return ResponseEntity.ok(res);
        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage();
            SeriesResponseDto errorResponse = new SeriesResponseDto();
            errorResponse.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
@PostMapping("/addDirector")
public ResponseEntity<SeriesResponseDto> addDirector(
        @RequestParam(value = "SeriesId", required = true,
                defaultValue = "id") long SeriesId,
        @RequestParam(value = "DirectorName", required = true,
                defaultValue = "name") String DirectorName) {
    var res = seriesService.addDirector(SeriesId, DirectorName);
    System.out.println("response DTO= " + res.getDirector());

    return ResponseEntity.ok(res);
}

    //
    @PostMapping("/addActorsByNames")
    public ResponseEntity<SeriesResponseDto> addActors(
            @RequestParam(value = "SeriesId", required = true,
                    defaultValue = "id") long SeriesId,
            @RequestParam(value = "ActorNames", required = true,
                    defaultValue = "names") String... ActorNames) {
        try {
        var res = seriesService.addActors(SeriesId, ActorNames);
        System.out.println("response DTO= " + res.getActors());
            return ResponseEntity.ok(res);
        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage();
            SeriesResponseDto errorResponse = new SeriesResponseDto();
            errorResponse.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }




    @GetMapping
    public ResponseEntity<SeriesListDto> getAll(
            @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sortDir", required = false, defaultValue = "asc") String sortDir,
            @RequestParam(value = "sortBy", required = false, defaultValue = "id") String... sortBy


    ) {

        var res = seriesService.getAll(pageNo, pageSize, sortDir, sortBy);

        return ResponseEntity.ok(res);

    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<DeleteSeriesListDto> deleteAll() {

        var res = seriesService.deleteAll();

        return ResponseEntity.ok(res);

    }

    @GetMapping("/top/{num}")
    public ResponseEntity<List<SeriesResponseDto>> top(@PathVariable int num) {
        return ResponseEntity.ok(seriesService.getTop(num));
    }

    @GetMapping("/last/{num}")
    public ResponseEntity<List<SeriesResponseDto>> last(@PathVariable int num) {
        return ResponseEntity.ok(seriesService.getLast(num));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSeriesResponseDto> deleteSeriesById(@PathVariable long id) {

        return ResponseEntity.ok(seriesService.deleteSeriesById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeriesResponseDto> updateSeries(
            @PathVariable long id,
            @Valid @RequestBody UpdateRequestSeries req) {

        return ResponseEntity.ok(seriesService.update(id, req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeriesResponseDto> getSeriesById(@PathVariable long id) {
        return ResponseEntity.ok(seriesService.getSeriesById(id));

    }

    @GetMapping("contains/{seriesName}")
    public ResponseEntity<SeriesListDto> search(@PathVariable String seriesName) {

        var res = seriesService.search(seriesName);
        var list = new SeriesListDto(res);
        return ResponseEntity.ok(list);

    }
}
//@GetMapping("/search/{query}")
//public ResponseEntity<List<SeriesResponseDto>> search(@PathVariable String query) {
//}

//@PutMapping("/{id}")
//public ResponseEntity<UserResponseDTO> updateUser(
//        @PathVariable long id,
//        @Valid @RequestBody PutUpdateRequestUser req) {
//
//    return ResponseEntity.ok(userService.update(id,req));
//}
//
//public UserResponseDTO update(long id,PutUpdateRequestUser req) {
//
//    User user=getUserByIdOrThrow(id);
//
//    //update:
//    user.setEmail(req.email());
//    user.setPassword(req.password());
//    var saved = userRepository.save(user);
//    return modelMapper.map(saved, UserResponseDTO.class);
//}
//
//private Movie getMovieByIdOrThrow(long id) {
//    return movieRepository.findById(id).orElseThrow(
//            () -> new ResourceNotFoundException("Movie", "id", id)
//    );
//}