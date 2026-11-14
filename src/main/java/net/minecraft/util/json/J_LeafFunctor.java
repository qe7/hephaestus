package net.minecraft.util.json;

public abstract class J_LeafFunctor implements J_Functor {

    public J_LeafFunctor() {
    }

    public final Object func_27059_b(Object obj) {
        if (!func_27058_a(obj)) {
            throw J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException.func_27322_a(this);
        } else {
            return func_27063_c(obj);
        }
    }

    public abstract Object func_27063_c(Object obj);
}
