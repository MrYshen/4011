package edu.hust.screen;
import hdb.Hdb;
import hdb.IndexTag;
import hdb.IndexTagListHolder;

import java.util.*;
public class Screen {

    /**
     * 返回Lis对象
     * 对象包含id,tag两个信息
     * 删除tag中“备用”字眼
     */

    public static List<IndexTag> deleteTag(){

        Hdb rdb =new Hdb();
        int ret = rdb.open("localhost", 22135, "root", "root1234");
        System.out.println("open result = "+ret);

        IndexTagListHolder paraList = new IndexTagListHolder();

        int idList[] = new int[4658];
        for (int a1 = 1;a1 <= 4658;a1++){
            idList[a1-1] = a1;
        }
        System.out.println("获取标签的状态为"+rdb.floatTags(idList, paraList));

        IndexTag[] a = paraList.value;


        List<IndexTag> indexTagArrayList = new ArrayList<IndexTag>();
        for(int i= 0;i<a.length;i++){
            String a1 = a[i].tag;
            if(a1.contains("备用")){
                continue;
            }
            indexTagArrayList.add(a[i]);
        }

        rdb.close();

        return indexTagArrayList;
    }

    public LinkedHashMap<String,List<IndexTag>> judge(IndexTag[] a){

        Set<String> set = new HashSet<String>();
        List<IndexTag> indexTagArrayList = new ArrayList<IndexTag>();
        LinkedHashMap<String,List<IndexTag>> map = new LinkedHashMap<String, List<IndexTag>>();
        for (int c = 0;c < a.length;c++) {
            String tagStr = a[c].tag;
            if(tagStr.contains("备用"))
                continue;
            String[] tagStrs = tagStr.split("\\.");
            set.add(tagStrs[2]);
            indexTagArrayList.add(a[c]);
        }
        List<String> list = new ArrayList<String>(set);
        for(int m = 0;m<list.size();m++) {
            List<IndexTag> mList = new ArrayList<IndexTag>();
            for (int i = 0; i < indexTagArrayList.size(); i++) {
                String str = indexTagArrayList.get(i).tag;
                String[] strs = str.split("\\.");
                if (list.get(m).equals(strs[2]) ) {
                    mList.add(indexTagArrayList.get(i));
                }
            }
            map.put(list.get(m), mList);
        }
        return map;
    }

    public LinkedHashMap<String,List<IndexTag>> four(LinkedHashMap<String,List<IndexTag>> map, String a_1){

        LinkedHashMap<String,List<IndexTag>> map1 = new LinkedHashMap<String, List<IndexTag>>();
        Set<String> set = new HashSet<String>();
        if(!map.containsKey(a_1))
            return null;
        List<IndexTag> list_01 = map.get(a_1);
        for (int c = 0;c < list_01.size();c++) {
            String tagStr = list_01.get(c).tag;
            String[] tagStrs = tagStr.split("\\.");
            set.add(tagStrs[3]);
        }
        List<String> list = new ArrayList<String>(set);
        for(int m = 0;m<list.size();m++) {
            List<IndexTag> mList = new ArrayList<IndexTag>();
            for (int i = 0; i < list_01.size(); i++) {
                String str = list_01.get(i).tag;
                String[] strs = str.split("\\.");
                if (list.get(m).equals(strs[3]) ) {
                    mList.add(list_01.get(i));
                }
            }
            map1.put(list.get(m), mList);
        }
        return map1;

    }

}
