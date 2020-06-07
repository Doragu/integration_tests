--this script initiates db for h2 db (used in test profile)
insert into user (account_status, email, first_name, last_name) values ('CONFIRMED', 'confirm_test_user@domain.com', 'John', 'Steward')
insert into user (account_status, email, first_name) values ('NEW', 'new_test_user@domain.com', 'Brian', 'Wazowski')
insert into user (account_status, email, first_name) values ('REMOVED', 'removed_test_user@domain.com', 'Edd', 'Milton')
