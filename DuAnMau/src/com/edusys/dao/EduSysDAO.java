/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.dao;

import java.util.List;

/**
 *
 * @author dangd
 */
abstract public class EduSysDAO<EntityType, KeyType> {

    abstract public void insert(EntityType entity);

    abstract public void update(EntityType entity);

    abstract public void delete(KeyType id);

    abstract public EntityType selectByID(KeyType id);

    abstract public List<EntityType> selectAll();

    abstract protected List<EntityType> selectBySql(String sql, Object... args);

}
