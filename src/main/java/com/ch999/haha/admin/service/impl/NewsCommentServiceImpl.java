package com.ch999.haha.admin.service.impl;

import com.ch999.haha.admin.document.mongo.NewsCommentBO;
import com.ch999.haha.admin.document.redis.CommentZanBO;
import com.ch999.haha.admin.entity.UserInfo;
import com.ch999.haha.admin.repository.mongo.NewsCommentRepository;
import com.ch999.haha.admin.repository.redis.CommentZanRepository;
import com.ch999.haha.admin.service.NewsCommentService;
import com.ch999.haha.admin.service.UserInfoService;
import com.ch999.haha.admin.vo.CommentReplyVO;
import com.ch999.haha.admin.vo.NewsCommentVO;
import com.ch999.haha.common.PageableVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hahalala
 */
@Service
public class NewsCommentServiceImpl implements NewsCommentService {

    @Resource
    private NewsCommentRepository newsCommentRepository;

    @Resource
    private CommentZanRepository commentZanRepository;

    @Resource
    private UserInfoService userInfoService;

    @Override
    public Boolean addComment(Integer newsId, String commentId, String content, Integer userId) {
        NewsCommentBO newsCommentBO;
        if (newsId != null) {
            newsCommentBO = new NewsCommentBO(newsId, content, userId);
        } else {
            NewsCommentBO replyComment = newsCommentRepository.findOne(commentId);
            NewsCommentBO replyReply = newsCommentRepository.findByHotReplyReplyId(commentId);
            newsCommentBO = replyComment == null ? replyReply : replyComment;
            if (newsCommentBO == null) {
                return false;
            }
            NewsCommentBO.addReply(newsCommentBO, content, userId, commentId);
        }
        newsCommentRepository.save(newsCommentBO);
        return true;
    }

    @Override
    public Boolean addZan(String id, Integer userId) {
        CommentZanBO one = commentZanRepository.findOne(id);
        if (one != null) {
            if (one.getZanUserList().parallelStream().anyMatch(li -> li.equals(userId))) {
                return false;
            } else {
                one.getZanUserList().add(userId);
            }
        } else {
            one = new CommentZanBO();
            one.setCommentId(id);
            List<Integer> zanList = new ArrayList<>();
            zanList.add(userId);
            one.setZanUserList(zanList);
        }
        NewsCommentBO newsCommentBO = newsCommentRepository.findOne(id);
        newsCommentBO = newsCommentBO != null ? newsCommentBO : newsCommentRepository.findByHotReplyReplyId(id);
        NewsCommentBO.updateZanCount(newsCommentBO, id, one.getZanUserList().size());
        newsCommentRepository.save(newsCommentBO);
        commentZanRepository.save(one);
        return true;
    }

    @Override
    public NewsCommentVO getNewsCommentList(Integer newsId, Pageable pageable, Integer userId) {
        Page<NewsCommentBO> allByNewsIdAndIsDel = newsCommentRepository.findAllByNewsIdAndIsDel(newsId, false, pageable);
        List<NewsCommentBO> commentList = allByNewsIdAndIsDel.getContent();
        commentList.forEach(li -> {
            //评论中评论的回复只显示未删除的点赞最多的三条
            if (li.getReplies().size() > 3) {
                li.setReplies(li.getReplies().stream()
                        //这里过滤后可能会空掉，报npe
                        .filter(de -> !de.getIsDel())
                        .sorted(Comparator.comparing(NewsCommentBO.Reply::getZan).reversed()).collect(Collectors.toList())
                        .subList(0, 3));
            }
            //评论字段赋值
            UserInfo userInfo = userInfoService.selectById(li.getUserId());
            li.setUserName(userInfo.getUsername());
            li.setAvatar(userInfo.getPicPath());
            if (li.getZan() > 0 && userId != null) {
                CommentZanBO zanInfo = commentZanRepository.findOne(li.getId());
                if (zanInfo != null && zanInfo.getZanUserList().parallelStream().anyMatch(zan -> zan.equals(userId))) {
                    li.setIsPraised(true);
                } else {
                    li.setIsPraised(false);
                }
            } else {
                li.setIsPraised(false);
            }
            //回复字段赋值
            li.getReplies().forEach(re -> handleReplies(re, userId));
        });
        return new NewsCommentVO(allByNewsIdAndIsDel, pageable.getPageNumber(), commentList);
    }

    @Override
    public CommentReplyVO getCommentReplies(String commentId, PageableVo pageable, Integer userId) {
        CommentReplyVO commentReplyVO = new CommentReplyVO(newsCommentRepository.findOne(commentId), pageable);
        if (commentReplyVO.getNewsCommentAndReply() != null) {
            UserInfo userInfo = userInfoService.selectById(commentReplyVO.getNewsCommentAndReply().getUserId());
            commentReplyVO.getNewsCommentAndReply().setAvatar(userInfo.getPicPath());
            commentReplyVO.getNewsCommentAndReply().setUserName(userInfo.getUsername());
            if (commentReplyVO.getNewsCommentAndReply().getZan() > 0 && userId != null) {
                CommentZanBO one = commentZanRepository.findOne(commentReplyVO.getNewsCommentAndReply().getId());
                if (one != null) {
                    commentReplyVO.getNewsCommentAndReply().setIsPraised(one.getZanUserList().parallelStream().anyMatch(li -> li.equals(userId)));
                } else {
                    commentReplyVO.getNewsCommentAndReply().setIsPraised(false);
                }
            } else {
                commentReplyVO.getNewsCommentAndReply().setIsPraised(false);
            }
            if (CollectionUtils.isNotEmpty(commentReplyVO.getNewsCommentAndReply().getReplies())) {
                commentReplyVO.getNewsCommentAndReply().setReplies(
                        commentReplyVO.getNewsCommentAndReply().getReplies().stream()
                                //这里过滤后可能会空掉，报npe
                                .filter(li -> !li.getIsDel())
                                .sorted(Comparator.comparing(NewsCommentBO.Reply::getZan).reversed())
                                .collect(Collectors.toList()));
                Integer replySize = commentReplyVO.getNewsCommentAndReply().getReplies().size();
                if (replySize >= pageable.getPage() * pageable.getSize() - pageable.getSize()) {
                    if (pageable.getPage() == 1) {
                        commentReplyVO.getNewsCommentAndReply().setReplies(
                                commentReplyVO.getNewsCommentAndReply().getReplies().subList(0, pageable.getSize() > replySize ? replySize : pageable.getSize()));
                    } else {
                        commentReplyVO.getNewsCommentAndReply().setReplies(
                                commentReplyVO.getNewsCommentAndReply().getReplies()
                                        .subList(pageable.getPage() * pageable.getSize() - pageable.getSize()
                                                , replySize > pageable.getPage() * pageable.getSize() ? pageable.getPage() * pageable.getSize() : replySize));
                    }
                } else {
                    commentReplyVO.getNewsCommentAndReply().setReplies(new ArrayList<>());
                }
                commentReplyVO.getNewsCommentAndReply().getReplies().forEach(li -> handleReplies(li, userId));
            }

        }
        return commentReplyVO;
    }

    private void handleReplies(NewsCommentBO.Reply re, Integer userId) {
        UserInfo replyUserInfo = userInfoService.selectById(re.getReplyUserId());
        UserInfo toUserInfo = userInfoService.selectById(re.getToUserId());
        re.setReplyUserAvatar(replyUserInfo.getPicPath());
        re.setReplyUserName(replyUserInfo.getUsername());
        re.setToUserName(toUserInfo.getUsername());
        if (re.getZan() > 0 && userId != null) {
            CommentZanBO toZanInfo = commentZanRepository.findOne(re.getReplyId());
            if (toZanInfo != null && toZanInfo.getZanUserList().parallelStream().anyMatch(z -> z.equals(userId))) {
                re.setIsPraised(true);
            } else {
                re.setIsPraised(false);
            }
        } else {
            re.setIsPraised(false);
        }
    }
}
