package ca.utoronto.utm.assignment2.paint;

public record ToolDescriptor(String name, String iconPath, Class<? extends ToolStrategy> strategyClass) {
}
