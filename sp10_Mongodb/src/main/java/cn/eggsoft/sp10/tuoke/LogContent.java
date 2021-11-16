package cn.eggsoft.sp10.tuoke;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
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
}