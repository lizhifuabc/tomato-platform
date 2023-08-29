package com.tomato.seckill.domain;

/**
 * 缓存通用类
 *
 * @author lizhifu
 * @since 2023/7/15
 */
public class CommonCache<T> {

	private T data;

	/**
	 * 缓存数据是否存在
	 */
	protected boolean exist;

	/**
	 * 缓存版本
	 */
	protected Long version;

	/**
	 * 稍后再试
	 */
	protected boolean retryLater;

	public CommonCache<T> with(T data) {
		this.data = data;
		this.exist = true;
		return this;
	}

	public CommonCache<T> withVersion(Long version) {
		this.version = version;
		return this;
	}

	public CommonCache<T> retryLater() {
		this.retryLater = true;
		return this;
	}

	public CommonCache<T> notExist() {
		this.exist = false;
		return this;
	}

	public T getData() {
		return data;
	}

	public boolean isExist() {
		return exist;
	}

	public Long getVersion() {
		return version;
	}

	public boolean isRetryLater() {
		return retryLater;
	}

	@Override
	public String toString() {
		return "CommonCache{" + "data=" + data + ", exist=" + exist + ", version=" + version + ", retryLater="
				+ retryLater + '}';
	}

}