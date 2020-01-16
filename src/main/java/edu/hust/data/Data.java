package edu.hust.data;

import edu.hust.mysql.Mysql;
import hdb.*;

public class Data {

    //将数据写入数据库
    public void writeData(String tableName,int id,String newTableName,int mode){

        Mysql mysql = new Mysql();

        Hdb rdb = new Hdb();
        int ret = rdb.open("localhost", 22135, "root", "root1234");
        System.out.println("open result = " + ret);


        String[] num = tableName.split("\\_");
        int year = Integer.parseInt(num[2]);
        int month = Integer.parseInt(num[3]);

        int b = rdb.toUTC(year, month, 1, 0, 0, 0);

//        int b = rdb.toUTC(year ,month,1,0,0,0);

        if (month == 12) {
            year = year + 1;
            month = 0;
        }
        int c = rdb.toUTC(year, month + 1, 1, 0, 0, 0);

        if(mode == 1) {
            //bool值
            BoolDataListHolder dataList = new BoolDataListHolder();
            ret = rdb.readBoolHisDataById(id,b,c,0,dataList);
            System.out.println(ret);
            BoolData[] data1 = dataList.value;
            System.out.println(data1.length);
            mysql.insertBoolValue(data1, tableName, id, newTableName);
        }else {

            //float值
            //需多加一位差值模式  线性插值的值为0
            FloatDataListHolder dataList = new FloatDataListHolder();
            ret = rdb.readFloatHisDataById(id, b, c, 0, 0, dataList);
            System.out.println(ret);
            FloatData[] data1 = dataList.value;
            System.out.println(data1.length);


            //需要改成BoolData
            mysql.insertFloatValue(data1, tableName, id, newTableName);
        }

        rdb.close();


    }

}
