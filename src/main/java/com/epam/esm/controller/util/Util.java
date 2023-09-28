package com.epam.esm.controller.util;

import com.epam.esm.exception.exceptions.InvalidRequestBodyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.HashSet;
import java.util.Set;

/**
 * Util class for Controller group
 */
public class Util {

    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    private Util() {

    }

    /**
     * Checks the provided BindingResult for errors and throws an InvalidRequestBodyException if errors are found.
     * <p>
     * This method iterates through all errors in the BindingResult and collects their default messages.
     * If any errors are found, they are concatenated into a comma-separated string, and an InvalidRequestBodyException
     * is thrown with the collected error messages.
     *
     * @param bindingResult The BindingResult to be checked for errors.
     * @throws InvalidRequestBodyException if errors are found in the BindingResult. The exception message will contain
     *                                     a comma-separated list of error messages.
     */
    public static void bindingResultCheck(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Set<String> errorMessages = new HashSet<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            String str = String.join(", ", errorMessages);

            logger.warn("Invalid request body: {}", str);
            throw new InvalidRequestBodyException(str);
        }
    }
}
