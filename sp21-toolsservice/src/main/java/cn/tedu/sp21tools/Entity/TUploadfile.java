package cn.tedu.sp21tools.Entity;

import java.io.Serializable;

public class TUploadfile implements Serializable {

    private String uploadfileID;

    public String getUploadfileID() {
        return uploadfileID;
    }

    public void setUploadfileID(String id) {
        this.uploadfileID = id;
    }

    private String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String id) {
        this.filename = id;
    }

    private String filedesc;

    public String getFiledesc() {
        return filedesc;
    }

    public void setFiledesc(String id) {
        this.filedesc = id;
    }

    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String id) {
        this.account = id;
    }

    private String base64;

    public String getBase64() {
        return base64;
    }

    public void setBase64(String id) {
        this.base64 = id;
    }
}
