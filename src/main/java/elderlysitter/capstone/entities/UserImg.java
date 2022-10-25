package elderlysitter.capstone.entities;

import lombok.*;

import javax.persistence.*;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "user_img")
public class UserImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "font_id_img_url")
    private String fontIdImgUrl;
    @Column(name = "back_id_img_url")
    private String backIdImgUrl;
    @Column(name = "avatar_img_url")
    private String avatarImgUrl;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}
