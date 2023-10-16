package br.com.oresteslucas.backend.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String description;

    @Column(length = 40)
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    @Column(length = 15)
    private String priority;

    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setTitle(String title) throws Exception{
        if(title.length() > 40){
            throw new Exception("O campo title deve conter no máximo 40 caracteres.");
        }
        this.title = title;
    }

    public void setPriority(String priority) throws Exception{
        if(priority.length() > 15){
            throw new Exception("O campo priority deve conter no máximo 10 caracteres.");
        }
        this.priority = priority;
    }

}