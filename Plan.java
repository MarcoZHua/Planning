import java.util.Arrays;
import java.util.Collections;

public class Plan {
    public static void main(String[] args) {
        int numInterval = 4; //分部分项工程数量
        int [] earliestStart = {1,9,12,17}; //分部分项工程的最早开始时间，相对时间，后续需要更换为日期
        int [] nomDura =  {50,45,45,37}; // 分部分项工程正常工期
        int [] limDura = {40,37,40,30}; // 分部分项工程极限工期

        // 第一步：根据分部分项工程工期，推出起止日期相关信息

        int [] earliestFinish = new int[numInterval];  //最早完成日期
        int [] latestFinish = new int[numInterval]; // 最晚完成日期
        int [] latestStart = new int[numInterval];  // 最晚开始日期
        int [] newEarliestStart = new int[numInterval]; // 更新后的最早开始日期
        int [] newEarliestFinish = new int[numInterval]; //更新后的最早完成日期

        for (int id = 0; id < numInterval; id++) {
            earliestFinish[id] = earliestStart[id] + limDura[id]-1;
            latestFinish[id] = earliestStart[id] + nomDura[id]-1;
            latestStart[id] = latestFinish[id] - limDura[id] + 1;
        }

        int minLS = Min(latestStart);  //最小的最晚开始时间 也即总工期开始时间

        for (int id = 0; id < numInterval; id++) {
            if (earliestStart[id] < minLS) {
                newEarliestStart[id] = minLS;
                newEarliestFinish[id] = newEarliestStart[id] + limDura[id];
            } else {
                newEarliestStart[id] = earliestStart[id];
                newEarliestFinish[id] = newEarliestStart[id] + limDura[id]-1;
            }
        }

        int maxEF = Max(newEarliestFinish);  //最大的最早完成日期 也即总工期结束日期

        int totalDur;  //总工期
        totalDur = maxEF - minLS + 1;
        System.out.println("施工开始时间为"+minLS+"，施工结束日期为"+maxEF+",总工期为"+totalDur+"天");

        int [] startTime= newEarliestStart;  // 分部分项工程开始时间
        int [] finishTime = new int[numInterval];  // 分部分项工程结束时间
        for (int id = 0; id < numInterval; id++) {
            finishTime[id] = Math.min(maxEF,latestFinish[id]);
        }
        System.out.println("-----------------------------------");
        for (int id = 0; id < numInterval; id++) {
            System.out.println("分部分项工程"+(id+1)+"的开始日期为"+startTime[id]+"，结束日期为"+finishTime[id]);
        }


    }

    public static int Min(int [] arr) {
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) min = arr[i];
        }
        return min;
    }

    public static int Max(int [] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) max = arr[i];
        }
        return max;
    }

}
