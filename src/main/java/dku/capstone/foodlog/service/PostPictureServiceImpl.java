package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.domain.PostPicture;
import dku.capstone.foodlog.repository.PostPictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostPictureServiceImpl implements PostPictureService {

    private final PostPictureRepository postPictureRepository;

    private PostPicture savePostPicture(String pictureUrl, Post post) {
        PostPicture postPicture = PostPicture.builder()
                .pictureUrl(pictureUrl)
                .post(post)
                .build();

        return postPictureRepository.save(postPicture);
    }

    public List<PostPicture> savePostPictureList(List<String> pictureUrlList, Post post) {

        List<PostPicture> postPictureList = new ArrayList<>();

        for (String pictureUrl : pictureUrlList) {
            postPictureList.add(savePostPicture(pictureUrl, post));
        }
        post.setPictureList(postPictureList);

        return postPictureList;
    }
}
