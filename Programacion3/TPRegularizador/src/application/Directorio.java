package application;

import java.io.File;

public class Directorio {
	// Ruta del directorio
	private File file;
		
	// Cursor que indica la posicion del ultimo archivo
	private int cursor = 0;
	
	public Directorio(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public int getCursor() {
		return cursor;
	}

	public void setCursor(int cursor) {
		this.cursor = cursor;
	}
}
