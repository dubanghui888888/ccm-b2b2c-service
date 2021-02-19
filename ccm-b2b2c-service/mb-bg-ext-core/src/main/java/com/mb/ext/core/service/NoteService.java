
package com.mb.ext.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.NoteDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.framework.exception.BusinessException;

/**系统通知接口
 * @author B2B2C商城
 *
 * @param <T>
 */
public interface NoteService<T extends BodyDTO>
{
	/**删除系统通知
	 * @param noteDTO - 系统通知
	 * @throws BusinessException
	 */
	@Transactional
	void deleteNote(NoteDTO noteDTO) throws BusinessException;
	
	/**查询会员系统通知
	 * @param userDTO - 会员
	 * @return
	 * @throws BusinessException
	 */
	@Transactional
	List<NoteDTO> getNotes(UserDTO userDTO) throws BusinessException;
	
	/**标记通知已读
	 * @param noteDTO - 系统通知
	 * @throws BusinessException
	 */
	@Transactional
	void markRead(NoteDTO noteDTO) throws BusinessException;
	
	/**获取会员未读通知数量
	 * @param userDTO - 会员
	 * @return
	 * @throws BusinessException
	 */
	int getUnreadNoteCount(UserDTO userDTO) throws BusinessException;
	
	/**发送系统通知
	 * @param userDTO - 会员
	 * @param noteType - 通知类型
	 * @param params - 参数
	 * @throws BusinessException
	 */
	void sendNotification(UserDTO userDTO, String noteType, String[] params) throws BusinessException;
	
	/**短信通知是否开启
	 * @param noteType - 通知类型
	 * @return
	 * @throws BusinessException
	 */
	boolean isSMSEnabled(String noteType) throws BusinessException;
	
	/** 邮件通知是否开启
	 * @param noteType - 通知类型
	 * @return
	 * @throws BusinessException
	 */
	boolean isEmailEnabled(String noteType) throws BusinessException;
	
	/**微信通知是否开启
	 * @param noteType - 通知类型
	 * @return
	 * @throws BusinessException
	 */
	boolean isWechatEnabled(String noteType) throws BusinessException;
	
	/**标记会员所有通知已读
	 * @param userDTO - 会员
	 * @throws BusinessException
	 */
	void markUserNoteRead(UserDTO userDTO) throws BusinessException;
	
}
