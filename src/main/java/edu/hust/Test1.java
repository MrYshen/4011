package edu.hust;

import edu.hust.Dao.BaseDao;
import edu.hust.Dao.UserDao;
import edu.hust.data.Data;
import edu.hust.mysql.Mysql;

public class Test1 {
    public static void main(String[] args) {


        int[] booleans = {819, 1624, 1625, 1635, 1638, 1640, 1642, 1649, 1786};
//        int[] floats = {31,34,93, 97, 94, 98, 102, 106, 99, 95, 101, 105, 107, 49};
//        int[] floats = {101,	105,	107,	49,97,	93,	31,};
        int[] floats = {22,	23,	25,	26,	27,	28,	29,	30,	85,	86,	87,	88,	89,	100,	103,	104,	108,	109,	159,
                160,	161,	431,	432,	553,	554,	555,	556,	557,	558,	559,	560,	561,
                562,	563,	564,	565,	566,	567,	568,	569,	570,	571,	572,	573,	574,
                575,	576,	577,	578,	579,	580,	581,	582,	583,	584,	585,	586,	587,
                588,	589,	590,	591,	592,	593,	594,	596,	597,	598,	599,	600,	601,
                602,	603,	604,	605,	606,	608,	609,	610,	611,	612,	614,	616,	617,
                618,	619,	620,	622,	623,	624,	625,	626,	627,	628,	629,	630,	631,
                632,	633,	634,	635,	636,	637,	638,	639,	640,	641,	642,	643,	644,
                646,	647,	648,	649,	650,	651,	652,	653,	654,	656,	657,	658,	659,
                660,	662,	663,	664,	665,	666,	667,	668,	669,	670,	671,	672,	673,
                674,	675,	677,	678,	680,	683,	684,	685,	686,
        };
//        int[] floats={4};


        /**
         * 更改bool值，float值步骤
         * 1.更改数据库名
         */

        //写入bool值
//        int m = 0;
//        for (int a = 0; a < booleans.length; a++) {
//            m++;
//            BaseDao baseDao1 = new BaseDao();
//            if(baseDao1.writeDataIfExist(1,booleans[a],"bool")) {
//                System.out.println("有重复的点，不写入");
//                continue;
//            }
//            for (int i = 4; i < 30; i++) {
//                Mysql mysql1 = new Mysql();
//                BaseDao baseDao = new BaseDao();
//                Data data = new Data();
//                UserDao userDao = new UserDao();
//                String timeString = userDao.timeString(i);
//                String tableName = new String("bool_" + booleans[a] + timeString);
////                //改表名
////                String sql = alter_booltable(booleans[a],tableName);
////                System.out.println(sql);
////                if(sql!=null){
////                    baseDao.affectRowMore1(sql,1);
////                }
//                //更改表名
//                String newTableName = newboolTableName(booleans[a],tableName);
//                //创建bool表
//                String sql = mysql1.getboolTblSQL(newTableName);
//                baseDao.writeToMysql(sql, -1,1);
//                //写入数据
//                data.writeData(tableName, booleans[a],newTableName,1);
//                System.out.println("正在写第" + m + "个测点");
//            }
//        }

            //写入float值
            int m1 = 0;
            for (int a1 = 0; a1 < floats.length; a1++) {
                m1++;
                BaseDao baseDao1 = new BaseDao();
                if(baseDao1.writeDataIfExist(1,floats[a1],"float")) {
                    System.out.println("有重复的点，不写入");
                    continue;
                }
                for (int i = 4; i < 30; i++) {
                    Mysql mysql1 = new Mysql();
                    BaseDao baseDao = new BaseDao();
                    Data data = new Data();
                    UserDao userDao = new UserDao();
                    String timeString = userDao.timeString(i);
                    String tableName1 = new String("float_" + floats[a1] + timeString);
                    //更改表名
                    String newTableName = newfloatTableName(floats[a1],tableName1);
                    //创建float表
                    String sql = mysql1.getfloatTblSQL(newTableName);
//                    String sql = deleteTable(newTableName);
                    baseDao.writeToMysql(sql, -1,1);
                    //写入数据
                    data.writeData(tableName1, floats[a1],newTableName,0);

                    System.out.println("正在写第" + m1 + "个测点");
                }
            }
    }
    public static String alter_booltable(int id ,String tableName){

        String[] num = tableName.split("\\_");
        int year = Integer.parseInt(num[2]);
        int month = Integer.parseInt(num[3]);
        if(month <10){
            String newTableName = Test1.alter_booltable(id, tableName);
            String sql ="alter table "+tableName+" rename to "+newTableName+";";
            return sql;
        }

        return null;
    }

    public static String alter_floattable(int id ,String tableName ){

        String[] num = tableName.split("\\_");
        int year = Integer.parseInt(num[2]);
        int month = Integer.parseInt(num[3]);
        if(month <10){
            String newTableName = Test1.alter_floattable(id, tableName);
            String sql ="alter table "+tableName+" rename to "+newTableName+";";
            return sql;
        }

        return null;
    }

    public static String newfloatTableName(int id ,String tableName){

        String[] num = tableName.split("\\_");
        int year = Integer.parseInt(num[2]);
        int month = Integer.parseInt(num[3]);

        if(month<10)
            return "float_"+id+"_"+year+"_"+"0"+month;
        return tableName;
    }

    public static String newboolTableName(int id ,String tableName){

        String[] num = tableName.split("\\_");
        int year = Integer.parseInt(num[2]);
        int month = Integer.parseInt(num[3]);
        if(month<10)
            return "bool_"+id+"_"+year+"_"+"0"+month;
        return tableName;
    }


    public static String deleteTable(String tableName){

        String sql = "DROP TABLE "+tableName+";";
        System.out.println(sql);
        return sql;
    }
}
