package com.kiraly.todolist.mappers;

public interface Mapper<E, D>
{
     D mapTo(E e);

     E mapFrom(D d);
}
