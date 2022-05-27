package com.example.grafy;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PathAlgorithmsTest {

    private final ShortestPath pathAlgorithms = new PathAlgorithms();

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
    void whenWantingShortestPath_givenNormalCoherentGraph_thenReturnCorrectWeightAndPath() throws IOException {

        //Given
        GridGraph graph=new GridGraph("src/test/TestGraphs/TestAlgorithms/ShortestPathAlgorithms/Test1_givenNormalGraph1");

        //When

        int [] expectedPath={0,1,2,4};
        double expectedWeight=5;

        //Then

        ShortestPathSolution sol1= pathAlgorithms.dijkstra(graph,0);
        ShortestPathSolution sol2= pathAlgorithms.bellmanFord(graph,0);
        ShortestPathSolution sol3= pathAlgorithms.floydWarshall(graph,0);

        double dijkstraSolution=sol1.getWeightSolution(4);
        double bellmanFordSolution=sol2.getWeightSolution(4);
        double floydWarshallSolution=sol3.getWeightSolution(4);

        ArrayList<Integer> dijkstraPathSolution=sol1.getPathSolution(4);
        ArrayList<Integer> bellmanFordPathSolution=sol1.getPathSolution(4);
        ArrayList<Integer> floydWarshallPathSolution=sol3.getPathSolution(4);

        assertEquals(expectedWeight, dijkstraSolution);
        assertEquals(expectedWeight, bellmanFordSolution);
        assertEquals(expectedWeight, floydWarshallSolution);

        assertTrue(comparePath(dijkstraPathSolution, expectedPath));
        assertTrue(comparePath(bellmanFordPathSolution, expectedPath));
        assertTrue(comparePath(floydWarshallPathSolution, expectedPath));

    }

    @Test
    void whenWantingShortestPath_givenNormalCoherentGraph1_thenReturnCorrectWeightAndPath() throws IOException {

        //Given
        GridGraph graph=new GridGraph("src/test/TestGraphs/TestAlgorithms/ShortestPathAlgorithms/Test2_givenNormalCoherentGraph1");

        //When

        int [] expectedPath={11,10,5,0};
        double expectedWeight=146.10516427741626;

        //Then

        ShortestPathSolution sol1= pathAlgorithms.dijkstra(graph,11);
        ShortestPathSolution sol2= pathAlgorithms.bellmanFord(graph,11);
        ShortestPathSolution sol3= pathAlgorithms.floydWarshall(graph,11);

        double dijkstraSolution=sol1.getWeightSolution(0);
        double bellmanFordSolution=sol2.getWeightSolution(0);
        double floydWarshallSolution=sol3.getWeightSolution(0);

        ArrayList<Integer> dijkstraPathSolution=sol1.getPathSolution(0);
        ArrayList<Integer> bellmanFordPathSolution=sol1.getPathSolution(0);
        ArrayList<Integer> floydWarshallPathSolution=sol3.getPathSolution(0);

        assertEquals(expectedWeight, dijkstraSolution);
        assertEquals(expectedWeight, bellmanFordSolution);
        assertEquals(expectedWeight, floydWarshallSolution);

        assertTrue(comparePath(dijkstraPathSolution, expectedPath));
        assertTrue(comparePath(bellmanFordPathSolution, expectedPath));
        assertTrue(comparePath(floydWarshallPathSolution, expectedPath));

    }

    @Test
    void whenWantingShortestPath_givenGraphWithOneColumn_thenReturnCorrectWeightAndPath() throws IOException {

        //Given
        GridGraph graph=new GridGraph("src/test/TestGraphs/TestAlgorithms/ShortestPathAlgorithms/Test5_givenGraphWithOneColumn");

        //When

        int [] expectedPath={0,1,2};
        double expectedWeight=3;

        //Then

        ShortestPathSolution sol1= pathAlgorithms.dijkstra(graph,0);
        ShortestPathSolution sol2= pathAlgorithms.bellmanFord(graph,0);
        ShortestPathSolution sol3= pathAlgorithms.floydWarshall(graph,0);

        double dijkstraSolution=sol1.getWeightSolution(2);
        double bellmanFordSolution=sol2.getWeightSolution(2);
        double floydWarshallSolution=sol3.getWeightSolution(2);

        ArrayList<Integer> dijkstraPathSolution=sol1.getPathSolution(2);
        ArrayList<Integer> bellmanFordPathSolution=sol1.getPathSolution(2);
        ArrayList<Integer> floydWarshallPathSolution=sol3.getPathSolution(2);

        assertEquals(expectedWeight, dijkstraSolution);
        assertEquals(expectedWeight, bellmanFordSolution);
        assertEquals(expectedWeight, floydWarshallSolution);

        assertTrue(comparePath(dijkstraPathSolution, expectedPath));
        assertTrue(comparePath(bellmanFordPathSolution, expectedPath));
        assertTrue(comparePath(floydWarshallPathSolution, expectedPath));

    }

    @Test
    void whenWantingShortestPath_givenNonCoherentGraph_thenThrowException() {

        //Given//When

        Throwable exception1=assertThrows(IllegalArgumentException.class, () -> pathAlgorithms.dijkstra(new GridGraph("src/test/TestGraphs/TestAlgorithms/ShortestPathAlgorithms/Test3_givenNonCoherentGraph"),0));
        Throwable exception2=assertThrows(IllegalArgumentException.class, () -> pathAlgorithms.floydWarshall(new GridGraph("src/test/TestGraphs/TestAlgorithms/ShortestPathAlgorithms/Test3_givenNonCoherentGraph"),0));
        Throwable exception3=assertThrows(IllegalArgumentException.class, () -> pathAlgorithms.bellmanFord(new GridGraph("src/test/TestGraphs/TestAlgorithms/ShortestPathAlgorithms/Test3_givenNonCoherentGraph"),0));

        //Then

        assertEquals("SHORTEST_PATH_ALGO_PROBLEM: There is a problem with arguments given to the method!", exception1.getMessage());
        assertEquals("SHORTEST_PATH_ALGO_PROBLEM: There is a problem with arguments given to the method!", exception2.getMessage());
        assertEquals("SHORTEST_PATH_ALGO_PROBLEM: There is a problem with arguments given to the method!", exception3.getMessage());

    }

    @Test
    void whenWantingShortestPath_givenNegativeCycleGraph_thenThrowException() {

        //Given//When

        Throwable exception=assertThrows(IllegalArgumentException.class, () -> pathAlgorithms.bellmanFord(new GridGraph("src/test/TestGraphs/TestAlgorithms/ShortestPathAlgorithms/Test4_givenNegativeCycleGraph"),0));

        //Then

        assertEquals("ERROR: Given graph contains cycle!", exception.getMessage());
    }

    @Test
    void whenWantingShortestPath_givenNodeGreaterThanNumOfGraphNodes_thenThrowException() {

        //Given//When
        Throwable exception1=assertThrows(IllegalArgumentException.class, () -> pathAlgorithms.dijkstra(new GridGraph("src/test/TestGraphs/TestAlgorithms/ShortestPathAlgorithms/Test6_givenNodeGreaterThanNumOfNodes"),100));
        Throwable exception2=assertThrows(IllegalArgumentException.class, () -> pathAlgorithms.floydWarshall(new GridGraph("src/test/TestGraphs/TestAlgorithms/ShortestPathAlgorithms/Test6_givenNodeGreaterThanNumOfNodes"),100));
        Throwable exception3=assertThrows(IllegalArgumentException.class, () -> pathAlgorithms.bellmanFord(new GridGraph("src/test/TestGraphs/TestAlgorithms/ShortestPathAlgorithms/Test6_givenNodeGreaterThanNumOfNodes"),100));

        //Then

        assertEquals("SHORTEST_PATH_ALGO_PROBLEM: There is a problem with arguments given to the method!", exception1.getMessage());
        assertEquals("SHORTEST_PATH_ALGO_PROBLEM: There is a problem with arguments given to the method!", exception2.getMessage());
        assertEquals("SHORTEST_PATH_ALGO_PROBLEM: There is a problem with arguments given to the method!", exception3.getMessage());

    }

}