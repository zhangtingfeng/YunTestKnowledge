package cn.eggsoft.sp10.tuoke;


import com.fasterxml.jackson.annotation.JsonFormat;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

@NoArgsConstructor
@Document(collection = "logContent")
public class LogContent {
    private Object num;

    @Id
    private String  id;
    private String title;
    private String subtitle;
    private String content;


    private Date updatetime;

    private Date startTime;
    private Date endTime;

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getContent() {
        return content;
    }

    public Date getStartTime() {
        return  startTime;
    }


    public Date getEndTime() {
        return  endTime;
    }

    public Date getUpdatetime() {
        return  updatetime;
    }

    public void setNum(Integer arginteger) {
        num=arginteger;
    }

    public void setTitle(String argtitle) {
        title=argtitle;
    }

    public void setSubtitle(String argsubtitle) {
        subtitle=argsubtitle;
    }

    public void setContent(String argcontent) {
        content=argcontent;
    }

    public void setUpdatetime(Date argmongoDate) {
        updatetime=argmongoDate;
    }

    public void setStartTime(Date argstartTime) {
        startTime=argstartTime;
    }

    public void setEndTime(Date argendTime) {
        endTime=argendTime;
    }
}