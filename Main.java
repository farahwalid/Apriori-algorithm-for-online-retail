package com.company;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.ArrayUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
public class Main {

    public static void main(String[] args) throws IOException {
/*
        int a[] = {2, 6, 1, 4};
        int b[] = {2, 1, 4, 4};

        int result[] = new int[a.length];
        Arrays.setAll(result, i -> a[i] + b[i]);
        for (int i=0 ; i<result.length ;i++){
            System.out.println(result[i]);

        }
  */
        Clustering c = new Clustering();
        ArrayList<Cluster> a = c.iterateDataSet();
        System.out.println("0  "+a.get(0).mean);
        System.out.println(a.get(0).users);
        System.out.println("1  "+a.get(1).mean);
        System.out.println(a.get(1).users);

        /*
        ArrayList<Cluster> arr = new ArrayList<>();
        arr = c.chooseRandomCentroids();
        arr.get(0).users.add(1);
        arr.get(0).users.add(2);
        arr.get(1).users.add(3);
        arr.get(1).users.add(4);
        ArrayList<Cluster> a = c.calcMean(arr);
        System.out.println(a.get(0).mean);

        System.out.println(a.get(1).mean);

        for(int i=0;i<arr.size();i++){
            System.out.println(arr.get(i).mean);
        }
        System.out.println(c.calculateDistance(arr,1));
*/
    }
}
