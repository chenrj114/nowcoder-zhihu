package com.chenrj.zhihu.model;
import java.util.Map;

/**
 * 邮件接收参数
 */

public class MailDO {

    //标题
    private String title;
    //内容
    private String content;
    //接收人邮件地址
    private String email;
    //附加，value 文件的绝对地址/动态模板数据
    private Map<String, Object> attachment;

    public String getTitle() {
        return title;
    }

    public MailDO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MailDO setContent(String content) {
        this.content = content;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public MailDO setEmail(String email) {
        this.email = email;
        return this;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void addAttachment(String key, Object value) {
        attachment.put(key, value);
    }
}