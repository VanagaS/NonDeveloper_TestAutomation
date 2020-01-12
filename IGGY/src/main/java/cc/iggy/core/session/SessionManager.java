package cc.iggy.core.session;

import cc.iggy.api.constants.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by VanagaS on 07-05-2017.
 *
 */
public class SessionManager {

    private static final Logger logger = LogManager.getLogger(SessionManager.class.getName());
    public static final long MAIN_THREAD_ID = Thread.currentThread().getId();
    private static final int MIN_GLOBAL_VALUES_SIZE = 128;

    /**
     * All the variables defined as globals will be registered here and will be accessible across threads
     */
    private static final Map<String, Object> GLOBAL_SCOPE = new ConcurrentHashMap<>(MIN_GLOBAL_VALUES_SIZE);

    /**
     * All the variables defined as local (within a block scope) will be registered here and will be per thread.
     * {@code InheritableThreadLocal} has no use with thread pools {@code ExecutorService}, hence resorting to
     * {@code ThreadPool} which means that, further down the hierarchy, if more child threads are invoked, the
     * contents of this variable needs to be passed.
     */
    private static final ThreadLocal<HashMap<String, Object>> LOCAL_SCOPE = ThreadLocal.withInitial(HashMap::new);


    private int globalContext = Context.AT_START;
    private static final ThreadLocal<Integer> LOCAL_CONTEXT = ThreadLocal.withInitial(() -> 0); // AT_START;
    private String currentGlobalParent; // TODO
    private int currentGlobalParentDupeCount; // TODO
    private String globalNameSpace; // TODO


    public SessionManager setGlobalNameSpace(String key) {
        if(key.equals(currentGlobalParent)) {
            currentGlobalParentDupeCount += 1;
        } else {
            globalNameSpace += "." + key;
            currentGlobalParent = key;
            currentGlobalParentDupeCount = 0;
        }
        return this;
    }

    public SessionManager setLocalNameSpace(String key) {
        if(key.equals(currentGlobalParent)) {
            currentGlobalParentDupeCount += 1;
        } else {
            globalNameSpace += "." + key;
            currentGlobalParent = key;
            currentGlobalParentDupeCount = 0;
        }
        return this;
    }

    public Object getGlobalValue(String key) {
        return GLOBAL_SCOPE.get(key);
    }

    public Object getLocalValue(String key) {
        return LOCAL_SCOPE.get().get(key);
    }

    public String getGlobalNameSpace() {
        return globalNameSpace;
    }

    public SessionManager setGlobalValue(Object object) {
        String key = getGlobalNameSpace();
        setGlobalValue(key, object);
        return this;
    }

    public SessionManager setGlobalValue(String key, Object object) {
        if(Thread.currentThread().getId() == SessionManager.MAIN_THREAD_ID) {
            /**
             * Since source file is read from top to bottom, there wouldn't be any chance of duplicates, so there is
             * no need of checking for a duplicate and warning user. However, for map kind of definitions like:
             *
             *     key-of-type-map:
             *         some-key-under-map: value1
             *         some-key-under-map: value2
             *
             * there can be duplicates, but these should be taken care by globalNameSpace and localNameSpace by
             * numbering the repetitions so that there won't be any duplicates.
             */
            GLOBAL_SCOPE.put(key, object);
        } else {
            logger.debug("Global values cannot be registered when running in parallel (threads), saving in local context. {}", key);
            LOCAL_SCOPE.get().put(key, object);
        }
        return this;
    }

    public String getLocalNameSpace() {
        return "TODO"; // TODO
    }

    public SessionManager setLocalValue(Object object) {
        setLocalValue(getLocalNameSpace(), object);
        return this;
    }

    public SessionManager setLocalValue(String key, Object object) {
        LOCAL_SCOPE.get().put(key, object);
        return this;
    }

    /**
     * Sets the present context we are in.
     * Only main thread can set the {@code s_globalContext}. The inner threads will have their own local context
     * (ThreadLocal).
     *
     * @param context
     */
    public static void setGlobalContext(int context) {
        if(Thread.currentThread().getId() == SessionManager.MAIN_THREAD_ID) {
            // FIXME: Context.INSTANCE.globalContext = context;
        } else {
            LOCAL_CONTEXT.set(context);
        }
    }


    /**
     * Returns the current global context. This is required for the inner threads to know the path of the context.
     * @return current Global context
     */
    public static int getGlobalContext() {
       // FIXME: return Context.INSTANCE.globalContext;
        return 1;
    } // TODO




    /**
     * @return current local context if running within a thread other than main, or if running in main thread, returns
     *          global context.
     */
    public static int getLocalContext() {
        if (Thread.currentThread().getId() == SessionManager.MAIN_THREAD_ID) {
            // FIXME: return Context.INSTANCE.globalContext; // TODO
            return 1;
        } else {
            return LOCAL_CONTEXT.get();
        }

    }
}
