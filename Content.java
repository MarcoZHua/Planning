import java.util.ArrayList;
import java.util.List;

public class Content {
    public static void main(String[] args) {
        int startTime = 11;
        int finishTime = 51;
        String [] contentName = {"除草","清除表土","推土","推石","整平","碾压"};
        String [] machineModel = {"SD16","SG18-C5","SR20MA"};
        int numContent = 6; // 工程内容项的数量
        double [] units = {105,150,120,129,225,120};  //每个工程内容项需要的有效台班数 存放在数组units中，其长度与工程内容项的数量相同
        double [] dayUnit = new double[numContent];
        int [] contentStartTime = new int[numContent];
        int [] contentFinishTime = new int[numContent];

        // 前置工程内容的前置搭接时间矩阵 preLapTime[0][1]=2工程内容0是工程内容1的前置工程内容，搭接时间为2
        int [][] preLapTime = new int[numContent][numContent];
        // 后置工程内容的后置搭接时间矩阵 sucLapTime[5][4]=2工程内容5是工程内容4的后置工程内容，搭接时间为2
        int [][] sucLapTime = new int[numContent][numContent];

        preLapTime[0][1] = 2; //赋初值
        preLapTime[1][2] = 3;
        preLapTime[1][3] = 3;
        preLapTime[2][4] = 3;
        preLapTime[3][4] = 3;
        preLapTime[4][5] = 2;

        sucLapTime[1][0] =2; // 赋初值
        sucLapTime[2][1] =3;
        sucLapTime[3][1] =3;
        sucLapTime[4][2] = 3;
        sucLapTime[4][3] = 3;
        sucLapTime[5][4] = 2;

//        int [] preSum = new int[numContent];  // 每个工程内容项的前置工程内容项数量
//        int [] sucSum = new int[numContent];  // 每个工程内容项的前置工程内容项数量

        // list中包含每个工程内容项的前置工程内容项的序号集合
        // list[4]=[2,3] 工程内容项4的前置工程内容项为 2和3
//        for(int i = 0; i < numContent;i++) {
//            for(int j = 0; j < numContent; j++) {
//                if (preLapTime[j][i] != 0) {
//                    preSum[i]++;
//                }
//                if (sucLapTime[j][i] != 0) {
//                    sucSum[i]++;
//                }
//            }
//
//        }


        for(int i = 0; i < numContent; i++) {
            boolean fisrtCon = true;  // 工程内容项i 是否有前置工程内容项
            boolean lastCon = true; //工程内容项i是否有后置工程内容项
            for (int j = 0; j < numContent; j++) {
                if (preLapTime[j][i] != 0) {
                    fisrtCon = false;
                }

                if(sucLapTime[j][i] != 0) {
                    lastCon = false;
                }

            }

            if (fisrtCon) {
                //第i个工程内容项无前置工程内容项，可确定开始时间
                contentStartTime[i] = startTime;
            }

            if (lastCon) {
                // 第i个工程内容项无后置工程内容项，可确定结束时间
                contentFinishTime[i] = finishTime;
            }
        }


        for (int i = 0; i < numContent;i++) {
            if (contentStartTime[i] == 0) {
                // 对于没有确定开始时间的工程内容项，确定其开始时间
                int tempStart = 0;
                for (int j = 0; j < numContent; j++) {
                    if ((contentStartTime[j] !=0) && (preLapTime[j][i] != 0) ) {
                        // 如果工程内容项j是工程内容项i的前置，且其开始时间已经确定，则可确定i的开始时间
                        if(contentStartTime[j] + preLapTime[j][i] > tempStart) {
                            tempStart = contentStartTime[j] + preLapTime[j][i];
                        }
                    }
                }
                contentStartTime[i] = tempStart;

            }
        }

        for (int i = numContent-1; i >= 0;i--) {
            if (contentFinishTime[i] == 0) {
                // 对于没有确定结束时间的工程内容项，确定其结束时间
                int tempFinish = 51;
                for (int j = 0; j < numContent; j++) {
                    if ((contentFinishTime[j] !=0) && (sucLapTime[j][i] != 0) ) {
                        // 如果工程内容项j是工程内容项i的后置，且其结束时间已经确定，则可确定i的结束时间
                        if(contentFinishTime[j] - sucLapTime[j][i] < tempFinish) {
                            tempFinish = contentFinishTime[j] - sucLapTime[j][i];
                        }
                    }
                }
                contentFinishTime[i] = tempFinish;

            }
        }

        System.out.println("--------------------");
        for (int i = 0; i < numContent; i++) {
            System.out.println(contentStartTime[i]+" ");
            System.out.println(contentFinishTime[i]+" ");
        }

        for(int i = 0; i < numContent; i++) {
            dayUnit[i] = units[i]/(contentFinishTime[i]-contentStartTime[i]+1);
            System.out.println("第"+i+"工程内容项每日台班数为"+dayUnit[i]);
        }

        for(int i = startTime; i <= finishTime; i++) {

        }



    }
}
