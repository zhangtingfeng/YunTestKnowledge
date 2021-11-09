package cn.tedu.sp0ag4studio.core.tplengine;

/**
 * 文件模板
 *
 * @author OSWorks-XC
 * @see DefaultTemplate
 * @since 2009-07-28
 */
public class FileTemplate implements DefaultTemplate {
    /**
     * 文件模板资源路径
     */
    private String resource;

    /**
     * 构造函数
     *
     * @param pResource 文件模板资源路径
     */
    public FileTemplate(String pResource) {
        this.resource = pResource;
    }

    /**
     * 构造函数
     */
    public FileTemplate() {
    }

    /**
     * 获取文件模板资源路径
     */
    public String getTemplateResource() {
        return getResource();
    }

    /**
     * 设置文件模板资源路径
     */
    public void setTemplateResource(String pResource) {
        this.resource = pResource;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
