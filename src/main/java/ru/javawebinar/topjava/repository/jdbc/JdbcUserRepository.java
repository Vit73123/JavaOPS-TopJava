package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.validation.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    private final Validator validator;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    @Override
    @Transactional
    public User save(User user) {
        Set<ConstraintViolation<User>> validation = validator.validate(user);
        if (validation.size() > 0) {
            throw new ConstraintViolationException(validation);
        }

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else {
            if (namedParameterJdbcTemplate.update("""
                       UPDATE users SET name=:name, email=:email, password=:password, 
                       registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                    """, parameterSource) == 0) {
                return null;
            }
            jdbcTemplate.update("DELETE FROM user_role WHERE user_id=?", user.id());
        }
        Iterator<Role> iterator = user.getRoles().iterator();
        jdbcTemplate.batchUpdate(
                "INSERT INTO user_role (user_id, role) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, user.id());
                        ps.setString(2, iterator.next().name());
                    }

                    @Override
                    public int getBatchSize() {
                        return user.getRoles().size();
                    }
                });
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        return DataAccessUtils.singleResult(jdbcTemplate.query(
                "SELECT * FROM users u LEFT JOIN user_role r ON u.id = r.user_id WHERE u.id=?",
                new UserExtractor(), id));
    }

    @Override
    public User getByEmail(String email) {
        Set<ConstraintViolation<Class <User>>> validation = validator.validateProperty(User.class, "email");
        if (validation.size() > 0) {
            throw new ConstraintViolationException(validation);
        }

        return DataAccessUtils.singleResult(jdbcTemplate.query(
                "SELECT * FROM users u LEFT JOIN user_role r ON u.id = r.user_id WHERE u.email=?",
                new UserExtractor(), email));
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(
                "SELECT * FROM users u LEFT JOIN user_role r ON u.id = r.user_id ORDER BY u.name, u.email",
                new UserExtractor());
    }

    private static class UserExtractor implements ResultSetExtractor<List<User>> {

        @Override
        public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<User> users = new ArrayList<>();
            Map<Integer, User> usersMap = new HashMap<>();
            while (rs.next()) {
                int userId = rs.getInt("id");
                User user = usersMap.get(userId);
                if (user == null) {
                    user = new User(
                            userId,
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getInt("calories_per_day"),
                            rs.getBoolean("enabled"),
                            rs.getDate("registered"),
                            new HashSet<>()
                    );
                    usersMap.put(userId, user);
                    users.add(user);
                }
                String role = rs.getString("role");
                if (role != null) {
                    user.getRoles().add(Role.valueOf(role));
                }
            }
            return users;
        }
    }
}
