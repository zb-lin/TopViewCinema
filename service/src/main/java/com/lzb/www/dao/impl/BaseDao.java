package com.lzb.www.dao.impl;

import lorm.factory.query.Query;
import lorm.factory.query.QueryFactory;

/**
 * @author lzb
 */
public class BaseDao {
    protected static final Query query = QueryFactory.createQuery();
}
