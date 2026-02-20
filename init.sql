-- 데이터베이스 생성 및 선택 (필요시 사용)
CREATE DATABASE IF NOT EXISTS anipia CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE anipia;

-- 1. 유저 테이블 (users)
CREATE TABLE users (
                       user_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
                       email VARCHAR(255) NULL UNIQUE COMMENT '이메일 (UK)',
                       nickname VARCHAR(50) NULL UNIQUE COMMENT '닉네임 (UK)',
                       password VARCHAR(255) NOT NULL COMMENT '암호화된 비밀번호',
                       role ENUM('ROLE_USER', 'ROLE_ADMIN') NOT NULL DEFAULT 'ROLE_USER' COMMENT '권한',
                       profile_image_path VARCHAR(2048) NULL COMMENT '프로필 이미지 경로',
                       email_verified TINYINT(1) NOT NULL DEFAULT 0 COMMENT '이메일 인증 여부',
                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
                       updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
                       deleted_at DATETIME NULL COMMENT '삭제일(Soft Delete)'
) ENGINE=InnoDB COMMENT='유저 정보';

-- 3. 회사 테이블 (companies) - 애니메이션보다 먼저 생성 필요
CREATE TABLE companies (
                           company_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
                           name VARCHAR(100) NOT NULL COMMENT '회사명'
) ENGINE=InnoDB COMMENT='제작사/배급사 정보';

-- 2. 애니메이션 테이블 (animations)
CREATE TABLE animations (
                            animation_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
                            company_id INT UNSIGNED NULL COMMENT 'FK: 회사 ID',
                            title VARCHAR(255) NOT NULL COMMENT '제목',
                            image_path VARCHAR(2048) NULL COMMENT '포스터/썸네일 경로',
                            summary TEXT NULL COMMENT '줄거리',
                            season TINYINT UNSIGNED NULL COMMENT '시즌 정보',
                            review_count INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '리뷰 개수 캐싱',
                            bookmark_count INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '북마크 개수 캐싱',
                            rating_sum FLOAT NOT NULL DEFAULT 0.0 COMMENT '평점 합계 (평균 계산용)',
                            release_date DATETIME NULL COMMENT '방영/출시일',
                            CONSTRAINT fk_animations_company FOREIGN KEY (company_id) REFERENCES companies (company_id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB COMMENT='애니메이션 정보';

CREATE TABLE curated_animations (
                            curated_animation_id int(10) unsigned NOT NULL AUTO_INCREMENT,
                            animation_id int(10) unsigned DEFAULT NULL,
                            display_order int(10) unsigned NOT NULL,
                            created_at datetime NOT NULL DEFAULT NOW(),
                            PRIMARY KEY (curated_animation_id),
                            UNIQUE KEY display_order (display_order),
                            UNIQUE KEY animation_id (animation_id),
                            CONSTRAINT curated_animations_animations_FK FOREIGN KEY (animation_id) REFERENCES animations (animation_id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 6. 태그 테이블 (tags)
CREATE TABLE tags (
                      tag_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
                      text VARCHAR(50) NOT NULL UNIQUE COMMENT '태그명 (UK)'
) ENGINE=InnoDB COMMENT='태그 마스터';

-- 애니메이션-태그 연결 테이블 (animation_tags)
CREATE TABLE animation_tags (
                                animation_id INT UNSIGNED NOT NULL COMMENT 'FK: 애니메이션',
                                tag_id INT UNSIGNED NOT NULL COMMENT 'FK: 태그',
                                PRIMARY KEY (animation_id, tag_id),
                                CONSTRAINT fk_ani_tags_animation FOREIGN KEY (animation_id) REFERENCES animations (animation_id) ON DELETE CASCADE,
                                CONSTRAINT fk_ani_tags_tag FOREIGN KEY (tag_id) REFERENCES tags (tag_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='애니메이션-태그 매핑';

-- 4. 리뷰 테이블 (reviews)

CREATE TABLE reviews (
     review_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
     user_id INT UNSIGNED NOT NULL COMMENT 'FK: 유저',
     animation_id INT UNSIGNED NOT NULL COMMENT 'FK: 애니메이션',
     content TEXT NOT NULL COMMENT '리뷰 내용',
     rating FLOAT NOT NULL DEFAULT 0.0 COMMENT '평점',
     is_spoiler TINYINT(1) NOT NULL DEFAULT 0 COMMENT '스포일러 포함 여부',
     like_count INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '좋아요 개수 캐싱',
     -- 리뷰 신고 기능 추가
     report_count INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '신고 횟수',
     -- 신고 횟수가 n번이상이면 블라인드
     is_hidden TINYINT(1) NOT NULL DEFAULT 0 COMMENT '블라인드 여부',
     created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
     updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
     deleted_at DATETIME NULL COMMENT '삭제일(Soft Delete)',
     KEY idx_reviews_user (user_id),
     KEY idx_reviews_animation (animation_id),
     CONSTRAINT fk_reviews_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
     CONSTRAINT fk_reviews_animation FOREIGN KEY (animation_id) REFERENCES animations (animation_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='리뷰';

-- 7-1. 리뷰 좋아요 테이블 (review_likes)
CREATE TABLE review_likes (
                              review_like_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
                              user_id INT UNSIGNED NOT NULL COMMENT 'FK: 유저',
                              review_id INT UNSIGNED NOT NULL COMMENT 'FK: 리뷰',
                              created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
                              UNIQUE KEY uk_like_user_review (user_id, review_id), -- 중복 좋아요 방지
                              CONSTRAINT fk_likes_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
                              CONSTRAINT fk_likes_review FOREIGN KEY (review_id) REFERENCES reviews (review_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='리뷰 좋아요';

-- 7-2. 리뷰 신고 이력 테이블(review_reports)
CREATE TABLE review_reports(
    report_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    review_id INT UNSIGNED NOT NULL,
    user_id INT UNSIGNED NOT NULL,
    reason VARCHAR(100) NULL COMMENT '신고 사유',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_report_user_review (review_id,user_id),

    CONSTRAINT fk_reports_review FOREIGN KEY (review_id) REFERENCES reviews (review_id) ON DELETE CASCADE,
    CONSTRAINT fk_reports_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='리뷰 신고 이력';

-- 7-3. 리뷰 답글 테이블(review_comments)
CREATE TABLE review_comments (
    comment_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    review_id INT UNSIGNED NOT NULL COMMENT '어떤 리뷰에 달린 댓글인지',
    user_id INT UNSIGNED NOT NULL COMMENT '작성자',
    content VARCHAR(500) NOT NULL COMMENT '댓글 내용',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME NULL COMMENT '삭제일(Soft Delete)',

    CONSTRAINT fk_comments_review FOREIGN KEY (review_id) REFERENCES reviews (review_id) ON DELETE CASCADE,
    CONSTRAINT fk_comments_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='리뷰 답글(댓글)';

-- 8. 북마크 테이블 (bookmarks)
CREATE TABLE bookmarks (
                           bookmark_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
                           user_id INT UNSIGNED NOT NULL COMMENT 'FK: 유저',
                           animation_id INT UNSIGNED NOT NULL COMMENT 'FK: 애니메이션',
                           created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
                           UNIQUE KEY uk_bookmark_user_ani (user_id, animation_id), -- 중복 북마크 방지
                           CONSTRAINT fk_bookmarks_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
                           CONSTRAINT fk_bookmarks_animation FOREIGN KEY (animation_id) REFERENCES animations (animation_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='북마크';

-- 5. 문의사항 테이블 (inquiries)
CREATE TABLE inquiries (
                           inquiry_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
                           writer_email VARCHAR(255) NOT NULL COMMENT '작성자 이메일 (비회원 가능 고려)',
                           subject VARCHAR(255) NOT NULL COMMENT '제목',
                           content TEXT NOT NULL COMMENT '문의 내용',
                           type ENUM('GENERAL', 'ACCOUNT', 'BUG', 'OTHER') NOT NULL DEFAULT 'GENERAL' COMMENT '문의 유형',
                           is_resolved TINYINT(1) NOT NULL DEFAULT 0 COMMENT '처리 완료 여부',
                           created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
                           updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
                           deleted_at DATETIME NULL COMMENT '삭제일(Soft Delete)'
) ENGINE=InnoDB COMMENT='고객 문의사항';
