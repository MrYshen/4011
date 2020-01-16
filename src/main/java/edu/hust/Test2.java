package edu.hust;

import edu.hust.screen.Screen;
import hdb.FloatData;
import hdb.FloatDataListHolder;
import hdb.Hdb;
import hdb.IndexTag;

import java.util.List;

public class Test2 {
    public static void main(String[] args) {
        Hdb rdb =new Hdb();
        int ret = rdb.open("localhost", 22135, "root", "root1234");
        System.out.println("open result = "+ret);



        int b = rdb.toUTC(2019, 12, 31, 18, 0, 0);

//        int b = rdb.toUTC(year ,month,1,0,0,0);


        FloatDataListHolder dataList = new FloatDataListHolder();

        ret = rdb.readFloatHisDataById(1,b,b+100,1, 2,dataList );
        System.out.println(ret);
        FloatData[] data1 = dataList.value;
        System.out.println(data1.length);

        for (int i = 0;i<data1.length;i++) {
            System.out.println(data1[i].tm+"\t"+data1[i].val);
        }
        rdb.close();
    }
}
