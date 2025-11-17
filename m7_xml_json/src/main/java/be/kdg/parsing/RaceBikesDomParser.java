package be.kdg.parsing;

import be.kdg.model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;

public class RaceBikesDomParser {

    public static RaceBikes domReadXML(String fileName) {
        RaceBikes raceBikes = new RaceBikes();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(fileName));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("race-bike");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);

                Brand brand = Brand.valueOf(element.getAttribute("brand").toUpperCase());
                String model = getElementText(element, "model");
                String colorStr = element.getElementsByTagName("color").item(0).getTextContent();
                Color color;
                try {
                    color = Color.valueOf(colorStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    color = Color.WHITE;
                }
                double weight = Double.parseDouble(getElementText(element, "weight"));
                int frameSize = Integer.parseInt(getElementText(element, "frame-size"));
                LocalDate releaseDate = LocalDate.parse(getElementText(element, "release-date"));
                BigDecimal price = new BigDecimal(getElementText(element, "price"));
                Currency currency = Currency.valueOf(getElementText(element, "currency").toUpperCase());
                String groupsetStr = getElementText(element, "groupset")
                        .toUpperCase()
                        .replace("-", "_")
                        .replace(" ", "_");
                Groupset groupset;
                try {
                    groupset = Groupset.valueOf(groupsetStr);
                } catch (IllegalArgumentException e) {
                    groupset = null;
                }
                FrameMaterial frameMaterial = hasElement(element, "frame-material") ? FrameMaterial.valueOf(getElementText(element, "frame-material").toUpperCase()) : null;
                ForkMaterial forkMaterial = hasElement(element, "fork-material") ? ForkMaterial.valueOf(getElementText(element, "fork-material").toUpperCase()) : null;
                BrakeType brakeType = hasElement(element, "brake-type") ? BrakeType.valueOf(getElementText(element, "brake-type").toUpperCase()) : null;
                WheelSize wheelSize = hasElement(element, "wheel-size") ? WheelSize.valueOf(getElementText(element, "wheel-size").toUpperCase()) : null;

                RaceBike raceBike = new RaceBike(
                        brand, model, color, weight, frameSize, releaseDate,
                        price, currency, groupset, frameMaterial, forkMaterial,
                        brakeType, wheelSize
                );

                raceBikes.add(raceBike);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error reading XML file", e);
        }

        return raceBikes;
    }

    private static String getElementText(Element parent, String tagName) {
        return parent.getElementsByTagName(tagName).item(0).getTextContent().trim();
    }

    private static boolean hasElement(Element parent, String tagName) {
        return parent.getElementsByTagName(tagName).getLength() > 0;
    }
}
