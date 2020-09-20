package com.company;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Clustering {
    int k;
    int percentage;
    String filename;
    Clustering(int k, int percentage, String filename){
        this.k=k;
        this.filename=filename;
        this.percentage=percentage;
    }
    Clustering(){}

    ////////////to initialize the first centroids////////////////
    ArrayList<Cluster> chooseRandomCentroids() throws IOException {
        ArrayList<Cluster> centroids = new ArrayList<>();
        File myFile = new File("Review_ratings.xlsx");
        FileInputStream fis = new FileInputStream(myFile);
        XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);
        int j=0;
        while(j < k){
            int x = (int)(Math.random()*(percentage*10 +1));
            System.out.println(x);
            Row getrandom = mySheet.getRow(x);
            Cluster c = new Cluster();
            for(int i=1 ; i<=24 ;i++){
                c.mean.add(getrandom.getCell(i).getNumericCellValue());
            }
            centroids.add(c);
            j++;
        }
        return  centroids;
    }
    ///////////To Get th cluster in which the object will be added to//////////
    int calculateDistance(ArrayList<Cluster> centroids, int userId) throws IOException {
        ArrayList<Double> categories = new ArrayList<>();
        File myFile = new File("Review_ratings.xlsx");
        FileInputStream fis = new FileInputStream(myFile);
        XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);
        Row row =  mySheet.getRow(userId);
        for(int i=1 ; i<=24 ;i++){
            categories.add(row.getCell(i).getNumericCellValue());
        }
        double previous = 10000000;
        int clusterNumber=0;
        for(int i=0 ; i<centroids.size();i++){
            double distance =  0;
            for(int j=0;j<centroids.get(i).mean.size();j++){
                distance+= Math.abs(centroids.get(i).mean.get(j) - categories.get(j));
            }
            if(distance < previous){
                previous = distance;
                clusterNumber = i;
                System.out.println(clusterNumber);
            }

            //System.out.println("previous: "+previous+"  distance: "+distance);

        }

        return clusterNumber;
    }

    ///////////calculate new mean/////////
    ArrayList<Cluster> calcMean(ArrayList<Cluster> clusters) throws IOException {
        File myFile = new File("Review_ratings.xlsx");
        FileInputStream fis = new FileInputStream(myFile);
        XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);
        for(int i=0 ; i<clusters.size();i++){
            double sum[] = new double[24];
            for(int j=0;j<clusters.get(i).users.size();j++){
                Row row =  mySheet.getRow(clusters.get(i).users.get(j));
                double categories[] = new double[24] ;
                for(int n=1 ; n<=24 ;n++){
                    categories[n-1]=row.getCell(n).getNumericCellValue();
                }
                Arrays.setAll(sum, s -> sum[s] + categories[s]);
                //System.out.println(j);
            }
            ArrayList<Double> mean = new ArrayList<>();
            for(int n=0 ; n<sum.length ; n++){
                sum[n] /= clusters.get(i).users.size();
                mean.add(sum[n]);
            }
            clusters.get(i).mean = mean;
        }
        return clusters;
    }

    /////////To iterate on the dataset to add each object to its cluster//////////
    ArrayList<Cluster> iterateDataSet() throws IOException {
        ArrayList<Cluster> centroids = chooseRandomCentroids();
        while(true){
            for(int i=1;i< percentage*10 ; i++){
                int clusterNumber = calculateDistance(centroids,i);
                centroids.get(clusterNumber).users.add(i);
            }
            ArrayList<Cluster> lastIteration = centroids;
            calcMean(centroids);
            for(int i=0 ; i<lastIteration.size();i++){
                if(! (lastIteration.get(i).mean == centroids.get(i).mean)){
                    break;
                }
                return centroids;
            }
        }
    }

    ///////////////outlier detection///////////////
    ArrayList<Integer> detectOutlier(ArrayList<Cluster> clusters) throws IOException {
        ArrayList<Integer> outliers = new ArrayList<>();
        ArrayList<Double> categories = new ArrayList<>();
        File myFile = new File("Review_ratings.xlsx");
        FileInputStream fis = new FileInputStream(myFile);
        XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);
        for(int i=0 ; i<clusters.size();i++){
            ArrayList<Double> distances = new ArrayList<>();
            double sum =0;
            for(int j=0;j<clusters.get(i).users.size();j++){
                Row row =  mySheet.getRow(clusters.get(i).users.get(j));
                double distance =0;
                for(int n=1 ; n<=24 ;n++){
                    categories.add(row.getCell(n).getNumericCellValue());
                }
                for(int n=0;n<clusters.get(i).mean.size();n++){
                    distance+=Math.abs(clusters.get(i).mean.get(n) - categories.get(n));
                }
                distances.add(distance);
                sum+=distance;
            }
            double average = sum / distances.size();
            for(int j=0;j<distances.size();j++){
                if(distances.get(j) > average){
                    outliers.add(clusters.get(i).users.get(j));
                }
            }
        }
        //System.out.println(outliers);
        return outliers;
    }

}
