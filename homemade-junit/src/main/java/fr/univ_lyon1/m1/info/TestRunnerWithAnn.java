package fr.univ_lyon1.m1.info;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Exécuteur de tests personnalisé utilisant des annotations.
 * <p>
 * Cette classe parcourt toutes les méthodes d'un objet de test et exécute
 * automatiquement celles qui sont annotées avec @HomeMadeTest.
 * Si une méthode possède aussi @HomeMadeArgs, elle est appelée avec chaque
 * argument spécifié dans l'annotation.
 * </p>
 * 
 * Pattern: Reflection - utilise l'introspection Java pour découvrir et exécuter dynamiquement
 * les méthodes de test sans les connaître à la compilation.
 */
public class TestRunnerWithAnn {
    /** L'objet contenant les méthodes de test à exécuter. */
    Object objectUnderTest;

    /**
     * Crée un exécuteur de tests pour l'objet donné.
     * 
     * @param tc L'objet de test contenant les méthodes annotées.
     */
    public TestRunnerWithAnn(Object tc) {
        objectUnderTest = tc;
    }

    /**
     * Exécute tous les tests de l'objet.
     * Parcourt toutes les méthodes publiques et exécute celles annotées avec @HomeMadeTest.
     * 
     * @throws IllegalAccessException Si une méthode n'est pas accessible.
     * @throws IllegalArgumentException Si les arguments fournis sont invalides.
     * @throws InvocationTargetException Si une méthode de test lève une exception.
     */
    public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<? extends Object> classUnderTest = objectUnderTest.getClass();
        System.out.println("testing " + classUnderTest.getName() + "...");
        
        // Parcourt toutes les méthodes publiques de la classe
        for (Method method : classUnderTest.getMethods()) {
            processMethod(method);
        }
        
        System.out.println("testing " + classUnderTest.getName() + "... DONE");
    }

    /**
     * Traite une méthode : l'exécute si elle est annotée @HomeMadeTest.
     * Si la méthode possède aussi @HomeMadeArgs, elle est appelée plusieurs fois
     * avec chacun des arguments spécifiés.
     * 
     * @param method La méthode à traiter.
     * @throws IllegalAccessException Si la méthode n'est pas accessible.
     * @throws InvocationTargetException Si la méthode lève une exception.
     */
    private void processMethod(Method method) throws IllegalAccessException, InvocationTargetException {
        // Vérifie si la méthode est annotée @HomeMadeTest
        HomeMadeTest a = method.getAnnotation(HomeMadeTest.class);
        if (a != null) {
            // Vérifie si la méthode a aussi @HomeMadeArgs
            HomeMadeArgs p = method.getAnnotation(HomeMadeArgs.class);
            if (p != null) {
                // Exécute la méthode avec chaque argument fourni
                for (int arg : p.value()) {
                    System.out.println("  invoking " + method.getName() + " with arg " + arg);
                    method.invoke(objectUnderTest, arg);
                }
            } else {
                // Exécute la méthode sans argument
                System.out.println("  invoking " + method.getName());
                method.invoke(objectUnderTest);
            }
        } else {
            // La méthode n'est pas un test, on l'ignore
            System.out.println("  NOT invoking " + method.getName());
        }
    }

}
