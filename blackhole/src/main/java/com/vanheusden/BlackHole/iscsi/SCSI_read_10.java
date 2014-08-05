/* Released under GPL 2.0
 * (C) 2011 by folkert@vanheusden.com
 */
package com.vanheusden.BlackHole.iscsi;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.vanheusden.BlackHole.Utils;

public class SCSI_read_10 extends SCSI_response {
	public SCSI_read_10(FileChannel fc, CDB cdb, int blockSize)
			throws IOException {
		byte[] bIn = cdb.bytes;
		long lba = Utils.byteArrayToInt(bIn, 2) & 0xffffffff;
		int nBlocks = ((int) (bIn[7] & 0xff) << 8) + (bIn[8] & 0xff);

		ByteBuffer b = ByteBuffer.allocate(blockSize * nBlocks);
		int rc = fc.read(b, lba * blockSize);
		bytes = b.array();
	}
}
