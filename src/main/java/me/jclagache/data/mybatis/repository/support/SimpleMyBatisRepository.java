package me.jclagache.data.mybatis.repository.support;

import me.jclagache.data.mybatis.repository.MyBatisRepository;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Specifies Default binding for findOne, findAll, exists, count methods
 * between org.springframework.data.mybatis.repository.MyBatisRepository and mybatis mapper
 */
public class SimpleMyBatisRepository<T, ID extends Serializable> implements MyBatisRepository<T, ID> {
	
	protected final SqlSessionTemplate sessionTemplate;
	protected final String mappedStatementId;
	
	public SimpleMyBatisRepository(SqlSessionTemplate sessionTemplate, String mappedStatementNamespace) {
		Assert.notNull(sessionTemplate, "SqlSessionTemplate must not be null!");
		Assert.notNull(mappedStatementNamespace, "mappedStatementNamespace must not be null!");
		this.sessionTemplate = sessionTemplate;
		this.mappedStatementId = mappedStatementNamespace + ".find";
	}
	
	@Override
	public T findOne(ID id) {
		Map<String, ID> params = new HashMap<>();
		params.put("pk", id);
		return sessionTemplate.selectOne(mappedStatementId, params);
	}

	@Override
	public Iterable<T> findAll() {
		Map<String, ID> params = new HashMap<>();
		return sessionTemplate.selectList(mappedStatementId, params);
	}

	@Override
	public boolean exists(ID id) {
		return findOne(id) != null;
	}

	@Override
	public long count() {
		//FIXME: query for count, don't pull all the results back
		Map<String, ID> params = new HashMap<>();
		return sessionTemplate.selectList(mappedStatementId, params).size();
	}

}
