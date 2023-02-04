package com.hansung.hansungcommunity.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQnaArticle is a Querydsl query type for QnaArticle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQnaArticle extends EntityPathBase<QnaArticle> {

    private static final long serialVersionUID = -1731282285L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQnaArticle qnaArticle = new QQnaArticle("qnaArticle");

    public final QAuditingFields _super = new QAuditingFields(this);

    public final NumberPath<Integer> bookmarkHits = createNumber("bookmarkHits", Integer.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> hits = createNumber("hits", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final NumberPath<Integer> report = createNumber("report", Integer.class);

    public final StringPath tag = createString("tag");

    public final StringPath title = createString("title");

    public final QUser user;

    public QQnaArticle(String variable) {
        this(QnaArticle.class, forVariable(variable), INITS);
    }

    public QQnaArticle(Path<? extends QnaArticle> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQnaArticle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQnaArticle(PathMetadata metadata, PathInits inits) {
        this(QnaArticle.class, metadata, inits);
    }

    public QQnaArticle(Class<? extends QnaArticle> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

