package com.example.msharp;

public abstract class Expression extends AST {
    public abstract void execute() throws Exception;
    public abstract int type();
    public abstract int resultInt();
    public abstract String resultBool();
    public abstract String resultString();
}
