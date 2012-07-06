/**
 * [Copyright]
 * @author oujinliang
 * Apr 18, 2012 7:53:04 PM
 */
package org.jinou.test

/**
 * 
 给定一个二叉树， 每个节点都是字符串。
    查询第一个（父节点优先, 左子节点优先）包含某个字符串 (a) 的节点
    查询第一个包含 字符串 a 和 b 的节点
    查询第一个包含 字符串 a 或 b 的节点
    查询第一个包含 字符串 a 和 b 和 c 的节点
    查询第一个不包含 字符串 a 和 b 的节点
    查询第一个包含 字符串 a 和 b ，但不包含 c 的节点
    其他可能的要求 查询第一个。。。
 设计API 提供以上功能并实现， 要求： 调用方简单好用， 程序结构清晰。

 */
 
// ===

/** A tree node.
 */ 
class Node[A](val value: A, left: Node[A], right: Node[A]) {
    def this(value: A) = this(value, null, null)
    
    def find(pred: Predicate[A]): Node[A] = find(pred.pred)
    def find(pred: A => Boolean): Node[A] = {
        if (pred(value)) {
            return this
        }
        val foundLeft = if (left != null) left.find(pred) else null
        if (foundLeft == null && right != null) right.find(pred) else foundLeft
    }
    override def toString = if (value == null) "null" else value.toString
}

class Predicate[A] (val pred: A => Boolean) {
    require(pred != null)

    def &&(that: Predicate[A]): Predicate[A] = new Predicate(value => pred(value) && that.pred(value))
    def ||(that: Predicate[A]): Predicate[A] = new Predicate(value => pred(value) || that.pred(value))
    def unary_!()             : Predicate[A] = new Predicate(value => !pred(value))
}

/**
 * The main test method 
 */
object Test {
    val tree = new Node("com", 
                   new Node("xiaomi", 
                        new Node("hello", 
                             new Node("world"), 
                             new Node("good")),
                        new Node("abc", 
                             new Node("service"), 
                             new Node("perf"))), 
                   new Node("miliao", 
                        new Node("mt", 
                             new Node("userconnect"), 
                             new Node("userprofile")),
                        new Node("web", 
                             new Node("api"), 
                             new Node("miliaoapi") )))

    def main(args: Array[String]) {
        val contains = (value: String) => new Predicate((node: String)=> node != null && node.contains(value)) 

        // Which one is better? passing a function or a predicate object?
        
        // option 1: a function.
        tree.find(it => it.contains("llo") && it.contains("xiao"))
        
        // option 2: a predicate object.
        tree.find(contains("llo") && contains("xiao"))
        
        tree.find(it => it.contains("user") && it.contains("o") && !it.contains("nect"))
        tree.find(contains("user") && contains("o") && !contains("nect"))
        
        
        // compose different expressions.
        validate("hello",  contains("llo"))
        validate("api",    contains("api"))
        validate("miliao", contains("mi") && !contains("xiao"))
        validate("userprofile", contains("user") && contains("o") && !contains("nect"))
    }
    
    def validate(expected: String, pred: Predicate[String]) {
        val actual = tree.find(pred)
        if (actual.value == expected) 
            println ("PASS  :")
        else 
            println ("FAILED: Actual: %s, Expected %s" format (actual, expected))
    }
}
