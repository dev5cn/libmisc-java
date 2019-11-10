/*
  Copyright 2019 www.dev5.cn, Inc. dev5@qq.com
 
  This file is part of X-MSG-IM.
 
  X-MSG-IM is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  X-MSG-IM is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
 
  You should have received a copy of the GNU Affero General Public License
  along with X-MSG-IM.  If not, see <https://www.gnu.org/licenses/>.
 */
package misc;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;
import java.util.zip.CRC32;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Crypto
{
	private static final char __0aA__[] = { '_', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	public static final String base64enc(byte by[])
	{
		try
		{
			return Base64.getEncoder().encodeToString(by);
		} catch (Exception e)
		{
			if (Log.isDebug())
				Log.debug("%s", Log.trace(e));
			return null;
		}
	}

	public static final String base64enc(String str)
	{
		try
		{
			return Base64.getEncoder().encodeToString(str.getBytes());
		} catch (Exception e)
		{
			if (Log.isDebug())
				Log.debug("%s", Log.trace(e));
			return null;
		}
	}

	public static final byte[] base64dec(String str)
	{
		try
		{
			return Base64.getDecoder().decode(str);
		} catch (Exception e)
		{
			if (Log.isDebug())
				Log.debug("%s", Log.trace(e));
			return null;
		}
	}

	public static final String base64dec2Str(String str)
	{
		try
		{
			return new String(Base64.getDecoder().decode(str));
		} catch (Exception e)
		{
			if (Log.isDebug())
				Log.debug("%s", Log.trace(e));
			return null;
		}
	}

	public static final byte[] base64UrlDec(String str)
	{
		try
		{
			return Base64.getUrlDecoder().decode(str);
		} catch (Exception e)
		{
			if (Log.isDebug())
				Log.debug("%s", Log.trace(e));
			return null;
		}
	}

	public static final String base64UrlDec2Str(String str)
	{
		try
		{
			return new String(Base64.getUrlDecoder().decode(str));
		} catch (Exception e)
		{
			if (Log.isDebug())
				Log.debug("%s", Log.trace(e));
			return null;
		}
	}

	public static final byte[] rc4enc(byte key[], byte[] org)
	{
		return Crypto.rc4enc(key, org, 0, org.length);
	}

	public static final byte[] rc4enc(byte key[], byte[] org, int ofst, int len)
	{
		try
		{
			Key k = new SecretKeySpec(key, "RC4");
			Cipher cip = Cipher.getInstance("RC4");
			cip.init(Cipher.ENCRYPT_MODE, k);
			return cip.doFinal(org, ofst, len);
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
			return null;
		}
	}

	public static final byte[] rc4dec(byte key[], byte[] crypto)
	{
		return Crypto.rc4dec(key, crypto, 0, crypto.length);
	}

	public static final byte[] rc4dec(byte key[], byte[] crypto, int ofst, int len)
	{
		try
		{
			Key k = new SecretKeySpec(key, "RC4");
			Cipher cip = Cipher.getInstance("RC4");
			cip.init(Cipher.DECRYPT_MODE, k);
			return cip.doFinal(crypto, ofst, len);
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
			return null;
		}
	}

	public static final byte[] aesEnc(byte[] key, byte[] org)
	{
		try
		{
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));
			return cipher.doFinal(org);
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
			return null;
		}
	}

	public static final String aesEnc2StrLowerCase(byte[] key, byte[] org)
	{
		return Net.byte2hexStr(Crypto.aesEnc(key, org)).toLowerCase();
	}

	public static final String aesEnc2StrLowerCase(String key, String org)
	{
		return Crypto.aesEnc2StrLowerCase(key.getBytes(), org.getBytes());
	}

	public static final byte[] aesDec(byte[] key, byte[] crypto)
	{
		try
		{
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, 0, 0x10, "AES"));
			return cipher.doFinal(crypto);
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
			return null;
		}
	}

	public static final byte[] aesDec(String key, String crypto )
	{
		return Crypto.aesDec(key.getBytes(), Net.hex2bytes(crypto));
	}

	public static final String aesDec2Str(String key, String crypto )
	{
		return new String(Crypto.aesDec(key.getBytes(), Net.hex2bytes(crypto)));
	}

	public static final byte[] rsaPrivateEnc(byte priKey[], byte[] org)
	{
		try
		{
			PKCS8EncodedKeySpec specx = new PKCS8EncodedKeySpec(priKey);
			PrivateKey pri = KeyFactory.getInstance("RSA").generatePrivate(specx);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, pri);
			if (org.length < 117)
				return cipher.doFinal(org);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int c = org.length / 117;
			int m = org.length % 117;
			for (int i = 0; i < c; ++i)
				out.write(cipher.doFinal(org, i * 117, 117));
			if (m != 0)
				out.write(cipher.doFinal(org, c * 117, m));
			return out.toByteArray();
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
			return null;
		}
	}

	public static final byte[] rsaPrivateDec(byte priKey[], byte[] org)
	{
		try
		{
			PKCS8EncodedKeySpec specx = new PKCS8EncodedKeySpec(priKey);
			PrivateKey pri = KeyFactory.getInstance("RSA").generatePrivate(specx);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, pri);
			if (org.length < 128)
				return cipher.doFinal(org);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int c = org.length / 128;
			int m = org.length % 128;
			for (int i = 0; i < c; ++i)
				out.write(cipher.doFinal(org, i * 128, 128));
			if (m != 0)
				out.write(cipher.doFinal(org, c * 128, m));
			return out.toByteArray();
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
			return null;
		}
	}

	public static final byte[] rsaPublicEnc(byte pubKey[], byte[] org)
	{
		try
		{
			X509EncodedKeySpec spec = new X509EncodedKeySpec(pubKey);
			PublicKey pub = KeyFactory.getInstance("RSA").generatePublic(spec);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, pub);
			if (org.length < 117)
				return cipher.doFinal(org);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int c = org.length / 117;
			int m = org.length % 117;
			for (int i = 0; i < c; ++i)
				out.write(cipher.doFinal(org, i * 117, 117));
			if (m != 0)
				out.write(cipher.doFinal(org, c * 117, m));
			return out.toByteArray();
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
			return null;
		}
	}

	public static final byte[] rsaPublicDec(byte pubKey[], byte[] org)
	{
		try
		{
			X509EncodedKeySpec spec = new X509EncodedKeySpec(pubKey);
			PublicKey pub = KeyFactory.getInstance("RSA").generatePublic(spec);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, pub);
			if (org.length < 128)
				return cipher.doFinal(org);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int c = org.length / 128;
			int m = org.length % 128;
			for (int i = 0; i < c; ++i)
				out.write(cipher.doFinal(org, i * 128, 128));
			if (m != 0)
				out.write(cipher.doFinal(org, c * 128, m));
			return out.toByteArray();
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
			return null;
		}
	}

	public static final byte[] md5(byte[] by)
	{
		try
		{
			return MessageDigest.getInstance("MD5").digest(by);
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
			return null;
		}
	}

	public static final String md5StrUpperCase(byte by[])
	{
		return Net.byte2hexStr(Crypto.md5(by));
	}

	public static final String md5StrUpperCase(String str)
	{
		return Net.byte2hexStr(Crypto.md5(str.getBytes()));
	}

	public static final String md5StrLowerCase(byte by[])
	{
		return Net.byte2hexStr(Crypto.md5(by)).toLowerCase();
	}

	public static final String md5StrLowerCase(String str)
	{
		return Net.byte2hexStr(Crypto.md5(str.getBytes())).toLowerCase();
	}

	public static final byte[] sha1(byte[] by)
	{
		try
		{
			return MessageDigest.getInstance("SHA-1").digest(by);
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
			return null;
		}
	}

	public static final String sha1StrUpperCase(byte by[])
	{
		return Net.byte2hexStr(Crypto.sha1(by));
	}

	public static final String sha1StrLowerCase(byte by[])
	{
		return Net.byte2hexStr(Crypto.sha1(by)).toLowerCase();
	}

	public static final byte[] sha256(byte[] by)
	{
		try
		{
			return MessageDigest.getInstance("SHA-256").digest(by);
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
			return null;
		}
	}

	public static final String sha256StrUpperCase(byte by[])
	{
		return Net.byte2hexStr(Crypto.sha256(by));
	}

	public static final String sha256StrLowerCase(byte by[])
	{
		return Net.byte2hexStr(Crypto.sha256(by)).toLowerCase();
	}

	public static final String sha256StrLowerCase(String str)
	{
		return Crypto.sha256StrLowerCase(str.getBytes());
	}

	public static final byte[] sha512(byte[] by)
	{
		try
		{
			return MessageDigest.getInstance("SHA-512").digest(by);
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
			return null;
		}
	}

	public static final String sha512StrUpperCase(byte by[])
	{
		return Net.byte2hexStr(Crypto.sha512(by));
	}

	public static final String sha512StrLowerCase(byte by[])
	{
		return Net.byte2hexStr(Crypto.sha512(by)).toLowerCase();
	}

	public static final String sha512StrLowerCase(String str)
	{
		return Crypto.sha512StrLowerCase(str.getBytes());
	}

	
	public static final String uuid()
	{
		return new UUID(Misc.randLang(), Misc.randLang()).toString().replaceAll("-", "");
	}

	public static final String gen0aAkey128()
	{
		byte by[] = new byte[16];
		for (int i = 0; i < 4; ++i)
		{
			int x = Misc.randInt();
			by[4 * i + 0] = (byte) __0aA__[(x) % __0aA__.length];
			by[4 * i + 1] = (byte) __0aA__[(x >> 0x08) % __0aA__.length];
			by[4 * i + 2] = (byte) __0aA__[(x >> 0x10) % __0aA__.length];
			by[4 * i + 3] = (byte) __0aA__[(x >> 0x18) % __0aA__.length];
		}
		return new String(by);
	}

	public static final String gen0aAkey256()
	{
		byte by[] = new byte[32];
		for (int i = 0; i < 8; ++i)
		{
			int x = Misc.randInt();
			by[4 * i + 0] = (byte) __0aA__[(x) % __0aA__.length];
			by[4 * i + 1] = (byte) __0aA__[(x >> 0x08) % __0aA__.length];
			by[4 * i + 2] = (byte) __0aA__[(x >> 0x10) % __0aA__.length];
			by[4 * i + 3] = (byte) __0aA__[(x >> 0x18) % __0aA__.length];
		}
		return new String(by);
	}

	public static final String gen0aAkey512()
	{
		byte by[] = new byte[64];
		for (int i = 0; i < 8; ++i)
		{
			int x = Misc.randInt();
			by[8 * i + 0] = (byte) __0aA__[(x) % __0aA__.length];
			by[8 * i + 1] = (byte) __0aA__[(x >> 0x08) % __0aA__.length];
			by[8 * i + 2] = (byte) __0aA__[(x >> 0x10) % __0aA__.length];
			by[8 * i + 3] = (byte) __0aA__[(x >> 0x18) % __0aA__.length];
			x = Misc.randInt();
			by[8 * i + 4] = (byte) __0aA__[(x) % __0aA__.length];
			by[8 * i + 5] = (byte) __0aA__[(x >> 0x08) % __0aA__.length];
			by[8 * i + 6] = (byte) __0aA__[(x >> 0x10) % __0aA__.length];
			by[8 * i + 7] = (byte) __0aA__[(x >> 0x18) % __0aA__.length];
		}
		return new String(by);
	}

	public static final long crc32(String str)
	{
		CRC32 crc = new CRC32();
		crc.update(str.getBytes());
		return crc.getValue();
	}

	public static final short crc16(byte by[], int ofst, int len)
	{
		int crc_table[] = { 0x0000, 0x1021, 0x2042, 0x3063, 0x4084, 0x50A5, 0x60C6, 0x70E7, 0x8108, 0x9129, 0xA14A, 0xB16B, 0xC18C, 0xD1AD, 0xE1CE, 0xF1EF, 0x1231, 0x0210, 0x3273, 0x2252, 0x52B5, 0x4294, 0x72F7, 0x62D6, 0x9339, 0x8318, 0xB37B, 0xA35A, 0xD3BD, 0xC39C, 0xF3FF, 0xE3DE, 0x2462, 0x3443, 0x0420, 0x1401, 0x64E6, 0x74C7, 0x44A4, 0x5485, 0xA56A, 0xB54B, 0x8528, 0x9509, 0xE5EE, 0xF5CF, 0xC5AC, 0xD58D, 0x3653, 0x2672, 0x1611, 0x0630, 0x76D7, 0x66F6, 0x5695, 0x46B4, 0xB75B, 0xA77A, 0x9719, 0x8738, 0xF7DF, 0xE7FE, 0xD79D, 0xC7BC, 0x48C4, 0x58E5, 0x6886, 0x78A7, 0x0840, 0x1861, 0x2802, 0x3823, 0xC9CC, 0xD9ED, 0xE98E, 0xF9AF, 0x8948, 0x9969, 0xA90A, 0xB92B, 0x5AF5, 0x4AD4, 0x7AB7, 0x6A96, 0x1A71, 0x0A50, 0x3A33, 0x2A12, 0xDBFD, 0xCBDC, 0xFBBF, 0xEB9E, 0x9B79, 0x8B58, 0xBB3B, 0xAB1A, 0x6CA6, 0x7C87, 0x4CE4, 0x5CC5, 0x2C22, 0x3C03, 0x0C60, 0x1C41, 0xEDAE, 0xFD8F, 0xCDEC, 0xDDCD, 0xAD2A, 0xBD0B, 0x8D68, 0x9D49, 0x7E97, 0x6EB6, 0x5ED5, 0x4EF4, 0x3E13, 0x2E32, 0x1E51, 0x0E70, 0xFF9F, 0xEFBE, 0xDFDD, 0xCFFC, 0xBF1B, 0xAF3A, 0x9F59, 0x8F78, 0x9188, 0x81A9, 0xB1CA, 0xA1EB, 0xD10C, 0xC12D, 0xF14E, 0xE16F, 0x1080, 0x00A1, 0x30C2, 0x20E3, 0x5004, 0x4025, 0x7046, 0x6067, 0x83B9, 0x9398, 0xA3FB, 0xB3DA, 0xC33D, 0xD31C, 0xE37F, 0xF35E, 0x02B1, 0x1290, 0x22F3, 0x32D2, 0x4235, 0x5214, 0x6277, 0x7256, 0xB5EA, 0xA5CB, 0x95A8, 0x8589, 0xF56E, 0xE54F, 0xD52C, 0xC50D, 0x34E2, 0x24C3, 0x14A0, 0x0481, 0x7466, 0x6447, 0x5424, 0x4405, 0xA7DB, 0xB7FA, 0x8799, 0x97B8, 0xE75F, 0xF77E, 0xC71D, 0xD73C, 0x26D3, 0x36F2, 0x0691, 0x16B0, 0x6657, 0x7676, 0x4615, 0x5634, 0xD94C, 0xC96D, 0xF90E, 0xE92F, 0x99C8, 0x89E9, 0xB98A, 0xA9AB, 0x5844, 0x4865, 0x7806, 0x6827, 0x18C0, 0x08E1, 0x3882, 0x28A3, 0xCB7D, 0xDB5C, 0xEB3F, 0xFB1E, 0x8BF9, 0x9BD8, 0xABBB, 0xBB9A, 0x4A75, 0x5A54, 0x6A37, 0x7A16, 0x0AF1, 0x1AD0, 0x2AB3, 0x3A92, 0xFD2E, 0xED0F, 0xDD6C, 0xCD4D, 0xBDAA, 0xAD8B, 0x9DE8, 0x8DC9, 0x7C26, 0x6C07, 0x5C64, 0x4C45, 0x3CA2, 0x2C83, 0x1CE0, 0x0CC1, 0xEF1F, 0xFF3E, 0xCF5D, 0xDF7C, 0xAF9B, 0xBFBA, 0x8FD9, 0x9FF8, 0x6E17, 0x7E36, 0x4E55, 0x5E74, 0x2E93, 0x3EB2, 0x0ED1, 0x1EF0 };
		short crc = 0;
		for (int i = ofst; i < len; i++)
			crc = (short) (crc_table[((crc >> 8) ^ by[i]) & 0xff] ^ (crc << 8));
		return crc;
	}
}
