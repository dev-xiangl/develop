set global validate_password.length=4;
set global validate_password.policy=LOW;

drop database if exists `tdpay_test`;
create database `tdpay_test`;

grant all on `tdpay_test`.* to 'tdpay'@'localhost';
grant all on `tdpay_test`.* to 'tdpay'@'%';
