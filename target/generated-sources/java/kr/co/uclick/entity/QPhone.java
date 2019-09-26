package kr.co.uclick.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPhone is a Querydsl query type for Phone
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPhone extends EntityPathBase<Phone> {

    private static final long serialVersionUID = 1718498115L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPhone phone = new QPhone("phone");

    public final StringPath agency = createString("agency");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath number = createString("number");

    public final QUser user;

    public QPhone(String variable) {
        this(Phone.class, forVariable(variable), INITS);
    }

    public QPhone(Path<? extends Phone> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPhone(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPhone(PathMetadata metadata, PathInits inits) {
        this(Phone.class, metadata, inits);
    }

    public QPhone(Class<? extends Phone> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

