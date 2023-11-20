package com.learn.universities

object Fibonacci {


    fun Fibonacci_series(n:Int):Long
    {
        var total=1L
        var a=0L
        var b=1L
        if(n==0||n==1)
        {
            return n.toLong()
        }
        (1..n-1).forEach{i->
            total = a+b
            a=b
            b=total
        }
        return total
    }
}