package com.chenrj.zhihu.service.mail;

import com.chenrj.zhihu.model.MailDO;

/**
 * @author rjchen
 * @date 2020/10/13
 */

public interface MailService {

    void sendTextMail(MailDO mailDO);

    void sendHtmlMail(MailDO mailDO, boolean isShowHtml);

    void sendTemplateMail(MailDO mailDO);

}
