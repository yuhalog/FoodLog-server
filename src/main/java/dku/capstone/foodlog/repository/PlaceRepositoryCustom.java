package dku.capstone.foodlog.repository;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.domain.Place;
import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.dto.MapDto;
import dku.capstone.foodlog.dto.RecommendDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlaceRepositoryCustom {

    List<Place> filterPlaceAll(MapDto.Filter mapRequest);

    List<Place> filterPlaceLimit(MapDto.Filter mapRequest);

    Page<Place> getPageSearchPlaceByName(MapDto.Search mapSearch, Pageable pageable);

    Page<Place> getPageSearchPlaceByAddress(MapDto.Search mapSearch, Pageable pageable);

    Page<Post> getPageRecommendPost(RecommendDto.Request recommendRequest, Member member, Pageable pageable);

    Page<Place> getPageSearchPlaceByMenu(MapDto.Search mapSearch, Pageable pageable);

    List<Place> getPlaceByMember(Long memberId);
}
