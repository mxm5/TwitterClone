package Repository;

import Base.Repository.Repository;
import Domain.User;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserRepository extends Repository<User, Long> {
    @Override
    public Class<User> getType() {
        return User.class;
    }

    public User login(User user) {

        String userName = user.getUserName();
        String password = user.getPassword();
        String userClass = getType().getSimpleName();
        TypedQuery<User> query = getEntityManager().createQuery(
                "select u FROM User u WHERE u.userName = '" + userName + "' " +
                        "AND u.password = '" + password + "' "
                , getType());

        return query.getSingleResult();
    }

    @Override
    public void safeRemove(User user) {
        user.cleanAssociations();
        save(user);
        delete(user);
    }

    public List<User> findUserByUserName(String query) {
        TypedQuery<User> nativeQuery = (TypedQuery<User>) getEntityManager().createNativeQuery("SELECT * FROM twitter_clone.User where User.user_name like '%" + query + "%';", User.class);
        return nativeQuery.getResultList();

    }
}
