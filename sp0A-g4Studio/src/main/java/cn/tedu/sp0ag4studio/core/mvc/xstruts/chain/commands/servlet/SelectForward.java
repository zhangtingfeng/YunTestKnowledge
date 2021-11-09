package cn.tedu.sp0ag4studio.core.mvc.xstruts.chain.commands.servlet;

import cn.tedu.sp0ag4studio.core.mvc.xstruts.action.ActionForward;
import cn.tedu.sp0ag4studio.core.mvc.xstruts.chain.commands.AbstractSelectForward;
import cn.tedu.sp0ag4studio.core.mvc.xstruts.chain.contexts.ActionContext;
import cn.tedu.sp0ag4studio.core.mvc.xstruts.config.ForwardConfig;
import cn.tedu.sp0ag4studio.core.mvc.xstruts.config.ModuleConfig;

/**
 * <p>
 * Create and return a <code>ForwardConfig</code> representing the specified
 * module-relative destination.
 * </p>
 *
 * @version $Rev: 421119 $ $Date: 2005-05-07 12:11:38 -0400 (Sat, 07 May 2005) $
 */
public class SelectForward extends AbstractSelectForward {
    // ------------------------------------------------------- Protected Methods

    /**
     * <p>
     * Create and return a <code>ForwardConfig</code> representing the specified
     * module-relative destination.
     * </p>
     *
     * @param context      The context for this request
     * @param moduleConfig The <code>ModuleConfig</code> for this request
     * @param uri          The module-relative URI to be the destination
     */
    protected ForwardConfig forward(ActionContext context, ModuleConfig moduleConfig, String uri) {
        return (new ActionForward(null, uri, false, moduleConfig.getPrefix()));
    }
}
