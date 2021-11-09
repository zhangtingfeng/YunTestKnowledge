package cn.tedu.sp24.controller;


import cn.tedu.sp24.entity.Tuploadfile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 实现RowMapper接口，返回User对象
 */
public class MyRowMapper implements RowMapper<Tuploadfile> {

    @Override
    public Tuploadfile mapRow(ResultSet resultSet, int i) throws SQLException {
//        获取结果集中的数据
        String uploadFileID = resultSet.getString("uploadfileID");
        String fileName = resultSet.getString("filename");
        String Account = resultSet.getString("account");
        String Base64 = resultSet.getString("base64");
//        把数据封装成User对象
        Tuploadfile user = new Tuploadfile();
        user.setUploadfileID(uploadFileID);
        user.setFilename(fileName);
        user.setAccount(Account);
        user.SetBase64(Base64);
        return user;
    }
}
