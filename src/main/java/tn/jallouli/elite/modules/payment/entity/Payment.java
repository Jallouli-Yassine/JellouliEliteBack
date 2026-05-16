package tn.jallouli.elite.modules.payment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tn.jallouli.elite.modules.course.entity.Course;
import tn.jallouli.elite.modules.user.entity.UserEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "payments")
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private Double amount;
    private String provider;
    private String status;
    private LocalDateTime paidAt = LocalDateTime.now();
    private String transactionId;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private Course course;




}
