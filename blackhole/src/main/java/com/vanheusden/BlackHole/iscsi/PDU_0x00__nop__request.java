/* Released under GPL 2.0
 * (C) 2011 by folkert@vanheusden.com
 */
package com.vanheusden.BlackHole.iscsi;

import java.io.IOException;
import java.io.InputStream;

import com.vanheusden.BlackHole.Utils;

public class PDU_0x00__nop__request extends PDU {
	public PDU_0x00__nop__request(byte[] b, byte[] d) {
		bytes = b;
		data = d;
	}

	public PDU_0x00__nop__request(int sn, int esn, CDB cdb) {
		setBit(0, 6, true); // 1
		setBits(0, 0, 0x01, 6); // byte 0, bit 0-5, opcode 0x01, 6 wide
		setBit(1, 7, true); // F=1
		setBit(1, 6, true); // R=1
		setBit(1, 5, false); // W=1
		setBits(1, 0, 1, 3); // ATTR=1
		bytes[16] = 0x0a; // initiator task tag 0x0a
		bytes[23] = 8;
		bytes[24] = (byte) ((sn >> 24) & 255);
		bytes[25] = (byte) ((sn >> 16) & 255);
		bytes[26] = (byte) ((sn >> 8) & 255);
		bytes[27] = (byte) (sn & 255);// CmdSN
		bytes[28] = (byte) ((esn >> 24) & 255);
		bytes[29] = (byte) ((esn >> 16) & 255);
		bytes[30] = (byte) ((esn >> 8) & 255);
		bytes[31] = (byte) (esn & 255);// expatsn
		Utils.arrayCopy(bytes, 32, cdb.getAsByteArray());
	}

	public PDU_0x00__nop__request(InputStream is) throws IOException {
		is.read(bytes);
	}

	public PDU_0x00__nop__request() {
		setBits(0, 0, 0x00, 6); // byte 0, bit 0-5, opcode 0x00, 6 wide
		setBit(1, 7, true);
	}

	public boolean getI() {
		return getBit(0, 6);
	}

	public void setI(boolean to) {
		setBit(0, 6, to);
	}

	public long getLUN() {
		return getLong(8);
	}

	public void setLUN(long lun) {
		setLong(8, lun);
	}

	public int getInitiatorTaskTag() {
		return getInt(16);
	}

	public void setInitiatorTaskTag(int tag) {
		setInt(16, tag);
	}

	public int getTargetTransferTag() {
		return getInt(20);
	}

	public void setTargetTransferTag(int value) {
		setInt(20, value);
	}

	public int getCmdSN() {
		return getInt(24);
	}

	public void setCmdSN(int cs) {
		setInt(24, cs);
	}

	public int getExpStatSN() {
		return getInt(28);
	}

	public void setExpStatSN(int ess) {
		setInt(28, ess);
	}
}
