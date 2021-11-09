package cn.tedu.sp0ag4studio.core.id;

/**
 * FormatSequenceExcepiton
 * 此代码源于开源项目E3,原作者：黄云辉
 *
 * @author OSWorks-XC
 * @see IDException
 * @since 2010-03-17
 */
public class FormatSequenceExcepiton extends IDException {

    public FormatSequenceExcepiton() {
        super("格式化序号异常!");
    }

    public FormatSequenceExcepiton(String message, Throwable cause) {
        super(message, cause);
    }

    public FormatSequenceExcepiton(String message) {
        super(message);
    }

    public FormatSequenceExcepiton(Throwable cause) {
        super(cause);
    }

}
