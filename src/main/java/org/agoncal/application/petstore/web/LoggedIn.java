//A Qualifier is an application-defined annotation that enables you to identify an implementation of a bean type. 
//Define a qualifier for each implementation of a bean type that you are providing.
//Define qualifiers only if you are providing multiple implementations of a bean type and if you are not using alternatives. 
//If no qualifiers are defend for a bean type, CDI applies the predefined qualifier @Default when a bean of the type is injected.

package org.agoncal.application.petstore.web;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Antonio Goncalves http://www.antoniogoncalves.org --
 */

//tells us it is a quaifier type of bean.
@Qualifier

//the qualifier is to be retained by the virtual machine at run time
@Retention(RUNTIME)

// the qualifier may be applied to the program elements METHOD, FIELD, PARAMETER, and TYPE.
@Target({ FIELD, TYPE, METHOD })
public @interface LoggedIn {
}
