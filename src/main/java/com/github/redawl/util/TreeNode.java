package com.github.redawl.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class TreeNode<T> {
    private final T data;
    private TreeNode<T> leftNode;
    private TreeNode<T> rightNode;

    final List<OutOfOrderData<T>> outOfOrderDataList = new ArrayList<>();

    public TreeNode(T data){
        this.data = data;
    }

    public T getData(){
        return this.data;
    }

    public void setLeftNode(TreeNode<T> leftNode){
        this.leftNode = leftNode;
    }

    public void setRightNode(TreeNode<T> rightNode){
        this.rightNode = rightNode;
    }

    public Optional<TreeNode<T>> getLeftNode(){
        return Optional.ofNullable(leftNode);
    }

    public Optional<TreeNode<T>> getRightNode(){
        return Optional.ofNullable(rightNode);
    }

    public void insert(T currentData, T leftData, T rightData){
        if(!insertHelper(this, currentData, leftData, rightData)){
            outOfOrderDataList.add(new OutOfOrderData<>(currentData, leftData, rightData));
        } else {
            List<OutOfOrderData<T>> toRemove = new ArrayList<>();

            for(OutOfOrderData<T> data: outOfOrderDataList){
                if(insertHelper(this, data.currentData, data.leftData, data.rightData)){
                   toRemove.add(data);
                }
            }

            for(OutOfOrderData<T> remove: toRemove){
                outOfOrderDataList.remove(remove);
            }
        }
    }

    private static <T> boolean insertHelper(TreeNode<T> node, T currentData, T leftData, T rightData){
        if(node.getData().equals(currentData)){
            TreeNode<T> leftNode = new TreeNode<>(leftData);
            node.setLeftNode(leftNode);

            TreeNode<T> rightNode = new TreeNode<>(rightData);
            node.setRightNode(rightNode);
            return true;
        } else {
            if(node.getLeftNode().isEmpty() && node.getRightNode().isEmpty()){
                return false;
            }

            AtomicBoolean left = new AtomicBoolean(false);
            AtomicBoolean right = new AtomicBoolean(false);

            node.getRightNode().ifPresent(rNode -> right.set(insertHelper(rNode, currentData, leftData, rightData)));
            node.getLeftNode().ifPresent(lNode -> left.set(insertHelper(lNode, currentData, leftData, rightData)));

            return left.get() || right.get();
        }
    }

    public boolean isClean(){
        return outOfOrderDataList.isEmpty();
    }

    private static class OutOfOrderData<T>{
        T currentData;
        T leftData;
        T rightData;

        public OutOfOrderData(T currentData, T leftData, T rightData) {
            this.currentData = currentData;
            this.leftData = leftData;
            this.rightData = rightData;
        }
    }
}
