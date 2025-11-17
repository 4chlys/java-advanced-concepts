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
                String model = null;
                Color color = Color.WHITE;
                double weight = 0;
                int frameSize = 0;
                LocalDate releaseDate = null;
                BigDecimal price = BigDecimal.ZERO;
                Currency currency = Currency.EUR;
                Groupset groupset = null;
                FrameMaterial frameMaterial = null;
                ForkMaterial forkMaterial = null;
                BrakeType brakeType = null;
                WheelSize wheelSize = null;

                // Iterate only over direct child nodes
                for (int j = 0; j < element.getChildNodes().getLength(); j++) {
                    if (!(element.getChildNodes().item(j) instanceof Element)) continue;
                    Element child = (Element) element.getChildNodes().item(j);
                    String tag = child.getTagName();
                    String text = child.getTextContent().trim();

                    switch (tag) {
                        case "model" -> model = text;
                        case "color" -> {
                            try {
                                color = Color.valueOf(text.toUpperCase());
                            } catch (IllegalArgumentException ignored) {
                            }
                        }
                        case "weight" -> weight = Double.parseDouble(text);
                        case "frame-size" -> frameSize = Integer.parseInt(text);
                        case "release-date" -> releaseDate = LocalDate.parse(text);
                        case "price" -> price = new BigDecimal(text);
                        case "currency" -> currency = Currency.valueOf(text.toUpperCase());
                        case "groupset" -> {
                            try {
                                String gs = text.toUpperCase().replace("-", "_").replace(" ", "_");
                                groupset = Groupset.valueOf(gs);
                            } catch (IllegalArgumentException ignored) {
                            }
                        }
                        case "frame-material" -> {
                            try {
                                frameMaterial = FrameMaterial.valueOf(text.toUpperCase());
                            } catch (IllegalArgumentException ignored) {
                            }
                        }
                        case "fork-material" -> {
                            try {
                                forkMaterial = ForkMaterial.valueOf(text.toUpperCase());
                            } catch (IllegalArgumentException ignored) {
                            }
                        }
                        case "brake-type" -> {
                            try {
                                brakeType = BrakeType.valueOf(text.toUpperCase());
                            } catch (IllegalArgumentException ignored) {
                            }
                        }
                        case "wheel-size" -> {
                            try {
                                wheelSize = WheelSize.valueOf(text.toUpperCase());
                            } catch (IllegalArgumentException ignored) {
                            }
                        }
                    }
                }

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
}
