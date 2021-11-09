package cn.tedu.sp25.controller.QuizStatics;

import cn.tedu.sp0ag4studio.core.metatype.Dto;
import cn.tedu.sp0ag4studio.core.metatype.impl.BaseDto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Count_QuizID_28 {


    ////员工有无合作精神是衡量一项工作成功与否的关键因素之一，通过合作，产生1＋1≥2的效果。
    ///恋爱性格 id=28
    public static String QuizID_28(Integer intinvest_quiz_userID, cn.tedu.sp25.base.BaseMapper bizService) throws Exception {
        // Score=47;
        Dto dtoInfoquiz_userID = new BaseDto();
        dtoInfoquiz_userID.put("id", intinvest_quiz_userID);
        dtoInfoquiz_userID.put("abc123", 1);
        Dto totalCountA = (BaseDto) bizService.queryForDto("invest_quiz_search.Count_ID_28__total___", dtoInfoquiz_userID);
        Integer intA = totalCountA.getAsInteger("total");

        dtoInfoquiz_userID.put("abc123", 2);
        Dto totalCountB = (BaseDto) bizService.queryForDto("invest_quiz_search.Count_ID_28__total___", dtoInfoquiz_userID);
        Integer intB = totalCountB.getAsInteger("total");

        dtoInfoquiz_userID.put("abc123", 3);
        Dto totalCountC = (BaseDto) bizService.queryForDto("invest_quiz_search.Count_ID_28__total___", dtoInfoquiz_userID);
        Integer intC = totalCountC.getAsInteger("total");

        String strResult = "需要进一步测试，请联系我们";
        if (intA > 0 && intB == 0 && intC == 0) {
            strResult = "全部回答“A”";
        } else if (intA >= intB && intA >= intC) {
            strResult = "大部分回答“A”";
        } else if (intB >= intA && intB >= intC) {
            strResult = "大部分回答“B”";
        } else if (intC >= intA && intC >= intB) {
            strResult = "大部分回答“C”";
        }
        return strResult;
    }
}


         /*
      1）T分小于30分为重度失常；
2）30-40分中度失常；
3）T分40-50分轻度失常；
4）T分50±5为正常值。
*/



