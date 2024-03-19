package setxpro.challengseniorbackendpicpay.infra.email;

public class SentEmailDto {
    private String to;
    private String from;

    private String html;

    private String subject;

    private String base64Attachment;

    private String base64AttachmentName;

    private String message;

    public SentEmailDto(String to, String from, String html, String subject, String base64Attachment, String base64AttachmentName, String message) {
        this.to = to;
        this.from = from;
        this.html = html;
        this.subject = subject;
        this.base64Attachment = base64Attachment;
        this.base64AttachmentName = base64AttachmentName;
        this.message = message;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBase64Attachment(String base64Attachment) {
        this.base64Attachment = base64Attachment;
    }

    public void setBase64AttachmentName(String base64AttachmentName) {
        this.base64AttachmentName = base64AttachmentName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBase64AttachmentName() {
        return base64AttachmentName;
    }
    public String getBase64Attachment() {
        return base64Attachment;
    }
    public String getMessage() {
        return message;
    }
    public String getSubject() {
        return subject;
    }
    public String getHtml() {
        return html;
    }
    public String getTo() {
        return to;
    }
    public String getFrom() {
        return from;
    }
}
