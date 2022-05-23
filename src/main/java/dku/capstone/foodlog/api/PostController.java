package dku.capstone.foodlog.api;

import dku.capstone.foodlog.dto.request.PostFormDto;
import dku.capstone.foodlog.service.PostService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/post/new")
    public PostFormDto newPost(@RequestBody PostFormDto postFormDto){
        log.info("rating={}, review={}, type={}, purpose={}",
                //postFormDto.getPictureList(),
                postFormDto.getRating(),
                postFormDto.getReview(),
                postFormDto.getType(),
                postFormDto.getPurpose());
                //postFormDto.getPlace(),
                //postFormDto.getDate());

        return postFormDto;
    }
}
