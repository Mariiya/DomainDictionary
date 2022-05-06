package com.pullenti.unisharp;

public class DeflateStream extends Stream {

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
    public long setPosition(long p) {
        pos = p;
        return p;
    }


    @Override
    public int read() throws java.io.IOException {
        if(barr == null)
            throw new java.io.IOException("Can't read from this write-only stream");
        if (pos >= len) {
            return -1;
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
        if (bout != null) {
            bout.write(b);
        }
    }

    @Override
    public void write(byte[] buf, int off, int len) throws java.io.IOException {
        for (int i = 0; i < len; i++) {
            write(buf[off + i]);
        }
    }

    Stream stream;

    public Stream getBaseStream() {
        return stream;
    }

    byte[] barr;
    java.io.ByteArrayOutputStream bout;
    boolean outzlib;

    public DeflateStream(Stream str, boolean compress, boolean outZlib) throws java.io.IOException {
        stream = str; outzlib = outZlib;
        if (compress) {
            bout = new java.io.ByteArrayOutputStream();
            pos = 0;
            len = 0;
        } else {
            byte[] data = Utils.readAllBytes(str);
            java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream(data.length);
            byte[] buf = new byte[1024];
            java.util.zip.Inflater inflate = new java.util.zip.Inflater(!outZlib);
            inflate.setInput(data);
            while (!inflate.finished()) {
                try {
                    int count = inflate.inflate(buf);
                    if (count > 0) {
                        outputStream.write(buf, 0, count);
                    }
                } catch (java.util.zip.DataFormatException e) {
                    throw new java.io.IOException(e.getMessage());
                }
            }
            outputStream.close();
            barr = outputStream.toByteArray();
            pos = 0;
            len = barr.length;
        }
    }

    @Override
    public void close() throws java.io.IOException {
        if(bout == null) return;
        byte[] data = bout.toByteArray();
        java.util.zip.Deflater deflate = new java.util.zip.Deflater(java.util.zip.Deflater.DEFAULT_COMPRESSION, !outzlib);
        deflate.setInput(data);
        deflate.finish();
        byte[] buf = new byte[1024]; 
        while (!deflate.finished()) {
                int count = deflate.deflate(buf, 0, buf.length);
                if (count > 0) 
                    stream.write(buf, 0, count); 
        }
        bout = null;
        barr = null;
    }
}