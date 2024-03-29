package com.tomato.notice.mapper;

import com.tomato.mybatis.domain.Sort;
import com.tomato.mybatis.paginate.Page;
import com.tomato.notice.entity.NoticeRecordHistoryEntity;
import com.tomato.notice.entity.NoticeRuleEntity;
import jakarta.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

/**
 * NoticeRecordHistoryMapper
 *
 * @author lizhifu
 * @since 2023/5/18
 */
@SpringBootTest
public class NoticeRecordHistoryMapperTest {

	@Resource
	NoticeRecordHistoryMapper noticeRecordHistoryMapper;

	@Resource
	NoticeRuleMapper noticeRuleMapper;

	@Test
	public void count() {
		// 统计相关测试
		System.out.println("统计:" + noticeRecordHistoryMapper.count());
		System.out.println("统计:" + noticeRuleMapper.count());

		NoticeRecordHistoryEntity count = new NoticeRecordHistoryEntity();
		count.setNoticeRecordId(2L);
		System.out.println("countByCriteria:" + noticeRecordHistoryMapper.countByCriteria(count));

		NoticeRuleEntity noticeRule = new NoticeRuleEntity();
		System.out.println("countByCriteria:" + noticeRuleMapper.countByCriteria(noticeRule));
	}

	@Test
	public void updateByPrimaryKey() {
		// 更新相关测试
		NoticeRecordHistoryEntity update = new NoticeRecordHistoryEntity();
		update.setId(6L);
		update.setCreateTime(LocalDateTime.now());
		update.setNoticeRecordId(1L);
		System.out.println("更新:" + noticeRecordHistoryMapper.updateByPrimaryKey(update));
	}

	@Test
	public void insert() {
		NoticeRecordHistoryEntity insert = new NoticeRecordHistoryEntity();
		insert.setNoticeRecordId(1L);
		insert.setNoticeResult("insert");
		insert.setCreateTime(LocalDateTime.now());
		System.out.println("插入:" + noticeRecordHistoryMapper.insert(insert));
	}

	@Test
	public void batchInsertSelective() {
		NoticeRecordHistoryEntity batch1 = new NoticeRecordHistoryEntity();
		batch1.setNoticeRecordId(1L);
		batch1.setNoticeResult("batch1");
		batch1.setCreateTime(LocalDateTime.now());

		NoticeRecordHistoryEntity batch2 = new NoticeRecordHistoryEntity();
		batch2.setNoticeRecordId(1L);
		batch2.setNoticeResult("batch2");
		batch2.setCreateTime(LocalDateTime.now());
		noticeRecordHistoryMapper.batchInsertSelective(List.of(batch1, batch2));
	}

	@Test
	public void batchInsert() {
		NoticeRecordHistoryEntity batch1 = new NoticeRecordHistoryEntity();
		batch1.setNoticeRecordId(1L);
		batch1.setNoticeResult("batchInsert1");
		batch1.setCreateTime(LocalDateTime.now());

		NoticeRecordHistoryEntity batch2 = new NoticeRecordHistoryEntity();
		batch2.setNoticeRecordId(1L);
		batch2.setNoticeResult("batchInsert2");
		batch2.setCreateTime(LocalDateTime.now());
		noticeRecordHistoryMapper.batchInsert(List.of(batch1, batch2));
	}

	@Test
	public void selectByPrimaryKeyIn() {
		noticeRecordHistoryMapper.selectByPrimaryKeyIn(List.of(1L, 2L)).forEach(System.out::println);
		noticeRecordHistoryMapper.selectByPrimaryKeyIn(List.of(11L, 12L)).forEach(System.out::println);
	}

	@Test
	public void selectPageByCriteria() {
		Sort sort = Sort.by("id", Sort.Direction.ASC).and("create_time", Sort.Direction.DESC);
		// 分页查询
		RowBounds rowBounds = new RowBounds(0, 10);
		Page page = new Page(0, 10, sort);
		noticeRecordHistoryMapper.selectPageByCriteria(page, new NoticeRecordHistoryEntity())
			.forEach(System.out::println);
	}

	@Test
	public void updateByPrimaryKeySelective() {
		NoticeRecordHistoryEntity update2 = new NoticeRecordHistoryEntity();
		update2.setId(1114L);
		update2.setCreateTime(LocalDateTime.now());
		update2.setNoticeRecordId(1L);
		update2.setNoticeResult("跟下游对账成功");
		System.out.println("更新:" + noticeRecordHistoryMapper.updateByPrimaryKeySelective(update2));
	}

	@Test
	public void selectByCriteria() {
		NoticeRecordHistoryEntity entity = new NoticeRecordHistoryEntity();

		entity.setId(5L);
		Sort sort = Sort.by("id", Sort.Direction.ASC).and("create_time", Sort.Direction.DESC);
		noticeRecordHistoryMapper.selectByCriteria(sort, entity).forEach(System.out::println);
	}

	@Test
	public void deleteByCriteria() {
		NoticeRecordHistoryEntity delete = new NoticeRecordHistoryEntity();
		delete.setNoticeRecordId(2L);
		System.out.println("删除:" + noticeRecordHistoryMapper.deleteByCriteria(delete));
	}

	@Test
	public void insertSelective() {
		// 插入相关测试
		NoticeRecordHistoryEntity insert1 = new NoticeRecordHistoryEntity();
		insert1.setNoticeRecordId(2L);
		insert1.setNoticeResult("insertSelective1");
		insert1.setId(1111L);
		noticeRecordHistoryMapper.insertSelective(insert1);
		System.out.println("insertSelective:返回ID：" + insert1.getId());

		insert1 = new NoticeRecordHistoryEntity();
		insert1.setNoticeRecordId(2222L);
		noticeRecordHistoryMapper.insertSelective(insert1);
	}

	@Test
	public void selectOneByCriteria() {
		NoticeRecordHistoryEntity entity = new NoticeRecordHistoryEntity();
		entity.setId(1114L);
		noticeRecordHistoryMapper.selectOneByCriteria(entity).ifPresent(System.out::println);
	}

	@Test
	public void test() {
		NoticeRecordHistoryEntity entity = new NoticeRecordHistoryEntity();

		entity.setId(5L);
		Sort sort = Sort.by("id", Sort.Direction.ASC).and("create_time", Sort.Direction.DESC);
		noticeRecordHistoryMapper.selectByCriteria(sort, entity).forEach(System.out::println);

		noticeRecordHistoryMapper.selectAll(sort).forEach(System.out::println);

		// 分页查询
		RowBounds rowBounds = new RowBounds(0, 10);
		Page page = new Page(1, 10, sort);
		noticeRecordHistoryMapper.selectPageByCriteria(page, new NoticeRecordHistoryEntity())
			.forEach(System.out::println);

		// 更新相关测试
		NoticeRecordHistoryEntity update = new NoticeRecordHistoryEntity();
		update.setId(6L);
		update.setCreateTime(LocalDateTime.now());
		update.setNoticeRecordId(1L);
		System.out.println("更新:" + noticeRecordHistoryMapper.updateByPrimaryKey(update));

		NoticeRecordHistoryEntity update2 = new NoticeRecordHistoryEntity();
		update2.setId(7L);
		update2.setCreateTime(LocalDateTime.now());
		update2.setNoticeRecordId(1L);
		System.out.println("更新:" + noticeRecordHistoryMapper.updateByPrimaryKeySelective(update2));

		// 删除相关测试
		System.out.println("删除:" + noticeRecordHistoryMapper.deleteByPrimaryKey(2L));

		NoticeRecordHistoryEntity delete = new NoticeRecordHistoryEntity();
		delete.setNoticeRecordId(3L);
		System.out.println("删除:" + noticeRecordHistoryMapper.deleteByCriteria(delete));

		System.out.println("删除:" + noticeRecordHistoryMapper.logicDeleteByPrimaryKey(4L));

		NoticeRecordHistoryEntity count = new NoticeRecordHistoryEntity();
		count.setNoticeRecordId(3L);
		System.out.println("统计:" + noticeRecordHistoryMapper.countByCriteria(count));

		// 插入相关测试
		NoticeRecordHistoryEntity insert1 = new NoticeRecordHistoryEntity();
		insert1.setNoticeRecordId(1L);
		insert1.setCreateTime(LocalDateTime.now());
		noticeRecordHistoryMapper.insertSelective(insert1);
		System.out.println("返回ID：" + insert1.getId());

		NoticeRecordHistoryEntity insert = new NoticeRecordHistoryEntity();
		insert.setNoticeRecordId(1L);
		insert.setCreateTime(LocalDateTime.now());
		noticeRecordHistoryMapper.insert(insert);
		System.out.println("返回ID：" + insert.getId());

		NoticeRecordHistoryEntity batch1 = new NoticeRecordHistoryEntity();
		batch1.setNoticeRecordId(1L);
		batch1.setCreateTime(LocalDateTime.now());

		NoticeRecordHistoryEntity batch2 = new NoticeRecordHistoryEntity();
		batch2.setNoticeRecordId(1L);
		batch2.setCreateTime(LocalDateTime.now());
		noticeRecordHistoryMapper.batchInsert(List.of(batch1, batch2));

		// 查询相关测试
		noticeRecordHistoryMapper.selectByPrimaryKey(2L).ifPresent(System.out::println);

		noticeRecordHistoryMapper.selectByPrimaryKeyIn(List.of(1L, 2L)).forEach(System.out::println);

		noticeRecordHistoryMapper.selectOneByCriteria(entity).ifPresent(System.out::println);

		entity.setNoticeRecordId(3L);
	}

}
