package br.com.oresteslucas.backend.user;

import java.util.UUID;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_users")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(length = 80, nullable = false)
    private String name;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setName(String name) throws Exception{
        if(name.length() > 80){
            throw new Exception("O campo name ter mais de 80 caracteres.");
        }
        this.name = name;
    }
    
}