package dku.capstone.foodlog.controller;

import dku.capstone.foodlog.dto.MapDto;
import dku.capstone.foodlog.dto.PageDto;
import dku.capstone.foodlog.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping("/v1/map")
    public ResponseEntity<?> getPlaceWithFilter(
            @RequestBody MapDto.Filter mapFilterRequest) {
        List<MapDto.Response> responses = placeService.searchPlaceWithFilter(mapFilterRequest);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/v1/place/search/name")
    public PageDto searchPlaceByName(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "") Double latitude,
            @RequestParam(defaultValue = "") Double latitudeDelta,
            @RequestParam(defaultValue = "") Double longitude,
            @RequestParam(defaultValue = "") Double longitudeDelta,
            @PageableDefault(size=10, sort = "placeId", direction = Sort.Direction.DESC) Pageable pageable) {
        MapDto.Search searchRequest = new MapDto.Search(longitude, latitude, longitudeDelta, latitudeDelta, query);
        PageDto placeResponsePageDto = placeService.searchPlaceByName(searchRequest, pageable);

        return placeResponsePageDto;
    }

    @GetMapping("/v1/place/search/address")
    public PageDto searchPlaceByAddress(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "") Double latitude,
            @RequestParam(defaultValue = "") Double latitudeDelta,
            @RequestParam(defaultValue = "") Double longitude,
            @RequestParam(defaultValue = "") Double longitudeDelta,
            @PageableDefault(size=10, sort = "placeId", direction = Sort.Direction.DESC) Pageable pageable) {
        MapDto.Search searchRequest = new MapDto.Search(longitude, latitude, longitudeDelta, latitudeDelta, query);
        PageDto placeResponsePageDto = placeService.searchPlaceByAddress(searchRequest, pageable);

        return placeResponsePageDto;
    }
}
