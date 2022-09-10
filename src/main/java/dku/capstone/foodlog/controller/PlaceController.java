//package dku.capstone.foodlog.api;
//
//import dku.capstone.foodlog.dto.response.KakaoPlaceResponse;
//import dku.capstone.foodlog.service.PlaceService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequiredArgsConstructor
//@RestController
//public class PlaceController {
//
//    private final PlaceService placeService;
//
//    @GetMapping("/api/place")
//    public ResponseEntity<?> getPlacePost() {
//        KakaoPlaceResponse kaKaoPlaceResponse = placeService.kakaoPlaceQueryApi();
//        return new ResponseEntity<>(kaKaoPlaceResponse, HttpStatus.OK);
//    }
//}
