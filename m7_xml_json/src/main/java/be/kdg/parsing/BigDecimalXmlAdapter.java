package be.kdg.parsing;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.math.BigDecimal;

public class BigDecimalXmlAdapter extends XmlAdapter<String, BigDecimal> {
    @Override
    public BigDecimal unmarshal(String v) {
        return v != null ? new BigDecimal(v) : null;
    }

    @Override
    public String marshal(BigDecimal v) {
        return v != null ? v.toString() : null;
    }
}