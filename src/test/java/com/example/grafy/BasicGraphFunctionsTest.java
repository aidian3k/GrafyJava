package com.example.grafy;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BasicGraphFunctionsTest {

    private static BasicGraphFunctions bf=new BasicGraphFunctions();

    @Test
    void whenReadingGraph_givenNormalFileToRead_thenReadTheGraphWithoutExceptions() throws IOException {

        //Given
        bf.readGraph("src/test/TestGraphs/TestReadingAndSaving/Test1_givenNormalFileToRead");

        //When

        int expectedColNum=5;
        int expectedRowsNum=5;
        boolean expectedMinWeight=true;
        boolean expectedMaxWeight=true;

        //Then

        assertEquals(expectedColNum, bf.colNum);
        assertEquals(expectedRowsNum, bf.rowsNum);
        assertEquals(expectedMaxWeight, bf.maxWeight<=1 && bf.maxWeight>=0);
        assertEquals(expectedMinWeight, bf.maxWeight<=1 && bf.maxWeight>=0);

    }

    @Test
    void whenReadingGraph_givenFileWithMoreDataThanRowsOrCol_thenThrowAnException() throws IOException {

        BasicGraphFunctions bf1=new BasicGraphFunctions();

        //Given and when
        Throwable exception=assertThrows(IndexOutOfBoundsException.class, () -> bf1.readGraph("src/test/TestGraphs/TestReadingAndSaving/Test2_givenFileWithMoreDataThanRowsOrCol"));

        //Then

        assertEquals("There is a problem with reading given graph! The size given is > than given lines with data in file", exception.getMessage());

    }

    @Test
    void whenReadingGraph_givenFileHasBadStructure_thenThrowAnException(){

        //Given and when
        Throwable exception=assertThrows(IOException.class, () -> bf.readGraph("src/test/TestGraphs/TestReadingAndSaving/Test3_givenFileHasBadStructure"));

        //Then

        assertEquals("FILE_READ_PROBLEM: There is a problem with reading given graph, check the file with graph!", exception.getMessage());

    }

    @Test
    void whenReadingGraph_givenFileHasLessArgumentsInLineThanExpected_thenThrowAnException(){

        //Given and when
        Throwable exception=assertThrows(IOException.class, () -> bf.readGraph("src/test/TestGraphs/TestReadingAndSaving/Test4_givenFileHasLessArgumentsInLineThanExpected"));

        //Then

        assertEquals("FILE_READ_PROBLEM: There is a problem with reading given graph, check the file with graph!", exception.getMessage());

    }

    @Test
    void whenSavingGraph_givenGraphIsCorrect_thenSaveToTheFile() throws IOException {

        BasicGraphFunctions bf2=new BasicGraphFunctions();

        //Given

        bf2.readGraph("src/test/TestGraphs/TestReadingAndSaving/Test5_givenGraphIsCorrect");
        bf2.saveGraph("src/test/TestGraphs/TestReadingAndSaving/TestIsTheSame");

        //When

        boolean isEqual=true;

        //Then

            Path of = Path.of("src/test/TestGraphs/TestReadingAndSaving/Test5_givenGraphIsCorrect");
            Path path = Path.of("src/test/TestGraphs/TestReadingAndSaving/TestIsTheSame");
            if (Files.size(of) != Files.size(path)) {
                isEqual=false;
            }

            byte[] first = Files.readAllBytes(of);
            byte[] second = Files.readAllBytes(path);
            isEqual= Arrays.equals(first, second);

            assertFalse(isEqual);

    }
}
