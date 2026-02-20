package com.afivestudio.anipia.user.query;

import com.afivestudio.anipia.user.domain.exception.UserErrorCode;
import com.afivestudio.anipia.user.domain.exception.UserException;
import com.afivestudio.anipia.user.query.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "会員関連API")
@RequiredArgsConstructor
@Validated
@RequestMapping("/users")
@RestController
public class UserQueryController {

    private final UserQueryMapper mapper;

    @Parameter(
            name = "sort",
            description = "並び替え基準（形式：フィールド名,asc|desc)",
            in = ParameterIn.QUERY,
            array = @ArraySchema(schema = @Schema(
                    type = "string",
                    allowableValues = {"userId", "email", "nickname", "updatedAt"}
            ))
    )
    @Operation(operationId = "searchUsers", summary = "会員一覧取得", description = "会員の一覧を取得します。")
    @GetMapping
    public PagedModel<UserDto> search(
            @ParameterObject UserSearchCondition condition,
            @ParameterObject @PageableDefault(size = 20) Pageable pageable
    ) {
        List<UserDto> list = mapper.search(condition, pageable);
        long count = mapper.count(condition);

        PageImpl<UserDto> page = new PageImpl<>(list, pageable, count);

        return new PagedModel<>(page);
    }

    @Operation(operationId = "getMe", summary = "自分の情報取得", description = "ログイン中の会員情報を取得します。")
    @GetMapping("/me")
    public MeDto getUser(@AuthenticationPrincipal long userId) {
        UserDto userInfo = mapper.findOneById(userId);
        long totalReviewCount = mapper.countReviews(userId);
        long totalLikeCount = mapper.countLikes(userId);
        return new MeDto(userInfo, totalReviewCount, totalLikeCount);
    }

    @Operation(operationId = "checkEmail", summary = "メールアドレス重複確認", description = "メールアドレスの重複有無を確認します。")
    @GetMapping("/check-email")
    public ExistsDto existsByEmail(
            @RequestParam
            @Pattern(regexp = ".*\\S.*", message = "メールアドレスは空白にできません。")
            @Email(message = "メールアドレスの形式が正しくありません。")
            String email
    ) {
        boolean isAvailable = !mapper.existsByEmail(email);
        return new ExistsDto(isAvailable);
    }

    @Operation(operationId = "checkNickname", summary = "ニックネーム重複確認", description = "ニックネームの重複有無を確認します。")
    @GetMapping("/check-nickname")
    public ExistsDto existsByNickname(
            @RequestParam
            @Pattern(regexp = ".*\\S.*", message = "ニックネームは空白にできません。")
            String nickname
    ) {
        boolean isAvailable = !mapper.existsByNickname(nickname);
        return new ExistsDto(isAvailable);
    }

    @Parameter(
            name = "sort",
            description = "並び替え基準（形式：フィールド名,asc|desc)",
            in = ParameterIn.QUERY,
            array = @ArraySchema(schema = @Schema(
                    type = "string",
                    allowableValues = {"reviewId", "likeCount", "updateAt"}
            ))
    )
    @Operation(operationId = "searchUserReviews", summary = "会員レビュー一覧取得", description = "会員が登録したレビューの一覧を取得します。")
    @GetMapping("/{userId}/reviews")
    public PagedModel<UserReviewDto> searchReviews(
            @AuthenticationPrincipal long currentUserId,
            @PathVariable @Positive long userId,
            @ParameterObject @PageableDefault(size = 20) Pageable pageable
    ) {
        if (currentUserId != userId) {
            throw new UserException(UserErrorCode.FORBIDDEN);
        }

        List<UserReviewDto> contents = mapper.searchReviews(userId, pageable);
        long count = mapper.countReviews(userId);

        return new PagedModel<>(new PageImpl<>(contents, pageable, count));
    }

    @Parameter(
            name = "sort",
            description = "並び替え基準（形式：フィールド名,asc|desc)",
            in = ParameterIn.QUERY,
            array = @ArraySchema(schema = @Schema(
                    type = "string",
                    allowableValues = {"rating", "bookmarkedAt"}
            ))
    )
    @Operation(operationId = "searchUserBookmarks", summary = "会員ブックマーク一覧取得", description = "会員がブックマークしたアニメ一覧を取得します。")
    @GetMapping("/{userId}/bookmarks")
    public PagedModel<UserBookmarkDto> searchBookmarks(
            @AuthenticationPrincipal long currentUserId,
            @PathVariable @Positive long userId,
            @ParameterObject @PageableDefault(size = 20) Pageable pageable
    ) {
        if (currentUserId != userId) {
            throw new UserException(UserErrorCode.FORBIDDEN);
        }

        List<UserBookmarkDto> contents = mapper.searchBookmarks(userId, pageable);
        long count = mapper.countBookmarks(userId);

        return new PagedModel<>(new PageImpl<>(contents, pageable, count));
    }

    @Parameter(
            name = "sort",
            description = "並び替え基準（形式：フィールド名,asc|desc)",
            in = ParameterIn.QUERY,
            array = @ArraySchema(schema = @Schema(
                    type = "string",
                    allowableValues = {"reviewCreatedAt", "reviewLikeCount", "reviewUpdatedAt", "likedAt"}
            ))
    )
    @Operation(operationId = "searchUserLikes", summary = "会員いいね一覧取得", description = "会員が「いいね」したレビュー一覧を取得します。")
    @GetMapping("/{userId}/likes")
    public PagedModel<UserReviewDto> searchLikes(
            @AuthenticationPrincipal long currentUserId,
            @PathVariable @Positive long userId,
            @ParameterObject @PageableDefault(size = 20) Pageable pageable
    ) {
        if (currentUserId != userId) {
            throw new UserException(UserErrorCode.FORBIDDEN);
        }

        List<UserReviewDto> contents = mapper.searchLikes(userId, pageable);
        long count = mapper.countLikes(userId);

        return new PagedModel<>(new PageImpl<>(contents, pageable, count));
    }
}
