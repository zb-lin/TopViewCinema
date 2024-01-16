package sspring.bean.config;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {
    private final List<PropertyValue> propertyValues = new ArrayList<>();

    public void addPropertyValue(PropertyValue propertyValue) {
        propertyValues.add(propertyValue);
    }

    public PropertyValue[] getPropertyValues() {
        return this.propertyValues.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyValueName) {
        for (PropertyValue propertyValue : propertyValues) {
            if (propertyValue.getName().equals(propertyValueName)) {
                return propertyValue;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "PropertyValues{" +
                "propertyValues=" + propertyValues +
                '}';
    }
}
