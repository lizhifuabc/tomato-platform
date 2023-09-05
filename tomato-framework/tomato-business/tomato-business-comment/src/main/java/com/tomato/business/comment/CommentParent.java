package com.tomato.business.comment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

/**
 * 一级评论
 *
 * @author lizhifu
 * @since 2023/9/5
 */
@Getter
@Setter
public class CommentParent {
	/**
	 * id
	 */
	private Long id;

	/**
	 * 条目id
	 */
	private Long itemId;

	/**
	 * 用户id
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
