package com.mindhub.todolist.repositories;

import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.services.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    //@Query("select t from Task t where t.id=?1 and t.userEntity.id=?2")
    //Task findTaskByIdAndUser(Long id, Long userId);

    Task findByUserEntityAndId(UserEntity user, Long id);
}
