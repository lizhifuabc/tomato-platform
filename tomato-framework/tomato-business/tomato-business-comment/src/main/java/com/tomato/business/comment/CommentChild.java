package com.tomato.business.comment;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

/**
 * 二级评论
 *
 * @author lizhifu
 * @since 2023/9/5
 */
@Getter
@Setter
@Entity
public class CommentChild {
	/**
	 * id
	 */
	private Long id;

	/**
	 * 条目id
	 */
	private Long itemId;

	/**
	 * 一级评论id
	 */
	private Long parentId;

	/**
	 * 被回复的评论id（null 回复父级评论，有则是回复 replyId 的评论）
	 */
	private Long replyId;

	/**
	 * 评论人id
	 */
	private Long userId;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 点赞数
	 */
	private Integer likeCount;

	/**
	 * 是否为发布者
	 */
	private int isPublisher;

	/**
	 * 是否删除
	 */
	private int isDelete;

	/**
	 * 创建时间
	 */
	private LocalTime createTime;
}
