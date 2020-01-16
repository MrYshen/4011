package edu.hust.mysql;

import edu.hust.Dao.BaseDao;
import edu.hust.Dao.UserDao;
import hdb.BoolData;
import hdb.FloatData;
import hdb.IndexTag;
import java.util.List;


public class Mysql {

    //获取创建表的语句
    public String getboolTblSQL(String tableName) {

        String boolsql = new String("create table if not exists " + tableName + "(t int not null primary key,"
            +"v int not null ,f int not null"+") ENGINE=INNODB DEFAULT CHARSET=utf8;");

        return boolsql;
    }

    public String getfloatTblSQL(String tableName) {

        String floatsql = new String("create table if not exists " + tableName + "(t int not null primary key,"
                +"v float not null ,f int not null"+") ENGINE=INNODB DEFAULT CHARSET=utf8;");

        return floatsql;
    }



    public void insertFloatValue(FloatData[] floatData, String tableName, int id, String newTableName){

        UserDao userDao = new UserDao();
        StringBuffer suffix = new StringBuffer();

        for ( int i = 0;i<floatData.length;i++) {
//            if (floatData[i].flag == 0)
//                continue;
            suffix.append("(" + floatData[i].tm + "," + floatData[i].val + ","+floatData[i].flag+ "),");
            if(suffix.length()>1000000){
                String suffix1 = suffix.substring(0, suffix.length()-1);
                boolean b = userDao.insertMore(suffix1+";", i,newTableName,id);
                if(!b) {
                    System.out.println("出错了----");
                    System.exit(0);
                }else {
                    // 清空上一次添加的数据
                    suffix = new StringBuffer();
                }
            }
        }
        String suffix1 = suffix.substring(0, suffix.length()-1);

        boolean b = userDao.insertMore(suffix1+";", 1,newTableName,id);

        if(!b) {
            System.out.println("出错了----");
            System.exit(0);
        }else {
            // 清空上一次添加的数据
            suffix = new StringBuffer();
        }
    }

    public void insertBoolValue(BoolData[] boolData, String tableName, int id, String newTableName){

        UserDao userDao = new UserDao();
        StringBuffer suffix = new StringBuffer();

        for ( int i = 0;i<boolData.length;i++) {
//            if (boolData[i].flag == 0)
//                continue;
            suffix.append("(" + boolData[i].tm + "," + boolData[i].val + ","+boolData[i].flag+ "),");
            if(suffix.length()>1000000){
                String suffix1 = suffix.substring(0, suffix.length()-1);
                boolean b = userDao.insertMore(suffix1+";", i,newTableName,id);
                if(!b) {
                    System.out.println("出错了----");
                    System.exit(0);
                }else {
                    // 清空上一次添加的数据
                    suffix = new StringBuffer();
                }
            }
        }
        String suffix1 = suffix.substring(0, suffix.length()-1);

        boolean b = userDao.insertMore(suffix1+";", 1,newTableName,id);

        if(!b) {
            System.out.println("出错了----");
            System.exit(0);
        }else {
            // 清空上一次添加的数据
            suffix = new StringBuffer();
        }
    }


}