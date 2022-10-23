package elderlysitter.capstone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "user")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    @Column(name = "Full_name")
    private String fullName;

    private LocalDate dob;

    private String gender;

    private String phone;

    private String address;

    @Column(unique=true)
    private String email;

    @Column(name = "create_date")
    private LocalDate createDate;


    @JoinColumn(name = "status_id")
    @ManyToOne( fetch = FetchType.EAGER)
    private Status status;

    @JsonIgnore
    @JoinColumn (name = "role_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Elder> elders;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<UserImg> userImgs;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;

//    @JsonIgnore
//    @OneToMany(mappedBy = "sitter")
//    private List<Booking> _bookings;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private SitterProfile sitterProfile;
}
