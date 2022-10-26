package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.dto.MapDto;
import dku.capstone.foodlog.dto.PageDto;
import dku.capstone.foodlog.dto.PostDto;
import dku.capstone.foodlog.dto.RecommendDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlaceService {

    Place checkPlaceInDb(PostDto.Request postRequest);

    List<MapDto.Response> searchPlaceWithFilter(MapDto.Filter mapFilterRequest);

    PageDto searchPlaceByName(MapDto.Search mapSearch, Pageable pageable);

    PageDto searchPlaceByAddress(MapDto.Search mapSearch, Pageable pageable);

    PageDto searchPlaceByMenu(MapDto.Search mapSearch, Pageable pageable);

    PageDto recommendPost(RecommendDto.Request request, Member member, Pageable pageable);

}
