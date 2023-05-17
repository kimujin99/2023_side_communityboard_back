package side.boardservice.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import side.boardservice.domain.post.Post;
import side.boardservice.domain.post.PostDto;
import side.boardservice.domain.post.PostRepository;
import side.boardservice.domain.category.Category;
import side.boardservice.domain.category.CategoryRepository;
import side.boardservice.domain.reply.ReplyDto;
import side.boardservice.domain.reply.ReplyRepository;
import side.boardservice.domain.user.User;
import side.boardservice.domain.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    ReplyRepository replyRepository;

    //게시물 리스트 가져오기
    public List<PostDto.ListResponse> getPostList() {
        List<Post> postList = postRepository.findAll(Sort.by(Sort.Direction.DESC, "insTime"));
        //dto변환
        List<PostDto.ListResponse> dtos = postList.stream().map(PostDto.ListResponse::new).collect(Collectors.toList());

        return dtos;
    }

    public List<PostDto.ListResponse> getPostList(Long categoryCode) {
        Category category = categoryRepository.findById(categoryCode).get();
        List<Post> postList = postRepository.findByCategory(category, Sort.by(Sort.Direction.DESC, "insTime"));
        //dto변환
        List<PostDto.ListResponse> dtos = postList.stream().map(PostDto.ListResponse::new).collect(Collectors.toList());

        return dtos;
    }

    //게시물 상세 가져오기
    public PostDto.DetailResponse getPostDetail(Long postingCode) {
        Post post = postRepository.findById(postingCode).get();

        PostDto.DetailResponse dto = new PostDto.DetailResponse(post);

        return dto;
    }

//    //게시물 조회수 늘리기
//    public void plusViewCount(Long postingCode) {
//        Post post = postRepository.findByPostingCode(postingCode);
//
//        //조회수 늘리기
//        Integer viewCount = post.getViewCount() + 1;
//        post.setViewCount(viewCount);
//        postRepository.save(post);
//    }

    //게시물 저장하기 (신규)
    public Post savePost(String userEmailID, PostDto.Request dto) {

        User user = userRepository.findByUserEmailId(userEmailID);
        dto.setUser(user);

        Post post = dto.toEntity();

        return postRepository.save(post);
    }

    //게시물 저장하기 (수정)
    public Post eidtPost(Long postingCode, PostDto.Request dto) {
        Post post = postRepository.findById(postingCode).get();
        Category category = categoryRepository.findById(dto.getCategory().getCategoryCode()).get();

        post.updatePost(category, dto.getPostingTitle(), dto.getEditor4());
        return postRepository.save(post);
    }

    //게시물 삭제
    public void deletePost(Long postingCode) {
        postRepository.deleteById(postingCode);
    }

    //카테고리 리스트 가져오기
    public List<Category> getcategoryList() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "categoryCode"));
    }

    //댓글 저장
    public void saveReply(Long postingCode, String userEmailID, ReplyDto.Request dto) {
        User user = userRepository.findByUserEmailId(userEmailID);
        Post post = postRepository.findById(postingCode).get();

        dto.setUser(user);
        dto.setPost(post);

        replyRepository.save(dto.toEntity());
    }

    //댓글 삭제
    public void deleteReply(Long replyCode) {
        replyRepository.deleteById(replyCode);
    }
}
