package com.hansung.hansungcommunity.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1737651028L;

    public static final QUser user = new QUser("user");

    public final StringPath career = createString("career");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath introduce = createString("introduce");

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final ListPath<FreeArticle, QFreeArticle> postFreeArticles = this.<FreeArticle, QFreeArticle>createList("postFreeArticles", FreeArticle.class, QFreeArticle.class, PathInits.DIRECT2);

    public final ListPath<QnaArticle, QQnaArticle> postQnaArticles = this.<QnaArticle, QQnaArticle>createList("postQnaArticles", QnaArticle.class, QQnaArticle.class, PathInits.DIRECT2);

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

