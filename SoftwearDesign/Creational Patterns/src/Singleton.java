/**
 * Thread-safe Singleton using the Bill Pugh (static inner class) approach.
 *
 * Key idea:
 * - The instance is created only when Holder is first accessed (lazy).
 * - Class initialization in the JVM is guaranteed to be thread-safe.
 */
public final class Singleton {

    // Private constructor prevents direct instantiation from outside this class.
    // Also guards against accidental creation via reflection (best-effort).
    private Singleton() {
        if (Holder.INSTANCED_CREATED) {
            // If someone tries to reflectively call the constructor after initialization,
            // fail fast. (Reflection can still bypass in some extreme cases, but this is
            // a reasonable defensive measure in typical codebases.)
            throw new IllegalStateException("Singleton already initialized. Use getInstance().");
        }
    }

    /**
     * Static inner class is not loaded until it is referenced.
     * The JVM initializes classes in a thread-safe manner (no synchronized needed here).
     */
    private static final class Holder {
        // Exactly ONE instance field (the singleton instance) lives here.
        private static final Singleton INSTANCE = new Singleton();

        // Used only to help the constructor detect late reflective construction attempts.
        // This is not a second "instance field" of type Singleton—it's just a guard flag.
        private static final boolean INSTANCED_CREATED = true;

        private Holder() {
            // Not instantiable.
        }
    }

    /**
     * Global access point.
     * The first call triggers loading/initialization of Holder, creating the instance once.
     */
    public static Singleton getInstance() {
        return Holder.INSTANCE;
    }

    // Example method to show it behaves like a normal object.
    public String ping() {
        return "pong";
    }
}
