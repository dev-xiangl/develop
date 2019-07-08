package tdpay.mvc.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * メールメッセージクラス
 */
@Data
public class MailMessage {

    /** メールサーバーに対する、接続ユーザー名 */
    private String userName;

    /** メールサーバーに対する、接続ユーザーのパスワード */
    private String password;

    /** 送信文字エンコード */
    private String encoding = "ISO-2022-JP";

    /** SMTPサーバーのホスト名 */
    private String smtpHost;

    /** SMTPサーバーのポート番号 */
    private Integer smtpPort;

    /** SMTP認証の可否 */
    private Boolean smtpAuth;

    /** STARTTLSの可否 */
    private Boolean startTls;

    /** FROMメールアドレス */
    private String from;

    /** TOメールアドレスのリスト */
    private List<String> toList;

    /** CCメールアドレスのリスト */
    private List<String> ccList;

    /** メールのSubject */
    private String subject;

    /** メールの本文 */
    private String text;

    /** メールの添付ファイルのリスト */
    private List<File> attachmentFileList;

    /**
     * TOメールアドレスを追加する。
     *
     * @param mailAddress 対象メールアドレス
     */
    public void addToAddress(final String mailAddress) {
        if (this.toList == null) {
            this.toList = new ArrayList<>();
        }
        this.toList.add(mailAddress);
    }

    /**
     * CCメールアドレスを追加する。
     *
     * @param mailAddress 対象メールアドレス
     */
    public void addCcAddress(final String mailAddress) {
        if (this.ccList == null) {
            this.ccList = new ArrayList<>();
        }
        this.ccList.add(mailAddress);
    }

    /**
     *
     */
    public enum MailMessageType {
        NONE,
    }
}
