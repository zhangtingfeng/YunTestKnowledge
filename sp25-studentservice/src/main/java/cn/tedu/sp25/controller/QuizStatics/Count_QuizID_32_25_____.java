package cn.tedu.sp25.controller.QuizStatics;

import cn.tedu.sp0ag4studio.core.metatype.Dto;
import cn.tedu.sp0ag4studio.core.metatype.impl.BaseDto;

import java.math.BigDecimal;
import java.util.List;

public class Count_QuizID_32_25_____ {


    ///智力类型
    public static List<Dto> sqlCount_QuizID_8(int intinvest_quiz_userID, cn.tedu.sp25.base.BaseMapper bizService) {
        Dto dtoInfo = new BaseDto();
        dtoInfo.put("keyuserID", intinvest_quiz_userID);

        List<Dto> returnList = bizService.queryForList("invest_quiz_search.Count_ID_8____", dtoInfo);

        for (int j = 0; j < returnList.size(); j++) {
            Dto scoreMember = returnList.get(j);
            BigDecimal tScore = scoreMember.getAsBigDecimal("tScore");
            String title = scoreMember.getAsString("title");

            Dto dtobase_invest_quiz_score_method = new BaseDto();
            dtobase_invest_quiz_score_method.put("ShortDes", title);
            dtobase_invest_quiz_score_method.put("quizID", 8);

            List<Dto> dto_invest_quiz_score_methodList = bizService.queryForList("invest_quiz_score_method.queryList", dtobase_invest_quiz_score_method);

            for (int i = 0; i < dto_invest_quiz_score_methodList.size(); i++) {
                Dto curdto = dto_invest_quiz_score_methodList.get(i);
                BigDecimal ddlowScore = curdto.getAsBigDecimal("lowScore");
                BigDecimal ddhightScore = curdto.getAsBigDecimal("hightScore");
                if ((tScore.compareTo(ddlowScore) > -1 || tScore.compareTo(ddlowScore) == 0) && (tScore.compareTo(ddhightScore) < 1 || tScore.compareTo(ddhightScore) == 0)) {
                    scoreMember.put("REMARK", curdto.getAsString("content"));
                    break;
                }
            }
        }

        //Dto totalCountG = (BaseDto) bizService.queryForDto("invest_quiz_search.Count_ID_21_____", dtoInfo);
        return returnList;
    }

    ///威廉斯创造力倾向测量表
    public static List<Dto> sqlCount_QuizID_16(int intinvest_quiz_userID, cn.tedu.sp25.base.BaseMapper bizService) {
        Dto dtoInfo = new BaseDto();
        dtoInfo.put("keyuserID", intinvest_quiz_userID);

        List<Dto> returnList = bizService.queryForList("invest_quiz_search.Count_ID_16____", dtoInfo);

        //Dto totalCountG = (BaseDto) bizService.queryForDto("invest_quiz_search.Count_ID_21_____", dtoInfo);
        return returnList;
    }

    ///17 霍兰德职业兴趣测试题
    public static List<Dto> sqlCount_QuizID_17(int intinvest_quiz_userID, cn.tedu.sp25.base.BaseMapper bizService) {
        Dto dtoInfo = new BaseDto();
        dtoInfo.put("keyuserID", intinvest_quiz_userID);

        List<Dto> returnList = bizService.queryForList("invest_quiz_search.Count_ID_17____", dtoInfo);

        //Dto totalCountG = (BaseDto) bizService.queryForDto("invest_quiz_search.Count_ID_21_____", dtoInfo);
        return returnList;
    }

    ///23     DISC性格测评-测试题目
    public static List<Dto> sqlCount_QuizID_23(int intinvest_quiz_userID, cn.tedu.sp25.base.BaseMapper bizService) {
        Dto dtoInfo = new BaseDto();
        dtoInfo.put("keyuserID", intinvest_quiz_userID);

        List<Dto> returnList = bizService.queryForList("invest_quiz_search.Count_ID_23____", dtoInfo);

        //Dto totalCountG = (BaseDto) bizService.queryForDto("invest_quiz_search.Count_ID_21_____", dtoInfo);
        return returnList;

        /*
       最终得出你的性格色彩结果，类似如例子：红15蓝3黄8绿4

*/

    }

    ///21 九型人格测试(180题版)
    public static List<Dto> sqlCount_QuizID_21(int intinvest_quiz_userID, cn.tedu.sp25.base.BaseMapper bizService) {
        Dto dtoInfo = new BaseDto();
        dtoInfo.put("keyuserID", intinvest_quiz_userID);
        dtoInfo.put("quizID", 21);

        List<Dto> returnList = bizService.queryForList("invest_quiz_search.Count_ID_21_____", dtoInfo);

        //Dto totalCountG = (BaseDto) bizService.queryForDto("invest_quiz_search.Count_ID_21_____", dtoInfo);
        return returnList;

        /*
       最终得出你的性格色彩结果，类似如例子：红15蓝3黄8绿4

*/

    }

    ///乐嘉性格色彩测试（完全版) id=25
    public static String sqlCount_QuizID_25(int intinvest_quiz_userID, cn.tedu.sp25.base.BaseMapper bizService) {
        Dto dtoInfo = new BaseDto();
        dtoInfo.put("keyuserID", intinvest_quiz_userID);
        dtoInfo.put("keywordENTJ", "G");
        Dto totalCountG = (BaseDto) bizService.queryForDto("invest_quiz_search.Count_ID_32_25_____", dtoInfo);

        dtoInfo.put("keywordENTJ", "Y");
        Dto totalCountY = (BaseDto) bizService.queryForDto("invest_quiz_search.Count_ID_32_25_____", dtoInfo);

        dtoInfo.put("keywordENTJ", "B");
        Dto totalCountB = (BaseDto) bizService.queryForDto("invest_quiz_search.Count_ID_32_25_____", dtoInfo);

        dtoInfo.put("keywordENTJ", "R");
        Dto totalCountR = (BaseDto) bizService.queryForDto("invest_quiz_search.Count_ID_32_25_____", dtoInfo);

        String strG = "绿".concat(Integer.toString(totalCountG.getAsInteger("total")));
        String strY = "黄".concat(Integer.toString(totalCountY.getAsInteger("total")));
        String strB = "蓝".concat(Integer.toString(totalCountB.getAsInteger("total")));
        String strR = "红".concat(Integer.toString(totalCountR.getAsInteger("total")));
        return strR.concat(strB).concat(strY).concat(strG);
        /*
       最终得出你的性格色彩结果，类似如例子：红15蓝3黄8绿4

*/

    }


    ///恋爱性格 id=32
    public static String sqlCount_QuizID_32(int intinvest_quiz_userID, cn.tedu.sp25.base.BaseMapper bizService) {
        Dto dtoInfo = new BaseDto();
        dtoInfo.put("keyuserID", intinvest_quiz_userID);
        dtoInfo.put("keywordENTJ", "E");
        Dto totalCountE = (BaseDto) bizService.queryForDto("invest_quiz_search.Count_ID_32_25_____", dtoInfo);

        dtoInfo.put("keywordENTJ", "I");
        Dto totalCountI = (BaseDto) bizService.queryForDto("invest_quiz_search.Count_ID_32_25_____", dtoInfo);

        dtoInfo.put("keywordENTJ", "N");
        Dto totalCountN = (BaseDto) bizService.queryForDto("invest_quiz_search.Count_ID_32_25_____", dtoInfo);

        dtoInfo.put("keywordENTJ", "S");
        Dto totalCountS = (BaseDto) bizService.queryForDto("invest_quiz_search.Count_ID_32_25_____", dtoInfo);

        dtoInfo.put("keywordENTJ", "F");
        Dto totalCountF = (BaseDto) bizService.queryForDto("invest_quiz_search.Count_ID_32_25_____", dtoInfo);

        dtoInfo.put("keywordENTJ", "T");
        Dto totalCountT = (BaseDto) bizService.queryForDto("invest_quiz_search.Count_ID_32_25_____", dtoInfo);

        dtoInfo.put("keywordENTJ", "J");
        Dto totalCountJ = (BaseDto) bizService.queryForDto("invest_quiz_search.Count_ID_32_25_____", dtoInfo);

        dtoInfo.put("keywordENTJ", "P");
        Dto totalCountP = (BaseDto) bizService.queryForDto("invest_quiz_search.Count_ID_32_25_____", dtoInfo);

        String strEI = (totalCountE.getAsInteger("total") > totalCountI.getAsInteger("total")) ? "E" : "I";
        String strNS = (totalCountN.getAsInteger("total") > totalCountS.getAsInteger("total")) ? "N" : "S";
        String strFT = (totalCountF.getAsInteger("total") > totalCountT.getAsInteger("total")) ? "F" : "T";
        String strJP = (totalCountJ.getAsInteger("total") > totalCountP.getAsInteger("total")) ? "J" : "P";
        return strEI.concat(strNS).concat(strFT).concat(strJP);
        /*
        E（你的答案中有几个E）：        I（你的答案中有几个I）：
        N（你的答案中有几个N）：       S（你的答案中有几个S）：
        F（你的答案中有几个F）：        T（你的答案中有几个T）：
        J（你的答案中有几个J）：         P（你的答案中有几个P）：

*/

    }

    public static List<Dto> sqlCount_QuizID_22(int intinvest_quiz_userID, cn.tedu.sp25.base.BaseMapper bizService,StringBuilder Score) {
        Dto dtoInfo = new BaseDto();
        dtoInfo.put("keyuserID", intinvest_quiz_userID);
        List<Dto> returnList = bizService.queryForList("invest_quiz_search.Count_ID_22____", dtoInfo);

        Dto dtoInfoItem = new BaseDto();

        for(int i=0;i<returnList.size();i++)
        {
            dtoInfoItem.put(returnList.get(i).getAsString("title"),returnList.get(i).getAsString("ClassScore"));
        }
      String strEI=  dtoInfoItem.getAsInteger("E")>dtoInfoItem.getAsInteger("I")?"E":"I";
        String strSN=  dtoInfoItem.getAsInteger("S")>dtoInfoItem.getAsInteger("N")?"S":"N";
        String strTF=  dtoInfoItem.getAsInteger("T")>dtoInfoItem.getAsInteger("F")?"T":"F";
        String strJP=  dtoInfoItem.getAsInteger("J")>dtoInfoItem.getAsInteger("P")?"J":"P";
        Score.append(strEI.concat(strSN).concat(strTF).concat(strJP));
        return returnList;
        /*
        同分处理规则     假如  E=I     请填上I
                        假如  S=N     请填上N
                        假如  T=F     请填上F
                        假如  J=P     请填上P


*/

    }



}
