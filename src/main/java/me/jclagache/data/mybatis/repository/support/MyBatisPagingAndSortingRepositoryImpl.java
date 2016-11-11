package me.jclagache.data.mybatis.repository.support;

import me.jclagache.data.mybatis.repository.MyBatisPagingAndSortingRepository;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nate Good on 11/11/16.
 */
public class MyBatisPagingAndSortingRepositoryImpl<T, ID extends Serializable>
    extends SimpleMyBatisRepository<T,ID>
    implements MyBatisPagingAndSortingRepository<T,ID> {


    public MyBatisPagingAndSortingRepositoryImpl(SqlSessionTemplate sessionTemplate, String mappedStatementNamespace) {
        super(sessionTemplate, mappedStatementNamespace);
    }

    @Override
    public Iterable findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        Map<String, ID> params = new HashMap<>();
        List<T> results = sessionTemplate.selectList(mappedStatementId, params);
        return new PageImpl(results, new PageRequest(pageable.getPageNumber()+1, 0), count());
    }

    public List<T> findAllList(Pageable pageable) {
        Map<String, ID> params = new HashMap<>();
        List<T> results = sessionTemplate.selectList(mappedStatementId, params);
        return results;
    }

    @Override
    public Object save(Object entity) {
        return null;
    }

    @Override
    public Iterable save(Iterable entities) {
        return null;
    }

    @Override
    public boolean exists(Serializable serializable) {
        return false;
    }

    @Override
    public Iterable<T> findAll() {
        return null;
    }

    @Override
    public Iterable findAll(Iterable iterable) {
        return null;
    }

    @Override
    public void delete(Serializable serializable) {

    }

    @Override
    public void delete(Object entity) {

    }

    @Override
    public void delete(Iterable entities) {

    }

    @Override
    public void deleteAll() {

    }
}
