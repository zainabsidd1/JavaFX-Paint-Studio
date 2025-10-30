package ca.utoronto.utm.assignment2.paint;

import ca.utoronto.utm.assignment2.paint.ToolStrategy;

public final class ToolDescriptor {
    private final String name;
    private final String iconPath;
    private final Class<? extends ToolStrategy> strategyClass;

    public ToolDescriptor(String name, String iconPath,
                          Class<? extends ToolStrategy> strategyClass) {
        this.name = name;
        this.iconPath = iconPath;
        this.strategyClass = strategyClass;
    }

    public String name() { return name; }
    public String iconPath() { return iconPath; }
    public Class<? extends ToolStrategy> strategyClass() { return strategyClass; }
}
