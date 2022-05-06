package com.pullenti.unisharp;

public class GZipStream extends Stream {

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

    public GZipStream(Stream str, boolean compress) throws java.io.IOException {
        stream = str;
        if (compress) {
            bout = new java.io.ByteArrayOutputStream();
            pos = 0;
            len = 0;
        } else {
            byte[] data = Utils.readAllBytes(str);
            java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream(data.length);
            java.io.ByteArrayInputStream inStream = new java.io.ByteArrayInputStream(data);

            java.util.zip.GZIPInputStream gzip = new java.util.zip.GZIPInputStream(inStream);
            byte[] buf = new byte[1024];
            int length;
            while ((length = gzip.read(buf, 0, buf.length)) != -1) {
                outputStream.write(buf, 0, length);
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
        java.io.ByteArrayOutputStream bbb = new java.io.ByteArrayOutputStream();
        java.util.zip.GZIPOutputStream gzip = new java.util.zip.GZIPOutputStream(bbb);
        gzip.write(data);
        gzip.close();
        byte[] data1 = bbb.toByteArray();
        stream.write(data1, 0, data1.length);

        bout = null;
        barr = null;
    }
}
