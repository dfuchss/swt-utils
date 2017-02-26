package home.fox.swt.notifiable;

@FunctionalInterface
public interface SWTNotifiable<INFO> {
    SWTNotifiable<?> ALL = info -> {};

    void inform(INFO info);

    @SuppressWarnings("unchecked")
    static <INFO> SWTNotifiable<INFO> getAllID() {
        return (SWTNotifiable<INFO>) SWTNotifiable.ALL;
    }
}
