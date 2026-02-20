package com.afivestudio.anipia.user.query;

import com.afivestudio.anipia.user.query.dto.UserBookmarkDto;
import com.afivestudio.anipia.user.query.dto.UserDto;
import com.afivestudio.anipia.user.query.dto.UserReviewDto;
import com.afivestudio.anipia.user.query.dto.UserSearchCondition;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

@Mapper
interface UserQueryMapper {

    List<UserDto> search(
            @Param("cond") UserSearchCondition cond,
            @Param("pageable") Pageable pageable
    );

    long count(@Param("cond") UserSearchCondition cond);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    // user reviews
    List<UserReviewDto> searchReviews(long userId, Pageable pageable);

    long countReviews(long userId);


    // user likes
    List<UserReviewDto> searchLikes(long userId, Pageable pageable);

    long countLikes(long userId);


    // user bookmarks
    List<UserBookmarkDto> searchBookmarks(long userId, Pageable pageable);

    long countBookmarks(long userId);

    UserDto findOneById(long userId);
}
