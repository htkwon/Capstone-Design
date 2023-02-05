package com.hansung.hansungcommunity.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFreeArticle is a Querydsl query type for FreeArticle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFreeArticle extends EntityPathBase<FreeArticle> {

    private static final long serialVersionUID = -1237758743L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFreeArticle freeArticle = new QFreeArticle("freeArticle");

    public final NumberPath<Integer> bookmarkHits = createNumber("bookmarkHits", Integer.class);

    public final StringPath content = createString("content");

    public final NumberPath<Integer> hits = createNumber("hits", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> postDate = createDateTime("postDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> report = createNumber("report", Integer.class);

    public final StringPath title = createString("title");

    public final QUser user;

    public QFreeArticle(String variable) {
        this(FreeArticle.class, forVariable(variable), INITS);
    }

    public QFreeArticle(Path<? extends FreeArticle> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFreeArticle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFreeArticle(PathMetadata metadata, PathInits inits) {
        this(FreeArticle.class, metadata, inits);
    }

    public QFreeArticle(Class<? extends FreeArticle> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

