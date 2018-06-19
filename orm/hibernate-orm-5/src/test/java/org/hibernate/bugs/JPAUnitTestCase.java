package org.hibernate.bugs;

import java.util.Date;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.bugs.hhh12641.AccessibleResourcePojo;
import org.hibernate.bugs.hhh12641.DataBaseSourceResource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase extends Assert {
	private EntityManagerFactory entityManagerFactory;

	public JPAUnitTestCase() {
	    super();
    }

	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	public void hhh12641SeparateTransactionsTest() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
		    DataBaseSourceResource r1 = doInTransaction(entityManager, JPAUnitTestCase::createDataBaseSourceResource);
	        DataBaseSourceResource r2 = findById(entityManager, r1.getId(), DataBaseSourceResource.class);
		    assertNotNull("Cannot find persistence instance", r2);
		} finally {
		    entityManager.close();
		}
	}

    @Test
    public void hhh12641SingleTransactionsest() throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            doInTransaction(entityManager, JPAUnitTestCase::createAndFindDBResource);
        } finally {
            entityManager.close();
        }
    }

    private static DataBaseSourceResource createAndFindDBResource(EntityManager entityManager) {
        DataBaseSourceResource r1 = createDataBaseSourceResource(entityManager);
        Long id = r1.getId();
        DataBaseSourceResource r2 = entityManager.find(DataBaseSourceResource.class, id);
        assertNotNull("Cannot find persistence instance", r2);
        return r2;
    }

	private static DataBaseSourceResource createDataBaseSourceResource(EntityManager entityManager) {
	    DataBaseSourceResource r = initializeAccessibleResourcePojo(new DataBaseSourceResource());
	    r.setDbName("DB-" + new Date());
	    r.setQuery("SELECT COUNT(*) FROM " + r.getDbName());
	    entityManager.persist(r);
	    return r;
	}

	private static <A extends AccessibleResourcePojo> A initializeAccessibleResourcePojo(A pojo) {
	    pojo.setProtocol(pojo.getClass().getSimpleName());
	    pojo.setPath(pojo.getClass().getPackage().getName().replace('.', '/'));
	    return pojo;
	}

	private static <R> R findById(EntityManager entityManager, Long id, Class<R> entity) {
	    return doInTransaction(entityManager, em -> em.find(entity, id));
	}

	private static <R> R doInTransaction(EntityManager entityManager, Function<? super EntityManager, ? extends R> handler) {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        R result = handler.apply(entityManager);
        tx.commit();
        return result;
	}
}
