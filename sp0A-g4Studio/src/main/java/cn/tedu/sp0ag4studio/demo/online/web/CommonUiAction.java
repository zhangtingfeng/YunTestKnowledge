package cn.tedu.sp0ag4studio.demo.online.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.sp0ag4studio.common.web.BaseAction;
import cn.tedu.sp0ag4studio.core.mvc.xstruts.action.ActionForm;
import cn.tedu.sp0ag4studio.core.mvc.xstruts.action.ActionForward;
import cn.tedu.sp0ag4studio.core.mvc.xstruts.action.ActionMapping;

/**
 * 常用UI组件标准范例暨教程Action
 *
 * @author OSWorks-XC
 * @see BaseAction
 * @since 2010-10-30
 */
public class CommonUiAction extends BaseAction {

    /**
     * 面板页面初始化
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward panelDemo1Init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        return mapping.findForward("panelDemo1View");
    }

    /**
     * 窗口页面初始化
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward windowDemo1Init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        return mapping.findForward("windowDemo1View");
    }

    /**
     * TabPanel标签卡页面初始化
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward tabPanelDemo1Init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        return mapping.findForward("tabPanelDemo1View");
    }

    /**
     * ViewPort布局页面初始化
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward viewportLayoutInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {

        return mapping.findForward("viewportLayoutView");
    }

    /**
     * ViewPort嵌套复杂布局页面初始化
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward viewportComplexLayoutInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                   HttpServletResponse response) throws Exception {

        return mapping.findForward("viewportComplexLayoutView");
    }
}
