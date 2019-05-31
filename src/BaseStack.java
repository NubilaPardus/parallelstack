/**
 * This interface that contains base stack functions 
 * 
 * @author NubilaPardus
 * @name BaseStack
 * @interface
 */
public interface BaseStack<T>{
        public T pop() throws Exception, InterruptedException;
        public void push(T value) throws InterruptedException;
}
