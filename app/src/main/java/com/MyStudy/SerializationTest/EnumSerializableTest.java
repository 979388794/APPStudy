package com.MyStudy.SerializationTest;
enum Num1{
    ONE,TWO,THREE;

    public void printValues(){
        System.out.println("ONE: "+ ONE.ordinal() + ", TWO: " + TWO.ordinal() + ", THREE: " + THREE.ordinal());
    }
}

/**
 * Java的序列化机制针对枚举类型是特殊处理的。简单来讲，在序列化枚举类型时，只会存储枚举类的引用和枚举常量的名称。随后的反序列化的过程中，
 * 这些信息被用来在运行时环境中查找存在的枚举类型对象。
 */
public class EnumSerializableTest {
//
    public static void main(String[] args) throws Exception {
        byte[] bs =SerializeableUtils.serialize(Num1.THREE);
        Num1.THREE.printValues();
        System.out.println("hashCode: " + Num1.THREE.hashCode());
        System.out.println("反序列化后");
        Num1 s1 = SerializeableUtils.deserialize(bs);
        s1.printValues();
        System.out.println("hashCode: " + s1.hashCode());
        System.out.println("== " + (Num1.THREE == s1));
    }

}
