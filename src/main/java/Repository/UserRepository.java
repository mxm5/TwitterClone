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

    public User findUserNamePasswordInDb(User user) {
        User singleResult = null;
        String userName = user.getUserName();
        String password = user.getPassword();

        TypedQuery<User> query = getEntityManager().createQuery(
                "select u FROM User u WHERE u.userName = '" + userName + "' " +
                        "AND u.password = '" + password + "' "
                , getType());

        try {

            singleResult = query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return singleResult;
    }

    public List<User> searchUserByUserNames(String query) {
        TypedQuery<User> nativeQuery = (TypedQuery<User>) getEntityManager().createNativeQuery("SELECT * FROM User where User.user_name like '%" + query + "%';", User.class);
        return nativeQuery.getResultList();

    }
}
