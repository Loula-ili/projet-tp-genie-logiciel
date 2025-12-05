package fr.univ_lyon1.m1.info;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Annotation personnalisée pour marquer les méthodes de test.
 * <p>
 * Cette annotation imite le comportement de JUnit @Test.
 * Les méthodes annotées avec @HomeMadeTest seront automatiquement
 * exécutées par le TestRunner.
 * </p>
 * 
 * Utilisation :
 * <pre>
 * {@code @HomeMadeTest}
 * public void testSomething() {
 *     // code de test
 * }
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)  // L'annotation est conservée à l'exécution (réflexion)
@Target(ElementType.METHOD)          // Peut uniquement être appliquée à des méthodes
public @interface HomeMadeTest {

}
