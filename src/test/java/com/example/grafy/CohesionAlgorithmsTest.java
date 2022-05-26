package com.example.grafy;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CohesionAlgorithmsTest {

    private final Cohesion cohesionAlgorithms = new CohesionAlgorithms();
    @Test
    void whenWantingToCheckCohesion_givenNonCoherentGraph_thenReturnFalse() throws IOException {

        //Given
        GridGraph graph=new GridGraph("src/test/TestGraphs/TestAlgorithms/CohesionAlgorithms/Test1_givenNonCoherentGraph");

        //When

        boolean bfsCohesion=false;
        boolean dfsCohesion=false;

        //Then

        boolean bfsValue= cohesionAlgorithms.breathFirstSearch(graph,0);
        boolean dfsValue= cohesionAlgorithms.depthFirstSearch(graph,0);

        assertEquals(bfsCohesion, bfsValue);
        assertEquals(dfsCohesion, dfsValue);

    }

    @Test
    void whenWantingToCheckCohesion_givenCoherentGraph_thenReturnFalse() throws IOException {

        //Given
        GridGraph graph=new GridGraph("src/test/TestGraphs/TestAlgorithms/CohesionAlgorithms/Test2_givenCoherentGraph");

        //When

        boolean bfsCohesion=true;
        boolean dfsCohesion=true;

        //Then

        boolean bfsValue= cohesionAlgorithms.depthFirstSearch(graph,0);
        boolean dfsValue= cohesionAlgorithms.breathFirstSearch(graph,0);

        assertEquals(bfsCohesion, bfsValue);
        assertEquals(dfsCohesion, dfsValue);

    }

    @Test
    void whenWantingToCheckCohesion_givenNodeGreaterThanNumOfNodes_thenThrowAnException() {

        //Given//When
        Throwable exception1 = assertThrows(IllegalArgumentException.class, () -> cohesionAlgorithms.depthFirstSearch(new GridGraph("src/test/TestGraphs/TestAlgorithms/CohesionAlgorithms/Test3_givenNodeGreaterThanNumOfNodes"),100));
        Throwable exception2 = assertThrows(IllegalArgumentException.class, () -> cohesionAlgorithms.breathFirstSearch(new GridGraph("src/test/TestGraphs/TestAlgorithms/CohesionAlgorithms/Test3_givenNodeGreaterThanNumOfNodes"),100));

        //Then

        assertEquals("COHERENT_ALGO_PROBLEM: There is a problem with arguments given to the method!", exception1.getMessage());
        assertEquals("COHERENT_ALGO_PROBLEM: There is a problem with arguments given to the method!", exception2.getMessage());

    }

}