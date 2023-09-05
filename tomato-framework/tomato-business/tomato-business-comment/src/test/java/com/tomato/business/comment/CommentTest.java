package com.tomato.business.comment;

import java.time.LocalTime;

/**
 * 二级评论测试
 *
 * @author lizhifu
 * @since 2023/9/5
 */
public class CommentTest {
	public static void main(String[] args) {

	}

	/**
	 * 一级评论
	 * @return CommentParent 一级评论
	 */
	public static CommentParent commentParent(){
		CommentParent commentParent = new CommentParent();
		commentParent.setId(1L);
		commentParent.setItemId(1L);
		commentParent.setUserId(1L);
		commentParent.setContent("一级评论");
		commentParent.setLikeCount(1);
		commentParent.setIsPublisher(1);
		commentParent.setIsDelete(1);
		commentParent.setCreateTime(LocalTime.now());
		return commentParent;
	}
	/**
	 * 二级评论
	 * @param commentParent 一级评论
	 * @param isReply 是否回复的别人评论
	 * @return CommentChild 二级评论
	 */
	public static CommentChild commentChild(CommentParent commentParent,boolean isReply){
		CommentChild commentChild = new CommentChild();
		commentChild.setId(1L);
		commentChild.setItemId(1L);
		commentChild.setParentId(commentParent.getId());
		commentChild.setReplyId(commentParent.getId());
		commentChild.setUserId(1L);
		commentChild.setContent("二级评论");
		commentChild.setLikeCount(1);
		commentChild.setIsPublisher(1);
		commentChild.setIsDelete(1);
		commentChild.setCreateTime(LocalTime.now());
		return commentChild;
	}
}
