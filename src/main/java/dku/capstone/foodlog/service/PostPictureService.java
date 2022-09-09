package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.domain.PostPicture;

import java.util.List;

public interface PostPictureService {

    List<PostPicture> savePostPictureList(List<String> pictureUrlList, Post post);
}
