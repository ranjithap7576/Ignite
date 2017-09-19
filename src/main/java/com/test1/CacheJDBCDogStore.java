/**
 * 
 */
package com.test1;

import java.sql.Date;

import javax.cache.Cache.Entry;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;

import org.apache.ignite.cache.store.CacheStoreAdapter;
import org.apache.ignite.cache.store.CacheStoreSession;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.apache.ignite.resources.CacheStoreSessionResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * @author ranjithpu
 *
 */

@Component
public class CacheJDBCDogStore extends CacheStoreAdapter<Long, Dog> {

	@CacheStoreSessionResource
	private CacheStoreSession ses;

	MongoTemplate mongoTemplate = ApplicationContextProvider.getApplicationContext().getBean("mongoTemplate",
			MongoTemplate.class);

	// Complete transaction or simply close connection if there is no transaction.
	@Override
	public void sessionEnd(boolean commit) {
	}

	@Override
	public Dog load(Long arg0) throws CacheLoaderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Object arg0) throws CacheWriterException {
		// TODO Auto-generated method stub
			mongoTemplate.remove(arg0);

	}

	@Override
	public void write(Entry<? extends Long, ? extends Dog> entries) throws CacheWriterException {
		mongoTemplate.save(entries.getValue());

	}

	@Override
	public void loadCache(IgniteBiInClosure<Long, Dog> clo, Object... args) {
		super.loadCache(clo, args);

	}

}
