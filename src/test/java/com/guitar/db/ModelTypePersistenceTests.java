package com.guitar.db;

import com.guitar.db.model.ModelType;
import com.guitar.db.repository.ModelTypeJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModelTypePersistenceTests {

    @Autowired
    private ModelTypeJpaRepository modelTypeJpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testSaveAndGetAndDelete() {
        ModelType mt = new ModelType();
        mt.setName("Test Model Type");
        mt = modelTypeJpaRepository.save(mt);

        // clear the persistence context so we don't return the previously cached location object
        // this is a test only thing and normally doesn't need to be done in prod code
        entityManager.clear();

        ModelType otherModelType = modelTypeJpaRepository.findOne(mt.getId());
        assertEquals("Test Model Type", otherModelType.getName());

        modelTypeJpaRepository.delete(otherModelType);
    }

    @Test
    public void testFind() throws Exception {
        ModelType mt = modelTypeJpaRepository.findOne(1L);
        assertEquals("Dreadnought Acoustic", mt.getName());
    }

    @Test
    public void testForNull() throws Exception {
        List<ModelType> mts = modelTypeJpaRepository.findByNameIsNull();
        assertNull(mts.get(0).getName());
    }
}
