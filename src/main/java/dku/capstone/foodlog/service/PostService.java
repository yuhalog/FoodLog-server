package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.dto.request.PostRequest;
import dku.capstone.foodlog.dto.response.PostResponse;

import java.util.List;

public interface PostService{

    public PostResponse createPost(Member member, PostRequest postRequest, List<String> pictureUrlList);

}
