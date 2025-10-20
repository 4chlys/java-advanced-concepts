package be.kdg.persist;

import be.kdg.model.RaceBikes;

import java.io.*;

public class RaceBikesSerializer {
    private final String FILENAME;

    public RaceBikesSerializer(String filename) {
        this.FILENAME = filename;
    }

    public void serialize(RaceBikes raceBikes) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("db/" + FILENAME + ".ser"))) {
            oos.writeObject(raceBikes);
            System.out.println("\nNa serialize / deserialize: (sommige data staan op null omwille van transient)");
        } catch (Exception e) {
            throw new IOException("Error serializing racebikes: " + e.getMessage());
        }
    }

    public RaceBikes deserialize() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("db/" + FILENAME + ".ser"))) {
            return (RaceBikes) ois.readObject();
        } catch (IOException e) {
            throw new IOException("Error deserializing racebikes: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Class not found: " + e.getMessage());
        }
    }
}
