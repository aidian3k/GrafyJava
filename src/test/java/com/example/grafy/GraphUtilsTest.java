package com.example.grafy;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GraphUtilsTest {

    private boolean comparePath(ArrayList<Integer> pathSol, int [] pathExpected){

        if(pathSol.size()!=pathExpected.length){
            return false;
        }
        for(int i=0;i<pathExpected.length;++i){
            if(pathSol.get(i)!=pathExpected[i]){
                return false;
            }
        }
        return true;
    }

    @Test
    void whenWantingToCheckCohesion_givenNonCoherentGraph_thenReturnFalse() throws IOException {

        //Given
        GridGraph graph=new GridGraph("src/test/TestGraphs/TestAlgorithms/CohesionAlgorithms/Test1_givenNonCoherentGraph");

        //When

        boolean bfsCohesion=false;
        boolean dfsCohesion=false;

        //Then

        boolean bfsValue=GraphUtils.breathFirstSearch(graph,0);
        boolean dfsValue=GraphUtils.breathFirstSearch(graph,0);

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

        boolean bfsValue=GraphUtils.breathFirstSearch(graph,0);
        boolean dfsValue=GraphUtils.breathFirstSearch(graph,0);

        assertEquals(bfsCohesion, bfsValue);
        assertEquals(dfsCohesion, dfsValue);

    }

    @Test
    void whenWantingToCheckCohesion_givenNodeGreaterThanNumOfNodes_thenThrowAnException() {

        //Given//When
        Throwable exception1 = assertThrows(IllegalArgumentException.class, () -> GraphUtils.breathFirstSearch(new GridGraph("src/test/TestGraphs/TestAlgorithms/CohesionAlgorithms/Test3_givenNodeGreaterThanNumOfNodes"),100));
        Throwable exception2 = assertThrows(IllegalArgumentException.class, () -> GraphUtils.breathFirstSearch(new GridGraph("src/test/TestGraphs/TestAlgorithms/CohesionAlgorithms/Test3_givenNodeGreaterThanNumOfNodes"),100));

        //Then

        assertEquals("COHERENT_ALGO_PROBLEM: There is a problem with arguments given to the method!", exception1.getMessage());
        assertEquals("COHERENT_ALGO_PROBLEM: There is a problem with arguments given to the method!", exception2.getMessage());

    }

    @Test
    void whenWantingShortestPath_givenNormalCoherentGraph_thenReturnCorrectWeightAndPath() throws IOException {

        //Given
        GridGraph graph=new GridGraph("src/test/TestGraphs/TestAlgorithms/ShortestPathAlgorithms/Test1_givenNormalGraph1");

        //When

        int [] expectedPath={0,1,2,4};
        double expectedWeight=5;

        //Then

        ShortestPathSolution sol1=GraphUtils.dijkstra(graph,0,4);
        ShortestPathSolution sol2=GraphUtils.bellmanFord(graph,0,4);
        ShortestPathSolution sol3=GraphUtils.floydWarshall(graph,0,4);

        assertEquals(expectedWeight, sol1.minimumWeight);
        assertEquals(expectedWeight, sol2.minimumWeight);
        assertEquals(expectedWeight, sol3.minimumWeight);

        assertTrue(comparePath(sol1.path, expectedPath));
        assertTrue(comparePath(sol2.path, expectedPath));
        assertTrue(comparePath(sol3.path, expectedPath));
    }

    @Test
    void whenWantingShortestPath_givenNormalCoherentGraph1_thenReturnCorrectWeightAndPath() throws IOException {

        //Given
        GridGraph graph=new GridGraph("src/test/TestGraphs/TestAlgorithms/ShortestPathAlgorithms/Test2_givenNormalCoherentGraph1");

        //When

        int [] expectedPath={11,10,5,0};
        double expectedWeight=146.10516427741626;

        //Then

        ShortestPathSolution sol1=GraphUtils.dijkstra(graph,11,0);
        ShortestPathSolution sol2=GraphUtils.bellmanFord(graph,11,0);
        ShortestPathSolution sol3=GraphUtils.floydWarshall(graph,11,0);

        assertEquals(expectedWeight, sol1.minimumWeight);
        assertEquals(expectedWeight, sol2.minimumWeight);
        assertEquals(expectedWeight, sol3.minimumWeight);

        assertTrue(comparePath(sol1.path, expectedPath));
        assertTrue(comparePath(sol2.path, expectedPath));
        assertTrue(comparePath(sol3.path, expectedPath));

    }

    @Test
    void whenWantingShortestPath_givenGraphWithOneColumn_thenReturnCorrectWeightAndPath() throws IOException {

        //Given
        GridGraph graph=new GridGraph("src/test/TestGraphs/TestAlgorithms/ShortestPathAlgorithms/Test5_givenGraphWithOneColumn");

        //When

        int [] expectedPath={0,1,2};
        double expectedWeight=3;

        //Then

        ShortestPathSolution sol1=GraphUtils.dijkstra(graph,0,2);
        ShortestPathSolution sol2=GraphUtils.bellmanFord(graph,0,2);
        ShortestPathSolution sol3=GraphUtils.floydWarshall(graph,0,2);

        assertEquals(expectedWeight, sol1.minimumWeight);
        assertEquals(expectedWeight, sol2.minimumWeight);
        assertEquals(expectedWeight, sol3.minimumWeight);

        assertTrue(comparePath(sol1.path, expectedPath));
        assertTrue(comparePath(sol2.path, expectedPath));
        assertTrue(comparePath(sol3.path, expectedPath));
    }

    @Test
    void whenWantingShortestPath_givenNonCoherentGraph_thenThrowException() {

        //Given//When

        Throwable exception1=assertThrows(IllegalArgumentException.class, () -> GraphUtils.dijkstra(new GridGraph("src/test/TestGraphs/TestAlgorithms/ShortestPathAlgorithms/Test3_givenNonCoherentGraph"),0,1));
        Throwable exception2=assertThrows(IllegalArgumentException.class, () -> GraphUtils.floydWarshall(new GridGraph("src/test/TestGraphs/TestAlgorithms/ShortestPathAlgorithms/Test3_givenNonCoherentGraph"),0,1));
        Throwable exception3=assertThrows(IllegalArgumentException.class, () -> GraphUtils.bellmanFord(new GridGraph("src/test/TestGraphs/TestAlgorithms/ShortestPathAlgorithms/Test3_givenNonCoherentGraph"),0,1));

        //Then

        assertEquals("SHORTEST_PATH_ALGO_PROBLEM: There is a problem with arguments given to the method!", exception1.getMessage());
        assertEquals("SHORTEST_PATH_ALGO_PROBLEM: There is a problem with arguments given to the method!", exception2.getMessage());
        assertEquals("SHORTEST_PATH_ALGO_PROBLEM: There is a problem with arguments given to the method!", exception3.getMessage());

    }

    @Test
    void whenWantingShortestPath_givenNegativeCycleGraph_thenThrowException() {

        //Given//When

        Throwable exception=assertThrows(IllegalArgumentException.class, () -> GraphUtils.bellmanFord(new GridGraph("src/test/TestGraphs/TestAlgorithms/ShortestPathAlgorithms/Test4_givenNegativeCycleGraph"),0,1));

        //Then

        assertEquals("ERROR: Given graph contains cycle!", exception.getMessage());
    }

    @Test
    void whenWantingShortestPath_givenNodeGreaterThanNumOfGraphNodes_thenThrowException() {

        //Given//When
        Throwable exception1=assertThrows(IllegalArgumentException.class, () -> GraphUtils.dijkstra(new GridGraph("src/test/TestGraphs/TestAlgorithms/ShortestPathAlgorithms/Test6_givenNodeGreaterThanNumOfNodes"),100,1));
        Throwable exception2=assertThrows(IllegalArgumentException.class, () -> GraphUtils.floydWarshall(new GridGraph("src/test/TestGraphs/TestAlgorithms/ShortestPathAlgorithms/Test6_givenNodeGreaterThanNumOfNodes"),100,1));
        Throwable exception3=assertThrows(IllegalArgumentException.class, () -> GraphUtils.bellmanFord(new GridGraph("src/test/TestGraphs/TestAlgorithms/ShortestPathAlgorithms/Test6_givenNodeGreaterThanNumOfNodes"),100,1));

        //Then

        assertEquals("SHORTEST_PATH_ALGO_PROBLEM: There is a problem with arguments given to the method!", exception1.getMessage());
        assertEquals("SHORTEST_PATH_ALGO_PROBLEM: There is a problem with arguments given to the method!", exception2.getMessage());
        assertEquals("SHORTEST_PATH_ALGO_PROBLEM: There is a problem with arguments given to the method!", exception3.getMessage());

    }
}