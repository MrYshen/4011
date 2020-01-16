package edu.hust.Dao;

public class UserDao {

    BaseDao baseDao = new BaseDao();

    //编写写入数据的mysql语句
    public boolean insertMore(String sql_suffix,long flag,String newTableName,int id) {
        String sql_prefix = "insert into "+newTableName+"(t,v,f"+") values ";
        System.out.println(sql_prefix + sql_suffix);

        return baseDao.writeToMysql(sql_prefix + sql_suffix, flag,id);
    }

    //获取时间的字符串
    public String timeString(int a){

        int month = a%12;
        int year = a/12+2017;
        if(month == 0){
            month = 12;
            year--;
        }
        String timeString1 = new String("_"+year+"_"+month);
        return timeString1;
    }
}
