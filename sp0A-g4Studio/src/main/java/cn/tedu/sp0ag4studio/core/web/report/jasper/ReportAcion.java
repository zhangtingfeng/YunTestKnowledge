package cn.tedu.sp0ag4studio.core.web.report.jasper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cn.tedu.sp0ag4studio.common.web.BaseAction;
import cn.tedu.sp0ag4studio.core.mvc.xstruts.action.ActionForm;
import cn.tedu.sp0ag4studio.core.mvc.xstruts.action.ActionForward;
import cn.tedu.sp0ag4studio.core.mvc.xstruts.action.ActionMapping;
import cn.tedu.sp0ag4studio.core.util.G4Utils;

/**
 * JasperReport报表服务Acion
 *
 * @author OSWorks-XC
 * @since 2009-09-03
 */
public class ReportAcion extends BaseAction {

    /**
     * 打印模板页面初始化
     *
     * @param
     * @return
     */
    public ActionForward initAppletPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        String flag = request.getParameter("flag");
        String url = request.getContextPath() + "/report.do?reqCode=appletPreview&jsessionid=" + request.getSession().getId();
        if (!G4Utils.isEmpty(flag)) {
            url = url + "&reportflag=" + flag;
        }
        request.setAttribute("dataUrl", url);
        return mapping.findForward("printInitView");
    }

    /**
     * PDF模板页面初始化
     *
     * @param
     * @return
     */
    public ActionForward initPdfPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        String flag = request.getParameter("flag");
        String url = request.getContextPath() + "/report.do?reqCode=pdfPreview&jsessionid=" + request.getSession().getId();
        if (!G4Utils.isEmpty(flag)) {
            url = url + "&reportflag=" + flag;
        }
        request.setAttribute("dataUrl", url);
        return mapping.findForward("pdfInitView");
    }

    /**
     * 实例化报表对象,填充报表数据 显示方式:applet
     *
     * @param
     * @return
     */
    public ActionForward appletPreview(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        // 接收url传过来的报表标识参数
        String flag = request.getParameter("reportflag");
        flag = G4Utils.isEmpty(flag) ? "default" : flag;

        return mapping.findForward(null);
    }

    /**
     * 实例化报表对象,填充报表数据 显示方式:pdf
     *
     * @param
     * @return
     */
    public ActionForward pdfPreview(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        // 接收url传过来的报表标识参数
        String flag = request.getParameter("reportflag");
        flag = G4Utils.isEmpty(flag) ? "default" : flag;


        return mapping.findForward(null);
    }

}
