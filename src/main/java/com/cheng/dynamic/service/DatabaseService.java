package com.cheng.dynamic.service;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheng.dynamic.repository.primary.Test1Dao;

@Service
@Transactional("transactionManagerPrimary")
public class DatabaseService {
	@Autowired
	private Test1Dao test1Dao;
	@Autowired 
    @Qualifier("secondaryDS")
    private DataSource secondaryDS;
	
	private Map<Object, Object> map = new HashMap<>();
	
	public Map<Object,Object> getMap(){
		map.put("secondaryDS", secondaryDS);
		return map;
	}
}
