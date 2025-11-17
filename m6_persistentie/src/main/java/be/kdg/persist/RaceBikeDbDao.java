package be.kdg.persist;

import be.kdg.model.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RaceBikeDbDao implements RaceBikeDao {
    private Connection connection;
    private static final Logger logger = Logger.getLogger(RaceBikeDbDao.class.getName());

    public RaceBikeDbDao(String databasePath) {
        connect(databasePath);
        init();
    }

    private void connect(String databasePath) {
        try {
            connection = DriverManager.getConnection("jdbc:hsqldb:file:" + databasePath, "rb", "");
            System.out.println("Connection made");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Cannot connect to database: " + databasePath, e);
            System.err.println("Cannot connect to database: " + databasePath);
        }
    }

    private void init() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS racebikesdb");
            String createQuery = "CREATE TABLE racebikesdb " +
                    "(id INTEGER IDENTITY PRIMARY KEY," +
                    "brand VARCHAR(20) NOT NULL, " +
                    "model VARCHAR(50) NOT NULL," +
                    "color VARCHAR(15)," +
                    "weight DOUBLE," +
                    "frameSize INTEGER," +
                    "releaseDate DATE," +
                    "price DECIMAL(10,2)," +
                    "currency VARCHAR(5)," +
                    "groupset VARCHAR(50)," +
                    "frameMaterial VARCHAR(20)," +
                    "forkMaterial VARCHAR(20)," +
                    "brakeType VARCHAR(30)," +
                    "wheelSize VARCHAR(30)," +
                    "UNIQUE(brand, model))";
            statement.execute(createQuery);
            statement.close();
            System.out.println("Database created");
        } catch (SQLException e) {
            String message = e.getMessage();
            if (message.contains("object name already exists")) {
                logger.log(Level.INFO, "Table already exists, skipping creation");
                return;
            }
            logger.log(Level.SEVERE, "Unexpected error creating table", e);
            System.err.println("Unexpected error creating table: " + e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public boolean insert(RaceBike raceBike) {
        if (raceBike == null) {
            logger.log(Level.WARNING, "Cannot insert null RaceBike");
            return false;
        }

        try {
            PreparedStatement prepStatement = connection.prepareStatement(
                    "INSERT INTO racebikesdb (brand, model, color, weight, frameSize, " +
                            "releaseDate, price, currency, groupset, frameMaterial, " +
                            "forkMaterial, brakeType, wheelSize) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            prepStatement.setString(1, raceBike.getBrand() != null ? raceBike.getBrand().name() : null);
            prepStatement.setString(2, raceBike.getModel());
            prepStatement.setString(3, raceBike.getColor() != null ? raceBike.getColor().name() : null);
            prepStatement.setDouble(4, raceBike.getWeight());
            prepStatement.setInt(5, raceBike.getFrameSize());
            prepStatement.setDate(6, raceBike.getReleaseDate() != null ?
                    Date.valueOf(raceBike.getReleaseDate()) : null);
            prepStatement.setBigDecimal(7, raceBike.getPrice());
            prepStatement.setString(8, raceBike.getCurrency() != null ? raceBike.getCurrency().name() : null);
            prepStatement.setString(9, raceBike.getGroupset() != null ? raceBike.getGroupset().name() : null);
            prepStatement.setString(10, raceBike.getFrameMaterial() != null ?
                    raceBike.getFrameMaterial().name() : null);
            prepStatement.setString(11, raceBike.getForkMaterial() != null ?
                    raceBike.getForkMaterial().name() : null);
            prepStatement.setString(12, raceBike.getBrakeType() != null ?
                    raceBike.getBrakeType().name() : null);
            prepStatement.setString(13, raceBike.getWheelSize() != null ?
                    raceBike.getWheelSize().name() : null);

            boolean result = prepStatement.executeUpdate() == 1;

            if (result) {
                ResultSet generatedKeys = prepStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    raceBike.setId(generatedKeys.getInt(1));
                }
                generatedKeys.close();
            }

            prepStatement.close();
            return result;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, String.format("Error inserting RaceBike: %s %s",
                    raceBike.getBrand(), raceBike.getModel()), e);
            System.err.println("Fout bij insert: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(RaceBike raceBike) {
        if (raceBike == null || raceBike.getId() < 0) {
            logger.log(Level.WARNING, "Cannot update RaceBike with invalid id");
            return false;
        }

        try {
            PreparedStatement prepStatement = connection.prepareStatement(
                    "UPDATE racebikesdb SET brand=?, model=?, color=?, weight=?, frameSize=?, " +
                            "releaseDate=?, price=?, currency=?, groupset=?, frameMaterial=?, " +
                            "forkMaterial=?, brakeType=?, wheelSize=? WHERE id=?");

            prepStatement.setString(1, raceBike.getBrand() != null ? raceBike.getBrand().name() : null);
            prepStatement.setString(2, raceBike.getModel());
            prepStatement.setString(3, raceBike.getColor() != null ? raceBike.getColor().name() : null);
            prepStatement.setDouble(4, raceBike.getWeight());
            prepStatement.setInt(5, raceBike.getFrameSize());
            prepStatement.setDate(6, raceBike.getReleaseDate() != null ?
                    Date.valueOf(raceBike.getReleaseDate()) : null);
            prepStatement.setBigDecimal(7, raceBike.getPrice());
            prepStatement.setString(8, raceBike.getCurrency() != null ? raceBike.getCurrency().name() : null);
            prepStatement.setString(9, raceBike.getGroupset() != null ? raceBike.getGroupset().name() : null);
            prepStatement.setString(10, raceBike.getFrameMaterial() != null ?
                    raceBike.getFrameMaterial().name() : null);
            prepStatement.setString(11, raceBike.getForkMaterial() != null ?
                    raceBike.getForkMaterial().name() : null);
            prepStatement.setString(12, raceBike.getBrakeType() != null ?
                    raceBike.getBrakeType().name() : null);
            prepStatement.setString(13, raceBike.getWheelSize() != null ?
                    raceBike.getWheelSize().name() : null);
            prepStatement.setInt(14, raceBike.getId());

            boolean result = prepStatement.executeUpdate() == 1;
            prepStatement.close();
            return result;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, String.format("Error updating RaceBike with id %d: %s %s",
                    raceBike.getId(), raceBike.getBrand(), raceBike.getModel()), e);
            System.err.println("Fout bij update: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(String model) {
        if (model == null || model.trim().isEmpty()) {
            logger.log(Level.WARNING, "Cannot delete RaceBike with null or empty model");
            return false;
        }

        try {
            PreparedStatement prepStatement;

            // If model is "*", delete ALL records
            if ("*".equals(model)) {
                prepStatement = connection.prepareStatement("DELETE FROM racebikesdb");
            } else {
                prepStatement = connection.prepareStatement("DELETE FROM racebikesdb WHERE model=?");
                prepStatement.setString(1, model);
            }

            boolean result = prepStatement.executeUpdate() >= 1;
            prepStatement.close();
            return result;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, String.format("Error deleting RaceBike with model: %s", model), e);
            System.err.println("Fout bij delete: " + e.getMessage());
            return false;
        }
    }

    @Override
    public RaceBike retrieve(String model) {
        if (model == null || model.trim().isEmpty()) {
            logger.log(Level.WARNING, "Cannot retrieve RaceBike with null or empty model");
            return null;
        }

        try {
            PreparedStatement prepStatement = connection.prepareStatement(
                    "SELECT * FROM racebikesdb WHERE model=?");
            prepStatement.setString(1, model);

            ResultSet resultSet = prepStatement.executeQuery();
            RaceBike raceBike = null;

            if (resultSet.next()) {
                raceBike = createRaceBikeFromResultSet(resultSet);
            }

            resultSet.close();
            prepStatement.close();
            return raceBike;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, String.format("Error retrieving RaceBike with model: %s", model), e);
            System.err.println("Fout bij retrieve: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<RaceBike> sortedOn(String query) {
        List<RaceBike> raceBikes = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                raceBikes.add(createRaceBikeFromResultSet(resultSet));
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, String.format("Error executing query: %s", query), e);
            System.err.println("Fout bij sortedOn query: " + e.getMessage());
        }

        return raceBikes;
    }

    public List<RaceBike> sortedOnBrand() {
        return sortedOn("SELECT * FROM racebikesdb ORDER BY brand");
    }

    public List<RaceBike> sortedOnModel() {
        return sortedOn("SELECT * FROM racebikesdb ORDER BY model");
    }

    public List<RaceBike> sortedOnColor() {
        return sortedOn("SELECT * FROM racebikesdb ORDER BY color");
    }

    public List<RaceBike> sortedOnPrice() {
        return sortedOn("SELECT * FROM racebikesdb ORDER BY price");
    }

    public List<RaceBike> sortedOnReleaseDate() {
        return sortedOn("SELECT * FROM racebikesdb ORDER BY releaseDate");
    }

    public List<RaceBike> sortedOnWeight() {
        return sortedOn("SELECT * FROM racebikesdb ORDER BY weight");
    }

    private RaceBike createRaceBikeFromResultSet(ResultSet rs) throws SQLException {
        RaceBike raceBike = new RaceBike();

        raceBike.setId(rs.getInt("id"));

        String brandStr = rs.getString("brand");
        if (brandStr != null) raceBike.setBrand(Brand.valueOf(brandStr));

        raceBike.setModel(rs.getString("model"));

        String colorStr = rs.getString("color");
        if (colorStr != null) raceBike.setColor(Color.valueOf(colorStr));

        raceBike.setWeight(rs.getDouble("weight"));
        raceBike.setFrameSize(rs.getInt("frameSize"));

        Date releaseDate = rs.getDate("releaseDate");
        if (releaseDate != null) raceBike.setReleaseDate(releaseDate.toLocalDate());

        BigDecimal price = rs.getBigDecimal("price");
        if (price != null) raceBike.setPrice(price);

        String currencyStr = rs.getString("currency");
        if (currencyStr != null) raceBike.setCurrency(Currency.valueOf(currencyStr));

        String groupsetStr = rs.getString("groupset");
        if (groupsetStr != null) raceBike.setGroupset(Groupset.valueOf(groupsetStr));

        String frameMaterialStr = rs.getString("frameMaterial");
        if (frameMaterialStr != null) raceBike.setFrameMaterial(FrameMaterial.valueOf(frameMaterialStr));

        String forkMaterialStr = rs.getString("forkMaterial");
        if (forkMaterialStr != null) raceBike.setForkMaterial(ForkMaterial.valueOf(forkMaterialStr));

        String brakeTypeStr = rs.getString("brakeType");
        if (brakeTypeStr != null) raceBike.setBrakeType(BrakeType.valueOf(brakeTypeStr));

        String wheelSizeStr = rs.getString("wheelSize");
        if (wheelSizeStr != null) raceBike.setWheelSize(WheelSize.valueOf(wheelSizeStr));

        return raceBike;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error closing database connection", e);
        }
    }
}