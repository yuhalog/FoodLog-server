package dku.capstone.foodlog.controller;

import dku.capstone.foodlog.dto.PlacePostDto;
import dku.capstone.foodlog.service.PlacePostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/place-post")
@RestController
public class PlacePostController {

    private final PlacePostService placePostService;

    @ApiOperation(value = "중간 게시물 조회", notes = "중간 게시물 조회 \n 중간 게시물 id로 조회합니다.")
    @GetMapping("{placePostId}")
    public ResponseEntity<PlacePostDto.Detail> getPlacePost(
            @PathVariable("placePostId") Long placePostId) {
        PlacePostDto.Detail placePostDetail = placePostService.getPlacePostDetail(placePostId);
        return new ResponseEntity<>(placePostDetail, HttpStatus.OK);
    }
}
