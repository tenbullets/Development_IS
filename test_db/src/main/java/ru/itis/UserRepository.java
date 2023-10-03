package ru.itis;

import java.util.List;

public interface UserRepository <T> {
    List<T> findAllByAge(Integer age);
    boolean findUserByName(String name);
}
