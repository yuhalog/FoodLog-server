package dku.capstone.foodlog.api;

import dku.capstone.foodlog.dto.response.PlacePostDto;
import dku.capstone.foodlog.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PlaceApiController {

    private final PlaceService placeService;

    @GetMapping("api/place/{id}")
    public ResponseEntity<PlacePostDto> getPlacePost(
            @PathVariable("id") Long placeId) {
        PlacePostDto response = placeService.getPlacePost(placeId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
