package com.example.proxy.pureproxy.decorator.code;

public abstract class Decorator implements Component{
    Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        return component.operation();
    }
}
