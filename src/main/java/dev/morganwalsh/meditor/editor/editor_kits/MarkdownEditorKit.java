package dev.morganwalsh.meditor.editor.editor_kits;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLWriter;

public class MarkdownEditorKit extends StyledEditorKit {
    private static final String PLAIN_TEXT = "text/plain";
    private static final String HTML_TEXT = "text/html";
    private static final String BOLD_STYLE = "bold";
    private static final String ITALIC_STYLE = "italic";

    private static final String[] HEADERS = {
        "# ", "## ", "### ", "#### ", "##### ", "###### "
    };

    private static final String[] STYLES = {
        BOLD_STYLE,
        ITALIC_STYLE
    };

    @Override
    public String getContentType() {
        return "text/markdown";
    }
    
    @Override
    public Document createDefaultDocument() {
    	return super.createDefaultDocument();
    }

    @Override
    public void read(Reader in, Document doc, int pos) throws IOException, BadLocationException {
        BufferedReader reader = new BufferedReader(in);
        String line;
        int lineNumber = 0;

        while ((line = reader.readLine()) != null) {
            // check if this line is a header
            boolean isHeader = false;
            for (String header : HEADERS) {
                if (line.startsWith(header)) {
                    int level = header.length();
                    AttributeSet attributes = getHeaderAttributes(level);
                    insertStyledText(doc, pos, "\n" + line.substring(level), attributes);
                    pos += line.length() + 1;
                    isHeader = true;
                    break;
                }
            }

            // if this line is not a header, parse it for other markdown features
            if (!isHeader) {
                pos = parseMarkdownLine(doc, pos, line);
            }

            lineNumber++;
        }
    }

    @Override
    public void write(Writer out, Document doc, int pos, int len) throws IOException, BadLocationException {
        HTMLWriter writer = new HTMLWriter(out, (HTMLDocument)doc, pos, len);
        writer.write();
    }

    private int parseMarkdownLine(Document doc, int pos, String line) throws BadLocationException {
        StringBuilder builder = new StringBuilder();
        int i = 0;

        while (i < line.length()) {
            char c = line.charAt(i);

            if (c == '*') {
                // check for bold text
                if (i < line.length() - 1 && line.charAt(i + 1) == '*') {
                    String text = builder.toString();
                    builder.setLength(0);
                    insertStyledText(doc, pos, text, getStyleAttributes(BOLD_STYLE));
                    pos += text.length();
                    i += 2;
                }
                // check for italic text
                else {
                    String text = builder.toString();
                    builder.setLength(0);
                    insertStyledText(doc, pos, text, getStyleAttributes(ITALIC_STYLE));
                    pos += text.length();
                    i++;
                }
            } else {
                builder.append(c);
                i++;
            }
        }

        String text = builder.toString();
        insertText(doc, pos, text);
        pos += text.length();

        return pos;
    }

    private AttributeSet getHeaderAttributes(int level) {
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        int fontSize = 18 - (2 * level);
        StyleConstants.setFontSize(attributes, fontSize);
        StyleConstants.setBold(attributes, true);
        return attributes;
    }

    private AttributeSet getStyleAttributes(String style) {
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        if (style.equals(BOLD_STYLE)) {
            StyleConstants.setBold(attributes, true);
        } else if (style.equals(ITALIC_STYLE)) {
            StyleConstants.setItalic(attributes, true);
        }
        return attributes;
    }

    private void insertText(Document doc, int pos, String text) throws BadLocationException {
        doc.insertString(pos, text, null);
    }

    private void insertStyledText(Document doc, int pos, String text, AttributeSet attributes) throws BadLocationException {
        doc.insertString(pos, text, attributes);
    }
}

