package cn.tedu.sp0ag4studio.core.id.sequence;

import cn.tedu.sp0ag4studio.core.id.CreateSequnceException;

/**
 * AbstractRollingSequenceGenerator
 * 此代码源于开源项目E3,原作者：黄云辉
 *
 * @author OSWorks-XC
 * @see DefaultSequenceGenerator
 * @since 2010-03-17
 */
public abstract class AbstractRollingSequenceGenerator extends DefaultSequenceGenerator {

    public long next() throws CreateSequnceException {
        if (isResetCount()) {
            this.currCount = this.minValue;
            maxCount = this.currCount;
            sequenceStorer.updateMaxValueByFieldName(maxCount, this.getId());
        }
        return super.next();
    }

    abstract protected boolean isResetCount();

}
