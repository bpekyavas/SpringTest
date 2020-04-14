package jp.co.axa.springtest.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Size(min = 3, max = 20)
    private String name;

    @NonNull
    private String department;

}