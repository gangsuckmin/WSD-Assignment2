create table users
(
    id         int unsigned auto_increment primary key,
    role       enum ('user', 'admin') default 'user'            not null,
    nickname   varchar(50)                                      not null,
    login_id   varchar(50)                                      not null,
    password   varchar(255)                                     not null,
    name       varchar(50)                                      not null,
    gender     enum ('male', 'female')                          not null,
    birth_date date                                             not null,
    address    varchar(255)                                     not null,
    phone      varchar(50)                                      null,
    email      varchar(50)                                      null,
    created_at datetime               default CURRENT_TIMESTAMP not null,
    updated_at datetime               default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    deleted_at datetime                                         null,
    constraint uk_user_login_id
        unique (login_id),
    constraint uk_user_nickname
        unique (nickname)
) collate = utf8mb4_unicode_ci; create index idx_user_created on users (created_at);

create table authors
(
    id         int unsigned auto_increment
        primary key,
    name       varchar(50)                        null,
    country    varchar(100)                       not null,
    gender     enum ('male', 'female')            not null,
    birth_date date                               not null,
    created_at datetime default CURRENT_TIMESTAMP not null,
    updated_at datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP
) collate = utf8mb4_unicode_ci; create index idx_author_country on authors (country); create index idx_author_name on authors (name);

create table categories
(
    id         int unsigned auto_increment
        primary key,
    name       varchar(50)                        not null,
    parent_id  int unsigned                           null,
    depth      int unsigned default 0                 not null,
    created_at datetime default CURRENT_TIMESTAMP not null,
    updated_at datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint uk_category_name
        unique (name),
    constraint fk_category_parent
        foreign key (parent_id) references categories (id)
            on update cascade on delete set null
) collate = utf8mb4_unicode_ci; create index idx_category_parent on categories (parent_id);

create table books
(
    id             int unsigned auto_increment
        primary key,
    isbn13         varchar(13)                             not null,
    title          varchar(255)                            not null,
    publisher      varchar(50)                             not null,
    published_date date                                    not null,
    price          int unsigned                            not null,
    currency       varchar(3)    default 'KRW'             not null,
    stock          int unsigned  default 0                 not null,
    like_count     int unsigned  default 0                 not null,
    review_count   int unsigned  default 0                 not null,
    sales_count    int unsigned  default 0                 not null,
    avg_rating     decimal(3, 2) default 0.00              not null,
    created_at     datetime      default CURRENT_TIMESTAMP not null,
    updated_at     datetime      default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    deleted_at     datetime                                null,
    constraint uk_book_isbn
        unique (isbn13)
) collate = utf8mb4_unicode_ci; create index idx_book_like on books (like_count); create index idx_book_price on books (price);
create index idx_book_published on books (published_date); create index idx_book_rating on books (avg_rating);
create index idx_book_review on books (review_count); create index idx_book_title on books (title);

create table books_authors
(
    book_id   int unsigned not null,
    author_id int unsigned not null,
    primary key (book_id, author_id),
    constraint fk_book_author_author
        foreign key (author_id) references authors (id)
            on update cascade,
    constraint fk_book_author_book
        foreign key (book_id) references books (id)
            on update cascade on delete cascade
) collate = utf8mb4_unicode_ci; create index idx_book_author_author on books_authors (author_id);

create table books_categories
(
    book_id     int unsigned not null,
    category_id int unsigned not null,
    primary key (book_id, category_id),
    constraint fk_book_category_book
        foreign key (book_id) references books (id)
            on update cascade on delete cascade,
    constraint fk_book_category_category
        foreign key (category_id) references categories (id)
            on update cascade
) collate = utf8mb4_unicode_ci; create index idx_book_category_category on books_categories (category_id);

create table reviews
(
    id            int unsigned auto_increment
        primary key,
    user_id       int unsigned                           not null,
    book_id       int unsigned                           not null,
    rating        int unsigned                           not null,
    content       text                                   not null,
    like_count    int unsigned default 0                 not null,
    comment_count int unsigned default 0                 not null,
    created_at    datetime     default CURRENT_TIMESTAMP not null,
    updated_at    datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    deleted_at    datetime                               null,
    constraint uk_reviews_user_book
        unique (user_id, book_id),
    constraint fk_reviews_books
        foreign key (book_id) references books (id)
            on update cascade,
    constraint fk_reviews_users
        foreign key (user_id) references users (id)
            on update cascade,
    constraint chk_reviews_rating
        check (`rating` between 1 and 5)
) collate = utf8mb4_unicode_ci; create index idx_reviews_comment on reviews (book_id, comment_count); create index idx_reviews_created on reviews (book_id, created_at);
create index idx_reviews_like on reviews (book_id, like_count); create index idx_reviews_user on reviews (user_id);

create table comments
(
    id         int unsigned auto_increment
        primary key,
    review_id  int unsigned                           not null,
    user_id    int unsigned                           not null,
    content    varchar(255)                           not null,
    like_count int unsigned default 0                 not null,
    created_at datetime     default CURRENT_TIMESTAMP not null,
    updated_at datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    deleted_at datetime                               null,
    constraint fk_comment_review
        foreign key (review_id) references reviews (id)
            on update cascade on delete cascade,
    constraint fk_comment_user
        foreign key (user_id) references users (id)
            on update cascade
) collate = utf8mb4_unicode_ci; create index idx_comment_review_created on comments (review_id, created_at); create index idx_comment_user on comments (user_id);

create table favorites
(
    id         int unsigned auto_increment
        primary key,
    user_id    int unsigned                       not null,
    book_id    int unsigned                       not null,
    updated_at datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint uk_favorite_user_book
        unique (user_id, book_id),
    constraint fk_favorite_book
        foreign key (book_id) references books (id)
            on update cascade on delete cascade,
    constraint fk_favorite_user
        foreign key (user_id) references users (id)
            on update cascade on delete cascade
) collate = utf8mb4_unicode_ci; create index idx_favorite_book on favorites (book_id);

create table review_likes
(
    user_id    int unsigned                       not null,
    review_id  int unsigned                       not null,
    updated_at datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    primary key (user_id, review_id),
    constraint fk_review_like_review
        foreign key (review_id) references reviews (id)
            on update cascade on delete cascade,
    constraint fk_review_like_user
        foreign key (user_id) references users (id)
            on update cascade on delete cascade
) collate = utf8mb4_unicode_ci; create index idx_review_like_review on review_likes (review_id);

create table comment_likes
(
    user_id    int unsigned                       not null,
    comment_id int unsigned                       not null,
    updated_at datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    primary key (user_id, comment_id),
    constraint fk_comment_like_comment
        foreign key (comment_id) references comments (id)
            on update cascade on delete cascade,
    constraint fk_comment_like_user
        foreign key (user_id) references users (id)
            on update cascade on delete cascade
) collate = utf8mb4_unicode_ci; create index idx_comment_like_comment on comment_likes (comment_id);

create table carts
(
    id      int unsigned auto_increment
        primary key,
    user_id int unsigned not null,
    constraint uk_cart_user
        unique (user_id),
    constraint fk_carts_user
        foreign key (user_id) references users (id)
            on update cascade on delete cascade
) collate = utf8mb4_unicode_ci;

create table cart_items
(
    id         int unsigned auto_increment
        primary key,
    cart_id    int unsigned                           not null,
    book_id    int unsigned                           not null,
    quantity   int unsigned default 1                 not null,
    price      int unsigned                           not null,
    created_at datetime     default CURRENT_TIMESTAMP not null,
    constraint uk_cart_item_cart_book
        unique (cart_id, book_id),
    constraint fk_cart_item_book
        foreign key (book_id) references books (id)
            on update cascade,
    constraint fk_cart_item_cart
        foreign key (cart_id) references carts (id)
            on update cascade on delete cascade,
    constraint chk_cart_item_quantity
        check (`quantity` >= 1)
) collate = utf8mb4_unicode_ci; create index idx_cart_item_book on cart_items (book_id);
create index idx_cart_item_cart on cart_items (cart_id); create index idx_cart_item_created on cart_items (created_at);

create table orders
(
    id             int unsigned auto_increment
        primary key,
    user_id        int unsigned                                                                                                      not null,
    number         varchar(50)                                                                                                       not null,
    status         enum ('pending', 'paid', 'processing', 'shipped', 'completed', 'cancelled', 'refunded') default 'pending'         not null,
    payment_status enum ('unpaid', 'paid', 'refunded')                                                     default 'unpaid'          not null,
    subtotal       int unsigned                                                                                                      not null,
    shipping       int unsigned                                                                            default 0                 not null,
    discount       int unsigned                                                                            default 0                 not null,
    total          int unsigned                                                                                                      not null,
    currency       varchar(3)                                                                              default 'KRW'             not null,
    created_at     datetime                                                                                default CURRENT_TIMESTAMP not null,
    updated_at     datetime                                                                                default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    paid_at        datetime                                                                                                          null,
    cancelled_at   datetime                                                                                                          null,
    refunded_at    datetime                                                                                                          null,
    constraint uk_orders_number
        unique (number),
    constraint fk_orders_users
        foreign key (user_id) references users (id)
            on update cascade,
    constraint chk_orders_paid_at
        check (((`payment_status` = 'unpaid') and (`paid_at` is null)) or ((`payment_status` in ('paid','refunded')) and (`paid_at` is not null))),
    constraint chk_orders_refunded_at
        check (((`payment_status` = 'refunded') and (`refunded_at` is not null)) or ((`payment_status` in ('unpaid','paid')) and (`refunded_at` is null)))
) collate=utf8mb4_unicode_ci; create index idx_orders_created_at on orders (created_at); create index idx_orders_payment_status on orders (payment_status);
create index idx_orders_status on orders (status); create index idx_orders_user_id on orders (user_id);

create table order_items
(
    id         int unsigned auto_increment
        primary key,
    order_id   int unsigned             not null,
    book_id    int unsigned             not null,
    item_title varchar(255) not null,
    item_price int unsigned             not null,
    quantity   int unsigned default 1 not null,
    constraint fk_order_item_book
        foreign key (book_id) references books (id)
            on update cascade,
    constraint fk_order_item_order
        foreign key (order_id) references orders (id)
            on update cascade on delete cascade,
    constraint chk_order_item_quantity
        check (`quantity` >= 1)
) collate = utf8mb4_unicode_ci; create index idx_order_item_book on order_items (book_id); create index idx_order_item_order on order_items (order_id);

create table tokens
(
    id         int unsigned auto_increment
        primary key,
    user_id    int unsigned                        not null,
    type       enum ('refresh', 'reset', 'verify') not null,
    token_hash char(64)                            not null,
    user_agent varchar(255)                        null,
    ip_address varchar(45)                         null,
    expires_at datetime                            not null,
    created_at datetime default CURRENT_TIMESTAMP  not null,
    revoked_at datetime                            null,
    constraint uk_token_type_hash
        unique (type, token_hash),
    constraint fk_token_user
        foreign key (user_id) references users (id)
            on update cascade on delete cascade
) collate = utf8mb4_unicode_ci; create index idx_token_expires on tokens (expires_at); create index idx_token_user on tokens (user_id);


