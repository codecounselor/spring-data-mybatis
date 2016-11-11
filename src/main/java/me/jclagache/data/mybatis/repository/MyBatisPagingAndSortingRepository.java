package me.jclagache.data.mybatis.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * Created by Nate Good on 11/11/16.
 */
@NoRepositoryBean
public interface MyBatisPagingAndSortingRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T,ID> {


}
