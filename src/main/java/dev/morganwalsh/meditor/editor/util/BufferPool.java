package dev.morganwalsh.meditor.editor.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.DefaultStyledDocument;

import dev.morganwalsh.meditor.editor.model.Buffer;

public class BufferPool {
	
	private static Map<String, Buffer> buffers;
	
	static {
		buffers = new HashMap<>();
	}
	
	private BufferPool() {
	}
	
	public static void addBuffer(Buffer buffer) {
		buffers.put(buffer.getName(), buffer);
	}
	
	/**
	 * Get's a buffer by its name, if it exists. Otherwise returns a new 
	 * buffer with the given name.
	 * @param name
	 * @return
	 */
	public static Buffer getBuffer(String name) {
		Buffer buffer = buffers.get(name);
		if (buffer == null) {
			buffer = new Buffer();
			buffer.setName(name);
			buffer.setDocument(new DefaultStyledDocument());
			buffers.put(name, buffer);
		}
		return buffer;
	}
	
	public static Buffer getBuffer(String name, boolean createIfMissing) {
		if (createIfMissing) return getBuffer(name);
		else return buffers.get(name);
	}
	
	public static boolean deleteBuffer(String name) {
		if (buffers.containsKey(name)) {
			buffers.remove(name);
			return true;
		}
		return false;
	}
	
	public static Collection<Buffer> getAllBuffers() {
		return buffers.values();
	}
}
