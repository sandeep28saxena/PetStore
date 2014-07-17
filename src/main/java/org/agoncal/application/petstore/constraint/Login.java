package org.agoncal.application.petstore.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */


//cannot be nullified

@NotNull

//defines the size of a login
@Size(min = 1, max = 10)

//@Target({ METHOD, FIELD, ANNOTATION_TYPE }): 
//Says, that methods, fields and annotation declarations may be annotated with @CheckCase (but not type declarations e.g.)
@Target({METHOD, FIELD})

//@Retention(RUNTIME): Specifies, that annotations of this type will be available at runtime by the means of reflection
@Retention(RUNTIME)

// @Constraint(validatedBy = CheckCaseValidator.class):
// Specifies the validator to be used to validate elements annotated with @CheckCase
@Constraint(validatedBy = {})

//@Documented: Says, that the use of @CheckCase will be contained in the JavaDoc of elements annotated with it
@Documented
public @interface Login {
   
    // ======================================
    // =             Attributes             =
    // ======================================
    String message() default "{validator.invalidLogin}";
    
    //Method to returns the classes.
    Class<?>[] groups() default {};

    //Casts this as a sub class.
    Class<? extends Payload>[] payload() default {};
}
