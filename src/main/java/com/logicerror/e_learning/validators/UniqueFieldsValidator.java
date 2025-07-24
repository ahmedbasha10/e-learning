package com.logicerror.e_learning.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class UniqueFieldsValidator implements ConstraintValidator<UniqueFields, Object> {
    private String[] fields;
    private String collectionProperty;

    @Override
    public void initialize(UniqueFields constraintAnnotation) {
        this.fields = constraintAnnotation.fields();
        this.collectionProperty = constraintAnnotation.collectionProperty();
    }

    @Override
    public boolean isValid(Object request, ConstraintValidatorContext context) {
        if(request == null) return true;
        try {
            List<?> items = getCollectionFromObject(request, collectionProperty);
            if (items == null || items.isEmpty()) {
                return true;
            }

            context.disableDefaultConstraintViolation();
            boolean isValid = true;
            for (String field : fields) {
                if(!validateFieldUniqueness(items, field, context)){
                    isValid = false;
                }
            }
            return isValid;
        } catch (Exception e) {
            log.error("Error accessing collection property '{}': {}", collectionProperty, e.getMessage());
            return false;
        }
    }

    private List<?> getCollectionFromObject(Object obj, String propertyName)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        String getterName = "get" + capitalize(propertyName);
        Method getter = obj.getClass().getMethod(getterName);
        Object result = getter.invoke(obj);

        return result instanceof List ? (List<?>) result : null;
    }

    private boolean validateFieldUniqueness(List<?> items, String fieldName,
                                            ConstraintValidatorContext context) {
        Set<Object> seenValues = new HashSet<>();
        boolean isValid = true;

        for (int i = 0; i < items.size(); i++) {
            Object item = items.get(i);
            try {
                Object fieldValue = getFieldValue(item, fieldName);

                if (fieldValue != null && !seenValues.add(fieldValue)) {
                    // Found duplicate
                    context.buildConstraintViolationWithTemplate(
                                    String.format("Duplicate %s: %s", fieldName, fieldValue))
                            .addPropertyNode(collectionProperty)
                            .addPropertyNode(fieldName).inIterable().atIndex(i)
                            .addConstraintViolation();
                    isValid = false;
                }
            } catch (Exception e) {
                // Skip this field if we can't access it
                continue;
            }
        }

        return isValid;
    }

    private Object getFieldValue(Object obj, String fieldName)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        String getterName = "get" + capitalize(fieldName);
        Method getter = obj.getClass().getMethod(getterName);
        return getter.invoke(obj);
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}

















//
//
//if (request == null || request.getCreateSectionRequests() == null) {
//        return true; // Let @NotNull handle this
//        }
//
//List<CreateSectionRequest> sections = request.getCreateSectionRequests();
//Set<String> titles = new HashSet<>();
//Set<Integer> orders = new HashSet<>();
//boolean isValid = true;
//
//// Disable default error message
//        context.disableDefaultConstraintViolation();
//
//        for (int i = 0; i < sections.size(); i++) {
//CreateSectionRequest section = sections.get(i);
//
//// Check for duplicate title
//            if (section.getTitle() != null && !titles.add(section.getTitle())) {
//        context.buildConstraintViolationWithTemplate(
//                                "Duplicate title: " + section.getTitle())
//        .addPropertyNode("createSectionRequests")
//                        .addPropertyNode("title").inIterable().atIndex(i)
//                        .addConstraintViolation();
//isValid = false;
//        }
//
//        // Check for duplicate order
//        if (section.getOrder() != null && !orders.add(section.getOrder())) {
//        context.buildConstraintViolationWithTemplate(
//                                "Duplicate order: " + section.getOrder())
//        .addPropertyNode("createSectionRequests")
//                        .addPropertyNode("order").inIterable().atIndex(i)
//                        .addConstraintViolation();
//isValid = false;
//        }
//        }
//
//        return isValid;