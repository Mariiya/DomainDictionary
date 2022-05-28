package com.pullenti.unisharp;

public class MemoryStream extends Stream { 

    long pos;
    long len;

    @Override
    public long length() {
        return len;
    }

    @Override
    public long getPosition() {
        return pos;
    }

    @Override
    public long setPosition(long p) throws java.io.IOException {
		if(p > len)
			throw new java.io.IOException("setPosition can't be great than length in MemoryStream");
        pos = p;
        return p;
    }
    @Override
    public void setLength(long p) throws java.io.IOException {
    	if(p == len) return;
    	if(p == 0) {
    		pos = 0; barr = null;
    		if(bout != null)
    			bout.close();
    		bout = null;
    		return;
    	}
    	byte[] arr = toByteArray();
		if(bout != null)
			bout.close();
		barr = new byte[(int)p];
		for(int i = 0; i < p && i < arr.length; i++) barr[i] = arr[i];
		len = p;
		if(pos > len) pos = len;
	}


    @Override
	public boolean canRead() {
	    return true;
	}
    @Override
	public boolean canWrite() {
	    return true;
	}

    @Override
    public void close() throws java.io.IOException {
        if (bout != null) {
            bout.close();
        }
        bout = null;
        barr = null;
    }

    @Override
    public int read() throws java.io.IOException {
        if (pos >= len) {
            return -1;
        }
        if (barr == null) {
            if (bout == null) {
                throw new java.io.IOException("Can't read from this write-only stream");
            }
            barr = bout.toByteArray();
            bout.close();
            bout = null;
        }
        byte b = barr[(int) pos];
        pos++;
        int res = ((int) b) & 0xFF;
        return res;
    }

    @Override
    public int read(byte[] buf, int off, int len) throws java.io.IOException {
        int res = 0;
        for (int i = 0; i < len; i++) {
            int b = read();
            if (b < 0) {
                break;
            }
            buf[i + off] = (byte) b;
            res++;
        }
        return res;
    }

    @Override
    public void write(byte b) throws java.io.IOException {
    	if(barr != null) {
    		if(pos < len) {
    			barr[(int)pos] = b;
    			pos++;
    			return;
    		}
    		bout = new java.io.ByteArrayOutputStream();
    		bout.write(barr, 0, barr.length);
    		barr = null;
    	}
        if (bout == null) {
            throw new java.io.IOException("Can't write to this read-only stream");
        }
        if(pos >= len) {
        	bout.write(b);
        	pos++;
        	if (pos > len) {
        		len++;
        	}
        } else {
        	barr = bout.toByteArray();
        	bout.close(); bout = null;
        	barr[(int)pos] = b;
        	pos++;
        }
    }

    @Override
    public void write(byte[] buf, int off, int len) throws java.io.IOException {
        for (int i = 0; i < len; i++) {
            write(buf[off + i]);
        }
    }

    public void writeTo(Stream dst) throws java.io.IOException {
        if (barr != null) {
            dst.write(barr, 0, barr.length);
        }
    }

    java.io.ByteArrayOutputStream bout;

    public MemoryStream() {
        bout = new java.io.ByteArrayOutputStream();
        pos = 0;
        len = 0;
    }

	public MemoryStream(int _len) {
        bout = new java.io.ByteArrayOutputStream(_len);
        pos = 0;
        len = 0;
    }

    public MemoryStream(byte[] arr, int index, int count) {
        barr = new byte[count];
        for(int i = 0; i < count; i++) barr[i] = arr[index + i];
        len = count;
        pos = 0;
    }


    byte[] barr;

    public MemoryStream(byte[] arr) {
        barr = arr;
        len = arr.length;
        pos = 0;
    }

    public MemoryStream(byte[] arr, boolean writable) {
        barr = arr;
        len = arr.length;
        pos = 0;
    }

    public byte[] toByteArray() {
        if (bout != null) {
            return bout.toByteArray();
        }
        return barr;
    }

    public java.io.InputStream toInputStream() {
        if(barr != null)
            return new java.io.ByteArrayInputStream(barr);
        if(bout != null)
            return new java.io.ByteArrayInputStream(bout.toByteArray());
        return null;
    }
}
