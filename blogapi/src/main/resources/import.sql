--this script initiates db for h2 db (used in test profile)
insert into user (id, account_status, email, first_name, last_name) values (1, 'CONFIRMED', 'confirm_test_user@domain.com', 'John', 'Steward')
insert into user (id, account_status, email, first_name, last_name) values (2, 'NEW', 'new_test_user@domain.com', 'Brian', 'Wazowski')
insert into user (id, account_status, email, first_name, last_name) values (3, 'REMOVED', 'removed_test_user@domain.com', 'Edd', 'Milton')
insert into user (id, account_status, email, first_name, last_name) values (4, 'CONFIRMED', 'confirm2_test_user@domain.com', 'Jack', 'Sparrow')
insert into user (id, account_status, email, first_name, last_name) values (5, 'CONFIRMED', 'confirm3_test_user@domain.com', 'Clark', 'Kent')

insert into blog_post (id, entry, user_id) values (1, 'test post', 1)
insert into blog_post (id, entry, user_id) values (2, 'test post2', 1)
insert into blog_post (id, entry, user_id) values (3, 'test post3', 1)
insert into blog_post (id, entry, user_id) values (4, 'test post4', 4)
insert into blog_post (id, entry, user_id) values (5, 'test post4', 3)

insert into like_post (id, post_id, user_id) values (1, 4, 1)

