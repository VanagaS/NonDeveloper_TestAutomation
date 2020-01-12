package cc.iggy.core.session;

import java.util.HashMap;

/**
 * Created by VanagaS on 08-05-2017.
 *
 */
public final class NameSpace {

    /**
     * The global namespace, the current path
     */
    private static String globalNameSpace = "ROOT";

    /**
     * As the source is read from top to bottom, the key taken from current line being processed will be set
     * as currentGlobalParent. While the {@code globalNameSpace} looks like {@code w.x.y.z},
     * {@code currentGlobalParent} will be set to {@code z}.
     */
    private static String currentGlobalParent = "";

    /**
     * In the case like same keys repeated like below:
     *
     *     key-of-type-map:
     *         some-key-under-map: value1
     *         some-key-under-map: value2
     *
     * {@code currentGlobalParent} remains untouched and we will increment this value. This is used for defining
     * keys for {@code GLOBAL_SCOPE}: {@see setGlobalValue}. For example, for the second element
     * {@code some-key-under-map} with value {@code value2}, if {@code globalNameSpace} is
     * {@code ROOT.key-of-type-map.some-key-under-map}, the value of {@code currentGlobalParent} will be
     * {@code some-key-under-map} and the value of {@code currentGlobalParentDupeCount} will be 1
     */
    private static int currentGlobalParentDupeCount = 0;

    /**
     * Similar to {@see currentGlobalParent}. In case we are running in parallel threads, this variable will be
     * updated instead of {@code currentGlobalParent}.
     *
     * {@code InheritableThreadLocal} has no use with thread pools {@code ExecutorService}, hence resorting to
     * {@code ThreadPool} which means that, further down the hierarchy, if more child threads are invoked, the
     * contents of this variable (parent) needs to be passed (to child).
     */
    private static final ThreadLocal<String>  currentLocalParent = ThreadLocal.withInitial(() -> "");

    /**
     * Similar to {@see currentGlobalParentDupeCount}. In case we are running in parallel threads, this variable will be
     * updated instead of {@code currentGlobalParentDupeCount}. This is used for defining keys for {@code LOCAL_SCOPE}:
     * {@see setLocalValue}. Also {@see currentLocalParent} for {@code ThreadLocal} reference.
     *
     */
    private static final ThreadLocal<Integer> currentLocalParentDupeCount = ThreadLocal.withInitial(() -> 0);

    /**
     * Local namespace (if within a thread)
     * {@see currentLocalParent} for {@code ThreadLocal} reference.
     */
    private static final ThreadLocal<String> localNameSpace = ThreadLocal.withInitial(() -> "");

    public void setupChildThreadNameSpace() {
        /* TODO */
    }
}
