set global validate_password.length=4;
set global validate_password.policy=LOW;

drop user if exists 'tdpay'@'localhost';
create user if not exists 'tdpay'@'localhost' identified with mysql_native_password by 'tdpay123';

drop user if exists 'tdpay'@'%';
create user if not exists 'tdpay'@'%' identified with mysql_native_password by 'tdpay123';

drop database if exists `tdpay`;
create database `tdpay`;

grant all on `tdpay`.* to 'tdpay'@'localhost';
grant all on `tdpay`.* to 'tdpay'@'%';
