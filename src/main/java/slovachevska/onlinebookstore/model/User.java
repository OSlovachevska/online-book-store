package slovachevska.onlinebookstore.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE users SET is_Deleted = true WHERE id=?")
@Where(clause = "is_Deleted = true")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String shippingAddress;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
