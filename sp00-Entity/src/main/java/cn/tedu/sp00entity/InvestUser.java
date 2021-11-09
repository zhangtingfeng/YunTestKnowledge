package cn.tedu.sp00entity;

import java.io.Serializable;

/**
 * @author oliver zhang
 * @date 2021-03-31
 */
public class InvestUser extends BaseEntity implements Serializable {

    /**
     * id
     */
    private long id;
    /**
     * 聊聊号
     */
    private String chatCode;
    /**
     * 用户昵称
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 手机号
     */
    private String phone;
    /**
     * vip标示：0 月卡；1季卡；3年卡
     * 0:普通会员
     * 1:月vip
     * 2:季vip
     * 3:年vip
     */
    private String vipLogo;
    /**
     * 会员等级
     */
    private long level;
    /**
     * 个性签名
     */
    private String signature;
    /**
     * 0、休闲；1、忙碌；3、勿扰；4、离线
     */
    private String isActive;
    /**
     * 自我介绍
     */
    private String hobby;
    /**
     * 背景图片id
     */
    private String backPicture;
    /**
     * json  list  int
     */
    private String pictureUrls;
    /**
     * 身份证号
     */
    private String idNumber;
    /**
     * 身份证正面
     */
    private String idPictureFront;
    /**
     * 身份证反面
     */
    private String idPictureBack;
    /**
     * 所在城市
     */
    private String city;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 性别  1男  2女
     */
    private String sex;
    /**
     * 年龄
     */
    private String age;
    private String birthday;
    /**
     * qq联合登录
     */
    private String qqOpenid;
    /**
     * 微信联合登录
     */
    private String wxOpenid;
    /**
     * 是否认证，0未认证，1已认证，2审核中 3审核不通过
     */
    private long isVerify;
    /**
     * json  list  int  上传的自拍照
     */
    private String selfieUrls;
    /**
     * 创建人ID
     */
    private long creator;
    /**
     * 修改人ID
     */
    private long updator;
    /**
     * Y表示删除  N表示未删除
     */
    private String isDelete;


    /**
     * id
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    /**
     * 聊聊号
     */
    public String getChatCode() {
        return chatCode;
    }

    public void setChatCode(String chatCode) {
        this.chatCode = chatCode;
    }


    /**
     * 用户昵称
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * 用户密码
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * 手机号
     */
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    /**
     * vip标示：0 月卡；1季卡；3年卡
     * 0:普通会员
     * 1:月vip
     * 2:季vip
     * 3:年vip
     */
    public String getVipLogo() {
        return vipLogo;
    }

    public void setVipLogo(String vipLogo) {
        this.vipLogo = vipLogo;
    }


    /**
     * 会员等级
     */
    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }


    /**
     * 个性签名
     */
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }


    /**
     * 0、休闲；1、忙碌；3、勿扰；4、离线
     */
    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }


    /**
     * 自我介绍
     */
    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }


    /**
     * 背景图片id
     */
    public String getBackPicture() {
        return backPicture;
    }

    public void setBackPicture(String backPicture) {
        this.backPicture = backPicture;
    }


    /**
     * json  list  int
     */
    public String getPictureUrls() {
        return pictureUrls;
    }

    public void setPictureUrls(String pictureUrls) {
        this.pictureUrls = pictureUrls;
    }


    /**
     * 身份证号
     */
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }


    /**
     * 身份证正面
     */
    public String getIdPictureFront() {
        return idPictureFront;
    }

    public void setIdPictureFront(String idPictureFront) {
        this.idPictureFront = idPictureFront;
    }


    /**
     * 身份证反面
     */
    public String getIdPictureBack() {
        return idPictureBack;
    }

    public void setIdPictureBack(String idPictureBack) {
        this.idPictureBack = idPictureBack;
    }


    /**
     * 所在城市
     */
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    /**
     * 经度
     */
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    /**
     * 纬度
     */
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


    /**
     * 性别  1男  2女
     */
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    /**
     * 年龄
     */
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


    /**
     * qq联合登录
     */
    public String getQqOpenid() {
        return qqOpenid;
    }

    public void setQqOpenid(String qqOpenid) {
        this.qqOpenid = qqOpenid;
    }


    /**
     * 微信联合登录
     */
    public String getWxOpenid() {
        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }


    /**
     * 是否认证，0未认证，1已认证，2审核中 3审核不通过
     */
    public long getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(long isVerify) {
        this.isVerify = isVerify;
    }


    /**
     * json  list  int  上传的自拍照
     */
    public String getSelfieUrls() {
        return selfieUrls;
    }

    public void setSelfieUrls(String selfieUrls) {
        this.selfieUrls = selfieUrls;
    }


    /**
     * 创建人ID
     */
    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
        this.creator = creator;
    }


    /**
     * 修改人ID
     */
    public long getUpdator() {
        return updator;
    }

    public void setUpdator(long updator) {
        this.updator = updator;
    }


    /**
     * Y表示删除  N表示未删除
     */
    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

}
