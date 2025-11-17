package be.kdg.parsing;

import be.kdg.model.RaceBike;
import be.kdg.model.RaceBikes;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;

public class RaceBikesStaxParser {
    private final RaceBikes raceBikes;
    private XMLStreamWriter writer;

    public RaceBikesStaxParser(RaceBikes raceBikes, String filePath) {
        this.raceBikes = raceBikes;
        try {
            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            FileOutputStream fos = new FileOutputStream(filePath);
            XMLStreamWriter baseWriter = factory.createXMLStreamWriter(fos, "UTF-8");
            this.writer = new IndentingXMLStreamWriter(baseWriter);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing XML writer", e);
        }
    }

    public void staxWriteXML() throws XMLStreamException, IOException {
        try {
            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeStartElement("race-bikes");

            for (RaceBike raceBike : raceBikes.getRaceBikeList()) {
                writeElement(raceBike);
            }

            writer.writeEndElement();
            writer.writeEndDocument();
        } finally {
            writer.close();
        }
    }

    private void writeElement(RaceBike raceBike) throws XMLStreamException {
        writer.writeStartElement("race-bike");

        writer.writeAttribute("brand", raceBike.getBrand().toString());

        writer.writeStartElement("model");
        writeCharactersSafe(raceBike.getModel());
        writer.writeEndElement();

        writer.writeStartElement("color");
        writer.writeCharacters(raceBike.getColor().name());
        writer.writeEndElement();

        writer.writeStartElement("weight");
        writeCharactersSafe(String.valueOf(raceBike.getWeight()));
        writer.writeEndElement();

        writer.writeStartElement("frame-size");
        writeCharactersSafe(String.valueOf(raceBike.getFrameSize()));
        writer.writeEndElement();

        writer.writeStartElement("release-date");
        writeCharactersSafe(raceBike.getReleaseDate().toString());
        writer.writeEndElement();

        writer.writeStartElement("price");
        writeCharactersSafe(raceBike.getPrice().toString());
        writer.writeEndElement();

        writer.writeStartElement("currency");
        writeCharactersSafe(raceBike.getCurrency().toString());
        writer.writeEndElement();

        if (raceBike.getGroupset() != null) {
            writer.writeStartElement("groupset");
            writeCharactersSafe(raceBike.getGroupset().toString());
            writer.writeEndElement();
        }

        if (raceBike.getFrameMaterial() != null) {
            writer.writeStartElement("frame-material");
            writeCharactersSafe(raceBike.getFrameMaterial().toString());
            writer.writeEndElement();
        }

        if (raceBike.getForkMaterial() != null) {
            writer.writeStartElement("fork-material");
            writeCharactersSafe(raceBike.getForkMaterial().toString());
            writer.writeEndElement();
        }

        if (raceBike.getBrakeType() != null) {
            writer.writeStartElement("brake-type");
            writeCharactersSafe(raceBike.getBrakeType().toString());
            writer.writeEndElement();
        }

        if (raceBike.getWheelSize() != null) {
            writer.writeStartElement("wheel-size");
            writeCharactersSafe(raceBike.getWheelSize().toString());
            writer.writeEndElement();
        }

        writer.writeEndElement();
    }

    private void writeCharactersSafe(String value) throws XMLStreamException {
        writer.writeCharacters(value == null ? "" : value);
    }

}