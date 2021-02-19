package com.mb.framework.crypto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Iterator;

import com.mb.framework.exception.EncryptedException;
import com.mb.framework.util.SecurityUtil;
import com.mb.framework.util.log.LogHelper;

public class PasswordEncryptor
{
	/**
	 * ENCRYPTED_PREFIX
	 */
	private static final String ENCRYPTED_PREFIX = "{3DES}";

	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	private String pwdFileURL;
	
	private String paramKey;

	private static final String[] EMPTY_LINE = { null, null, null };

	private static final String COMMENT = "#";

	private static final String LF = System.getProperty("line.separator");

	private static final String KEYS_FILE_COMMENT = "# List of keys and their associated values \"keyname=keyvalue\", i.e." + LF + "#  (Example 1)    database.system.password=manager" + LF + "#  (Example 2)    My Keystore Passphrase=Je ne cherche pas, je trouve!" + LF + "#" + LF + "# Clear text passwords will be automatically encrypted at the next run." + LF + "# To change the value of an already encrypted password, simply replace it" + LF + "# with the new clear text value." + LF;

	/**
	 * This method is used for getting plain password while read the encrypted password from file
	 * @return plain password
	 */
	public String getPassword()
	{
		String password = pwdFileURL;
		File pwdfile = new File(pwdFileURL);
		if (!pwdfile.exists() || !pwdfile.canRead())
		{
			logger.error("IO Error reading password file: " + pwdFileURL);
		}
		else
		{//Nirenj
			logger.debug("Password file exist at: " + pwdFileURL);
		}

		try
		{
			String encryptedPwd = getEncryptedPassword(pwdfile,paramKey);
			if(encryptedPwd.contains(ENCRYPTED_PREFIX)){
				encryptedPwd = encryptedPwd.substring(ENCRYPTED_PREFIX.length());
			}
			password = SecurityUtil.decryptAES(encryptedPwd).trim();
		}
		catch (Exception e)
		{
			logger.error("Error while decrypting password", e);
		}
		return password;
	}

	/**
	 * This method is used for getting encrypted password with the parameter key
	 * @param pwdfile
	 * @param paramKey
	 * @return encrypted password
	 * @throws Exception
	 */
	private String getEncryptedPassword(File pwdfile,String paramKey) throws Exception
	{
		String encryptedPwd = "";
		if (pwdfile.exists())
		{
			ArrayList<String[]> pairs = new ArrayList<String[]>();
			boolean mustEncrypt = parseKeyFile(pwdfile, pairs);
			
			if (mustEncrypt)
			{
				String[] lines = new String[pairs.size()];
				int index = 0;
				StringBuffer tmp = new StringBuffer(64);

				for (Iterator<String[]> it = pairs.iterator(); it.hasNext();)
				{
					String[] trio = it.next();
					if (trio == EMPTY_LINE)
					{
						lines[(index++)] = "";
					}
					else if (trio[0] == COMMENT)
					{
						lines[(index++)] = trio[1];
					}
					else
					{
						if (trio[2] == null)
						{
							String encryptAES = SecurityUtil.encryptAES(trio[1]);
							trio[2] = ENCRYPTED_PREFIX + encryptAES;
						}

						if(trio[0].equalsIgnoreCase(paramKey)){
							encryptedPwd = trio[2];
						}
						
						tmp.setLength(0);
						tmp.append(trio[0]);
						tmp.append('=');
						tmp.append(trio[2]);
						
						lines[(index++)] = tmp.toString();
					}

				}
				writeKeys(lines, pwdfile);
			}else{
				for (Iterator<String[]> it = pairs.iterator(); it.hasNext();){
					String[] trio = it.next();
					if (trio != EMPTY_LINE && trio[0] != COMMENT && trio[0].equals(paramKey))
					{
						encryptedPwd = trio[1];
					}
				}
			}
		}
		return encryptedPwd;
	}

	/**
	 * This method is used for parsing file and reorganize the file content
	 * @param pwdFile
	 * @param pairs
	 * @return
	 * @throws Exception
	 */
	private boolean parseKeyFile(File pwdFile, ArrayList<String[]> pairs) throws EncryptedException
	{
		boolean mustEncrypt = false;
		LineNumberReader in = null;
		try
		{
			in = new LineNumberReader(new FileReader(pwdFile));
			String line = null;
			while ((line = in.readLine()) != null)
			{
				if (line.length() == 0)
				{
					pairs.add(EMPTY_LINE);
					continue;
				}

				if (line.charAt(0) == '#')
				{
					pairs.add(new String[] { COMMENT, line, null });
					continue;
				}

				int eq = line.indexOf('=');
				if (eq <= 0)
				{
					String trim = line.trim();
					if ("".equals(trim))
					{
						pairs.add(EMPTY_LINE);
					}

					throw new EncryptedException("Parsing error in key file. Valid line must be a comment, a blank line or a keyname=keyvalue association. Check file at line " + in.getLineNumber() + LF + line);
				}

				String keyname = line.substring(0, eq);

				if (eq == line.length() - 1)
				{
					pairs.add(new String[] { keyname, "", null });
					mustEncrypt = true;
				}
				else
				{
					String keyvalue = line.substring(eq + 1, line.length());
					if (!keyvalue.startsWith(ENCRYPTED_PREFIX))
					{
						pairs.add(new String[] { keyname, keyvalue, null });
						mustEncrypt = true;
					}else{
						pairs.add(new String[] { keyname, keyvalue, keyvalue });
					}
				}
			}
		}
		catch (IOException e)
		{
			logger.error("Encounter error while read password file.", e);
		}
		finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException ioe)
				{
					throw new EncryptedException(ioe);
				}

				in = null;
			}
		}

		return mustEncrypt;
	}

	/**
	 * This method is used for writing encrypted password into file
	 * @param lines
	 * @param file
	 * @throws Exception
	 */
	private void writeKeys(String[] lines, File file) throws EncryptedException
	{
		if (file.canWrite())
		{
			BufferedWriter out = null;
			try
			{
				out = new BufferedWriter(new FileWriter(file));

				if ((lines.length > 0) && (lines[0].length() > 0) && (lines[0].charAt(0) != '#'))
				{
					out.write(KEYS_FILE_COMMENT);
					out.write(LF);
				}
				for (int i = 0; i < lines.length; ++i)
				{
					out.write(lines[i]);
					out.write(LF);
				}
				out.close();
				out = null;
			}
			catch (IOException e)
			{
				logger.error("Encounter io exception while write keys into file." ,e);
			}
			finally
			{
				if (out != null)
				{
					try
					{
						out.close();
					}
					catch (IOException ioe)
					{
						throw new EncryptedException(ioe);
					}

					out = null;
				}
			}
		}
		else
		{
			throw new EncryptedException("Can not encrypt access keys: can not write file " + file.getAbsolutePath());
		}
	}

	/**
	 * @param pwdFileURL
	 *            the pwdFileURL to set
	 */
	public final void setPwdFileURL(String pwdFileURL)
	{
		this.pwdFileURL = pwdFileURL;
	}

	/**
	 * @param paramKey the paramKey to set
	 */
	public final void setParamKey(String paramKey)
	{
		this.paramKey = paramKey;
	}

}
