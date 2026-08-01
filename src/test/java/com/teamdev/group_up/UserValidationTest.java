package com.teamdev.group_up;

import com.teamdev.group_up.entity.User;
import com.teamdev.group_up.enums.Branch;
import com.teamdev.group_up.enums.Year;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidCollegeEmail() {
        User user = User.builder()
                .name("Test User")
                .username("test.user@learner.manipal.edu")
                .password("password")
                .year(Year.FIRST_YEAR)
                .branch(Branch.CSE)
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidEmailDomain() {
        User user = User.builder()
                .name("Test User")
                .username("test.user@gmail.com")
                .password("password")
                .year(Year.FIRST_YEAR)
                .branch(Branch.CSE)
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("please login with your college email id", violation.getMessage());
    }
}
