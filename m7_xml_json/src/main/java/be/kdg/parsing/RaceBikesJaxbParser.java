package be.kdg.parsing;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;

public class RaceBikesJaxbParser {

    public static void JaxbWriteXml(String file, Object root) {
        try {
            JAXBContext context = JAXBContext.newInstance(root.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(root, new File(file));
        } catch (Exception e) {
            throw new RuntimeException("Error writing XML with JAXB", e);
        }
    }

    public static <T> T JaxbReadXml(String file, Class<T> typeParameterClass) {
        try {
            JAXBContext context = JAXBContext.newInstance(typeParameterClass);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (T) unmarshaller.unmarshal(new File(file));
        } catch (Exception e) {
            throw new RuntimeException("Error reading XML with JAXB", e);
        }
    }
}