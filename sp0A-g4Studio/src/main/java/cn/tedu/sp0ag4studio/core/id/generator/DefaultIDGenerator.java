package cn.tedu.sp0ag4studio.core.id.generator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.tedu.sp0ag4studio.core.id.CreateIDException;
import cn.tedu.sp0ag4studio.core.id.IDGenerator;
import cn.tedu.sp0ag4studio.core.id.PrefixGenerator;
import cn.tedu.sp0ag4studio.core.id.SequenceFormater;
import cn.tedu.sp0ag4studio.core.id.SequenceGenerator;
import cn.tedu.sp0ag4studio.core.id.sequence.DefaultSequenceGenerator;

/**
 * DefaultIDGenerator 此代码源于开源项目E3,原作者：黄云辉
 *
 * @author OSWorks-XC
 * @see IDGenerator
 * @since 2010-03-17
 */
public class DefaultIDGenerator implements IDGenerator {

    private PrefixGenerator prefixGenerator;
    private SequenceGenerator sequenceGenerator = new DefaultSequenceGenerator();
    private SequenceFormater sequenceFormater;

    private final Log logger = LogFactory.getLog(DefaultIDGenerator.class);

    public synchronized String create() throws CreateIDException {
        final String prefix = prefixGenerator == null ? "" : prefixGenerator.create();
        logger.debug("ID前缀是:[" + prefix + "]");
        long sequence = sequenceGenerator.next();
        final String strSequence = sequenceFormater == null ? new Long(sequence).toString() : sequenceFormater
                .format(sequence);
        return prefix + strSequence;
    }

    public void setPrefixGenerator(PrefixGenerator prefixGenerator) {
        this.prefixGenerator = prefixGenerator;
    }

    public void setSequenceGenerator(SequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    public void setSequenceFormater(SequenceFormater sequenceFormater) {
        this.sequenceFormater = sequenceFormater;
    }

}
