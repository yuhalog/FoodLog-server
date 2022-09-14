package dku.capstone.foodlog.controller;

import dku.capstone.foodlog.dto.MapDto;
import dku.capstone.foodlog.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping("/v1/map")
    public ResponseEntity<?> getPlaceWithFilter(
            @RequestBody MapDto.Request mapRequest) {
        List<MapDto.Response> responses = placeService.searchPlaceWithFilter(mapRequest);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
