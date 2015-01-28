/*
 * Copyright 2012 Michael Mitteregger.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package at.mitteregger.fileformat.blp;

import java.io.IOException;
import java.io.InputStream;

/**
 * A DataInputStream which reads data types in little endian byte order.
 *
 * @author Michael Mitteregger
 */
public class LittleEndianDataInputStream extends InputStream {

    private InputStream is;

    public LittleEndianDataInputStream(final InputStream is) {
        this.is = is;
    }

    @Override
    public int read() throws IOException {
        return this.is.read();
    }

    /**
     * Reads a single byte from the input stream.
     *
     * @return the byte that was read.
     * @throws IOException if an I/O error occurs.
     */
    public byte readByte() throws IOException {
        return (byte) read();
    }

    /**
     * Reads a single unsigned byte from the input stream.
     *
     * @return the byte that was read.
     * @throws IOException if an I/O error occurs.
     */
    public short readUByte() throws IOException {
        return (short) (readByte() & 0x00FF);
    }

    /**
     * Reads a single Word (2 bytes) from the input stream.
     *
     * @return the word that was read.
     * @throws IOException if an I/O error occurs.
     */
    public short readWord() throws IOException {
        int ret = ((readByte()) & 0x00FF)
                | ((readByte() << 8) & 0xFF00);

        return (short) (ret & 0x0000FFFF);
    }

    /**
     * Reads a single DWord (4 bytes) from the input stream.
     *
     * @return the DWord that was read.
     * @throws IOException if an I/O error occurs.
     */
    public int readDWord() throws IOException {
        return ((readByte()) & 0x000000FF)
                | ((readByte() << 8) & 0x0000FF00)
                | ((readByte() << 16) & 0x00FF0000)
                | ((readByte() << 24) & 0xFF000000);
    }

    /**
     * Reads a single QWord (8 bytes) from the input stream.
     *
     * @return the QWord that was read.
     * @throws IOException if an I/O error occurs.
     */
    public long readQWord() throws IOException {
        return (((long) readByte()) & 0x00000000000000FFL)
                | (((long) readByte() << 8L) & 0x000000000000FF00L)
                | (((long) readByte() << 16L) & 0x0000000000FF0000L)
                | (((long) readByte() << 24L) & 0x00000000FF000000L)
                | (((long) readByte() << 32L) & 0x000000FF00000000L)
                | (((long) readByte() << 40L) & 0x0000FF0000000000L)
                | (((long) readByte() << 48L) & 0x00FF000000000000L)
                | (((long) readByte() << 56L) & 0xFF00000000000000L);
    }

    /**
     * Reads {@code length} bytes from the input stream and returns them as a String.
     *
     * @param length the number of bytes that should be read.
     * @return the String that was read.
     * @throws IOException if an I/O error occurs.
     */
    public final String readString(final int length) throws IOException {
        StringBuilder builder = new StringBuilder(length + 1);

        for (int i = 0; i < length; i++) {
            builder.append((char) readByte());
        }

        return builder.toString();
    }
}
