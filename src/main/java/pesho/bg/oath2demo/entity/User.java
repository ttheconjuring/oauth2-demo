package pesho.bg.oath2demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Basic
    private String name;

    @Basic
    private String password;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Column(unique = true, nullable = false, name = "provider_id")
    private Integer providerId;

    public User(String email, String name, AuthProvider authProvider, Integer providerId) {
        setEmail(email);
        setName(name);
        setAuthProvider(authProvider);
        setProviderId(providerId);
    }

}
