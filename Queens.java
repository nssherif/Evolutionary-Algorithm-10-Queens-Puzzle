import java.lang.Math;
import java.util.*;

/* YOU NEED TO ADD YOUR CODE TO THIS CLASS, REMOVING ALL DUMMY CODE
 *
 * DO NOT CHANGE THE NAME OR SIGNATURE OF ANY OF THE EXISTING METHODS
 * (Signature includes parameter types, return types and being static)
 *
 * You can add private methods to this class if it makes your code cleaner,
 * but this class MUST work with the UNMODIFIED Tester.java class.
 *
 * This is the ONLY class that you can submit for your assignment.
 *
 * MH September 2022
 */
public class Queens
{
    private static int boardSize = 12;
    
    // creates a valid genotype with random values
    public static Integer [] createGeno()
    {
        Integer [] genotype = new Integer [boardSize];
        
        genotype = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };

        Random r = new Random();
        // Using Fisher–Yates shuffle Algorithm
        // Start from the last element and swap one by one. We don't
        // need to run for the first element that's why i > 0
        for (int i = boardSize-1; i > 0; i--) {
             
            // Pick a random index from 0 to i
            int j = r.nextInt(i+1);
             
            // Swap arr[i] with the element at random index
            int temp = genotype[i];
            genotype[i] = genotype[j];
            genotype[j] = temp;
        }
        
        return genotype;
    }

    public static Integer[] binaryArrayFiller (int probability)
    {
        Integer [] randomBinaryInts = new Integer[] {0,0,0,0,0,0,0,0,0,0};
        // Fill randomBinaryInts with as many ones as the probablility. 
        // For example if p = 0.8, then randomBinaryInts becomes 8 ones and two zeros. 
        for (int i = 0 ; i < probability ; i++) {
            randomBinaryInts[i] = 1;
        }
        return randomBinaryInts;
    }
    
    // move a gene in the genotype
    // the move happens with probability p, so if p = 0.8
    // then 8 out of 10 times this method is called, a move happens
    public static Integer[] insertionMutate(Integer[] genotype, double p)
    {
        int probability = (int)Math.round(p*10);
        Integer[] randomBinaryInts = binaryArrayFiller (probability);
        Random r = new Random();
        int randomNum = r.nextInt(randomBinaryInts.length);

        // Dont mutate if selected random nubmer is 0;
        if (randomBinaryInts[randomNum] == 0) 
        {
            return genotype;
        }

        System.out.println("mutation is happening");

        // Pick two random indecies from 0 to length of genotype to be used as indecies for the allels. 
        // RandAllel2 will always be less than randAllel1 so it will be the first index. 
        int secondRandAllelIndex = r.nextInt(genotype.length);
        if (secondRandAllelIndex == 0) secondRandAllelIndex+=1; // Make sure that the first rand number is never zero.
        int initialRandAllelIndex = r.nextInt(secondRandAllelIndex);
        System.out.println(initialRandAllelIndex);
        System.out.println(secondRandAllelIndex);

        Integer[] mutatedArr  = new Integer[genotype.length];

        Integer[] tempArr  = new Integer[genotype.length-1];

        // Copy over all the elements (until initial random allel index) from original array into mutated array
        System.arraycopy(genotype, 0, mutatedArr, 0, initialRandAllelIndex+1);
        
        // Copy the value of the second alle after the first alle
        mutatedArr[initialRandAllelIndex+1] = genotype[secondRandAllelIndex];

        // Remove the value of the second alle index from the genotype
        for (int i = 0, j = 0; i < genotype.length; i++) {
            if (i != secondRandAllelIndex) {
                tempArr[j++] = genotype[i];
            }
        }

        // Copy over all the elements from genotype from intial alle index until the end
        for (int i = initialRandAllelIndex+1; i<tempArr.length; i++) 
        {
            mutatedArr[i+1] = tempArr[i];
        }

        return mutatedArr;
    }
    
    // creates 2 child genotypes using the 'cut-and-crossfill' method
    public static Integer[][] crossover(Integer[] parent0, Integer[] parent1)
    {
        Integer [][] children = new Integer [2][boardSize];
        
        // YOUR CODE GOES HERE
        // DUMMY CODE TO REMOVE:
        children[0] = new Integer[]{ 5, 4, 2, 11, 6, 8, 9, 1, 10, 12, 7, 3 };
        children[1] = new Integer[]{ 6, 12, 2, 5, 7, 3, 9, 1, 10, 4, 11, 8 };
        // END OF YOUR CODE
        
        return children;
    }
    
    // calculates the fitness of an individual
    public static int fitness(Integer [] genotype)
    {
        /* The initial fitness is the maximum pairs of queens
         * that can be in check (all possible pairs in check).
         * So we are using it as the maximum fitness value.
         * We deduct 1 from this value for every pair of queens
         * found to be in check.
         * So, the lower the score, the lower the fitness.
         * For a 12x12 board the maximum fitness is 66 (no checks),
         * and the minimum fitness is 0 (all queens in a line).
         */
        int fitness = (int) (0.5 * boardSize * (boardSize - 1));
        
        // YOUR CODE GOES HERE
        
        return fitness;
    }
}
