import material.Position;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * This class represents a tree data structure using a linked implementation.
 * It implements the NAryTree interface.
 *
 * @param <E> the type of element stored in the tree
 */
public class LinkedTree<E> implements NAryTree<E> {

    private TreeNode<E> root;
    private int size;

    private class TreeNode<T> implements Position<T> {
        //Atributos
        private TreeNode<T>  parent;
        private T element;
        private List<TreeNode<T>> children;

        public TreeNode(T e, TreeNode<T> parent, List<TreeNode<T>> c){
            this.element = e;
            this.parent = parent;
            this.children = c;
        }
        @Override
        public T getElement() {
            return element;
        }

        public TreeNode(T e){
            this.element = e;
        }

        //Métodos varios que usar


        public TreeNode<T> getParent() {
            return parent;
        }

        public void setParent(TreeNode<T> parent) {
            this.parent = parent;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public List<TreeNode<T>> getChildren() {
            return children;
        }

        public void setChildren(List<TreeNode<T>> children) {
            this.children = children;
        }
    }

    @Override
    public Position<E> addRoot(E e) {
        if (!isEmpty()){
            throw new RuntimeException("Ya existe una raíz");
        }
        root = new TreeNode<E>(e, null, new ArrayList<TreeNode<E>>());
        size++;
        return root;
    }

    @Override
    public Position<E> add(E element, Position<E> p) {
        TreeNode<E> parent = checkposition(p);
        TreeNode<E> newNode = new TreeNode<E>(element, parent, new ArrayList<TreeNode<E>>());
        List<TreeNode<E>> l = parent.getChildren();
        l.add(newNode);
        return newNode;

    }

    private TreeNode<E> checkposition(Position<E> p){
        if ((p == null) || !(p instanceof TreeNode)){
            throw new RuntimeException("Posicion invalida");
        }
        TreeNode<E> aux = (TreeNode<E>) p;
        return aux;
    }

    @Override
    public Position<E> add(E element, Position<E> p, int n) {
       TreeNode<E> parent = checkposition(p);
       TreeNode<E> newNode = new TreeNode<E>(element, parent, new ArrayList<TreeNode<E>>());
       if (n>0 || n > parent.getChildren().size()){
           throw new RuntimeException("La posicion donde quiere insertarlo no existe");
       }
       List<TreeNode<E>> l = parent.getChildren();
       l.add(newNode);
       return newNode;
    }

    @Override
    public void swapElements(Position<E> p1, Position<E> p2) {
        TreeNode<E> node1 = checkposition(p1);
        TreeNode<E> node2 = checkposition(p2);
        E aux = node2.getElement();
        node2.setElement(p1.getElement());
        node1.setElement(p2.getElement());
    }

    @Override
    public E replace(Position<E> p, E e) {
        TreeNode<E> node = checkposition(p);
        E aux = node.getElement();
        node.setElement(e);
        return aux;
    }

    @Override
    public void remove(Position<E> p) {
        TreeNode<E> node = checkposition(p);
        if (isRoot(node)){
            root = null;
            size = 0;
        }
        TreeNode<E> parent = node.getParent();
        parent.getChildren().remove(node);
        size -= NumNodos(node);
    }

    private int NumNodos(TreeNode<E> n){
        int size = 1;
        for (TreeNode<E> child : n.getChildren()){
            size += NumNodos(child);
        }
        return size;
    }

    @Override
    public NAryTree<E> subTree(Position<E> v) {
        TreeNode<E> node = checkposition(v);
        LinkedTree<E> subTree = new LinkedTree<>();
        this.root = node;
        subTree.size = NumNodos(node);
        return subTree;
    }

    @Override
    //Añadir un arbol a otro
    public void attach(Position<E> p, NAryTree<E> t) {
        TreeNode<E> node = checkposition(p);
        LinkedTree<E> tree = checkTree(t);
        node.getChildren().addAll(tree.root.getChildren());
        size += tree.size;
    }

    private LinkedTree<E> checkTree(NAryTree<E> t){
        if (!(t instanceof LinkedTree)){
            throw new RuntimeException("El arbol es inválido");
        }
        return (LinkedTree<E>) t;
    }

    @Override
    public boolean isEmpty() {
        return (root == null);
    }

    @Override
    public Position<E> root() {
        if (root == null){
            throw new RuntimeException("No existe raiz");
        }
        return root;
    }

    @Override
    public Position<E> parent(Position<E> v) {
        TreeNode<E> node = checkposition(v);
        if (isRoot(node)){
            throw new RuntimeException("Es raiz y no tiene padre");
        }
        return node.getParent();
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        TreeNode<E> node = checkposition(v);
        return node.getChildren();
    }

    @Override
    public boolean isInternal(Position<E> v) {
        TreeNode<E> node = checkposition(v);
        return (!(isLeaf(node)));
    }

    @Override
    public boolean isLeaf(Position<E> v) {
        TreeNode<E> node = checkposition(v);
        return (node.getChildren().isEmpty());
    }

    @Override
    public boolean isRoot(Position<E> v) {
        TreeNode<E> node = checkposition(v);
        return (node.getParent() == null);
    }

    @Override
    public Iterator<Position<E>> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int size() {
       return size;
    }
}
