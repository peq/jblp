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
package at.mitteregger.fileformat.blp.enums;

/**
 * The compression of a BLP file.
 *
 * @author Michael Mitteregger
 */
public enum BlpCompression {

    /** The Blp body contains JPEG compressed data. */
    JPEG(0),

    /** The Blp body contains a uncompressed color palette and MipMap. */
    UNCOMPRESSED(1);

    private long blpCompressionId;

    private BlpCompression(final long blpCompressionId) {
        this.blpCompressionId = blpCompressionId;
    }

    public long getBlpCompressionId() {
        return this.blpCompressionId;
    }

    public static BlpCompression byBlpCompressionId(final long blpCompressionId) {
        for (BlpCompression blpCompression : values()) {
            if (blpCompression.getBlpCompressionId() == blpCompressionId) {
                return blpCompression;
            }
        }

        throw new IllegalArgumentException("Unknown blpCompressionId: " + blpCompressionId);
    }
}
