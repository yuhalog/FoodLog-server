package dku.capstone.foodlog.service;

import dku.capstone.foodlog.domain.Post;
import dku.capstone.foodlog.domain.PostPicture;
import dku.capstone.foodlog.repository.PostPictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PostPictureServiceImpl implements PostPictureService {

    private final PostPictureRepository postPictureRepository;
    private final AwsS3Service awsS3Service;

    @Transactional
    PostPicture savePostPicture(String imageName, Post post) {
        PostPicture postPicture = PostPicture.builder()
                .imageName(imageName)
                .pictureUrl(awsS3Service.getImagePath(imageName))
                .post(post)
                .build();

        return postPictureRepository.save(postPicture);
    }

    public List<PostPicture> savePostPictureList(List<String> imageNameList, Post post) {

        List<PostPicture> postPictureList = new ArrayList<>();

        for (String imageName : imageNameList) {
            postPictureList.add(savePostPicture(imageName, post));
        }
        post.setPictureList(postPictureList);

        return postPictureList;
    }

    @Transactional
    public void deletePostPicture(PostPicture postPicture) {
        awsS3Service.deleteImage(postPicture.getImageName());
        postPictureRepository.delete(postPicture);
    }

    @Transactional
    public void deletePostPictureList(List<PostPicture> postPictureList) {
        for (PostPicture postPicture : postPictureList) {
            deletePostPicture(postPicture);
        }
    }
}
