package dao;

import dao.Sql2oCategoryDao;
import dao.Sql2oTaskDao;
import models.Category;
import models.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class Sql2oCategoryDaoTest {

    private Sql2oCategoryDao catergoryDao;//ignore me for now. We'll create this soon.
    private Sql2oTaskDao taskDao;
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        catergoryDao = new Sql2oCategoryDao(sql2o); //ignore me for now

        //keep connection open through entire test so it does not get erased.
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }
    @Test
    public void addingCourseSetsId() throws Exception {
        Category category = new Category ("mow the lawn");
        int originalCategoryId = category.getId();
        catergoryDao.add(category);
        assertNotEquals(originalCategoryId, category.getId()); //how does this work?
    }

    @Test
    public void existingCategoryCanBeFoundById() throws Exception {
        Category category = new Category ("mow the lawn");
        catergoryDao.add(category); //add to dao (takes care of saving)
        Category foundCategory = catergoryDao.findById(category.getId()); //retrieve
        assertEquals(category, foundCategory); //should be the same
    }

    @Test
    public void addedCategoryAreReturnedFromgetAll() throws Exception {
        Category category = new Category ("mow the lawn");
        catergoryDao.add(category);
        assertEquals(1, catergoryDao.getAll().size());
    }

    @Test
    public void noCategoryReturnsEmptyList() throws Exception {
        assertEquals(0, catergoryDao.getAll().size());
    }

    @Test
    public void updateChangesCategoryContent() throws Exception {
        String initialDescription = "mow the lawn";
        Category category = new Category (initialDescription);
        catergoryDao.add(category);

        catergoryDao.update(category.getId(),"brush the cat");
        Category updatedCategory = catergoryDao.findById(category.getId()); //why do I need to refind this?
        assertNotEquals(initialDescription, updatedCategory.getDescription());
    }

    @Test
    public void deleteByIdDeletesCorrectCategory() throws Exception {
        Category category = new Category ("mow the lawn");
        catergoryDao.add(category);
        catergoryDao.deleteById(category.getId());
        assertEquals(0, catergoryDao.getAll().size());
    }

    @Test
    public void clearAllClearsAll() throws Exception {
        Category category = new Category ("mow the lawn");
        Category otherCategory = new Category("brush the cat");
        catergoryDao.add(category);
        catergoryDao.add(otherCategory);
        int daoSize = catergoryDao.getAll().size();
        catergoryDao.clearAllCategory();
        assertTrue(daoSize > 0 && daoSize > catergoryDao.getAll().size()); //this is a little overcomplicated, but illustrates well how we might use `assertTrue` in a different way.
    }

}