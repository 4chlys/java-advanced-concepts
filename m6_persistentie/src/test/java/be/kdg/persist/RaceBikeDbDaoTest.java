package be.kdg.persist;

import be.kdg.data.Data;
import be.kdg.model.Brand;
import be.kdg.model.RaceBike;
import org.junit.jupiter.api.*;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RaceBikeDbDaoTest {
    private static RaceBikeDbDao dao;

    @BeforeAll
    static void setUpAll() {
        dao = new RaceBikeDbDao("db/racebikestest");
        System.out.println("DAO instantiated for all tests");
    }

    @AfterAll
    static void tearDownAll() {
        if (dao != null) {
            dao.close();
            System.out.println("DAO connection closed");
        }
    }

    @BeforeEach
    void setUp() {
        List<RaceBike> raceBikes = Data.getData();
        for (RaceBike bike : raceBikes) {
            dao.insert(bike);
        }
        System.out.println("Database filled with " + raceBikes.size() + " bikes");
    }

    @AfterEach
    void tearDown() {
        dao.delete("*");
        System.out.println("Database cleared");
    }

    @Test
    void testInsert() {
        List<RaceBike> allBikes = dao.sortedOn("SELECT * FROM racebikesdb");
        int expectedCount = Data.getData().size();

        assertEquals(expectedCount, allBikes.size(),
                "Number of records in database should equal number of bikes in Data class");

        System.out.println("testInsert passed: " + allBikes.size() + " bikes in database");
    }

    @Test
    void testRetrieveUpdate() {
        String originalModel = "S-Works Tarmac SL7";
        RaceBike bike = dao.retrieve(originalModel);

        assertNotNull(bike, "Bike should be retrieved successfully");
        assertEquals(originalModel, bike.getModel(), "Retrieved bike should have correct model");

        String newModel = "S-Works Tarmac SL8";
        bike.setModel(newModel);

        boolean updateResult = dao.update(bike);
        assertTrue(updateResult, "Update should succeed");

        RaceBike updatedBike = dao.retrieve(newModel);
        assertNotNull(updatedBike, "Updated bike should be retrievable by new model name");
        assertEquals(newModel, updatedBike.getModel(), "Updated bike should have new model name");
        assertEquals(bike.getBrand(), updatedBike.getBrand(), "Brand should remain the same");
        assertEquals(bike.getId(), updatedBike.getId(), "ID should remain the same");

        RaceBike oldBike = dao.retrieve(originalModel);
        assertNull(oldBike, "Original model name should no longer exist in database");

        System.out.println("testRetrieveUpdate passed: model changed from '" +
                originalModel + "' to '" + newModel + "'");
    }

    @Test
    void testDelete() {
        List<RaceBike> allBikes = dao.sortedOn("SELECT * FROM racebikesdb");
        int initialCount = allBikes.size();
        assertTrue(initialCount > 0, "Database should contain bikes initially");

        String modelToDelete = "Dogma F12";
        boolean deleteResult = dao.delete(modelToDelete);
        assertTrue(deleteResult, "Delete should succeed");

        List<RaceBike> afterDelete = dao.sortedOn("SELECT * FROM racebikesdb");
        assertEquals(initialCount - 1, afterDelete.size(),
                "Database should contain one less bike after deletion");

        RaceBike deletedBike = dao.retrieve(modelToDelete);
        assertNull(deletedBike, "Deleted bike should no longer be retrievable");

        boolean secondDeleteResult = dao.delete(modelToDelete);
        assertFalse(secondDeleteResult, "Second delete attempt should fail");

        System.out.println("testDelete passed: successfully deleted '" + modelToDelete + "'");
    }

    @Test
    void testSort() {
        List<RaceBike> dataList = Data.getData();
        dataList.sort(Comparator.comparing(RaceBike::getBrand));

        List<RaceBike> dbList = dao.sortedOnBrand();

        assertEquals(dataList.size(), dbList.size(),
                "Sorted lists should have the same size");

        for (int i = 0; i < dataList.size(); i++) {
            assertEquals(dataList.get(i).getBrand(), dbList.get(i).getBrand(),
                    "Brand at position " + i + " should match");
            assertEquals(dataList.get(i).getModel(), dbList.get(i).getModel(),
                    "Model at position " + i + " should match");
        }

        System.out.println("testSort passed: sorted " + dbList.size() + " bikes by brand");
    }

    @Test
    void testSortOnModel() {
        List<RaceBike> sortedByModel = dao.sortedOnModel();

        assertTrue(sortedByModel.size() > 0, "Should return bikes");

        for (int i = 0; i < sortedByModel.size() - 1; i++) {
            String currentModel = sortedByModel.get(i).getModel();
            String nextModel = sortedByModel.get(i + 1).getModel();
            assertTrue(currentModel.compareToIgnoreCase(nextModel) <= 0,
                    "Bikes should be sorted alphabetically by model");
        }

        System.out.println("testSortOnModel passed: sorted " + sortedByModel.size() + " bikes");
    }

    @Test
    void testSortOnPrice() {
        List<RaceBike> sortedByPrice = dao.sortedOnPrice();

        assertTrue(sortedByPrice.size() > 0, "Should return bikes");

        for (int i = 0; i < sortedByPrice.size() - 1; i++) {
            assertTrue(sortedByPrice.get(i).getPrice().compareTo(sortedByPrice.get(i + 1).getPrice()) <= 0,
                    "Bikes should be sorted by price in ascending order");
        }

        System.out.println("testSortOnPrice passed: sorted " + sortedByPrice.size() + " bikes");
    }

    @Test
    void testDeleteAll() {
        List<RaceBike> allBikes = dao.sortedOn("SELECT * FROM racebikesdb");
        assertTrue(allBikes.size() > 0, "Database should contain bikes");

        boolean deleteResult = dao.delete("*");
        assertTrue(deleteResult, "Delete all should succeed");

        List<RaceBike> afterDelete = dao.sortedOn("SELECT * FROM racebikesdb");
        assertEquals(0, afterDelete.size(), "Database should be empty after delete all");

        System.out.println("testDeleteAll passed: all records deleted");
    }
}