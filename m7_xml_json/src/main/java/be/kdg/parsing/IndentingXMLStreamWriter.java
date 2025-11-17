package be.kdg.parsing;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Stack;

public class IndentingXMLStreamWriter implements XMLStreamWriter {
    private final XMLStreamWriter writer;
    private final String indentString;
    private final String lineSeparator;
    private final Stack<ElementState> elementStack = new Stack<>();
    private ElementState currentState = new ElementState();

    private static class ElementState {
        boolean hasChildElement = false;
        boolean hasContent = false;
    }

    public IndentingXMLStreamWriter(XMLStreamWriter writer) {
        this(writer, "  ");
    }

    public IndentingXMLStreamWriter(XMLStreamWriter writer, String indentString) {
        this.writer = writer;
        this.indentString = indentString;
        this.lineSeparator = System.lineSeparator();
    }

    private void writeIndent() throws XMLStreamException {
        int depth = elementStack.size();
        if (depth > 0) {
            writer.writeCharacters(lineSeparator);
            for (int i = 0; i < depth; i++) {
                writer.writeCharacters(indentString);
            }
        }
    }

    @Override
    public void writeStartElement(String localName) throws XMLStreamException {
        beforeStartElement();
        writer.writeStartElement(localName);
        afterStartElement();
    }

    @Override
    public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
        beforeStartElement();
        writer.writeStartElement(namespaceURI, localName);
        afterStartElement();
    }

    @Override
    public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
        beforeStartElement();
        writer.writeStartElement(prefix, localName, namespaceURI);
        afterStartElement();
    }

    private void beforeStartElement() throws XMLStreamException {
        if (!elementStack.isEmpty()) {
            currentState.hasChildElement = true;
        }
        writeIndent();
    }

    private void afterStartElement() {
        elementStack.push(currentState);
        currentState = new ElementState();
    }

    @Override
    public void writeEndElement() throws XMLStreamException {
        ElementState state = elementStack.pop();
        if (state.hasChildElement && !state.hasContent) {
            writeIndent();
        }
        writer.writeEndElement();

        if (!elementStack.isEmpty()) {
            currentState = elementStack.peek();
        } else {
            currentState = new ElementState();
            writer.writeCharacters(lineSeparator);
        }
    }

    @Override
    public void writeEmptyElement(String localName) throws XMLStreamException {
        beforeStartElement();
        writer.writeEmptyElement(localName);
    }

    @Override
    public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
        beforeStartElement();
        writer.writeEmptyElement(namespaceURI, localName);
    }

    @Override
    public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
        beforeStartElement();
        writer.writeEmptyElement(prefix, localName, namespaceURI);
    }

    @Override
    public void writeCharacters(String text) throws XMLStreamException {
        currentState.hasContent = true;
        writer.writeCharacters(text);
    }

    @Override
    public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
        currentState.hasContent = true;
        writer.writeCharacters(text, start, len);
    }

    @Override
    public void writeCData(String data) throws XMLStreamException {
        currentState.hasContent = true;
        writer.writeCData(data);
    }

    @Override
    public void writeComment(String data) throws XMLStreamException {
        writeIndent();
        writer.writeComment(data);
    }

    @Override
    public void writeStartDocument() throws XMLStreamException {
        writer.writeStartDocument();
        writer.writeCharacters(lineSeparator);
    }

    @Override
    public void writeStartDocument(String version) throws XMLStreamException {
        writer.writeStartDocument(version);
        writer.writeCharacters(lineSeparator);
    }

    @Override
    public void writeStartDocument(String encoding, String version) throws XMLStreamException {
        writer.writeStartDocument(encoding, version);
        writer.writeCharacters(lineSeparator);
    }

    @Override
    public void writeEndDocument() throws XMLStreamException {
        writer.writeEndDocument();
    }

    @Override
    public void writeAttribute(String localName, String value) throws XMLStreamException {
        writer.writeAttribute(localName, value);
    }

    @Override
    public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
        writer.writeAttribute(namespaceURI, localName, value);
    }

    @Override
    public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
        writer.writeAttribute(prefix, namespaceURI, localName, value);
    }

    @Override
    public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
        writer.writeNamespace(prefix, namespaceURI);
    }

    @Override
    public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
        writer.writeDefaultNamespace(namespaceURI);
    }

    @Override
    public void writeProcessingInstruction(String target) throws XMLStreamException {
        writeIndent();
        writer.writeProcessingInstruction(target);
    }

    @Override
    public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
        writeIndent();
        writer.writeProcessingInstruction(target, data);
    }

    @Override
    public void writeDTD(String dtd) throws XMLStreamException {
        writer.writeDTD(dtd);
        writer.writeCharacters(lineSeparator);
    }

    @Override
    public void writeEntityRef(String name) throws XMLStreamException {
        writer.writeEntityRef(name);
    }

    @Override
    public String getPrefix(String uri) throws XMLStreamException {
        return writer.getPrefix(uri);
    }

    @Override
    public void setPrefix(String prefix, String uri) throws XMLStreamException {
        writer.setPrefix(prefix, uri);
    }

    @Override
    public void setDefaultNamespace(String uri) throws XMLStreamException {
        writer.setDefaultNamespace(uri);
    }

    @Override
    public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
        writer.setNamespaceContext(context);
    }

    @Override
    public NamespaceContext getNamespaceContext() {
        return writer.getNamespaceContext();
    }

    @Override
    public Object getProperty(String name) throws IllegalArgumentException {
        return writer.getProperty(name);
    }

    @Override
    public void close() throws XMLStreamException {
        writer.close();
    }

    @Override
    public void flush() throws XMLStreamException {
        writer.flush();
    }
}