package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.dto.PostDto;

import java.util.List;

public interface PostService{

    PostDto.Response createPost(Member member, PostDto.Request postRequest, List<String> pictureUrlList);

}
