import java.lang.Math;
import java.util.*;

/************ 

Date: Oct 10
Written by Negib Sherif
Assignment 2

To compile run: javac Queens.java
************/

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

    private static Integer[] binaryArrayFiller (int probability)
    {
        Integer [] randomBinaryInts = new Integer[] {0,0,0,0,0,0,0,0,0,0};
        // Fill randomBinaryInts with as many ones as the probablility. 
        // For example if p = 0.8, then randomBinaryInts becomes 8 ones and two zeros. 
        for (int i = 0 ; i < probability ; i++) {
            randomBinaryInts[i] = 1;
        }
        return randomBinaryInts;
    }

    // Generate two random numbers
    private static Integer[] twoRandNumsGenerator ()
    {
        Integer [] randIndeces = new Integer[2];
        // Placeholder arraylist with all possible index values in genotype
        ArrayList<Integer> genome = new ArrayList<Integer>(
                Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));

        Random r = new Random();
        int index1 = r.nextInt(genome.size());
        int randNum1 = genome.get(index1); // Get first random index value

        genome.remove(index1); // Remove first index value so that the second index value is never the same

        int index2 = r.nextInt(genome.size());
        int randNum2 = genome.get(index2);

        randIndeces = new Integer[]{randNum1, randNum2};
        
        return randIndeces;
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

        // Select a random number from the binary ints array and dont mutate if selected nubmer is 0
        if (randomBinaryInts[randomNum] == 0) 
        {
            return genotype;
        }

        Integer[] mutatedArr  = new Integer[genotype.length];

        Integer[] tempArr  = new Integer[genotype.length-1];

        Integer[] randIndexes = twoRandNumsGenerator();

        System.out.println(Arrays.toString(randIndexes));

        if (randIndexes[0] < randIndexes[1]) {
            // Copy over all the elements (until initial random allel index) from original array into mutated array
            System.arraycopy(genotype, 0, mutatedArr, 0, randIndexes[0]+1);
            
            // Copy the value of the second alle after the first alle
            mutatedArr[randIndexes[0]+1] = genotype[randIndexes[1]];

            // Remove the value of the second alle index from the genotype
            for (int i = 0, j = 0; i < genotype.length; i++) {
                if (i != randIndexes[1]) {
                    tempArr[j++] = genotype[i];
                }
            }

            // Copy over all the elements from genotype from intial alle index until the end
            for (int i = randIndexes[0]+1; i<tempArr.length; i++) 
            {
                mutatedArr[i+1] = tempArr[i];
            }

        }

        else if (randIndexes[0] > randIndexes[1]) {
            // Copy over all elements until the greater index while skipping the value in the first index
            for (int i = 0, j = 0; i <= randIndexes[0]; i++) {
                if (i != randIndexes[1]) {
                    mutatedArr[j++] = genotype[i];
                }
            }

            // Copy the value of the second alle after the first alle
            mutatedArr[randIndexes[0]] = genotype[randIndexes[1]];

            // Copy over all the elements from genotype from first alle index until the end
            for (int i = 0; i<genotype.length; i++) 
            {
                if (mutatedArr[i] == null) {
                    mutatedArr[i] = genotype[i];
                }
            }
        }
        
        return mutatedArr;
    }

    // Checks if an element from first array is present in the first second array by looping through entire array (including wraparound)
    // Returns all the numbers that need to be added to crossover after the cutoff point
    private static ArrayList<Integer> getNumsForCrossover (Integer[] arr0, Integer[] arr1, Integer cutOffPoint) 
    {
        ArrayList<Integer> temp = new ArrayList<Integer>(cutOffPoint);

        // Loops that checks if an element from second parent is present in the first parent by looping through entire array (including wraparound)
        for (int i = cutOffPoint; i<arr1.length + cutOffPoint ; i++)
        {
            boolean numExists = false;
            for (int j = 0; j<cutOffPoint ; j++)
            {
                if (arr1[i%arr1.length] == arr0[j%arr0.length]) 
                {
                    numExists = true;
                };
            } 

            if (!numExists)
            {
                temp.add(arr1[i%arr0.length]);
            }
        }

        return temp;
    }
    
    // creates 2 child genotypes using the 'cut-and-crossfill' method
    public static Integer[][] crossover(Integer[] parent0, Integer[] parent1)
    {
        Integer [][] children = new Integer [2][boardSize];

        int cutOffPoint = parent0.length / 2;

        // Copy over all the elements until cutoff point
        System.arraycopy(parent0, 0, children[0], 0, cutOffPoint);
        System.arraycopy(parent1, 0, children[1], 0, cutOffPoint);

        ArrayList<Integer> temp = getNumsForCrossover(parent0, parent1, cutOffPoint);
        ArrayList<Integer> temp1 = getNumsForCrossover(parent1, parent0, cutOffPoint);

        // Copy over all the elements from cutoff point until the end
        System.arraycopy(temp.toArray(), 0, children[0], cutOffPoint, cutOffPoint);
        System.arraycopy(temp1.toArray(), 0, children[1], cutOffPoint, cutOffPoint);
        
        return children;
    }
    
    private static int getUniqueNums(Integer[] arr)
    {
        Arrays.sort(arr);
        int uniqueCounter = 1;
 
        for (int i = 1; i < arr.length; i++) 
        {
            if (arr[i-1] != arr[i])
            {
                uniqueCounter++;
            }
        }
        return uniqueCounter;
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
        
        int diagonalCheck = 0;

        for (int i=0; i < genotype.length;  i++) {
            for (int j=0; j < genotype.length ;  j++) {
                if ( i != j) 
                {
                    int n = Math.abs(i-j);
                    int m = Math.abs(genotype[i] - genotype[j]);
                    if(n == m) {diagonalCheck += 1;}
                }
            } 
        }

        int uniqueNums = getUniqueNums (genotype);
        int rowOrColumnCheck = genotype.length - uniqueNums;
        int totalChecks = rowOrColumnCheck/2 + diagonalCheck/2;

        int fitness = 66 - (totalChecks);
 
        return fitness;
    }
}
