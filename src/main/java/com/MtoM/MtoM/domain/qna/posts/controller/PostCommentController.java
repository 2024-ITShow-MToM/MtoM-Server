package com.MtoM.MtoM.domain.qna.posts.controller;

import com.MtoM.MtoM.domain.qna.posts.dto.CreatePostComment;
import com.MtoM.MtoM.domain.qna.posts.dto.UpdatePostComment;
import com.MtoM.MtoM.domain.qna.posts.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/comments")
@RequiredArgsConstructor
public class PostCommentController {

    private final PostCommentService postCommentService;


    // 댓글 생성 API
    @PostMapping
    public ResponseEntity<String> createComment(@RequestBody CreatePostComment createPostComment) {
        postCommentService.createComment(createPostComment);
        return ResponseEntity.status(HttpStatus.CREATED).body("댓글이 성공적으로 생성되었습니다.");
    }

    // 댓글 수정 API
    @PatchMapping("/{commentId}")
    public ResponseEntity<String> updateComment(
            @PathVariable Long commentId,
            @RequestParam String userId,
            @RequestBody UpdatePostComment updateDTO) {

        boolean isUpdated = postCommentService.updateComment(userId, commentId, updateDTO);
        if (isUpdated) {
            return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("댓글 수정 권한이 없습니다.");
        }
    }

    // 댓글 삭제 API
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@RequestParam String userId, @PathVariable Long commentId) {
        if (postCommentService.deleteComment(userId, commentId)) {
            return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("댓글 삭제 권한이 없습니다.");
        }
    }

    @PostMapping("/{commentId}/heart")
    public ResponseEntity<String> togglePostCommentHeart(@PathVariable Long commentId, @RequestParam String userId) {
        postCommentService.togglePostCommentHeart(userId, commentId);
        return ResponseEntity.ok("Heart toggled successfully.");
    }
}