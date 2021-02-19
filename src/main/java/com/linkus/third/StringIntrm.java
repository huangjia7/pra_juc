package com.linkus.third;

/**
 * 对string 中intern的理解
 */
public class StringIntrm {

    public static void main(String[] args) {
        String aa = new StringBuilder().append("mei").append("tuan").toString();
        System.out.println(aa);
        System.out.println(aa.intern());
        System.out.println(aa == aa.intern());

        System.out.println("=========");
        
        String bb = new StringBuilder().append("j").append("ava").toString();
        System.out.println(bb);
        System.out.println(bb.intern());
        System.out.println(bb == bb.intern());
    }
}
