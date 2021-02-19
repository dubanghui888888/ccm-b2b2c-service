package com.mb.ext.core.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
	/**  密钥算法 */
    private static final String ALGORITHM = "AES";
    /**  加解密算法/工作模式/填充方式  */
    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS5Padding";
    /** AES加密  */
    public static String encryptData(String data,String password) throws Exception {
        // 创建密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
        SecretKeySpec key = new SecretKeySpec(MD5Util.MD5(password).toLowerCase().getBytes(), ALGORITHM);
        // 初始化
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64Util.encode(cipher.doFinal(data.getBytes()));
    }
    /** AES解密 */
    public static String decryptData(String base64Data,String password) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
        SecretKeySpec key = new SecretKeySpec(MD5Util.MD5(password).toLowerCase().getBytes(), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decode = Base64Util.decode(base64Data);
        byte[] doFinal = cipher.doFinal(decode);
        return new String(doFinal,"utf-8");
    }
    
    public static void main(String[] args) {
		try {
			System.out.print(decryptData("ow75zl50fef/M8zaSwemGWuUmCYP5Nqzw/aR7j2otbtIrSDC+GOv5VmA74v/dChTcg7DSssWscRViz6JmWqsTiMsUlC2+dctQA4DoOFvvLt71jUWfWidqUckEjhqVHNLHxzcX7jVyjgfvigjJl0SZMZahAOwhsERPM4vO38OyRXkLH1qN5xVkUD/cvpazTbV7Qo+GBRxd1QSAjYafCdArESVNue444AOwKpLevubK1xsnZywO5cnd0FYCPGiYmg6xHQZ8iTjwYdFi5Bao1jOKzfmnBiIOfm6XDJz2M0MRYYEANBdDkcsqvtg/eYh5gi16UIDhvB8hZ0uc6UPy5Nsihuuwsd8YrrAbrzw9Uc3d9NWn9v3HUG70Y6AemMl/lYT1+JQJ+ZC7xZFVhZU65lW0tp5lqmfCiX68mctKuHf6BGYkeqad1YROBJ6Ona53wZXiXetJRb3jWtAVA5NCwWtodHa9bQu7lc7472NMMQKJ3dIciD2s4gZPR49NrwnrdYRtFb1D1wNv7GSG6QlQsmNK+B8AAd/jyN/GuVUpxepSiuLPp+YCRTwXoSrWY03YkMHHwwMJBwpNGJPeVVfwVOEgJ/YnwK34v5Lhfqcgo0vIUYfYYaxWi6Kb/8oOFtchSmqaLNkjGGgUzcAukMG6ZJjsWC4RZF843gJ3/87FrvuMc3ItUtFgO+G+Acbd8kPsLqv9w3VnU6B8m5NE8IMNcKF7ZPRfqQLwrt3PQdMj+9jiGpiMvQhtGm8PXaK9bIV7jnoMPnFenaPQi2dQGalteGvVxHbDEQwzs7Q+V+I4oIKAnjQ5ddEQqhAxso/5xA1nzbkYlCD2qOEGEkpJKIuAk1GW5CU4nibfpRD6VLr7/IOU5/j+8F6bwMmBGLoN5stbOO4tWHFVFHaKZApEMa4aUylNWMjADwVArSUYOpUW/1kKQlv+yJUiFdt7dxpr5e7vVBOcwwIV8c6b1zwwbg/8HWbqLuA1OCFugswkhGRReZSXR2ipt9aTghfTyZ8hutLzPtOM8g53/m9GI9t/g0XFPAQOnGdIk5unVD+VxQ3eIVN41M=", "12345678901234567890123456789012"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
