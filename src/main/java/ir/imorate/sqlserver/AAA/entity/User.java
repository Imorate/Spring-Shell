package ir.imorate.sqlserver.AAA.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "user_account")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @Email
    private String email;

    private String firstName;

    private String lastName;

    private String lastIP;
}
