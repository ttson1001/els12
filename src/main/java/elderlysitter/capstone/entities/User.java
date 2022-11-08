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

    @Column(name = "font_id_img_url")
    private String frontIdImgUrl;

    @Column(name = "back_id_img_url")
    private String backIdImgUrl;

    @Column(name = "avatar_img_url")
    private String avatarImgUrl;

    @Column(name = "create_date")
    private LocalDate createDate;

    private String status;

    @JsonIgnore
    @JoinColumn (name = "role_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Elder> elders;


    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;


    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private SitterProfile sitterProfile;
}
