// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.util.json;


// Referenced classes of package net.minecraft.src:
//            J_JsonNodeBuilder, J_JsonStringNode, J_JsonNode

public final class J_JsonFieldBuilder
{

    private J_JsonFieldBuilder()
    {
    }

    public static J_JsonFieldBuilder func_27301_a()
    {
        return new J_JsonFieldBuilder();
    }

    public J_JsonFieldBuilder func_27304_a(J_JsonNodeBuilder j_jsonnodebuilder)
    {
        field_27306_a = j_jsonnodebuilder;
        return this;
    }

    public J_JsonFieldBuilder func_27300_b(J_JsonNodeBuilder j_jsonnodebuilder)
    {
        field_27305_b = j_jsonnodebuilder;
        return this;
    }

    public J_JsonStringNode func_27303_b()
    {
        return (J_JsonStringNode)field_27306_a.func_27234_b();
    }

    public J_JsonNode func_27302_c()
    {
        return field_27305_b.func_27234_b();
    }

    private J_JsonNodeBuilder field_27306_a;
    private J_JsonNodeBuilder field_27305_b;
}
