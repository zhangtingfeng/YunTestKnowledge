package cn.tedu.sp25.controller.QuizStatics;

import cn.tedu.sp0ag4studio.core.metatype.Dto;
import cn.tedu.sp0ag4studio.core.metatype.impl.BaseDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Count_QuizID_4_5_ {


    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }

    ///恋爱性格 id=32
    public static String QuizID_4(Integer intinvest_quiz_userID, cn.tedu.sp25.base.BaseMapper bizService, Integer Score) throws Exception {
        // Score=47;
        String strReturnScore = "";
        try {
            Dto dtoInfoquiz_userID = new BaseDto();
            dtoInfoquiz_userID.put("id", intinvest_quiz_userID);
            Dto invest_quiz_userGetInfo = (BaseDto) bizService.queryForDto("invest_quiz_user.getInfo", dtoInfoquiz_userID);


            Dto dtoInfo = new BaseDto();
            dtoInfo.put("id", invest_quiz_userGetInfo.getAsInteger("investUserID"));
            Dto totalinvest_user = (BaseDto) bizService.queryForDto("invest_user.getInfo", dtoInfo);

            String strbirthday = totalinvest_user.getAsString("birthday");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
            Date datebirthDay = simpleDateFormat.parse(strbirthday);
            int intAge = getAge(datebirthDay);
            //intAge=54;
//定义二维数组的方式
            int[][] arr2 = {{7, 8, 9, 10, 12, 14, 16, 20, 25, 30, 55},
                    {50, 57, 59, 61, 63, 66, 69, 71, 71, 70, 70},
                    {47, 55, 54, 59, 62, 65, 67, 69, 69, 68, 68},
                    {44, 48, 52, 56, 59, 63, 65, 67, 66, 64, 63},
                    {40, 47, 49, 52, 56, 59, 62, 64, 63, 59, 59},
                    {35, 39, 43, 47, 51, 54, 57, 61, 58, 53, 53},
                    {30, 34, 38, 42, 46, 49, 51, 55, 53, 44, 44},
                    {22, 25, 27, 29, 31, 33, 35, 41, 39, 28, 28}
            };
            int intAgePos = arr2[0].length - 1;
            for (Integer i = 0; i < arr2[0].length; i++) {
                if (intAge <= arr2[0][i]) {
                    intAgePos = i;

                    break;
                }
            }

            List<Integer> titleList = new ArrayList<Integer>();


            for (Integer i = 1; i < arr2.length; i++) {
                titleList.add(arr2[i][intAgePos]);

            }

            int intGetPosLevel = titleList.size() - 1;
            for (Integer i = 0; i < titleList.size(); i++) {
                if (Score >= titleList.get(i)) {
                    intGetPosLevel = i;

                    break;
                }
            }
            String[] arr3 = {"130以上 超优",
                    "120~129 优秀",
                    "110~119 中上",
                    "90~110 中等",
                    "80~89 中下",
                    "70~79 迟钝",
                    "70 以下 低能"};
            strReturnScore = arr3[intGetPosLevel];
        } catch (Exception ee) {
            strReturnScore = String.valueOf(Score);
        }

        return strReturnScore;
    }

    public static String QuizID_5(Integer intinvest_quiz_userID, cn.tedu.sp25.base.BaseMapper bizService) throws Exception {
        Dto dtoInfoquiz_userID = new BaseDto();
        dtoInfoquiz_userID.put("id", intinvest_quiz_userID);

        Dto totalCountG = (BaseDto) bizService.queryForDto("invest_quiz_search.Count_ID_5__total___", dtoInfoquiz_userID);
        Integer tAllSocer = totalCountG.getAsInteger("total");
        String str = "T分" + tAllSocer;
        return str;
    }
}

         /*
      1）T分小于30分为重度失常；
2）30-40分中度失常；
3）T分40-50分轻度失常；
4）T分50±5为正常值。
*/



