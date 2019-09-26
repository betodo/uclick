package kr.co.uclick.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1884068010L;

    public static final QUser user = new QUser("user");

    public final StringPath dept = createString("dept");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath jobPosition = createString("jobPosition");

    public final StringPath name = createString("name");

    public final CollectionPath<Phone, QPhone> phones = this.<Phone, QPhone>createCollection("phones", Phone.class, QPhone.class, PathInits.DIRECT2);

    public final DateTimePath<java.util.Date> regDate = createDateTime("regDate", java.util.Date.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

