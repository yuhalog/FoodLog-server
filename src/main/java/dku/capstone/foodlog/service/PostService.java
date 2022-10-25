package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Member;
import dku.capstone.foodlog.dto.PageDto;
import dku.capstone.foodlog.dto.PostDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService{

    PostDto.Response createPost(Member member, PostDto.Request postRequest, List<String> pictureUrlList);

    PostDto.Response updatePost(Long postId, PostDto.Request postRequest);

    PostDto.Response getPost(Long postId);

    void deletePost(Member member, Long postId);

    PageDto getSubscriberPosts(Member member, Pageable pageable);

}

