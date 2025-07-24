package com.logicerror.e_learning.validators;

import com.logicerror.e_learning.requests.course.section.BatchCreateSectionRequest;
import com.logicerror.e_learning.requests.course.section.CreateSectionRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniqueFieldsValidator implements ConstraintValidator<UniqueFields, BatchCreateSectionRequest> {
    @Override
    public boolean isValid(BatchCreateSectionRequest request, ConstraintValidatorContext context) {
        if (request == null || request.getCreateSectionRequests() == null) {
            return true; // Let @NotNull handle this
        }

        List<CreateSectionRequest> sections = request.getCreateSectionRequests();
        Set<String> titles = new HashSet<>();
        Set<Integer> orders = new HashSet<>();
        boolean isValid = true;

        // Disable default error message
        context.disableDefaultConstraintViolation();

        for (int i = 0; i < sections.size(); i++) {
            CreateSectionRequest section = sections.get(i);

            // Check for duplicate title
            if (section.getTitle() != null && !titles.add(section.getTitle())) {
                context.buildConstraintViolationWithTemplate(
                                "Duplicate title: " + section.getTitle())
                        .addPropertyNode("createSectionRequests")
                        .addPropertyNode("title").inIterable().atIndex(i)
                        .addConstraintViolation();
                isValid = false;
            }

            // Check for duplicate order
            if (section.getOrder() != null && !orders.add(section.getOrder())) {
                context.buildConstraintViolationWithTemplate(
                                "Duplicate order: " + section.getOrder())
                        .addPropertyNode("createSectionRequests")
                        .addPropertyNode("order").inIterable().atIndex(i)
                        .addConstraintViolation();
                isValid = false;
            }
        }

        return isValid;
    }
}
