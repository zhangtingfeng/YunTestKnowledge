package cn.tedu.sp25.controller;


import cn.tedu.sp01.util.CommonUtil;
import cn.tedu.sp01.util.JsonResult;
import cn.tedu.sp0ag4studio.core.metatype.Dto;
import cn.tedu.sp0ag4studio.core.metatype.impl.BaseDto;
import cn.tedu.sp25.base.BaseController;

import cn.tedu.sp25.controller.QuizStatics.Count_QuizID_28;
import cn.tedu.sp25.controller.QuizStatics.Count_QuizID_32_25_____;
import cn.tedu.sp25.controller.QuizStatics.Count_QuizID_4_5_;

import cn.tedu.sp25.controller.QuizStatics.DoactionDrawNewPaper;
import cn.tedu.sp25.myuploadProperties;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import net.sf.json.JSONArray;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/wxSPRepoert")
public class wxSPReportController extends BaseController {
    private static final String SPPageOpenidUrl = "https://api.weixin.qq.com/sns/jscode2session";
    private static final String SPAppid = "wxbc586cfa552bfaa0";
    private static final String SPSecret = "63c3a7c9a7f8d95b68bcf17c38449912";
    public final static String GetPageAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    //微信公众号获取用户信息
    public final static String GetPageUserInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    //微信小程序获取用户信息
    public final static String GetPageUserInfoUrl_XCX = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    @Autowired
    private myuploadProperties foo;

    @ResponseBody
    @RequestMapping(value = "/saveTestQuesion")
    public JsonResult<?> saveTestQuesion(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        // Dto dto = WebUtils.getParamAsDto(request);
        Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
        JsonResult<?> result = JsonResult.ok();
        try {
            int intAllScore = 0;
            int intQuiz = 0;
            String intStrquizID = dto.getAsString("quizID");
            String TurnEndQuestion = dto.getAsString("TurnEndQuestion");
            Boolean PauseSave = dto.getAsBoolean("PauseSave");
            String strScore = "";
            Dto dtoInfoinvest_quiz_user = new BaseDto();


            String obgStringList = dto.getAsString("invest_quiz_user_answerList");
            List<Map<String, Object>> listMap = JSON.parseObject(obgStringList, new TypeReference<List<Map<String, Object>>>() {
            });
            for (int i = 0; i < listMap.size(); i++) {
                // Dto dtoEach= (Dto)listMap.get(i);
                Dto dtoEachPara = webUtils.getParamAsDto(listMap.get(i));

                bizService.updateInfo("invest_quiz_user_answer.updateInfo", dtoEachPara);

                Dto dtoInfo = new BaseDto();
                intQuiz = dtoEachPara.getAsInteger("invest_quiz_userID");
                dtoInfo.put("id", dtoEachPara.getAsInteger("answer"));
                Dto memberinvest_quiz = (BaseDto) bizService.queryForObject(
                        "invest_quiz_question_choice.getInfo", dtoInfo);
                int intScore = memberinvest_quiz.getAsInteger("aCorrectAnswerScore");
                intAllScore += intScore;
            }


            dtoInfoinvest_quiz_user.put("id", intQuiz);
            if (PauseSave != null && true == PauseSave) {
                // saveTestQuesion  存盘使用一个暂存的标记。不写endtime 和计算总分。
            } else {
                ///写endtime 和计算总分
                if (TurnEndQuestion.isEmpty()) {


                    if ("32".equals(intStrquizID)) {
                        String strENTJ = Count_QuizID_32_25_____.sqlCount_QuizID_32(intQuiz, bizService);
                        strScore = strENTJ;
                    } else if ("5".equals(intStrquizID)) {
                        String strTAllScore = Count_QuizID_4_5_.QuizID_5(intQuiz, bizService);
                        strScore = "训练报告已出";
                    } else if ("25".equals(intStrquizID)) {
                        String strBYGR = Count_QuizID_32_25_____.sqlCount_QuizID_25(intQuiz, bizService);
                        strScore = strBYGR;
                    } else if ("28".equals(intStrquizID)) {
                        String strBYGR = Count_QuizID_28.QuizID_28(intQuiz, bizService);
                        strScore = strBYGR;
                    } else if ("22".equals(intStrquizID)) {///MBTI
                        StringBuilder sb = new StringBuilder("");
                        List<Dto> dto_21List = Count_QuizID_32_25_____.sqlCount_QuizID_22(intQuiz, bizService, sb);
                        String json = JSONArray.fromObject(dto_21List).toString();
                        dtoInfoinvest_quiz_user.put("scoreDes", json);
                        strScore = sb.toString();
                    } else if ("8".equals(intStrquizID)) {
                        List<Dto> dto_21List = Count_QuizID_32_25_____.sqlCount_QuizID_8(intQuiz, bizService);
                        String json = JSONArray.fromObject(dto_21List).toString();
                        dtoInfoinvest_quiz_user.put("scoreDes", json);
                        strScore = dto_21List.get(0).getAsString("title");
                    } else if ("16".equals(intStrquizID)) {
                        List<Dto> dto_21List = Count_QuizID_32_25_____.sqlCount_QuizID_16(intQuiz, bizService);
                        String json = JSONArray.fromObject(dto_21List).toString();
                        dtoInfoinvest_quiz_user.put("scoreDes", json);
                        strScore = dto_21List.get(0).getAsString("title");
                    } else if ("17".equals(intStrquizID)) {
                        List<Dto> dto_21List = Count_QuizID_32_25_____.sqlCount_QuizID_17(intQuiz, bizService);
                        String json = JSONArray.fromObject(dto_21List).toString();
                        dtoInfoinvest_quiz_user.put("scoreDes", json);
                        strScore = dto_21List.get(0).getAsString("title");
                    } else if ("21".equals(intStrquizID)) {
                        List<Dto> dto_21List = Count_QuizID_32_25_____.sqlCount_QuizID_21(intQuiz, bizService);
                        String json = JSONArray.fromObject(dto_21List).toString();
                        dtoInfoinvest_quiz_user.put("scoreDes", json);
                        strScore = dto_21List.get(0).getAsString("title");
                    } else if ("23".equals(intStrquizID)) {
                        List<Dto> dto_21List = Count_QuizID_32_25_____.sqlCount_QuizID_23(intQuiz, bizService);
                        String json = JSONArray.fromObject(dto_21List).toString();
                        dtoInfoinvest_quiz_user.put("scoreDes", json);
                        strScore = dto_21List.get(0).getAsString("title") + "型";
                    } else {
                        strScore = Integer.toString(intAllScore);
                        if (intAllScore == 0) strScore = "";////0 说明不是这种方法计算分数的
                    }
                } else {
                    strScore = TurnEndQuestion;
                }

                if ("4".equals(intStrquizID)) {///瑞文
                    strScore = Count_QuizID_4_5_.QuizID_4(intQuiz, bizService, Integer.parseInt(strScore));
                }
                dtoInfoinvest_quiz_user.put("score", strScore);
                dtoInfoinvest_quiz_user.put("finished", "Y");

                Date date = new Date();
                SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date1 = temp.format(date);

                dtoInfoinvest_quiz_user.put("endTime", date1);
            }

            bizService.updateInfo(
                    "invest_quiz_user.updateInfo", dtoInfoinvest_quiz_user);
            result = JsonResult.ok(dtoInfoinvest_quiz_user);

        } catch (Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());

        }
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/readReportQuiz")
    public JsonResult<?> readReportQuiz(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        // Dto dto = WebUtils.getParamAsDto(request);
        Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
        JsonResult<?> result = JsonResult.ok();
        try {
            String TestID = dto.getAsString("TestID");//82
            String Quizid = dto.getAsString("Quizid");//15
            String strGroupID = dto.getAsString("groupID");//15
            if (strGroupID.equals("undefined")) {
                strGroupID = "";
            }
            ;
            Dto dtobaseinvest_quiz = new BaseDto();
            dtobaseinvest_quiz.put("id", Quizid);
            Dto dtoInvest_quiz = (BaseDto) bizService.queryForObject(
                    "invest_quiz.getInfo", dtobaseinvest_quiz);

            Dto scorebaseMember = new BaseDto();
            scorebaseMember.put("id", TestID);
            scorebaseMember.put("groupID", strGroupID);

            Dto scoreMember = (BaseDto) bizService.queryForObject(
                    "invest_quiz_user.getInfo", scorebaseMember);


            Dto dtobase_invest_quiz_score_method = new BaseDto();

            /////start 事后打开报告 修补数据
            String strScore = scoreMember.getAsString("score");/**/
            /////end 事后打开报告 修补数据
            Integer IntegerUserID = scoreMember.getAsInteger("investUserID");/*返回个人信息，使他看起来像一个东西 */
            Dto dtobaseUser = new BaseDto();
            dtobaseUser.put("id", IntegerUserID);
            Dto invest_userMember = (BaseDto) bizService.queryForObject(
                    "invest_user.getInfo", dtobaseUser);
            String strbirthday = invest_userMember.getAsString("birthday");/**/
            if (strbirthday != "" && strbirthday != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
                Date datebirthDay = simpleDateFormat.parse(strbirthday);
                int intAge = Count_QuizID_4_5_.getAge(datebirthDay);
                invest_userMember.put("age", intAge);

            }

            dtobase_invest_quiz_score_method.put("quizID", Quizid);
            dtobase_invest_quiz_score_method.put("Status", "online");

            if ("4".equals(Quizid)) {
                List<Dto> scoreMemberList = bizService.queryForList(
                        "invest_quiz_search.Count_ID_4_____", scorebaseMember);

                scoreMember.put("YourAnswer", scoreMemberList);
            } else if ("10".equals(Quizid)) {///三维人格
                List<Dto> scoreMemberList = bizService.queryForList(
                        "invest_quiz_search.Count_ID_3435363738____", scorebaseMember);

                scoreMember.put("YourAnswer1", scoreMemberList);

                List<Dto> scoreMemberList1 = bizService.queryForList(
                        "invest_quiz_search.Count_ID_3435363738____", scorebaseMember);

                scoreMember.put("YourAnswer", scoreMemberList1);
            } else if ("34".equals(Quizid) || "35".equals(Quizid) || "36".equals(Quizid) || "37".equals(Quizid) || "38".equals(Quizid)) {///1-5岁儿童发展评估量表
                List<Dto> scoreMemberList = bizService.queryForList(
                        "invest_quiz_search.Count_ID_3435363738____ErrorInfo", scorebaseMember);

                scoreMember.put("YourAnswer1", scoreMemberList);

                List<Dto> scoreMemberList1 = bizService.queryForList(
                        "invest_quiz_search.Count_ID_3435363738____", scorebaseMember);

                scoreMember.put("YourAnswer", scoreMemberList1);
            } else if ("5".equals(Quizid)) {
                List<Dto> scoreMemberList = bizService.queryForList(
                        "invest_quiz_search.Count_ID_5_____", scorebaseMember);


                for (int i = 0; i < scoreMemberList.size(); i++) {
                    String strTitle = scoreMemberList.get(i).getAsString("title");
                    String strREMARK = scoreMemberList.get(i).getAsString("REMARK");


                    ArrayList<String> ddArrayListdd = new ArrayList<String>();
                    if (!strREMARK.equals("正常")) {
                        Dto dtoinvest_quiz_score_method = new BaseDto();
                        dtoinvest_quiz_score_method.put("quizID", 5);
                        dtoinvest_quiz_score_method.put("ShortDes", strTitle + "-" + strREMARK);
                        Dto curXunLiandto = (BaseDto) bizService.queryForObject(
                                "invest_quiz_score_method.getInfo", dtoinvest_quiz_score_method);
                        String strcontent = curXunLiandto.getAsString("content");
                        String[] strcontentList = strcontent.split(","); // 用,分割

                        for (int j = 0; j < strcontentList.length; j++) {
                            String strShortDesc = strcontentList[j];
                            Dto dtoinvest_quiz_score_methodXunlian = new BaseDto();
                            dtoinvest_quiz_score_methodXunlian.put("quizID", 5);
                            dtoinvest_quiz_score_methodXunlian.put("ShortDes", strShortDesc);

                            Dto dtoinvest_quiz_score_methodXunlianResult = (BaseDto) bizService.queryForObject(
                                    "invest_quiz_score_method.getInfo", dtoinvest_quiz_score_methodXunlian);

                            ddArrayListdd.add(dtoinvest_quiz_score_methodXunlianResult.getAsString("content"));
                        }
                    }
                    scoreMemberList.get(i).put("XunLianList", ddArrayListdd);
                }

                scoreMember.put("YourAnswer", scoreMemberList);
            } else if ("32".equals(Quizid) || "20".equals(Quizid) || "40".equals(Quizid) || "28".equals(Quizid)) {
                dtobase_invest_quiz_score_method.put("ShortDes", strScore);

                Dto curdto = (BaseDto) bizService.queryForObject(
                        "invest_quiz_score_method.getInfo", dtobase_invest_quiz_score_method);
                scoreMember.put("YourAnswer", curdto);
            } else if ("22".equals(Quizid)) {
                dtobase_invest_quiz_score_method.put("ShortDes", strScore);

                Dto curdto = (BaseDto) bizService.queryForObject(
                        "invest_quiz_score_method.getInfo", dtobase_invest_quiz_score_method);

                dtobase_invest_quiz_score_method.put("ShortDes", "ALL");
                Dto curdtoALL = (BaseDto) bizService.queryForObject(
                        "invest_quiz_score_method.getInfo", dtobase_invest_quiz_score_method);
                curdtoALL.put("AddContentSingleReport", curdto.getAsString("content"));


                scoreMember.put("YourAnswer", curdtoALL);


            } else if ("17".equals(Quizid)) {
                dtobase_invest_quiz_score_method.put("ShortDes", strScore);

                Dto curdto = (BaseDto) bizService.queryForObject(
                        "invest_quiz_score_method.getInfo", dtobase_invest_quiz_score_method);
                // scoreMember.put("AddContentSingleReport", curdto);////有单独需要的问题

                dtobase_invest_quiz_score_method.put("ShortDes", "ALL");

                Dto curdtoALL = (BaseDto) bizService.queryForObject(
                        "invest_quiz_score_method.getInfo", dtobase_invest_quiz_score_method);
                curdtoALL.put("AddContentSingleReport", curdto.getAsString("content"));

                scoreMember.put("YourAnswer", curdtoALL);

            } else if ("16".equals(Quizid) || "8".equals(Quizid)) {

                dtobase_invest_quiz_score_method.put("ShortDes", "ALL");

                Dto curdtoALL = (BaseDto) bizService.queryForObject(
                        "invest_quiz_score_method.getInfo", dtobase_invest_quiz_score_method);

                scoreMember.put("YourAnswer", curdtoALL);

            } else if ("25".equals(Quizid) || "12".equals(Quizid)) {//乐嘉性格色彩测试(完全版) 人际信任量表
                dtobase_invest_quiz_score_method.put("ShortDes", "ALL");

                Dto curdto = (BaseDto) bizService.queryForObject(
                        "invest_quiz_score_method.getInfo", dtobase_invest_quiz_score_method);
                scoreMember.put("YourAnswer", curdto);
            } else if ("21".equals(Quizid) || "23".equals(Quizid)) {
                dtobase_invest_quiz_score_method.put("ShortDes", strScore);

                Dto curdto = (BaseDto) bizService.queryForObject(
                        "invest_quiz_score_method.getInfo", dtobase_invest_quiz_score_method);
                scoreMember.put("YourAnswer", curdto);
                //  scoreMember.put("YourAnswer", curdto);
            } else {
                List<Dto> dto_invest_quiz_score_methodList = bizService.queryForList("invest_quiz_score_method.queryList", dtobase_invest_quiz_score_method);

                BigDecimal UserScore = scoreMember.getAsBigDecimal("score");
                for (int i = 0; i < dto_invest_quiz_score_methodList.size(); i++) {
                    Dto curdto = dto_invest_quiz_score_methodList.get(i);
                    BigDecimal ddlowScore = curdto.getAsBigDecimal("lowScore");
                    BigDecimal ddhightScore = curdto.getAsBigDecimal("hightScore");
                    if ((UserScore.compareTo(ddlowScore) > -1
                            || UserScore.compareTo(ddlowScore) == 0)
                            &&
                            (UserScore.compareTo(ddhightScore) < 1
                                    ||
                                    UserScore.compareTo(ddhightScore) == 0)) {
                        scoreMember.put("YourAnswer", curdto);
                        break;
                    }
                }
            }
            scoreMember.put("YourUserInfo", invest_userMember);


            result = JsonResult.ok(scoreMember);

        } catch (Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());

        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/readTestedQuiz")
    public JsonResult<?> readTestedQuiz(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        // Dto dto = WebUtils.getParamAsDto(request);
        Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
        JsonResult<?> result = JsonResult.ok();
        try {
            String testquizID = dto.getAsString("testquizID");//82
            String quizID = dto.getAsString("quizID");//15

            Dto dtoinvest_quiz = new BaseDto();
            dtoinvest_quiz.put("id", quizID);
            Dto memberinvest_quiz = (BaseDto) bizService.queryForObject(
                    "invest_quiz.getUserQuizInfo", dtoinvest_quiz);

            Dto dtoinvest_quiz_user = new BaseDto();
            dtoinvest_quiz_user.put("id", testquizID);//82
            if (dto.containsKey("groupID") && !dto.getAsString("groupID").equals("undefined")) {

                dtoinvest_quiz_user.put("groupID", dto.getAsInteger("groupID"));//82
            }
            Dto member = (BaseDto) bizService.queryForObject(//82
                    "invest_quiz_user.getInfo", dtoinvest_quiz_user);

            getinvest_quiz_question_choice(member, "Y");
            member.put("quizInfo", memberinvest_quiz);
            result = JsonResult.ok(member);

        } catch (Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());

        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/readTestQuesion")
    public JsonResult<?> readTestQuesion(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        // Dto dto = WebUtils.getParamAsDto(request);
        Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
        JsonResult<?> result = JsonResult.ok();
        try {


            String quizID = dto.getAsString("quizID");

            Dto dtoOpenid = new BaseDto();
            dtoOpenid.put("quizID", quizID);
            dtoOpenid.put("finished", "N");
            String strGroupID = dto.getAsString("groupID");//15
            if (strGroupID.equals("undefined")) {
                strGroupID = "";
            }
            ;
            dtoOpenid.put("groupID", strGroupID);
            dtoOpenid.put("investUserID", dto.getAsString("id"));
            Dto dtoinvest_quiz = new BaseDto();
            dtoinvest_quiz.put("id", quizID);//15

            Dto memberinvest_quiz = (BaseDto) bizService.queryForObject(
                    "invest_quiz.getUserQuizInfo", dtoinvest_quiz);

            ///默认读取历史测试的  ///finished N传 让他读取所有的
            Dto member = (BaseDto) bizService.queryForObject(
                    "invest_quiz_user.getInfo", dtoOpenid);
            if (member != null) {
                getinvest_quiz_question_choice(member, null);
                member.put("quizInfo", memberinvest_quiz);
                result = JsonResult.ok(member);
            } else {
                int intUserid = bizService.saveInfo("invest_quiz_user.saveInfo", dtoOpenid);
                dto.put("id", intUserid);
                getinvest_quiz_question_choice(dtoOpenid, "N");
                dtoOpenid.put("quizInfo", memberinvest_quiz);
                result = JsonResult.ok(dtoOpenid);
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());

        }
        return result;
    }

    private void getinvest_quiz_question_choice(Dto dto, String stranswered) {
        Long invest_quiz_userID = dto.getAsLong("id");
        BaseDto baseDto = new BaseDto();
        baseDto.put("invest_quiz_userID", invest_quiz_userID);
        if (stranswered != null && !stranswered.isEmpty()) baseDto.put("answered", stranswered);
        List<Dto> invest_quiz_user_answerList = bizService.queryForList("invest_quiz_user_answer.queryList", baseDto);
        if (invest_quiz_user_answerList.size() > 0) {
            dto.put("invest_quiz_user_answerList", invest_quiz_user_answerList);
        } else {
            dto.put("invest_quiz_userID", invest_quiz_userID);
            bizService.saveInfo("invest_quiz_user_answer.batchInsertInfo", dto);
            invest_quiz_user_answerList = bizService.queryForList("invest_quiz_user_answer.queryList", baseDto);
            dto.put("invest_quiz_user_answerList", invest_quiz_user_answerList);
        }
        List<Dto> invest_quiz_user_answer_FullInfo_List = bizService.queryForList("invest_quiz_user_answer.batchqueryQustrionList", baseDto);
        dto.put("invest_quiz_user_answer_FullInfo_List", invest_quiz_user_answer_FullInfo_List);

        List<Dto> invest_Choice_FullInfo_List = bizService.queryForList("invest_quiz_user_answer.batchqueryChoiceList", baseDto);
        dto.put("invest_Choice_FullInfo_List", invest_Choice_FullInfo_List);


    }


    @ResponseBody
    @RequestMapping(value = "/OnGetNewsPaper")
    public JsonResult<?> OnGetNewsPaper(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
        JsonResult<?> result = JsonResult.ok();
        try {
            String strQuizID = dto.getAsString("quizID");
            BaseDto baseDto = new BaseDto();
            baseDto.put("id", strQuizID);
            Dto infoinvest_quiz = (BaseDto) bizService.queryForDto("invest_quiz.getInfo", baseDto);


            String strtestuserinfo = dto.getAsString("testuserinfo");
            String strIMG_PATH = foo.getServerIMGUpload();

            DoactionDrawNewPaper tt = new DoactionDrawNewPaper();
            String strTitle = infoinvest_quiz.getAsString("Title");
            BufferedImage d = tt.loadImageLocal(strIMG_PATH+ File.separator+"Capture.jpg");
            tt.setFont("楷体", 40);
            d = tt.modifyImage(d, strTitle, new Rectangle(0, 0, 750, 116), Color.white);

            String strQuizPictrue = infoinvest_quiz.getAsString("QuizPictrue");
            d = tt.modifyImagetogeter((tt.loadImageUrl(strQuizPictrue)), d, new Rectangle(98, 132, 552, 412));

            String strscore = dto.getAsString("score");
            Pattern pattern = Pattern.compile("[0-9]*");
            if (pattern.matcher(strscore).matches()) {///判断是否时数字
                strscore += "分";
            }
            tt.setFont("楷体", 40);
            String str = strscore;
            d = tt.modifyImage(d, str, new Rectangle(100, 546, 552, 132), Color.black);

            tt.setFont("楷体", 40);
            d = tt.modifyImage(d, strtestuserinfo, new Rectangle(100, 674, 552, 132), Color.black);

            tt.setFont("楷体", 20);
            Long timeStamp = System.currentTimeMillis();  //获取当前时间戳
            //   tt.setFont("楷体", 20);
            String strbeginTime = dto.getAsString("beginTime");
            String strendTime = dto.getAsString("endTime");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String sd = sdf.format(new Date(Long.parseLong(strbeginTime)));      // 时间戳转换成时间
            long timeDifference = Long.parseLong(strendTime) - Long.parseLong(strbeginTime);
            long Mintues = timeDifference / 1000 / 60;    //计算分钟
            if (Mintues > 0) {
                str = "测试时间：" + sd + "，测试时长：" + Mintues + "分种";
            } else {
                long seconds = timeDifference / 1000;    //计算秒
                str = "测试时间：" + sd + "，测试时长：" + seconds + "秒";
            }
            d = tt.modifyImage(d, str, new Rectangle(100, 788, 552, 132), Color.black);

            String strcontent = dto.getAsString("content");
            strcontent = strcontent.replaceAll("<[.[^<]]*>", "");
            tt.setFont("楷体", 20);
            String strScore = strcontent;
            d = tt.modifyImage(d, strScore, new Rectangle(100, 882, 552, 110), Color.black);

            String strWxCode = dto.getAsString("WxCode");
            d = tt.modifyImagetogeter(
                    tt.loadImageUrl(strWxCode),
                    d,
                    new Rectangle(750 / 2 - 177 / 2, 1010, 177, 177)
            );


            String strwx_PicURL = dto.getAsString("wx_PicURL");
            d = tt.insertLogoImage(
                    d,
                    tt.loadImageUrl(strwx_PicURL),
                    new Rectangle((750 / 2 - 40 / 2), (1090 - (40 / 2)), 40, 40)

            );

            //d = tt.modifyImagetogeter((tt.loadImageUrl("https://pics6.baidu.com/feed/8ad4b31c8701a18bb7b536c6a6fc9c002938fe3c.jpeg?token=791c28a33956fa4fb96cc411116f584e")), d, new Rectangle(152, 1010, 177, 177));
            ///BufferedImage derWEIMa = tt.loadImageUrl("https://pics6.baidu.com/feed/8ad4b31c8701a18bb7b536c6a6fc9c002938fe3c.jpeg?token=791c28a33956fa4fb96cc411116f584e");
            ///BufferedImage HechengderWEIMa=tt.insertLogoImage(derWEIMa,tt.loadImageUrl("https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI70K0ricibq72KzK0vzibxGvJYqvpqWSgvGpzdbvXVWD9ZmbuYFwcxu0c5R30nl6YpCGibJm6OMML6XQ/132"));
            ///tt.writeImageLocal("C:\\Temp\\112.jpg", HechengderWEIMa);

            //d = tt.modifyImagetogeter(HechengderWEIMa, d, new Rectangle(750/2-177/2, 1010, 177, 177));

            String strNewsPager_url = foo.getNewsPager_url();
            // String strNewsFileName =  strIMG_PATH + File.separator + strNewsPager_url+File.separator;
            String strFileName = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSSSSS") + "_" + dto.getAsString("investUserID") + "_" + dto.getAsString("quizID") + "_" + dto.getAsString("id");
            tt.writeImageLocal(strIMG_PATH + File.separator + strNewsPager_url + File.separator + strFileName, d);
            System.out.println("success");

            Dto dtoNewsPaperUrl = new BaseDto();
            dtoNewsPaperUrl.put("dtoNewsPaperUrl", strNewsPager_url + File.separator + strFileName);

            result = JsonResult.ok(dtoNewsPaperUrl);
        } catch (Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());

        }
        return result;
    }
}
