package ca.utoronto.utm.assignment2.paint;

public interface Command {
    void apply();
    void unapply();
    default String name() { return this.getClass().getSimpleName(); }
}
