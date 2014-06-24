package com.eallard.basic.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;

import com.eallard.basic.dao.IBaseDao;
import com.eallard.basic.model.Pager;
import com.eallard.basic.model.SystemContext;
import com.eallard.cms.utils.StringUtil;

@SuppressWarnings("unchecked")
public class BaseDaoImpl<T> implements IBaseDao<T>{
	
	private SessionFactory sessionFactory;
	
	private Class<T> clz;
	
	public Class<T> getClz() {
		if(clz == null) {
			clz = ((Class<T>)(((ParameterizedType)
					(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
		}
		return clz;
	}
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Inject
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public T add(T t) {
		getSession().save(t);
		return t;
	}

	public void update(T t) {
		getSession().update(t);
	}

	public void delete(Serializable id) {
		getSession().delete(this.load(id));
	}

	public T load(Serializable id) {
		return (T)getSession().load(getClz(), id);
	}
	
	public T get(Serializable id) {
		return (T)getSession().get(getClz(), id);
    }

	/**
	 * 初始化排序信息
	 * @param hql
	 * @return
	 */
	private String initSort(String hql) {
		String sort = SystemContext.getSort();
		String order = SystemContext.getOrder();
		if(StringUtil.isNotEmpty(sort)) {
			hql += " order by " + sort;
			if("desc".equals(order)) {
				hql += " desc";
			}
		}
		return hql;
	}
	
	/**
	 * 初始化通配符参数
	 * @param query
	 * @param objs
	 */
	private void initArgs(Query query, Object[] objs) {
		if(objs != null) {
			for(int i=0; i<objs.length; i++) {
				query.setParameter(i, objs[i]);
			}
		}
	}
	
	/**
	 * 初始化别名参数
	 * @param query
	 * @param alias
	 */
	private void initAlias(Query query, Map<String, Object> alias) {
		if(alias != null) {
			Set<String> keys = alias.keySet();
			for(String key : keys) {
				Object value = alias.get(key);
				if(value instanceof Collection) {
					query.setParameterList(key, (Collection<Object>)value);
				} else {
					query.setParameter(key, value);
				}
			}
		}
	}
	
	/**
	 * 设置分页查询
	 * @param query
	 * @param pager
	 */
	@SuppressWarnings("rawtypes")
	private void setPager(Query query, Pager pager) {
		Integer pageSize = SystemContext.getPageSize();
		Integer pageStart = SystemContext.getPageStart();
		
		if(pageSize == null || pageSize <= 0) {
			pageSize = 15;//需要配置
		}
		
		if(pageStart == null || pageStart <= 0) {
			pageStart = 0;
		}
		
		query.setFirstResult(pageStart).setMaxResults(pageSize);
		
		pager.setSize(pageSize);
		pager.setStart(pageStart);
		pager.setData(query.list());
	}
	
	/**
	 * 获取查询总数量hql
	 * @param hql
	 * @return
	 */
	private String getCountHql(String hql, boolean isHQL) {
		String end = hql.substring(hql.indexOf("from"));
		String result = "select count(*) " + end;
		if(isHQL) {
			result.replaceAll("fetch", "");
		}
		return result;
	}

	public Object queryObject(String hql) {
		Query query = getSession().createQuery(hql);
		return query.uniqueResult();
	}


	public Object queryObject(String hql, Object objs) {
		return queryObject(hql, new Object[]{objs}, null);
	}


	public Object queryObject(String hql, Object[] objs) {
		return queryObject(hql, objs, null);
	}

	public Object queryObject(String hql, Map<String, Object> alias) {
		return queryObject(hql, null, alias);
	}

	public Object queryObject(String hql, Object[] objs,
			Map<String, Object> alias) {
		
		Query query = getSession().createQuery(hql);
		
		/** 设置排序方式 */
		hql = initSort(hql);
		
		/** 设置通配符参数 */
		initArgs(query, objs);
		
		/** 设置别名参数 */
		initAlias(query, alias);
		
		return query.uniqueResult();
	}


	public List<T> list(String hql) {
		return list(hql, null, null);
	}

	public List<T> list(String hql, Object args) {
		return list(hql, new Object[]{args}, null);
	}

	public List<T> list(String hql, Object[] args) {
		return list(hql, args, null);
	}

	public List<T> list(String hql, Map<String, Object> alias) {
		return list(hql, null, alias);
	}

	public List<T> list(String hql, Object[] args, Map<String, Object> alias) {
		
		Query query = getSession().createQuery(hql);
		
		/** 设置排序方式 */
		hql = initSort(hql);
		
		/** 设置通配符参数 */
		initArgs(query, args);
		
		/** 设置别名参数 */
		initAlias(query, alias);
		
		return query.list();
	}

	public Pager<T> find(String hql) {
		return this.find(hql, null, null);
	}

	public Pager<T> find(String hql, Object args) {
		return this.find(hql, new Object[]{args}, null);
	}

	public Pager<T> find(String hql, Object[] args) {
		return this.find(hql, args, null);
	}

	public Pager<T> find(String hql, Map<String, Object> alias) {
		return this.find(hql, null, alias);
	}

	public Pager<T> find(String hql, Object[] args, Map<String, Object> alias) {
		Query query = getSession().createQuery(hql);
		
		/** 设置排序方式 */
		hql = initSort(hql);
		
		/** 设置通配符参数 */
		initArgs(query, args);
		
		/** 设置别名参数 */
		initAlias(query, alias);
		
		Pager<T> pager = new Pager<T>();
		
		setPager(query, pager);
		
		String countHql = getCountHql(hql, true);
		
		Query countQuery = getSession().createQuery(countHql);
		
		pager.setTotal((Long)countQuery.uniqueResult());
		
		return pager;
	}

	public List<Object> listBySql(String sql, Class<Object> clz, boolean hasEntity) {
		return listBySql(sql, null, null, clz, hasEntity);
	}

	public List<Object> listBySql(String sql, Object args, Class<Object> clz,
			boolean hasEntity) {
		return listBySql(sql, new Object[]{args}, null, clz, hasEntity);
	}

	public List<Object> listBySql(String sql, Object[] args, Class<Object> clz,
			boolean hasEntity) {
		return listBySql(sql, args, null, clz, hasEntity);
	}

	public List<Object> listBySql(String sql, Map<String, Object> alias,
			Class<Object> clz, boolean hasEntity) {
		return listBySql(sql, null, alias, clz, hasEntity);
	}

	public List<Object> listBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<Object> clz, boolean hasEntity) {
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		
		/** 设置排序方式 */
		sql = initSort(sql);
		
		/** 设置通配符参数 */
		initArgs(sqlQuery, args);
		
		/** 设置别名参数 */
		initAlias(sqlQuery, alias);
		
		if(hasEntity) {
			sqlQuery.addEntity(clz);
		} else {
			sqlQuery.setResultTransformer(Transformers.aliasToBean(clz));
		}
		
		return sqlQuery.list();
	}

	public Pager<Object> findBySql(String sql, Class<Object> clz, boolean hasEntity) {
		return this.findBySql(sql, null, null, clz, hasEntity);
	}

	public Pager<Object> findBySql(String sql, Object args, Class<Object> clz,
			boolean hasEntity) {
		return this.findBySql(sql, new Object[]{args}, null, clz, hasEntity);
	}

	public Pager<Object> findBySql(String sql, Object[] args, Class<Object> clz,
			boolean hasEntity) {
		return this.findBySql(sql, args, null, clz, hasEntity);
	}

	public Pager<Object> findBySql(String sql, Map<String, Object> alias,
			Class<Object> clz, boolean hasEntity) {
		return this.findBySql(sql, null, alias, clz, hasEntity);
	}

	public Pager<Object> findBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<Object> clz, boolean hasEntity) {
		
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		
		/** 设置排序方式 */
		sql = initSort(sql);
		
		/** 设置通配符参数 */
		initArgs(sqlQuery, args);
		
		/** 设置别名参数 */
		initAlias(sqlQuery, alias);
		
		if(hasEntity) {
			sqlQuery.addEntity(clz);
		} else {
			sqlQuery.setResultTransformer(Transformers.aliasToBean(clz));
		}
		
		Pager<Object> pager = new Pager<Object>();
		
		setPager(sqlQuery, pager);
		
		String countSql = getCountHql(sql, false);
		
		Query countQuery = getSession().createQuery(countSql);
		
		pager.setTotal(((BigInteger)countQuery.uniqueResult()).longValue());
		
		return pager;
	}

	public Pager<Object> findBySql4Page(String sql, String countSQL,
			Class<Object> clz, boolean hasEntity) {
		return this.findBySql4Page(sql, countSQL, null, null, clz, hasEntity);
	}

	public Pager<Object> findBySql4Page(String sql, String countSQL, Object args,
			Class<Object> clz, boolean hasEntity) {
		return this.findBySql4Page(sql, countSQL, new Object[]{args}, null, clz, hasEntity);
	}

	public Pager<Object> findBySql4Page(String sql, String countSQL, Object[] args,
			Class<Object> clz, boolean hasEntity) {
		return this.findBySql4Page(sql, countSQL, args, null, clz, hasEntity);
	}

	public Pager<Object> findBySql4Page(String sql, String countSQL,
			Map<String, Object> alias, Class<Object> clz, boolean hasEntity) {
		return this.findBySql4Page(sql, countSQL, null, alias, clz, hasEntity);
	}

	public Pager<Object> findBySql4Page(String sql, String countSQL, Object[] args,
			Map<String, Object> alias, Class<Object> clz, boolean hasEntity) {
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		
		/** 设置排序方式 */
		sql = initSort(sql);
		
		/** 设置通配符参数 */
		initArgs(sqlQuery, args);
		
		/** 设置别名参数 */
		initAlias(sqlQuery, alias);
		
		if(hasEntity) {
			sqlQuery.addEntity(clz);
		} else {
			sqlQuery.setResultTransformer(Transformers.aliasToBean(clz));
		}
		
		Pager<Object> pager = new Pager<Object>();
		
		setPager(sqlQuery, pager);
		
		SQLQuery countQuery = getSession().createSQLQuery(countSQL);
		
		pager.setTotal(((BigInteger)countQuery.uniqueResult()).longValue());
		
		return pager;
	}

	public int updateByHql(String hql) {
		return this.updateByHql(hql, null, null);
	}

	public int updateByHql(String hql, Object args) {
		return this.updateByHql(hql, new Object[]{args}, null);
	}

	public int updateByHql(String hql, Object[] args) {
		return this.updateByHql(hql, args, null);
	}

	public int updateByHql(String hql, Map<String, Object> alias) {
		return this.updateByHql(hql, null, alias);
	}

	public int updateByHql(String hql, Object[] args, Map<String, Object> alias) {
		
		Query query = this.getSession().createQuery(hql);
		
		/** 设置通配符参数 */
		initArgs(query, args);
		
		/** 设置别名参数 */
		initAlias(query, alias);
		
		return query.executeUpdate();
	}
	
}
