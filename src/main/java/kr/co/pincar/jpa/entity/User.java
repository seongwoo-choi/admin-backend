package kr.co.pincar.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@DynamicInsert
@Entity
public class User extends BaseEntity implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(length = 100, nullable = false)
    private String password;

    // 역할 (admin, staff)
    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String jandi_id;

    // 상태
    @Column(nullable = false, columnDefinition = "varchar(20) default 'enter'")
    private String status ;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void changeStatus(String status) {
        this.status = status;
    }
}
