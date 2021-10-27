package repository;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

import domain.UserDomain;

@Service
public class UserRepository{

    @Autowired
   public MongoTemplate mongoTemplate;



    public List<UserDomain> users(){
        Query query = new Query();
        return mongoTemplate
                .find(query,UserDomain.class,UserDomain.COLLECTION_NAME);
        }


    public UserDomain createUser(UserDomain userDomain) {
         mongoTemplate.insert(userDomain, UserDomain.COLLECTION_NAME);
         return userDomain;
    }

    public UserDomain findUser(String userId) {
        Query query = new Query(where(UserDomain.ID_FIELD).is(userId));
        return (UserDomain) mongoTemplate.findOne(query, UserDomain.class, UserDomain.COLLECTION_NAME);
    }

    public UserDomain updateUser(String userId, UserDomain userDomain) {
        Query query = new Query(where(UserDomain.ID_FIELD).is(userId));
        Update update = new Update().set(UserDomain.FIRST_NAME, userDomain.getFirstName())
                .set(UserDomain.LAST_NAME, userDomain.getLastName())
                .set(UserDomain.LATITUDE, userDomain.getLatitude())
                .set(UserDomain.LONGITUDE, userDomain.getLongitude());
        return (UserDomain) mongoTemplate
                .findAndModify(query, update, options().returnNew(true), UserDomain.class);
    }

    public void deleteUser(String userId) {
        Query query = query(where(UserDomain.ID_FIELD).in(userId));
        mongoTemplate.remove(query, UserDomain.class);
    }
}
