package com.pullenti.unisharp;

public class FileStream extends Stream {

    java.io.RandomAccessFile raf;

    @Override
    public long length() {
        try {
            return raf.length();
        } catch (Exception ee) {
            return -1;
        }
    }

    @Override
    public long getPosition() {
        try {
            return raf.getFilePointer();
        } catch (Exception ee) {
            return -1;
        }
    }

    @Override
    public long setPosition(long p) {
        try {
            raf.seek(p);
            return p;
        } catch (Exception ee) {
            return -1;
        }
    }
    @Override
    public void setLength(long p) {
        try {
            raf.setLength(p);
        } catch (Exception ee) {
        }
    }

    @Override
	public boolean canRead() {
	    return true;
	}
    @Override
	public boolean canWrite() {
	    return writeable;
	}
	boolean writeable;
	String name;
	public String getName() {
	    return name;
	}

    @Override
    public void close() throws java.io.IOException {
        raf.close();
        raf = null;
    }

    @Override
    public void flush() throws java.io.IOException {
    	raf.getFD().sync();
    }

    @Override
    public int read() throws java.io.IOException {
        return raf.read();
    }

    @Override
    public int read(byte[] buf, int off, int len) throws java.io.IOException {
        return raf.read(buf, off, len);
    }

    @Override
    public void write(byte b) throws java.io.IOException {
        raf.write(b);
    }

    @Override
    public void write(byte[] buf, int off, int len) throws java.io.IOException {
        raf.write(buf, off, len);
    }

    public FileStream(String fileName, String mode, String add) throws java.io.FileNotFoundException, java.io.IOException {
        raf = new java.io.RandomAccessFile(fileName, mode);
		name = fileName;
		writeable = !mode.equals("r");
        try {
            if (add == "t") {
                raf.setLength(0);
            } else if (add == "a") {
                raf.seek(raf.length());
            }
        } catch (Exception ee) {
        }
    }
}
