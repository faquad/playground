package de.khiem.offsite.rx.data;

import java.util.Date;
import java.util.Optional;
import org.immutables.value.Value;

/**
 *
 * @author kimyoung
 */
@Value.Immutable
public abstract class BEvent {

    @Value.Default
    public long boxId() {
        return 0l;
    }

    @Value.Default
    public int boxOp() {
        return 0;
    }

    abstract Optional<String> preBoxName();

    abstract Optional<String> postBoxName();

    @Value.Default
    public long uId() {
        return 0l;
    }

    abstract Optional<String> uName();

    @Value.Default
    public long nId() {
        return 0l;
    }

    @Value.Default
    public int nOp() {
        return 0;
    }

    abstract Optional<String> nPreName();

    abstract Optional<String> nPostName();

    @Value.Default
    public long preParent() {
        return 0l;
    }

    abstract Optional<String> preParentName();

    @Value.Default
    public long postParent() {
        return 0l;
    }

    abstract Optional<String> postParentName();

    abstract Date ts();

    @Value.Default
    public long sizeDiff() {
        return 0l;
    }
}
