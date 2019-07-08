set foreign_key_checks = 0;

set @DUMMY='dummy';
set @DUMMY_ID=0;

delete from `role` where `CREATED_BY` = @DUMMY;
delete from `role_authority` where `CREATED_BY` = @DUMMY;
delete from `user` where `CREATED_BY` = @DUMMY;
delete from `users_group` where `CREATED_BY` = @DUMMY;
delete from `user_authority` where `CREATED_BY` = @DUMMY;
delete from `login_history` where `CREATED_BY` = @DUMMY;
delete from `system_property` where `CREATED_BY` = @DUMMY;

set foreign_key_checks = 1;
